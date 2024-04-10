/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Banco;
import java.sql.Connection;
/**
 *
 * @author deividi-silva
 */
public interface DataBase {
        public Connection conectar();
    public void desconectar(Connection conn);
}



