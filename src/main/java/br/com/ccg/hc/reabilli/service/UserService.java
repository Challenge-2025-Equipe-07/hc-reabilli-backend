package br.com.ccg.hc.reabilli.service;

import br.com.ccg.hc.reabilli.dao.UserRepository;
import br.com.ccg.hc.reabilli.model.Login;
import br.com.ccg.hc.reabilli.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    public User getUserById(int id) {
        Optional<User> user = userRepository.getById(id);
        if(user.isPresent()) {
            return user.get();
        } else  {
            throw new NotFoundException("User not found.");
        }
    }

    public User getUserByUsername(Login login) {
        Optional<User> user = userRepository.getByUsername(login.getUsername());
        if(user.isPresent()) {
            return user.get();
        } else   {
            throw new NotFoundException("Account not found.");
        }
    }
    public List<User> getAllUsers() {
        Optional<List<User>> userList = userRepository.getAll();
        if (userList.isPresent()){
            return userList.get();
        } else {
            throw new NotFoundException("Users not found");
        }
    }

    public User updateUser(int id, User user) {
        Optional<User> userOptional = userRepository.update(id, user);
        if(userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new NotFoundException("User not found.");
    }

    public void postUser(User user) {
        userRepository.post(user);
    }

    public void deleteUser(int id) {
        userRepository.delete(id);
    }


}
