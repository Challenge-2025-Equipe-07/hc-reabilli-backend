package br.com.ccg.service;

import br.com.ccg.dao.ArticleDAO;
import br.com.ccg.dto.ArticleDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Set;

@ApplicationScoped
public class ArticleService {

    @Inject
    ArticleDAO articleDAO;

    public Set<ArticleDTO> getArticlesByName(String search){
        return articleDAO.getArticlesByName(search);
    }

    public ArticleDTO getArticleById(String id){
        return articleDAO.getArticleById(id);
    }
    public void updateArticle(String id, ArticleDTO dto){
        articleDAO.updateArticle(id, dto);
    }

    public void deleteArticle(String id){
        articleDAO.deleteArticle(id);
    }

    public void postArticle(ArticleDTO articleDTO) {
        System.out.println(articleDTO);
    }
}
