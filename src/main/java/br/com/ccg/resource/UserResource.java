package br.com.ccg.resource;

import br.com.ccg.dto.UserDTO;
import br.com.ccg.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

@ApplicationScoped
@Path(UserResource.USERS_PATH)
public class UserResource {


    static final String USERS_PATH = "/user";

    @Inject
    UserService userService;

    @GET
    @Path("/{id}")
    public UserDTO getUser(@PathParam("id") String id){
        return userService.getUser(id);
    }

    @PUT
    @Path("/{id}")
    public void putUser(@PathParam("id")String id, UserDTO userDTO){
        userService.updateUser(id, userDTO);
    }

    @POST
    public void postUser(UserDTO userDTO){
        userService.postUser(userDTO);
    }

    @DELETE
    @Path("/{id}")
    public void deleteUser(@PathParam("id") String id){
        userService.deleteUser(id);
    }

}
