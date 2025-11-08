package br.com.ccg.hc.reabilli.dao;

import br.com.ccg.hc.reabilli.model.Article;
import br.com.ccg.hc.reabilli.model.Related;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class ArticleRepository implements PanacheRepository<Article> {

    @Inject
    EntityManager entityManager;

    @Inject
    RelatedRepository relatedRepository;

    public Optional<Set<Article>> findAllWithRelated() {
        return Optional.of(find("SELECT a FROM Article a LEFT JOIN FETCH a.related ORDER BY a.articleId ASC")
                .stream()
                .collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    public Optional<Article> findByIdWithRelated(int id) {
        return find("SELECT a FROM Article a LEFT JOIN FETCH a.related WHERE a.articleId = ?1", id)
                .firstResultOptional();
    }

    @Transactional
    public Optional<Article> updateArticleWithRelateds(int id, Article updatedArticle) {
        Optional<Article> existingOpt = findByIdWithRelated(id);
        if (existingOpt.isEmpty()) {
            throw new NotFoundException("Article with id " + id + " does not exist");
        }

        Article existing = existingOpt.get();

        existing.setName(updatedArticle.getName());
        existing.setUserId(updatedArticle.getUserId());

        if (updatedArticle.getRelated() != null) {
            Set<Related> updatedRelateds = new HashSet<>(updatedArticle.getRelated());

            for (Related rel : updatedRelateds) {
                rel.setArticle(existing);
                if (rel.getId() == 0) {
                    existing.getRelated().add(rel);
                } else {
                    existing.getRelated().stream()
                            .filter(r -> r.getId() == rel.getId())
                            .findFirst()
                            .ifPresentOrElse(existingRel -> {
                                existingRel.setType(rel.getType());
                                existingRel.setUrl(rel.getUrl());
                                existingRel.setContent(rel.getContent());
                                existingRel.setUserId(rel.getUserId());
                            }, () -> existing.getRelated().add(rel));
                }
            }
        }

        persist(existing);
        return Optional.of(existing);
    }

    @Transactional
    public void deleteArticleAndRelateds(int articleId) {
        Article article = findById((long) articleId);
        if (article != null) {
            delete(article);
        }
    }

    @Transactional
    public Article post(Article article) {
        try {
            // 1. Insere Article
            String insertArticleSQL =
                    "INSERT INTO T_CCG_ARTICLE (ID_ARTICLE, NM_ARTICLE, T_CCG_USER_ID_USER) " +
                            "VALUES (SEQ_T_CCG_ARTICLE.NEXTVAL, :name, :userId)";

            getEntityManager().createNativeQuery(insertArticleSQL)
                    .setParameter("name", article.getName())
                    .setParameter("userId", article.getUserId())
                    .executeUpdate();

            // 2. Pega o ID gerado
            BigDecimal articleIdBigDecimal = (BigDecimal) getEntityManager()
                    .createNativeQuery("SELECT SEQ_T_CCG_ARTICLE.CURRVAL FROM DUAL")
                    .getSingleResult();

            Integer articleId = articleIdBigDecimal.intValue();
            article.setArticleId(articleId);
            System.out.println("✅ Article inserido com ID: " + articleId);

            // 3. Verifica se o Article realmente existe
            BigDecimal count = (BigDecimal) getEntityManager()
                    .createNativeQuery("SELECT COUNT(*) FROM T_CCG_ARTICLE WHERE ID_ARTICLE = :id")
                    .setParameter("id", articleId)
                    .getSingleResult();

            System.out.println("✅ Article existe no banco: " + (count.intValue() > 0));

            // 4. Insere os Related
            if (article.getRelated() != null && !article.getRelated().isEmpty()) {
                String insertRelatedSQL =
                        "INSERT INTO T_CCG_RELATED (ID_RELATED, T_CCG_ARTICLE_ID_ARTICLE, DS_TYPE, DS_URL, DS_CONTENT, ID_USER) " +
                                "VALUES (SEQ_T_CCG_RELATED.NEXTVAL, :articleId, :type, :url, :content, :userId)";

                for (Related related : article.getRelated()) {
                    System.out.println("📝 Inserindo Related para Article ID: " + articleId);

                    getEntityManager().createNativeQuery(insertRelatedSQL)
                            .setParameter("articleId", articleId)
                            .setParameter("type", related.getType())
                            .setParameter("url", related.getUrl())
                            .setParameter("content", related.getContent())
                            .setParameter("userId", related.getUserId())
                            .executeUpdate();
                }
                System.out.println("✅ Todos os Related inseridos com sucesso!");
            }

            return article;

        } catch (Exception e) {
            System.err.println("❌ Erro ao persistir: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao persistir: " + e.getMessage(), e);
        }
    }
}
