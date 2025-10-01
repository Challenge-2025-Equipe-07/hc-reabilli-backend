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

    public Set<ArticleDTO> getArticles() {
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet countRs = statement.executeQuery("SELECT COUNT(*) FROM T_CCG_ARTICLE");
            if (countRs.next()) {
                int count = countRs.getInt(1); // Get the first column (COUNT(*)) value
                System.out.println("Number of articles: " + count);
            }ResultSet rs = statement.executeQuery("SELECT * FROM T_CCG_ARTICLE");
            Set<ArticleDTO> articleSet = new HashSet<>();
            while (rs.next()) {
                articleSet.add(new ArticleDTO(rs.getInt("ID_ARTICLE"), rs.getString("NM_ARTICLE"), null));
            }
            connection.close();
            return articleSet;
        } catch (SQLException e) {
            Logger.getLogger(ArticleDAO.class).error(e.getMessage());
        }
        return Set.of();
    }

    public ArticleDTO getArticleById(String id) {
        return new ArticleDTO();
    }

    public void updateArticle(String id, ArticleDTO dto) {

    }

    public void deleteArticle(String id) {

    }

    public void postArticle(ArticleDTO dto) {

    }

    private Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl", "RM562700", "090805");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
