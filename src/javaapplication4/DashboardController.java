/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication4;

import static Banco.ObjectFactory.getConexao;
import Domain.Status;
import java.io.IOException;
import java.net.URL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

    double totalOsAberta;
    Integer cnc;
    Integer fresa;
    Integer retifica;
    Integer tempera;
    Integer torno;
    Integer fio;
    Integer erosao;
    Integer almox;
    Integer ajustagem;
    double atrasadas;

    @FXML
    private Label LblFio;

    @FXML
    private Button btn_voltar;
    @FXML
    private Label LblTempera;

    @FXML
    private Label LblCnc;

    @FXML
    private Label LblFresa;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private Label LblTorno;

    @FXML
    private Label LblErosao;
    @FXML
    private Label LblRetifica;
    @FXML
    private Label LblAlmox;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label LblAjustagem;
    @FXML
    private TableView<Status> tabela;
    @FXML
    private Label LblAtrasada;
    @FXML
    private TextField txtOs;
    @FXML
    private TableColumn<Status, String> setor;
    @FXML
    private TableColumn<Status, String> status;
    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    double resultado;

    final static String osAbertas = "ABERTAS";
    final static String osAtrasada = "ATRASADAS";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        atualizaOS();
        atualizaFresa();
        atualizaTorno();
        atualizaTempera();
        atualizaFio();
        atualizaErosao();
        atualizaAlmox();
        atualizaAjustagem();
        initializePieChart();
        atualizaRetifica();
        contaAtrasadas();
        
        contaOs();

        resultado = totalOsAberta - atrasadas;

        resultado = totalOsAberta / (resultado * 100);

        NumberFormat formatter = new DecimalFormat("#0,00");
        System.out.println(formatter.format(resultado));

        //progressIndicator.setPrefSize(250, 250);
        progressIndicator.setStyle(" -fx-progress-color: red;");

        progressIndicator.setProgress(resultado);
       
        barChart();

    }

    @FXML
    public void initTable() {

        setor.setCellValueFactory(new PropertyValueFactory("setor"));
        //status.setCellValueFactory(new PropertyValueFactory("entradaesaida"));

        tabela.setItems(atualizaTabela());

    }

    public ObservableList<Status> atualizaTabela() {
        Controller dao = new Controller();
        List<Status> status = new ArrayList<>();
        Status p = new Status();

        String sql = "SELECT setor,  MAX(dataentrada) AS data_recente "
                + "FROM cadastro WHERE os =" + "'" + txtOs.getText() + "'"
                + "GROUP BY setor,cracha, entradasaida ORDER BY data_recente DESC LIMIT 1";
        try {
            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {

                p.setSetor(rs.getString("setor"));//nome da coluna no B
               // p.setEntradaesaida(rs.getString("entradasaida"));//nome da coluna no BD
                status.add(p);
            }
            prs.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return FXCollections.observableArrayList(status);

    }

    public void initializePieChart() {
        // totalOs = cnc +fresa+tempera+torno+fio+erosao+almox+ajustagem;
        ObservableList<PieChart.Data> pierCharData = FXCollections.observableArrayList(
                //  new PieChart.Data("Total em Aberto: " + totalOs, totalOs),
                new PieChart.Data("CNC: " + cnc, cnc),
                new PieChart.Data("TÊMPERA: " + tempera, tempera),
                new PieChart.Data("FRESA: " + fresa, fresa),
                new PieChart.Data("TORNO: " + torno, torno),
                new PieChart.Data("FIO: " + fio, fio),
                new PieChart.Data("EROSÃO: " + erosao, erosao),
                new PieChart.Data("ALMOX: " + almox, almox),
                new PieChart.Data("AJUSTAGEM: " + ajustagem, ajustagem)
        );
        pieChart.getData().addAll(pierCharData);
    }

    public void barChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        //barChart.setTitle("Controle de OS");
        xAxis.setLabel("OS");
        yAxis.setLabel("Valor");

        int i = (int) atrasadas;
        int x = (int) totalOsAberta;
        
        barChart.setStyle(" -fx-background-color: transparent;");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("OS");
        series1.getData().add(new XYChart.Data(osAtrasada + " = " + i, i));
        series1.getData().add(new XYChart.Data(osAbertas + " = " + x, x));
        barChart.getData().addAll(series1);
    }

    public void atualizaOS() {
        //Controller dao = new Controller();
        // List<Status> status = new ArrayList<>();
        //Status p = new Status();

        String sql = "select count(entradasaida) from cadastro d where entradasaida ='Entrada' and setor = 'CNC'";
        try {
            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {

                LblCnc.setText(rs.getString("count"));//nome da coluna no BD
                cnc = Integer.parseInt(LblCnc.getText());
            }
            prs.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void atualizaRetifica() {
        //Controller dao = new Controller();
        // List<Status> status = new ArrayList<>();
        //Status p = new Status();

        String sql = "select count(entradasaida) from cadastro d where entradasaida ='Entrada' and setor = 'RETIFICA'";
        try {
            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {

                LblRetifica.setText(rs.getString("count"));//nome da coluna no BD
                retifica = Integer.parseInt(LblRetifica.getText());
            }
            prs.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void atualizaFresa() {
        //Controller dao = new Controller();
        // List<Status> status = new ArrayList<>();
        //Status p = new Status();

        String sql = "select count(entradasaida) from cadastro d where entradasaida ='Entrada' and setor = 'FRESA'";
        try {
            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {

                LblFresa.setText(rs.getString("count"));//nome da coluna no BD
                fresa = Integer.parseInt(LblFresa.getText());
            }
            prs.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void atualizaTempera() {
        //Controller dao = new Controller();
        // List<Status> status = new ArrayList<>();
        //Status p = new Status();

        String sql = "select count(entradasaida) from cadastro d where entradasaida ='Entrada' and setor = 'TÊMPERA'";
        try {
            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {

                LblTempera.setText(rs.getString("count"));//nome da coluna no BD
                tempera = Integer.parseInt(LblTempera.getText());
            }
            prs.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void atualizaTorno() {
        //Controller dao = new Controller();
        // List<Status> status = new ArrayList<>();
        //Status p = new Status();

        String sql = "select count(entradasaida) from cadastro d where entradasaida ='Entrada' and setor = 'TORNO'";
        try {
            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {

                LblTorno.setText(rs.getString("count"));//nome da coluna no BD
                torno = Integer.parseInt(LblTorno.getText());
            }
            prs.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void atualizaFio() {
        //Controller dao = new Controller();
        // List<Status> status = new ArrayList<>();
        //Status p = new Status();

        String sql = "select count(entradasaida) from cadastro d where entradasaida ='Entrada' and setor = 'ELETRO FIO'";
        try {
            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {

                LblFio.setText(rs.getString("count"));//nome da coluna no BD
                fio = Integer.parseInt(LblFio.getText());
            }
            prs.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void atualizaErosao() {
        //Controller dao = new Controller();
        // List<Status> status = new ArrayList<>();
        //Status p = new Status();

        String sql = "select count(entradasaida) from cadastro d where entradasaida ='Entrada' and setor = 'EROSÃO'";
        try {
            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {

                LblErosao.setText(rs.getString("count"));//nome da coluna no BD
                erosao = Integer.parseInt(LblErosao.getText());
            }
            prs.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void atualizaAlmox() {
        //Controller dao = new Controller();
        // List<Status> status = new ArrayList<>();
        //Status p = new Status();

        String sql = "select count(entradasaida) from cadastro d where entradasaida ='Entrada' and setor = 'ALMOXARIFADO'";
        try {
            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {

                LblAlmox.setText(rs.getString("count"));//nome da coluna no BD
                almox = Integer.parseInt(LblAlmox.getText());
            }
            prs.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void atualizaAjustagem() {
        //Controller dao = new Controller();
        // List<Status> status = new ArrayList<>();
        //Status p = new Status();

        String sql = "select count(entradasaida) from cadastro d where entradasaida ='Entrada' and setor = 'AJUSTAGEM'";
        try {
            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {

                LblAjustagem.setText(rs.getString("count"));//nome da coluna no BD
                ajustagem = Integer.parseInt(LblAjustagem.getText());
            }
            prs.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void atualizaData() {

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

                p.setData(dateStr);

                Date addedDate1 = addDays(data, 30);
                System.out.println("" + addedDate1);

            }
            prs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(FXMLBuscaPaulo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Date addDays(Date d, int days) {
        d.setTime(d.getTime() + days * 1000 * 60 * 60 * 24);
        return d;
    }

    public void contaAtrasadas() {
        //Controller dao = new Controller();
        // List<Status> status = new ArrayList<>();
        //Status p = new Status();

        String sql = "WITH min_dataentrada AS (\n"
                + "	    SELECT\n"
                + "	        os,\n"
                + "	        MIN(dataentrada) AS menor_dataentrada\n"
                + "	    FROM\n"
                + "	        cadastro\n"
                + "	    GROUP BY\n"
                + "	        os\n"
                + "	),\n"
                + "	os_status AS (\n"
                + "	    SELECT\n"
                + "	        os,\n"
                + "	        CASE\n"
                + "	            WHEN COUNT(*) FILTER (WHERE entradasaida = 'Encerrada') > 0 THEN 'OK'\n"
                + "	            ELSE\n"
                + "	                CASE\n"
                + "	                    WHEN NOW() <= MIN(dataentrada) + INTERVAL '60 days' THEN 'OK'\n"
                + "	                    ELSE 'ATRASADO'\n"
                + "	                END\n"
                + "	        END AS SLA,\n"
                + "	        CASE\n"
                + "	            WHEN NOW() > MIN(dataentrada) + INTERVAL '60 days' THEN\n"
                + "	                EXTRACT(DAY FROM NOW() - (MIN(dataentrada) + INTERVAL '60 days'))\n"
                + "	            ELSE\n"
                + "	                0\n"
                + "	        END AS atraso\n"
                + "	    FROM\n"
                + "	        cadastro\n"
                + "	    GROUP BY\n"
                + "	        os\n"
                + "	)\n"
                + "	SELECT \n"
                + "	      COUNT(DISTINCT c.os) AS os_atrasadas\n"
                + "	FROM\n"
                + "	    cadastro c\n"
                + "	JOIN\n"
                + "	    min_dataentrada m ON c.os = m.os\n"
                + "	JOIN\n"
                + "	    os_status s ON c.os = s.os\n"
                + "	WHERE\n"
                + "	    c.entradasaida <> 'Encerrada'\n"
                + "	    AND (c.datasaida IS NOT NULL AND c.entradasaida = 'Saída')\n"
                + "	    AND s.SLA = 'ATRASADO'";
        try {
            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {

                atrasadas = Integer.parseInt(rs.getString("os_atrasadas"));//nome da coluna no BD

            }
            prs.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void contaOs() {
        //Controller dao = new Controller();
        // List<Status> status = new ArrayList<>();
        //Status p = new Status();

        String sql = "select count(entradasaida) from cadastro c where entradasaida = 'Entrada'";
        try {
            PreparedStatement prs = getConexao().getConnection().prepareStatement(sql);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {

                totalOsAberta = Integer.parseInt(rs.getString("count"));//nome da coluna no BD

            }
            prs.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

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
      private void fecharForm() {
        Stage stage = (Stage) btn_voltar.getScene().getWindow();
        stage.close();

    }

}
