package br.com.ccg.hc.reabilli.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RepositoryConnection {

    private static final Logger logger = Logger.getLogger(RepositoryConnection.class.getName());

    public static final String USUARIO = "rm562700";
    public static final String SENHA = "090805";
    public static final String JDBC_ORACLE_THIN_ORACLE_FIAP_COM_BR_1521_ORCL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";

    Connection createConnection() {
        try {
           return DriverManager.getConnection(JDBC_ORACLE_THIN_ORACLE_FIAP_COM_BR_1521_ORCL, USUARIO, SENHA);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            return null;
        }
    }
}