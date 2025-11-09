package br.com.ccg.hc.reabilli.dao;

import br.com.ccg.hc.reabilli.model.Article;
import br.com.ccg.hc.reabilli.model.Related;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class ArticleDAO implements PanacheRepository<Article> {

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
    public Optional<Article> postArticle(Article article) {
        if (article.getRelated() != null) {
            for (Related rel : article.getRelated()) {
                rel.setArticle(article);
            }
        }
        persist(article);
        return findByIdWithRelated(article.getArticleId());
    }

}
