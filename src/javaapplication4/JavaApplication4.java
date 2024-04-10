/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class JavaApplication4 extends Application {

    private static Stage stage;
    private static Scene form;
    private static Scene menu;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //primaryStage = stage;
        primaryStage.setTitle("Menu");

        Parent fxmlForm = FXMLLoader.load(getClass().getResource("FXMLFormulario.fxml"));
        form = new Scene(fxmlForm);

        Parent fxmlMenu = FXMLLoader.load(getClass().getResource("FXMLMenu.fxml"));
        menu = new Scene(fxmlMenu);
        primaryStage.setResizable(false);
        primaryStage.setScene(menu);
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);

    }

}
