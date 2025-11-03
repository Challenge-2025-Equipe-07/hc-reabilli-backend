package br.com.ccg.hc.reabilli.dao;

import br.com.ccg.hc.reabilli.model.Article;
import br.com.ccg.hc.reabilli.model.Related;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@JBossLog
@ApplicationScoped
@RequiredArgsConstructor
public class ArticleDAO {

    private static final String SELECT_ALL_ARTICLES = "SELECT * FROM T_CCG_ARTICLE ORDER BY ID_ARTICLE ASC";
    private static final String GET_RELATED_BY_ARTICLE_ID = "SELECT * FROM T_CCG_RELATED WHERE T_CCG_ARTICLE_ID_ARTICLE = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM T_CCG_ARTICLE WHERE ID_ARTICLE= ?";
    public static final String INSERT_ARTICLE = "INSERT INTO t_ccg_article (id_article, nm_article, t_ccg_user_id_user) values (?,?,?)";
    public static final String INSERT_RELATED = "INSERT INTO T_CCG_RELATED (ID_RELATED, DS_TYPE, DS_URL, DS_CONTENT, T_CCG_ARTICLE_ID_ARTICLE, ID_USER) values (?,?,?,?,?,?)";
    public static final String UPDATE_ARTICLE = "UPDATE T_CCG_ARTICLE SET NM_ARTICLE = ?, T_CCG_USER_ID_USER= ? WHERE ID_ARTICLE = ?";
    public static final String DELETE_ARTICLE = "DELETE FROM T_CCG_ARTICLE WHERE ID_ARTICLE = ?";

    private final Logger logger = Logger.getLogger(ArticleDAO.class);

    @Inject
    DataSource datasource;

    public Set<Article> getArticles() {
        Set<Article> articles = new HashSet<>();

        try (Connection connection = datasource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SELECT_ALL_ARTICLES)
        ) {
            while (rs.next()) {
                articles.add(Article.builder()
                        .articleId(rs.getInt("ID_ARTICLE"))
                        .name(rs.getString("NM_ARTICLE"))
                        .userId(rs.getInt("T_CCG_USER_ID_USER"))
                        .build());
            }

        } catch (SQLException ex) {
            logger.error("Error fetching articles", ex);
            return Set.of();
        }
        return articles;
    }

    public Optional<Article> getArticleById(String id) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
             PreparedStatement relatedStatement = connection.prepareStatement(GET_RELATED_BY_ARTICLE_ID)
             ) {

            relatedStatement.setInt(1, Integer.parseInt(id));
            statement.setInt(1, Integer.parseInt(id));

            Set<Related> relatedSet = new HashSet<>();
            try(ResultSet relatedRs = relatedStatement.executeQuery()){
                while(relatedRs.next()){
                    relatedSet.add(Related.builder()
                            .articleId(Integer.parseInt(id))
                            .type(relatedRs.getString("DS_TYPE"))
                            .content(relatedRs.getString("DS_CONTENT"))
                            .id(relatedRs.getInt("ID_RELATED"))
                            .userId(relatedRs.getInt("ID_USER"))
                            .build());
                }
            }

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Article article = Article.builder()
                            .articleId(rs.getInt("ID_ARTICLE"))
                            .name(rs.getString("NM_ARTICLE"))
                            .userId(rs.getInt("T_CCG_USER_ID_USER"))
                            .related(relatedSet)
                            .build();
                    return Optional.of(article);
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching article with id={}", id, e);
        } catch (NumberFormatException e) {
            logger.warn("Invalid article id format: {}", id, e);
        }

        return Optional.empty();
    }

    public void updateArticle(String id, Article dto) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ARTICLE)) {
            statement.setString(1, dto.getName());
            statement.setInt(2, dto.getUserId());
            statement.setInt(3, Integer.parseInt(id));
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public void deleteArticle(String id) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ARTICLE)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public void postArticle(Article dto) {
        try (Connection connection = datasource.getConnection()) {
            connection.setAutoCommit(false);

            // Inserir Article
            try (PreparedStatement psArticle = connection.prepareStatement(
                    "INSERT INTO t_ccg_article (id_article, nm_article, t_ccg_user_id_user) VALUES (?, ?, ?)")) {
                psArticle.setInt(1, dto.getArticleId()); // ou recuperar ID gerado se for sequence
                psArticle.setString(2, dto.getName());
                psArticle.setInt(3, dto.getUserId());
                psArticle.executeUpdate();
            }

            // Inserir Related
            if (dto.getRelated() != null) {
                try (PreparedStatement psRelated = connection.prepareStatement(
                        "INSERT INTO T_CCG_RELATED (ID_RELATED, DS_TYPE, DS_URL, DS_CONTENT, T_CCG_ARTICLE_ID_ARTICLE, ID_USER) VALUES (?, ?, ?, ?, ?, ?)")) {
                    for (Related r : dto.getRelated()) {
                        psRelated.setInt(1, r.getId());
                        psRelated.setString(2, r.getType());
                        psRelated.setString(3, r.getUrl());
                        psRelated.setString(4, r.getContent());
                        psRelated.setInt(5, dto.getArticleId()); // mesmo ID do Article inserido
                        psRelated.setInt(6, r.getUserId());
                        psRelated.addBatch();
                    }
                    psRelated.executeBatch();
                }
            }

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
