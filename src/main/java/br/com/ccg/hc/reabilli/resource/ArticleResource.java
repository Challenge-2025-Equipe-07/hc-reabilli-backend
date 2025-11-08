package br.com.ccg.hc.reabilli.resource;

import br.com.ccg.hc.reabilli.model.Article;
import br.com.ccg.hc.reabilli.service.ArticleService;
import br.com.ccg.hc.reabilli.service.RelatedService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Optional;
import java.util.Set;

@Path("/article")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {

    @Inject
    ArticleService articleService;

    @Inject
    RelatedService relatedService;
    @GET
    public Set<Article> getArticles() {
        return articleService.getArticles();
    }

    @GET
    @Path("/{id}")
    public Article getArticleById(@PathParam("id") int id) {
        return articleService.getArticleById(id);
    }

    @PUT
    @Path("/{id}")
    public void updateArticle(@PathParam("id") int id, Article article) {
        articleService.updateArticle(id, article);
    }

    @POST
    public Article postArticle(Article article) {
        Optional<Article> postedArticle = Optional.empty();
        try {
            postedArticle = articleService.postArticle(article);
        } finally {
            if (postedArticle.isPresent()) {
                relatedService.persist(article.getRelated());
                return postedArticle.get();
            } else  {
                throw new NotFoundException("Error when getting posted article: " + article);
            }
        }

    }

    @DELETE
    @Path("/{id}")
    public void deleteArticle(@PathParam("id") int id) {
        articleService.deleteArticle(id);
    }
}
