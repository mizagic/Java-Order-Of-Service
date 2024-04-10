/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author deividi-silva
 */
public class DataBaseConnection {

    private Connection conn;
    String user, password,  url;

    public static void main(String[] args) {
        DataBaseConnection cnx = new DataBaseConnection();
        cnx.setUrl("jdbc:postgresql://192.168.0.225:5432/fleckprd");
        cnx.setUsuario("fleckprd");
        cnx.setSenha("fleckprd");
        cnx.getConnection();
    }

    public Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Postgree Conectado!");
            } catch (ClassNotFoundException e) {
                System.out.println("Impossivel registrar driver!");
            } catch (SQLException e) {
                System.out.println("Erro ao conectar postgree!!" + e);
            }
        }
//
        return conn;
    }

    public void setConnection(Connection cnx) {
        conn = cnx;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsuario(String usuario) {
        this.user = usuario;
    }

    public void setSenha(String senha) {
        this.password = senha;
    }

    public void reconecta() {
        conn = null;
        getConnection();
    }

}
