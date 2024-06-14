
package javaapplication4;

import static Banco.ObjectFactory.getConexao;
import Domain.Cadastro;
import Domain.Status;
import Dao.CadastroDao;
import java.io.IOException;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Controller implements Initializable {

    @FXML
    private Button btn_voltar;
    @FXML
    private Label lbl_setor;
    @FXML
    private TextField txt_os;
    @FXML
    private TextField txt_setor;
    @FXML
    private TextField txt_cracha;
    @FXML
    private Button btn_salvar;
    
    @FXML
    private Button btn_os;
    @FXML
    private Button btn_encerra;
    @FXML
    private RadioButton rb_encerra;
    // @FXML
    // private RadioButton cb_saida;
    @FXML
    private TableView<Status> tabela;
    @FXML
    private TableColumn<Status, String> setor;
    @FXML
    private TableColumn<Status, String> cracha;
    @FXML
    private TableColumn<Status, String> status;
    String inputOs;
    @FXML
    private Button btn_limpar;


    /*
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tamanhoTxt();

        btn_salvar.setDefaultButton(true);
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
        if (txt_setor.getText().equals("003")) {
            lbl_setor.setText("TERCEIROS");
        }
        if (txt_setor.getText().equals("420")) {
            lbl_setor.setText("CNC");
        }
        if (txt_setor.getText().equals("402")) {
            lbl_setor.setText("ALMOXARIFADO");
        }
        if (txt_setor.getText().equals("403")) {
            lbl_setor.setText("FRESA");
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

    public void buscaOperador() {

    }

    @FXML
    public void initTable() {

        setor.setCellValueFactory(new PropertyValueFactory("setor"));
        cracha.setCellValueFactory(new PropertyValueFactory("cracha"));
        status.setCellValueFactory(new PropertyValueFactory("entradaesaida"));

        tabela.setItems(atualizaTabela());

    }

    public ObservableList<Status> atualizaTabela() {
        Controller dao = new Controller();
        List<Status> status = new ArrayList<>();
        Status p = new Status();

        String sql = "SELECT setor,cracha,entradasaida,  MAX(dataentrada) AS data_recente "
                + "FROM cadastro WHERE os =" + "'" + txt_os.getText() + "'"
                + "GROUP BY setor,cracha, entradasaida ORDER BY data_recente DESC LIMIT 1";
        try {
            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {

                p.setSetor(rs.getString("setor"));//nome da coluna no BD
                p.setCracha(rs.getString("cracha"));//nome da coluna no BD
                p.setEntradaesaida(rs.getString("entradasaida"));//nome da coluna no BD
                status.add(p);
            }
            prs.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return FXCollections.observableArrayList(status);

    }

    @FXML
    public void connectButton(ActionEvent event) {

        Cadastro cadastro = new Cadastro();

        if (txt_setor.getText().equals("420")) {
            cadastro.setSetor("CNC");
        }
        if (txt_setor.getText().equals("001")) {
            cadastro.setSetor("FÁBIO");
        }
        if (txt_setor.getText().equals("002")) {
            cadastro.setSetor("PAULO");
        }
        if (txt_setor.getText().equals("003")) {
            cadastro.setSetor("TERCEIROS");
        }
        if (txt_setor.getText().equals("420")) {
            cadastro.setSetor("CNC");
        }
        if (txt_setor.getText().equals("402")) {
            cadastro.setSetor("ALMOXARIFADO");
        }
        if (txt_setor.getText().equals("403")) {
            cadastro.setSetor("FRESA");
        }
        if (txt_setor.getText().equals("407")) {
            cadastro.setSetor("AJUSTAGEM");
        }

        if (txt_setor.getText().equals("408")) {
            cadastro.setSetor("TÊMPERA");
        }
        if (txt_setor.getText().equals("413")) {
            cadastro.setSetor("TORNO");
        }
        if (txt_setor.getText().equals("415")) {
            cadastro.setSetor("ELETRO FIO");
        }
        if (txt_setor.getText().equals("416")) {
            cadastro.setSetor("RETIFICA");
        }
        if (txt_setor.getText().equals("417")) {
            cadastro.setSetor("EROSÃO");
        }
        CadastroDao cadastroDao = new CadastroDao();
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////SALVA NOME OPERADORES ↓↓↓↓/////////////
////// //// / / //  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (txt_cracha.getText().equals("1319")) {
            cadastro.setCracha("JERRY");
        }
        if (txt_cracha.getText().equals("130208")) {
            cadastro.setCracha("MARCELO");
        }
        if (txt_cracha.getText().equals("130089")) {
            cadastro.setCracha("FABIO");
        }
        if (txt_cracha.getText().equals("130103")) {
            cadastro.setCracha("JOSIAS");
        }
        if (txt_cracha.getText().equals("901055")) {
            cadastro.setCracha("JONAS");
        }
        if (txt_cracha.getText().equals("130185")) {
            cadastro.setCracha("RONEI");
        }
        if (txt_cracha.getText().equals("130085")) {
            cadastro.setCracha("PAULO");
        }
        if (txt_cracha.getText().equals("454")) {
            cadastro.setCracha("ARCANJO");
        }
        if (txt_cracha.getText().equals("700308")) {
            cadastro.setCracha("HENDRIO");
        }
        if (txt_cracha.getText().equals("502495")) {
            cadastro.setCracha("CÉLIO");
        }
        if (txt_cracha.getText().equals("2025")) {
            cadastro.setCracha("ODAIR");
        }
        if (txt_cracha.getText().equals("700319")) {
            cadastro.setCracha("LUCAS");
        }
        if (txt_cracha.getText().equals("900111")) {
            cadastro.setCracha("ANDERSON");
        }
        if (txt_cracha.getText().equals("700315")) {
            cadastro.setCracha("CRISTIAN");
        }
        if (txt_cracha.getText().equals("1043")) {
            cadastro.setCracha("AMAURI");
        }
        if (txt_cracha.getText().equals("152")) {
            cadastro.setCracha("LUIS");
        }
        if (txt_cracha.getText().equals("432")) {
            cadastro.setCracha("ALCIONE");
        }
        if (txt_cracha.getText().equals("700318")) {
            cadastro.setCracha("ANA");
        }
        if (txt_cracha.getText().equals("700200")) {
            cadastro.setCracha("DEIVIDI");
        }

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        cadastro.setOs(txt_os.getText());
        //cadastro.setSetor(txt_setor.getText());
        //cadastro.setCracha(txt_cracha.getText());
        cadastro.setEntradasaida("Entrada");
        cadastro.setData(dateFormat.format(date));

        

        if (txt_setor.getText().isEmpty() || txt_cracha.getText().isEmpty() || txt_setor.getText().isEmpty()) {

            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setTitle("Informação");
            dialogoInfo.setHeaderText("FAVOR PREENCHER TODOS OS CAMPOS");
            dialogoInfo.setContentText("");
            dialogoInfo.showAndWait();

        } else {

            cadastroDao.atualizar(cadastro);
            cadastroDao.inserir(cadastro);

            limpar();

        }

    }

    @FXML
    public void encerraOs(ActionEvent event) {
        String os = txt_os.getText();

        Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
        dialogoInfo.setTitle("Informação");
        dialogoInfo.setHeaderText("DESEJA REALMENTE ENCERRAR A OS: " + os + "?");
        dialogoInfo.setContentText("");
        dialogoInfo.showAndWait();

        CadastroDao cadDao = new CadastroDao();
        Cadastro cad = new Cadastro();
        cad.setOs(os);
        
        cadDao.atualizar(cad);
        cadDao.atualizarFinal(cad);
        limpar();

    }

    private void fecharForm() {
        Stage stage = (Stage) btn_voltar.getScene().getWindow();
        stage.close();

    }

    public void voltarmenu(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        fecharForm();
        stage.setResizable(false);
        Parent fxmlForm = FXMLLoader.load(getClass().getResource("FXMLMenu.fxml"));

        Scene scene = new Scene(fxmlForm);
        stage.setScene(scene);
        stage.show();

    }

    public void limpar() {
        txt_os.setText("");
        txt_cracha.setText("");
//        txt_data.setText("");
        txt_setor.setText("");
        //cb_entrada.setSelected(false);
        //cb_saida.setSelected(false);
    }

    @FXML
    public void connectButtonLimpar() {
        limpar();
    }

    //@FXML
    //public void entradaClicado() {
    //    cb_saida.setSelected(false);
    //}
    //@FXML
    // public void saidaClicado() {
    //    cb_entrada.setSelected(false);
    //}
    public void tamanhoTxt() {
        final int maxLength = 6;
        final int maxLengthSetor = 3;

        txt_os.setOnKeyTyped(t -> {

            if (txt_os.getText().length() > maxLength) {
                int pos = txt_os.getCaretPosition();
                txt_os.setText(txt_os.getText(0, maxLength));
                txt_os.positionCaret(pos); //To reposition caret since setText sets it at the beginning by default
            }
        });
        txt_setor.setOnKeyTyped(t -> {

            if (txt_setor.getText().length() > maxLengthSetor) {
                int pos = txt_setor.getCaretPosition();
                txt_setor.setText(txt_setor.getText(0, maxLengthSetor));
                txt_setor.positionCaret(pos); //To reposition caret since setText sets it at the beginning by default
            }
        });
    }
}
