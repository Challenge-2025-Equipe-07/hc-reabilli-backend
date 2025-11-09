package br.com.ccg.hc.reabilli.resource;

import br.com.ccg.hc.reabilli.model.Article;
import br.com.ccg.hc.reabilli.service.ArticleService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.Optional;

@Path("/article")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {


    private static final String BASE_URI = "http://localhost:8080/";
    @Inject
    ArticleService articleService;

    @GET
    public Response getArticles() {
        return Response.ok().entity(articleService.getArticles()).build();
    }

    @GET
    @Path("/{id}")
    public Response getArticleById(@PathParam("id") int id) {
        return Response.ok().entity(articleService.getArticleById(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateArticle(@PathParam("id") int id, Article article) {
        return Response.ok().entity(articleService.updateArticle(id, article)).build();
    }

    @POST
    public Response postArticle(Article article) {
        Optional<Article> postedArticle;
        postedArticle = articleService.postArticle(article);
        return Response.created(URI.create(BASE_URI + "/article/" + postedArticle.get().getArticleId()))
                .entity(postedArticle)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteArticle(@PathParam("id") int id) {
        articleService.deleteArticle(id);
        return Response.noContent().build();
    }
}
