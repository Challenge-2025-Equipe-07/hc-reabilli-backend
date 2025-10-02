package br.com.ccg.resource;

import br.com.ccg.model.Article;
import br.com.ccg.service.ArticleService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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

    @GET
    public Set<Article> getArticles() {
        return articleService.getArticles();
    }

    @GET
    @Path("/{id}")
    public Optional<Article> getArticleById(@PathParam("id") String id) {
        return articleService.getArticleById(id);
    }

    @PUT
    @Path("/{id}")
    public void updateArticle(@PathParam("id") String id, Article article) {
        articleService.updateArticle(id, article);
    }

    @POST
    public void postArticle(Article article) {
        articleService.postArticle(article);
    }

    @DELETE
    @Path("/{id}")
    public void deleteArticle(@PathParam("id") String id) {
        articleService.deleteArticle(id);
    }
}
