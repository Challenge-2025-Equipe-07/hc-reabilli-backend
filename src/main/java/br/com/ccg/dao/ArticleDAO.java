package br.com.ccg.dao;

import br.com.ccg.dto.ArticleDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class ArticleDAO {

    @Inject
    DataSource dataSource;

    public Set<ArticleDTO> getArticlesByName(String search) {
        return new HashSet<>();
    }

    public ArticleDTO getArticleById(String id) {
        return new ArticleDTO();
    }

    public void updateArticle(String id, ArticleDTO updateObject){

    }

    public void deleteArticle(String id){

    }
}
