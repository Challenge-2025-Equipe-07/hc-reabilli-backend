package br.com.ccg.resource;

import br.com.ccg.dto.LoginDTO;
import br.com.ccg.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import static br.com.ccg.resource.LoginResource.PATH;

@ApplicationScoped
@Path(PATH)
public class LoginResource {

    static final String PATH = "/login";
    @Inject
    UserService userService;

    @POST
    public boolean login(LoginDTO loginDTO){
        return userService.getUserByUsername(loginDTO);
    }

}
