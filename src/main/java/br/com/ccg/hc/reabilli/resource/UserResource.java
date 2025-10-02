package br.com.ccg.hc.reabilli.resource;

import br.com.ccg.hc.reabilli.model.User;
import br.com.ccg.hc.reabilli.service.UserService;
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
    public User getUserById(@PathParam("id") String id){
        return userService.getUserById(id);
    }

    @PUT
    @Path("/{id}")
    public void updateUser(@PathParam("id")String id, User user){
        userService.updateUser(id, user);
    }

    @POST
    public void postUser(User user){
        userService.postUser(user);
    }

    @DELETE
    @Path("/{id}")
    public void deleteUser(@PathParam("id") String id){
        userService.deleteUser(id);
    }

}
