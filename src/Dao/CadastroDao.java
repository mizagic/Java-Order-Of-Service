

package Dao;

import java.sql.Connection;
import Banco.ObjectFactory;
import Domain.Cadastro;
import Domain.Horas;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;

/**
 *
 * @author deividi-silva
 */
public class CadastroDao {

    @FXML
    private TextField txt_os;
    @FXML
    private TextField txt_setor;
    @FXML
    private TextField txt_data;
    @FXML
    private TextField txt_cracha;

    private Connection connection;

    public Connection getConnection() {

        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Cadastro cadastro) {
        String sql;
        sql = "INSERT INTO cadastro(os, setor, cracha, entradasaida, dataentrada)"
                + " VALUES ("
                + "'" + cadastro.getOs() + "',"
                + "'" + cadastro.getSetor() + "'" + ","
                + "'" + cadastro.getCracha() + "',"
                + "'" + cadastro.getEntradasaida() + "',"
                + "now() )";

        ObjectFactory.executeSql(sql);
    }
    public void atualizar(Cadastro cadastro) {
        String sql;
        sql = "UPDATE cadastro SET "
                + " entradasaida = 'Saída',"
                + " datasaida = now() "
                + " WHERE os = '" + cadastro.getOs() + "' and entradasaida = 'Entrada'";
        ObjectFactory.executeSqlUpdate(sql);
    }
    public void atualizarFinal(Cadastro cadastro) {
        String sql;
        sql = "INSERT INTO cadastro(os, setor, cracha, entradasaida, dataentrada)"
                + " VALUES ("
                + "'" + cadastro.getOs() + "',"
                + "'Almoxarifado','Ana/Deividi','Encerrada',now())";

        ObjectFactory.executeSql(sql);

    }

    public void atualizarHoras(Horas horas) {
        String sql;
        sql = "UPDATE horas SET "
                + " termino = now() "
                + " WHERE os = '" + horas.getOs() + "' and operador = '" + horas.getOperador() + "'";
        ObjectFactory.executeSqlUpdate(sql);

    }

    public void inserirHoras(Horas horas) {

        String sql;
        sql = "INSERT INTO horas(os, operador, maquina, setor, operacao, inicio)"
                + " VALUES ("
                + "'" + horas.getOs() + "',"
                + "'" + horas.getOperador() + "'" + ","
                + "'" + horas.getMaquina() + "',"
                + "'" + horas.getSetor() + "',"
                + "'" + horas.getOperacao() + "',"
                + "now() )";

        ObjectFactory.executeSql(sql);

    }

    public void inserirHorasTermino(Horas horas) {

        String sql;
        sql = "INSERT INTO horas(os, operador, maquina, setor, operacao, inicio)"
                + " VALUES ("
                + "'" + horas.getOs() + "',"
                + "'" + horas.getOperador() + "'" + ","
                + "'" + horas.getMaquina() + "',"
                + "'" + horas.getSetor() + "',"
                + "'" + horas.getOperacao() + "',"
                + "now() )";

        ObjectFactory.executeSql(sql);

    }

}
