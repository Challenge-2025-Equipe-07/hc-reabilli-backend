package br.com.ccg.dao;

import br.com.ccg.model.Login;
import br.com.ccg.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
@RequiredArgsConstructor
public class UserDAO {


    private static final String GET_BY_ID = "SELECT * FROM T_CCG_USER WHERE ID_USER = ?";
    private static final String UPDATE_USER = "UPDATE T_CCG_USER SET NM_USER= ?, DS_USERNAME= ?, DS_TOKEN = ? WHERE ID_USER = ?";
    private static final String INSERT_USER = "INSERT INTO T_CCG_USER (ID_USER, NM_USER, DS_USERNAME, DS_TOKEN)VALUES (?, ?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM T_CCG_USER WHERE ID_USER = ?";
    private static final String GET_BY_USERNAME = "SELECT * FROM T_CCG_USER WHERE DS_USERNAME = ?";

    public User getUserById(String id) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_BY_ID)) {
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return User.builder()
                        .userId(rs.getInt("ID_USER"))
                        .name(rs.getString("NM_USER"))
                        .username(rs.getString("DS_USERNAME"))
                        .token(rs.getString("DS_TOKEN"))
                        .build();
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class).info("Error when getting user by id.");
        }
        return User.builder().build();
    }

    public void updateUser(String id, User user) {
        try(
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_USER);
                ){
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getToken());
            preparedStatement.setInt(4, Integer.parseInt(id));
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(UserDAO.class).info("Error when updating user.");
        }
    }

    public void postUser(User user) {
        try(
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(INSERT_USER);
        ){
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getToken());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(UserDAO.class).info("Error when posting user.");
        }
    }

    public void deleteUser(String id) {
        try(
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USER);
        ){
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(UserDAO.class).info("Error when deleting user.");
        }
    }

    public User getUserByUsername(Login login) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_BY_USERNAME)) {
            ps.setString(1, login.getUsername());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return User.builder()
                        .userId(rs.getInt("ID_USER"))
                        .name(rs.getString("NM_USER"))
                        .username(rs.getString("DS_USERNAME"))
                        .token(rs.getString("DS_TOKEN"))
                        .build();
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class).info("Error when getting user by id.");
        }
        return User.builder().build();
    }
}


