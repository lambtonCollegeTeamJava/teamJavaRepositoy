/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author c0720648
 */
public class FXMLDocumentController implements Initializable {

    private Parent root;

    @FXML
    private Label labelLogin;

    @FXML
    private Label labelPassword;

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtPassword;
    
    
    @FXML
    private PasswordField txtAddCrewPassword;
    

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnReset;
    
    @FXML
    private Button btnNewCrew;
    
    
    @FXML
    private Button btnCrewDetails;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastname;

    @FXML
    private DatePicker txtDob;

    //@FXML
    //private ComboBox<?> boxGender;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtEmail;
    
    @FXML
    private Button btnSave;
    
    @FXML
    private TextField txtGender;

    @FXML
    private Button btnNewCrewReset;
    
    @FXML
    private TableColumn<Crew, Integer> columnLogin;

    @FXML
    private TableColumn<Crew, String> columnCrewName;

    @FXML
    private TableColumn<Crew, String> ColumnTitle;

    @FXML
    private TableColumn<Crew, ?> ColumnDob;

    @FXML
    private TableColumn<Crew, ?> ColumnProgress;
    
    ObservableList<String> options = 
    FXCollections.observableArrayList(
        "Male",
        "Female",
        "Don't Reveal"
    );
    
    @FXML
    final ComboBox boxGender = new ComboBox(options);
    

    @FXML
    void handleLoginButtonAction(ActionEvent event) {
        String user = txtLogin.getText();
        String pass = txtPassword.getText();
        
        if ("admin".equals(user) && "admin".equals(pass)){
        openCrewDetails();
        }
        else {
        openCrew();
        }          
    }
    
    @FXML 
    void handleResetButtonAction(ActionEvent event){
    txtLogin.setText("");
    txtPassword.setText("");  
    }
    
    
    @FXML
    void handleShowDetailAction(ActionEvent event) {
        //openWorkOrder();    
        //columnLogin.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        columnCrewName.setCellValueFactory(new PropertyValueFactory<>("Access"));
    }
    
    @FXML
    void handleNewCrewButtonAction(ActionEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("FXMLAddCrew.fxml"));
            TermProject.getPrimaryStage().setScene(new Scene(root));
            TermProject.getPrimaryStage().show();
            TermProject.setTitle("New Crew");
            System.out.println("Return to Login Page");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void handleLogoutButtonAction(ActionEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
            TermProject.getPrimaryStage().setScene(new Scene(root));
            TermProject.getPrimaryStage().show();
            TermProject.setTitle("Crew details");
            System.out.println("Return to Login Page");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    void handleCrewDetailsAction (ActionEvent event) {
            openCrewDetails();
    }
    
    
    
    @FXML
    void handleSaveAction(ActionEvent event) throws SQLException {
        
        Crew crewMember = new Crew();
        
        crewMember.setFirstname(txtFirstName.getText());
        crewMember.setLastname(txtLastname.getText());
        crewMember.setDob(txtDob.getValue());
        crewMember.setPhone(txtPhone.getText());
        crewMember.setEmail(txtEmail.getText());
        crewMember.setGender(txtGender.getText());
        crewMember.setPassword(txtAddCrewPassword.getText());
        
        String fName = txtFirstName.getText();
        String lName = txtLastname.getText();
        LocalDate dob = txtDob.getValue();
        
        String formattedDate = "";

        try {
            formattedDate = dob.format(DateTimeFormatter.ISO_DATE);
        } catch (Exception e) {
            System.out.println("Date Null");
        }
        
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String gender = txtGender.getText();
        String password = txtAddCrewPassword.getText();       
        DBManager queryExecuter = new DBManager();
        queryExecuter.saveData(crewMember);
    }
    
    
    
 // ------------  Not the CONTROLLER -----------------------------------
    
    
        public void openCrew(){
        try {
            root = FXMLLoader.load(getClass().getResource("FXMLCrew.fxml"));
            TermProject.getPrimaryStage().setScene(new Scene(root));
            TermProject.getPrimaryStage().show();
            TermProject.setTitle("Crew");
            System.out.println("Crew Page Opened");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void openCrewDetails(){
        try {
            root = FXMLLoader.load(getClass().getResource("FXMLCrewDetails.fxml"));
            TermProject.getPrimaryStage().setScene(new Scene(root));
            TermProject.getPrimaryStage().show();
            TermProject.setTitle("Crew details");
            System.out.println("Crew Details Page Opened");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
     public void openWorkOrder() {
        try {
            root = FXMLLoader.load(getClass().getResource("FXMLWorkOrder.fxml"));
            TermProject.getPrimaryStage().setScene(new Scene(root));
            TermProject.getPrimaryStage().show();
            TermProject.setTitle("Work Order");
            System.out.println("Work Order Page Opened");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

   

}
