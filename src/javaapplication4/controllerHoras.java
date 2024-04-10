/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication4;

import Dao.CadastroDao;
import Domain.Horas;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javaapplication4.JavaApplication4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

/**
 *
 * @author deividi-silva
 */
public class controllerHoras {

    @FXML
    private Button fechar;
    @FXML
    private Button btnVoltarHoras;
    @FXML
    private TextField txt_os;
    @FXML
    private TextField txt_operador;
    @FXML
    private TextField txt_maquina;
    @FXML
    private TextField txt_setor;
    @FXML
    private TextField txt_operacao;
    @FXML
    private RadioButton rb_inicio;
    @FXML
    private RadioButton rb_parada;
    @FXML
    private RadioButton rb_termino;
     @FXML
    private Label lbl_setor;

    public void voltarMenu(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setResizable(false);

        Parent fxmlForm = FXMLLoader.load(getClass().getResource("FXMLMenu.fxml"));
        // Parent fxmlForm2 = FXMLLoader.load(getClass().getResource("FXMLMenu.fxml"));

        Scene scene = new Scene(fxmlForm);
        stage.setScene(scene);
        stage.show();
        fecharHoras();

    }

    @FXML
    public void rb_inicio() {
        rb_parada.setSelected(false);
        rb_termino.setSelected(false);
    }
      public void rb_parada() {
        rb_inicio.setSelected(false);
        rb_termino.setSelected(false);
    }
        public void rb_termino() {
        rb_inicio.setSelected(false);
        rb_parada.setSelected(false);
    }

    private void fecharHoras() {
        Stage stage = (Stage) btnVoltarHoras.getScene().getWindow();
        stage.close();

    }

    public void connectButtonHoras(ActionEvent event) {

        Horas horas = new Horas();
        CadastroDao cadastroDao = new CadastroDao();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        if (rb_inicio.isSelected()) {
            horas.setOs(txt_os.getText());
            horas.setOperador(txt_operador.getText());
            horas.setMaquina(txt_maquina.getText());
            horas.setSetor(txt_setor.getText());
            horas.setOperacao(txt_operacao.getText());

            horas.setInicio(dateFormat.format(date));

            //cadastroDao.atualizar(cadastro);
            cadastroDao.inserirHoras(horas);
            limpar();
        }
        if (rb_termino.isSelected()) {
            horas.setOs(txt_os.getText());
            horas.setOperador(txt_operador.getText());
            horas.setMaquina(txt_maquina.getText());
            horas.setSetor(txt_setor.getText());
            horas.setOperacao(txt_operacao.getText());

            horas.setTermino(dateFormat.format(date));

            //cadastroDao.atualizar(cadastro);
            cadastroDao.atualizarHoras(horas);
            limpar();
        }
        if (rb_parada.isSelected()) {
            horas.setOs(txt_os.getText());
            horas.setOperador(txt_operador.getText());
            horas.setMaquina(txt_maquina.getText());
            horas.setSetor(txt_setor.getText());
            horas.setOperacao(txt_operacao.getText());

            horas.setTermino(dateFormat.format(date));

            //cadastroDao.atualizar(cadastro);
            cadastroDao.atualizarHoras(horas);
            limpar();
        }
    }

    public void limpar() {
        txt_os.setText("");
        txt_maquina.setText("");
        txt_operacao.setText("");
        txt_operador.setText("");
        txt_setor.setText("");

    }
    
    public void buscaSetor() {
        if (txt_setor.getText().equals("")) {
            lbl_setor.setText("");
        }
        if (txt_setor.getText().equals("420")) {
            lbl_setor.setText("CNC");
        }
         if (txt_setor.getText().equals("001")) {
            lbl_setor.setText("FÁBIO");
        }
          if (txt_setor.getText().equals("002")) {
            lbl_setor.setText("PAULO");
        }
        if (txt_setor.getText().equals("402")) {
            lbl_setor.setText("ALMOXARIFADO");
        }
        if (txt_setor.getText().equals("407")) {
            lbl_setor.setText("AJUSTAGEM");
        }
        if (txt_setor.getText().equals("407")) {
            lbl_setor.setText("AJUSTAGEM");
        }
        if (txt_setor.getText().equals("408")) {
            lbl_setor.setText("TÊMPERA");
        }
        if (txt_setor.getText().equals("413")) {
            lbl_setor.setText("TORNO");
        }
        if (txt_setor.getText().equals("415")) {
            lbl_setor.setText("ELETRO FIO");
        }
        if (txt_setor.getText().equals("416")) {
            lbl_setor.setText("RETIFICA");
        }
        if (txt_setor.getText().equals("417")) {
            lbl_setor.setText("EROSÃO");
        }
    }

}
