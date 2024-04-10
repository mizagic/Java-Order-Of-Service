/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import Banco.ObjectFactory;
import Dao.CadastroDao;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.ActionEvent;

/**
 *
 * @author deividi-silva
 */
public class Horas {

    private String os;
    private String operador;
    private String maquina;
    private String setor;
    private String operacao;
    private String inicio;
    private String parada;
    private String termino;

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getParada() {
        return parada;
    }

    public void setParada(String parada) {
        this.parada = parada;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    
}
