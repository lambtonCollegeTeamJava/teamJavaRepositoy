/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author c0720648
 */
public class TermProject extends Application {
    
    private static Stage primaryStage;
    
    
    
    
    
    
    @Override
    public void start(Stage value) throws Exception {
        
        DBManager instance = new DBManager();
//        try {
//            instance.dropTable();
//            System.out.println("Table Dropped");
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }    
        try {
            instance.createTableCrew();
            instance.createTableWorkOrder();
            System.out.println("Table Created");
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }finally {
        instance.closeConnection();
        }       
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("FXMLLogin.fxml"));
        primaryStage = value;
        primaryStage.setTitle("Welcome Screen");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
     /**
     *
     * @return
     */
    static public Stage getPrimaryStage() {
        return TermProject.primaryStage;
    }

    /**
     *
     * @param value
     */
    static public void setTitle(String value) {
        primaryStage.setTitle(value);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
