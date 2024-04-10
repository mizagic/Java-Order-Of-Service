/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Banco;

import java.sql.PreparedStatement;
import javaapplication4.Controller;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author deividi-silva
 */
public class ObjectFactory {

    @FXML
    private TextField txt_os;
    @FXML
    private TextField txt_setor;
    @FXML
    private TextField txt_data;
    @FXML
    private TextField txt_cracha;
    @FXML
    private Button btn_salvar;
    static DataBaseConnection conexao;

    public static DataBaseConnection getConexao() {
        if (conexao == null) {
            conexao = new DataBaseConnection();
            conexao.setUrl("jdbc:postgresql://192.168.0.225:5432/fleckprd");
            conexao.setUsuario("fleckprd");
            conexao.setSenha("fleckprd");
            conexao.getConnection();
        }
        return conexao;
    }

    public void limpar() {

    }

    public static void executeSql(String sql) {

        try {

            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            prs.execute();

            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setTitle("Informação");
            dialogoInfo.setHeaderText("Salvo com sucesso...");
            dialogoInfo.setContentText("");
            dialogoInfo.showAndWait();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void loginExecuteSql(String sql) {

        try {

            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            prs.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void executeSqlUpdate(String sql) {

        try {

            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            prs.execute();

            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setTitle("Informação");
            dialogoInfo.setHeaderText("Salvo com sucesso...");
            dialogoInfo.setContentText("");
            dialogoInfo.showAndWait();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
