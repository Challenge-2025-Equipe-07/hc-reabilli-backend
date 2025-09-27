package br.com.ccg.resource;

import br.com.ccg.dto.ArticleDTO;
import br.com.ccg.service.ArticleService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/article")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {

    @Inject
    ArticleService articleService;

    @GET
    public ArticleDTO getArticle(@QueryParam("search") String search){
        return articleService.likeSearchUsingName();
    }
}
