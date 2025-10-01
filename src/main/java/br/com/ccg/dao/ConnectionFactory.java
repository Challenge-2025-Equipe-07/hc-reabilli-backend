package br.com.ccg.dao;

import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@ApplicationScoped
public class ConnectionFactory {


    protected static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl", "RM562700", "090805");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Logger.getLogger(userDAO.class).info("Conexao estabelecida com o banco de daods.");
        }
    }

}
