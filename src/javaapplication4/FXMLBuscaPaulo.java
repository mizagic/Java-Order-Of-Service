/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javaapplication4;

import static Banco.ObjectFactory.getConexao;
import Domain.Status;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FXMLBuscaPaulo implements Initializable {

    @FXML
    private Button btn_buscar;
    @FXML
    private Button btn_menu;

    @FXML
    private TableView<Status> tabelaBuscaAjust;
    @FXML
    private TableColumn<Status, String> setor;
    @FXML
    private TableColumn<Status, String> cracha;
    @FXML
    private TableColumn<Status, String> status;
    @FXML
    private TableColumn<Status, String> os;
    @FXML
    private TableColumn<Status, String> data;
    @FXML
    private TableColumn<Status, Image> img;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void initTablePaulo() {

        setor.setCellValueFactory(new PropertyValueFactory("setor"));
        os.setCellValueFactory(new PropertyValueFactory("os"));
        cracha.setCellValueFactory(new PropertyValueFactory("cracha"));
        status.setCellValueFactory(new PropertyValueFactory("entradaesaida"));
        data.setCellValueFactory(new PropertyValueFactory("data"));
        img.setCellValueFactory(new PropertyValueFactory<>("imagem"));

        tabelaBuscaAjust.setItems(atualizaTabela());

    }

    public void voltarmenu(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        fecharForm();

        Parent fxmlForm = FXMLLoader.load(getClass().getResource("FXMLMenu.fxml"));
        stage.setResizable(false);

        Scene scene = new Scene(fxmlForm);
        stage.setScene(scene);
        stage.show();

    }

    private void fecharForm() {
        Stage stage = (Stage) btn_menu.getScene().getWindow();
        stage.close();

    }

    public ObservableList<Status> atualizaTabela() {

        List<Status> status = new ArrayList<>();
       ImageView image = new ImageView(new Image(this.getClass().getResourceAsStream("verde.png")));
        String dateStr;

        Date data = new Date();

        String sql = "SELECT * FROM cadastro WHERE setor IN ('PAULO','AJUSTAGEM', 'TÊMPERA', 'ELETRO FIO','RETIFICA','EROSÃO') AND entradasaida = 'Entrada';";

        try {
            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                Status p = new Status();

                dateStr = rs.getString("dataentrada");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dataformatada = sdf.parse(dateStr);
                sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                dateStr = sdf.format(dataformatada);

                p.setSetor(rs.getString("setor"));//nome da coluna no BD
                p.setOs(rs.getString("os"));//nome da coluna no BD
                p.setCracha(rs.getString("cracha"));//nome da coluna no BD
                p.setEntradaesaida(rs.getString("entradasaida"));//nome da coluna no BD
                p.setData(dateStr);
                //p.setImagem(image);

                status.add(p);
            }
            prs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(FXMLBuscaPaulo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(status);

    }
}
