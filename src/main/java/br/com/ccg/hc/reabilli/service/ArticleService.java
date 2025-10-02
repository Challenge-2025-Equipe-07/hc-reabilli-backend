package br.com.ccg.hc.reabilli.service;

import br.com.ccg.hc.reabilli.dao.ArticleDAO;
import br.com.ccg.hc.reabilli.model.Article;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class ArticleService {

    @Inject
    ArticleDAO articleDAO;

    public Set<Article> getArticles(){
        return articleDAO.getArticles();
    }

    public Optional<Article> getArticleById(String id){
        return articleDAO.getArticleById(id);
    }

    public void updateArticle(String id, Article dto){
        articleDAO.updateArticle(id, dto);
    }

    public void deleteArticle(String id){
        articleDAO.deleteArticle(id);
    }

    public void postArticle(Article article){
        articleDAO.postArticle(article);
    }
}
