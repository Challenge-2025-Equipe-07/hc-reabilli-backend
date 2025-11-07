package br.com.ccg.hc.reabilli.service;

import br.com.ccg.hc.reabilli.dao.ArticleRepository;
import br.com.ccg.hc.reabilli.model.Article;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class ArticleService {

    @Inject
    ArticleRepository articleDAO;

    public Set<Article> getArticles(){
        Optional<Set<Article>> articles = articleDAO.findAllWithRelated();
        if(articles.isPresent()) {
            return articles.get();
        } else {
            throw new NoSuchElementException("No articles found");
        }
    }

    public Optional<Article> getArticleById(int id){
        return articleDAO.findByIdWithRelated(id);
    }
//
//    public void updateArticle(String id, Article dto){
//        articleDAO.updateArticle(id, dto);
//    }
//
//    public void deleteArticle(String id){
//        articleDAO.deleteArticle(id);
//    }
//
//    public void postArticle(Article article){
//        articleDAO.postArticle(article);
//    }
}
