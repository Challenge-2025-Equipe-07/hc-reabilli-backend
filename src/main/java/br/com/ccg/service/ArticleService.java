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

    public Set<ArticleDTO> getArticles(){
        return articleDAO.getArticles();
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

    public void postArticle(ArticleDTO articleDTO){
        articleDTO.getRelated().forEach(relatedDTO -> {
            System.out.println(relatedDTO.getType().toString());
        });
//        articleDAO.postArticle(articleDTO);
    }
}
