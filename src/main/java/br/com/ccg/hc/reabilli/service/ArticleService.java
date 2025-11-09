package br.com.ccg.hc.reabilli.service;

import br.com.ccg.hc.reabilli.dao.ArticleDAO;
import br.com.ccg.hc.reabilli.model.Article;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.NotFoundException;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class ArticleService {

    @Inject
    ArticleDAO articleRepository;

    public Set<Article> getArticles() {
        Optional<Set<Article>> articles = articleRepository.findAllWithRelated();
        if (articles.isPresent()) {
            return articles.get();
        } else {
            throw new NoSuchElementException("No articles found");
        }
    }

    public Article getArticleById(int id) {
        Optional<Article> article = articleRepository.findByIdWithRelated(id);
        if (article.isPresent()) {
            return article.get();
        } else {
            throw new NotFoundException("No articles found");
        }
    }

    public Optional<Article> updateArticle(int id, Article dto) {
        return articleRepository.updateArticleWithRelateds(id, dto);
    }

    public void deleteArticle(int id){
        articleRepository.deleteArticleAndRelateds(id);
    }

    public Optional<Article> postArticle(Article article){
        Optional<Article> postedArticle = articleRepository.postArticle(article);
        if(postedArticle.isPresent()){
            return postedArticle;
        } else {
            throw new PersistenceException("Error when persisting article: " + article);
        }
    }
}
