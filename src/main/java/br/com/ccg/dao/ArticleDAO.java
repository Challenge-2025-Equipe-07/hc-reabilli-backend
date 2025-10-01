package br.com.ccg.dao;

import br.com.ccg.dto.ArticleDTO;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
@RequiredArgsConstructor
public class ArticleDAO {

    private static final String SELECT_ALL_ARTICLES = "SELECT * FROM T_CCG_ARTICLE";
    private static final String SELECT_BY_ID = "SELECT * FROM T_CCG_ARTICLE WHERE ID_ARTICLE= ?";
    public static final String INSERT_ARTICLE = "INSERT INTO t_ccg_article (id_article, nm_article, t_ccg_user_id_user) values ";
    public static final String UPDATE_ARTICLE = "UPDATE T_CCG_ARTICLE SET NM_ARTICLE = ?, T_CCG_USER_ID_USER= ? WHERE ID_ARTICLE = ?";
    public static final String DELETE_ARTICLE = "DELETE FROM T_CCG_ARTICLE WHERE ID_ARTICLE = ?";

    private Logger logger = Logger.getLogger(ArticleDAO.class);

    public Set<ArticleDTO> getArticles() {
        try {
            Connection connection = ConnectionFactory.getConnection();
            ResultSet rs = connection.createStatement().executeQuery(SELECT_ALL_ARTICLES);
            Set<ArticleDTO> articleSet = new HashSet<>();
            while (rs.next()) {
                articleSet.add(ArticleDTO.builder()
                        .articleId(rs.getInt("ID_ARTICLE"))
                        .name(rs.getString("NM_ARTICLE"))
                        .userId(rs.getInt("T_CCG_USER_ID_USER"))
                        .build());
            }
            connection.close();
            return articleSet;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return Set.of();
        }
    }

    public ArticleDTO getArticleById(String id) {
        ArticleDTO returnedDto = ArticleDTO.builder().build();
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1,Integer.parseInt(id));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                returnedDto = ArticleDTO
                        .builder()
                        .articleId(rs.getInt("ID_ARTICLE"))
                        .name(rs.getString("NM_ARTICLE"))
                        .userId(rs.getInt("T_CCG_USER_ID_USER"))
                        .build();
            }
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return returnedDto;
    }

    public void updateArticle(String id, ArticleDTO dto) {
        try{
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ARTICLE);
            statement.setString(1, dto.getName());
            statement.setInt(2, dto.getUserId());
            statement.setInt(3, Integer.parseInt(id));
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public void deleteArticle(String id) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ARTICLE);
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
    public void postArticle(ArticleDTO dto) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(INSERT_ARTICLE + "(" + dto.getArticleId() + ",'" + dto.getName() + "', " + dto.getUserId() + ")");
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
