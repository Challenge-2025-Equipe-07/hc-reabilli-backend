package br.com.ccg.service;

import br.com.ccg.dao.UserDAO;
import br.com.ccg.dto.LoginDTO;
import br.com.ccg.dto.UserDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Objects;

@ApplicationScoped
public class UserService {

    @Inject
    UserDAO userDAO;

    public UserDTO getUser(String id) {
        return userDAO.getUserById(id);
    }

    public void updateUser(String id, UserDTO userDTO) {
        userDAO.updateUser(id, userDTO);
    }

    public void postUser(UserDTO userDTO) {
        userDAO.postUser(userDTO);
    }

    public void deleteUser(String id) {
        userDAO.deleteUser(id);
    }

    public boolean getUserByUsername(LoginDTO loginDTO) {
        UserDTO dbObject = userDAO.getUserByUsername(loginDTO);
        if(Objects.nonNull(dbObject)){
            return dbObject.getToken().equals(loginDTO.getToken());
        }
        return false;
    }
}
