package br.com.ccg.resource;

import br.com.ccg.dao.ArticleDAO;
import br.com.ccg.dto.ArticleDTO;
import br.com.ccg.service.ArticleService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Set;

@Path("/article")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {

    @Inject
    ArticleService articleService;

    @GET
    public Set<ArticleDTO> getArticlesByName(@QueryParam("searchParam") String search){
        return articleService.getArticlesByName(search);
    }

    @GET
    @Path("/{id}")
    public ArticleDTO getArticleById(@PathParam("id") String id){
        return articleService.getArticleById(id);
    }
    @PUT
    @Path("/{id}/update")
    public void updateArticle(@PathParam("id") String id, ArticleDTO articleDTO){
        articleService.updateArticle(id, articleDTO);
    }
    @POST
    public void postArticle(ArticleDTO articleDTO){
        System.out.println(articleDTO);
    }
    @DELETE
    @Path("/{id}/delete")
    public void deleteArticle(@PathParam("id") String id){
        articleService.deleteArticle(id);
    }
}
