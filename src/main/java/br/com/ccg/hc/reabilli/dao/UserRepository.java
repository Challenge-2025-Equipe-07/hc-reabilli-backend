package br.com.ccg.hc.reabilli.dao;

import br.com.ccg.hc.reabilli.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public Optional<User> getById(int id) {
        return findByIdOptional((long) id);
    }

    public Optional<User> getByUsername(String username) {
        return find("username", username).firstResultOptional();
    }

    public Optional<List<User>> getAll() {
        return Optional.ofNullable(listAll());
    }

    @Transactional
    public Optional<User> post(User user) {
        persist(user);
        return Optional.ofNullable(user);
    }

    @Transactional
    public Optional<User> update(int id, User updatedUser) {
        User existing = findById((long) id);
        if (existing != null) {
            existing.setName(updatedUser.getName());
            existing.setUsername(updatedUser.getUsername());
            existing.setToken(updatedUser.getToken());
            persist(existing);
            return Optional.of(existing);
        }
        return Optional.empty();
    }

    @Transactional
    public void delete(int id) {
        deleteById((long) id);
    }
}
