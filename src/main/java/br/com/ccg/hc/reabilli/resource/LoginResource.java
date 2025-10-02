package br.com.ccg.hc.reabilli.resource;

import br.com.ccg.hc.reabilli.model.Login;
import br.com.ccg.hc.reabilli.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import static br.com.ccg.hc.reabilli.resource.LoginResource.PATH;

@ApplicationScoped
@Path(PATH)
public class LoginResource {

    static final String PATH = "/login";
    @Inject
    UserService userService;

    @POST
    public boolean login(Login loginDTO){
        return userService.getUserByUsername(loginDTO);
    }

}
