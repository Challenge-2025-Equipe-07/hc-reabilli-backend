package br.com.ccg.dao;

import br.com.ccg.model.Article;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
@RequiredArgsConstructor
public class ArticleDAO {

    private static final String SELECT_ALL_ARTICLES = "SELECT * FROM T_CCG_ARTICLE ORDER BY ID_ARTICLE ASC";
    private static final String SELECT_BY_ID = "SELECT * FROM T_CCG_ARTICLE WHERE ID_ARTICLE= ?";
    public static final String INSERT_ARTICLE = "INSERT INTO t_ccg_article (id_article, nm_article, t_ccg_user_id_user) values (?,?,?)";
    public static final String INSERT_RELATED = "INSERT INTO T_CCG_RELATED (ID_RELATED, DS_TYPE, DS_URL, DS_CONTENT, T_CCG_ARTICLE_ID_ARTICLE, ID_USER) values (?,?,?,?,?,?)";
    public static final String UPDATE_ARTICLE = "UPDATE T_CCG_ARTICLE SET NM_ARTICLE = ?, T_CCG_USER_ID_USER= ? WHERE ID_ARTICLE = ?";
    public static final String DELETE_ARTICLE = "DELETE FROM T_CCG_ARTICLE WHERE ID_ARTICLE = ?";

    private Logger logger = Logger.getLogger(ArticleDAO.class);

    public Set<Article> getArticles() {
        Set<Article> articles = new HashSet<>();

        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SELECT_ALL_ARTICLES)) {

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
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setInt(1, Integer.parseInt(id));

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Article article = Article.builder()
                            .articleId(rs.getInt("ID_ARTICLE"))
                            .name(rs.getString("NM_ARTICLE"))
                            .userId(rs.getInt("T_CCG_USER_ID_USER"))
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
        try (Connection connection = ConnectionFactory.getConnection();
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
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ARTICLE)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public void postArticle(Article dto) {

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ARTICLE);
             PreparedStatement ps = connection.prepareStatement(INSERT_RELATED)) {
            statement.setInt(1, dto.getArticleId());
            statement.setString(2, dto.getName());
            statement.setInt(3, dto.getUserId());
            statement.executeUpdate();
            if(Objects.nonNull(dto.getRelated())) {
                dto.getRelated().forEach(relatedDTO -> {
                    try {
                        ps.setInt(1, relatedDTO.getId());
                        ps.setString(2, relatedDTO.getType().toUpperCase());
                        ps.setString(3, relatedDTO.getUrl());
                        ps.setString(4, relatedDTO.getContent());
                        ps.setInt(5, dto.getArticleId());
                        ps.setInt(6, relatedDTO.getUserId());
                        ps.addBatch();
                    } catch (SQLException e) {
                        logger.error("Error when sending related Object: " + e.getMessage());
                    }
                });
            }
            ps.executeBatch();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
