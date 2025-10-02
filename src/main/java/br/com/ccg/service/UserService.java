package br.com.ccg.service;

import br.com.ccg.dao.UserDAO;
import br.com.ccg.model.Login;
import br.com.ccg.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Objects;

@ApplicationScoped
public class UserService {

    @Inject
    UserDAO userDAO;

    public User getUserById(String id) {
        return userDAO.getUserById(id);
    }

    public void updateUser(String id, User user) {
        userDAO.updateUser(id, user);
    }

    public void postUser(User user) {
        userDAO.postUser(user);
    }

    public void deleteUser(String id) {
        userDAO.deleteUser(id);
    }

    public boolean getUserByUsername(Login login) {
        User dbObject = userDAO.getUserByUsername(login);
        if(Objects.nonNull(dbObject)){
            return dbObject.getToken().equals(login.getToken());
        }
        return false;
    }
}
