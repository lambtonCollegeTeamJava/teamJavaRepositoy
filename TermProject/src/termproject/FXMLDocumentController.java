/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author c0720648
 */
public class FXMLDocumentController implements Initializable {

    private Parent root;

    private Connection conn;

    //public final String DB_URL = "jdbc:sqlite:F://Sem 2//Java EE//TermProject//crewmanagement.db";
    public final String DB_URL = "jdbc:sqlite://Users/Kishan/Desktop/TermProject/crewmanagement.db";

    @FXML
    private Label labelLogin;

    @FXML
    private TextField txtWOWorkID;

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

    @FXML
    private TableView<Crew> tableView;

    @FXML
    private TableView<Crew> tableCrew;
    
    @FXML
    private Button btnCreate;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtGender;

    @FXML
    public TextField txtInstance;

    @FXML
    public TextField txtID;

    @FXML
    private TextField txtCrewUserName;

    @FXML
    private Button btnNewCrewReset;

    @FXML
    private TableColumn<Crew, Integer> columnCrewID;

    @FXML
    private TableColumn<Crew, String> columnFirstName;

    @FXML
    private TableColumn<Crew, String> columnLastName;

    @FXML
    private TableColumn<Crew, String> columnTitle;

    @FXML
    private TableColumn<Crew, String> columnStart;

    @FXML
    private TableColumn<Crew, String> columnEnd;

    @FXML
    private TableColumn<Crew, String> columnSummary;

    @FXML
    private TableColumn<Crew, String> columnWOStatus;

    @FXML
    private TableColumn<Crew, ?> ColumnDob;

    @FXML
    private Button btnMyTask;

    @FXML
    private TableColumn<Crew, String> columnProgress;

    @FXML
    private TableColumn<WorkOrder, String> columnWO;

    @FXML
    private TableColumn<Crew, String> columnCrewWorkOrder;

    @FXML
    private TableColumn<Crew, String> columnCrewProgress;

    @FXML
    private TableColumn<Crew, String> columnCrewSummary;

    @FXML
    private TableColumn<Crew, String> columnWorkID;

    @FXML
    private Label labelworkOrder;

    @FXML
    private TextField txtWorkOrderName;

    @FXML
    private TextField txtCrewID;

    @FXML
    private DatePicker datePickerStartDate;

    @FXML
    private DatePicker datePickerEndDate;

    @FXML
    private TextArea txtAreaSummary;

    @FXML
    private Button btnChart;

    @FXML
    private TextField txtProgress;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnDeleteCrew;

    @FXML
    private TableColumn<Crew, String> columnStatus;

    Crew crewMember = new Crew();
    WorkOrder wo = new WorkOrder();
    DBManager instanceDB;

    public FXMLDocumentController() {
        try {
            this.instanceDB = new DBManager();
        } catch (SQLException ex) {
            System.out.println("Not Connected to DB");
        }
    }

    @FXML
    void handleLoginButtonAction(ActionEvent event) throws SQLException {
        String user = txtLogin.getText();
        String pass = txtPassword.getText();

        crewMember.setUsername(user);
        crewMember.setPassword(pass);

        // String user1 = user + pass + "08";
        //crewMember.setUsername(user1);
        System.out.println(user);
        System.out.println(pass);
        if ("admin".equals(user) && "admin".equals(pass)) {
            openCrewDetails();
        } else {
            if (instanceDB.validUser(crewMember)) {
                openCrew();
                int crew = instanceDB.findcrew(crewMember);
                String c_id = Integer.toString(crew);

                TextField objref1 = (TextField) root.lookup("#txtCrewID");
                objref1.setText(c_id);

                TextField objref2 = (TextField) root.lookup("#txtCrewUserName");
                objref2.setText(user);

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Not a Valid User", ButtonType.OK);
                alert.show();

            }
        }
    }

    @FXML
    void handleMyTaskButtonAction(ActionEvent event) throws SQLException {

        System.out.println("My Task");

        crewMember.setUsername(txtCrewUserName.getText());
        crewMember.setCrew_id(txtCrewID.getText());

        columnCrewWorkOrder.setCellValueFactory(new PropertyValueFactory<>("workOrderName"));
        columnCrewProgress.setCellValueFactory(new PropertyValueFactory<>("progress"));
        columnCrewSummary.setCellValueFactory(new PropertyValueFactory<>("summary"));
        columnWOStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableCrew.setItems(instanceDB.getCrewTask(crewMember));

        columnWOStatus.setCellValueFactory(
                new PropertyValueFactory<>("status"));
        columnWOStatus.setCellFactory(TextFieldTableCell.forTableColumn());
        columnWOStatus.setOnEditCommit((CellEditEvent<Crew, String> t) -> {
            ((Crew) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setStatus(t.getNewValue());

            rowData = t.getTableView().getItems().get(t.getTablePosition().getRow());

            System.out.println(t.getNewValue());
            System.out.println("Work Order : " + rowData.getWorkOrderName());

            crewMember.setWorkOrderName(rowData.getWorkOrderName());
            crewMember.setProgress(rowData.getProgress());
            crewMember.setSummary(rowData.getSummary());
            crewMember.setStatus(rowData.getStatus());

            //   tableCrew.getSelectionModel().selectedItemProperty().addListener(rowData.getProgress());
            if (t.getNewValue().equals("Started") || t.getNewValue().equals("Not Started")) {
                try {
                    instanceDB.updateStatusForCrew(crewMember);
                } catch (SQLException ex) {
                    System.out.println("Not Updated Record");
                }
            }
        });

        columnCrewProgress.setCellValueFactory(
                new PropertyValueFactory<>("progress"));
        columnCrewProgress.setCellFactory(TextFieldTableCell.forTableColumn());
        columnCrewProgress.setOnEditCommit((CellEditEvent<Crew, String> t) -> {
            rowData = t.getTableView().getItems().get(t.getTablePosition().getRow());

            crewMember.setWorkOrderName(rowData.getWorkOrderName());
            crewMember.setProgress(rowData.getProgress());
            crewMember.setSummary(rowData.getSummary());
            crewMember.setStatus(rowData.getStatus());
            crewMember.setCrew_id(txtCrewID.getText());

            if (!t.getNewValue().equals("100")) {
                ((Crew) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setProgress(t.getNewValue());
                crewMember.setProgress(rowData.getProgress());

                try {
                    instanceDB.updateProgress(crewMember);
                } catch (SQLException ex) {
                    System.out.println("Cannot Update Progress");
                }
            } else {
                crewMember.setProgress(t.getNewValue());
                try {
                    instanceDB.updateProgresswithStatus(crewMember);

//            System.out.println("Can't Change");
//            
//            Alert alert = new Alert(Alert.AlertType.ERROR, "No No NO ! Already Completed", ButtonType.OK);
//            alert.show();
//            rowData.setProgress("100");
                } catch (SQLException ex) {
                    System.out.println("updateProgresswithStatus not working");
                }
            }

            System.out.println("Work Order : " + rowData.getWorkOrderName());

            if (rowData.getStatus().equals("Started") && rowData.getProgress().equals("100")) {
                try {
                    instanceDB.updateStatusForCrewActive(crewMember);
                } catch (SQLException ex) {
                    System.out.println("Not Updated Record");
                }
            }
        });

    }

    Crew rowData = null;

    @FXML
    void handleShowListAction(ActionEvent event) {
        try {
            //openWorkOrder();
            columnCrewID.setCellValueFactory(new PropertyValueFactory<>("crew_id"));
            columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            columnWorkID.setCellValueFactory(new PropertyValueFactory<>("work_id"));
            columnWO.setCellValueFactory(new PropertyValueFactory<>("workOrderName"));
            columnProgress.setCellValueFactory(new PropertyValueFactory<>("progress"));
            columnStart.setCellValueFactory(new PropertyValueFactory<>("start"));
            columnEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
            columnSummary.setCellValueFactory(new PropertyValueFactory<>("summary"));
            columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            tableView.setItems(instanceDB.getCrew());
            tableView.setRowFactory(tv -> {
                TableRow<Crew> row = new TableRow<>();
                row.setOnMouseClicked(event2 -> {

                    Crew item = tableView.getSelectionModel().getSelectedItem();
                    System.out.println("My Crew ID:" + item.getCrew_id());

                    if (event2.getClickCount() == 2 && (!row.isEmpty())) {
                        rowData = row.getItem();
                        System.out.println(rowData.getProgress());

                        String p = rowData.getProgress();
                        String user = rowData.getFirstname() + rowData.getLastname() + "08";
                        try {

                            root = FXMLLoader.load(getClass().getResource("FXMLWorkOrder.fxml"));
                            TermProject.getPrimaryStage().setScene(new Scene(root));
                            TermProject.getPrimaryStage().show();
                            TermProject.setTitle("Work Order");

                            TextField objref = (TextField) root.lookup("#txtInstance");
                            objref.setText(user);
                            // txtWorkOrderName.setDisable(true);

                            TextField objrefa = (TextField) root.lookup("#txtID");
                            objrefa.setText(rowData.getCrew_id());

                            TextField objref6 = (TextField) root.lookup("#txtWOWorkID");
                            objref6.setText(rowData.getWork_id());

                            if (!rowData.getProgress().equals("100")) {
                                TextField objref1 = (TextField) root.lookup("#txtWorkOrderName");
                                objref1.setText(rowData.getWorkOrderName());

                                TextArea objref4 = (TextArea) root.lookup("#txtAreaSummary");
                                objref4.setText(rowData.getSummary());

                                TextField objref5 = (TextField) root.lookup("#txtProgress");
                                objref5.setText(rowData.getProgress());

                                final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                if (rowData.getStart() != null) {
                                    final LocalDate localDateStart = LocalDate.parse(rowData.getStart(), DATE_FORMAT);

                                    DatePicker objref2 = (DatePicker) root.lookup("#datePickerStartDate");
                                    objref2.setValue(localDateStart);

                                }

                                if (rowData.getEnd() != null) {
                                    final LocalDate localDateEnd = LocalDate.parse(rowData.getEnd(), DATE_FORMAT);

                                    DatePicker objref3 = (DatePicker) root.lookup("#datePickerEndDate");
                                    objref3.setValue(localDateEnd);

                                }
                            } else {
                                TextField objref1 = (TextField) root.lookup("#txtWorkOrderName");
                                objref1.setText(rowData.getWorkOrderName());
                                objref1.setDisable(true);

                                TextArea objref4 = (TextArea) root.lookup("#txtAreaSummary");
                                objref4.setText(rowData.getSummary());
                                objref4.setDisable(true);

                                TextField objref5 = (TextField) root.lookup("#txtProgress");
                                objref5.setText(rowData.getProgress());
                                objref5.setDisable(true);

                                final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                if (rowData.getStart() != null) {
                                    final LocalDate localDateStart = LocalDate.parse(rowData.getStart(), DATE_FORMAT);

                                    DatePicker objref2 = (DatePicker) root.lookup("#datePickerStartDate");
                                    objref2.setValue(localDateStart);
                                    objref2.setDisable(true);

                                }

                                if (rowData.getEnd() != null) {
                                    final LocalDate localDateEnd = LocalDate.parse(rowData.getEnd(), DATE_FORMAT);

                                    DatePicker objref3 = (DatePicker) root.lookup("#datePickerEndDate");
                                    objref3.setValue(localDateEnd);
                                    objref3.setDisable(true);
                                }

                                if (rowData.getWork_id() == null) {
                                    System.out.println("Null Bhai Null");
                                } else {
                                    System.out.println("Not Null");
                                    txtWorkOrderName.setEditable(false);
                                }

                            }
                        } catch (IOException ex) {
                            System.out.println("Blank Field");
                        }
                    }
                });
                return row;
            });

//            tableView.setRowFactory(tv -> {
//                TableRow<Crew> row = new TableRow<>();
//                row.setOnMouseClicked(event1 -> {
//                    if (event1.isControlDown() && event1.getClickCount() == 1 ){
//                    
//                    Crew rowValue = row.getItem();
//                    System.out.println(rowValue.getCrew_id()); }               
//                });
//                return row;
//       });
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void handlePieChartAction(ActionEvent event) throws SQLException {
        System.out.println("Pie Chart");
        Scene scene = new Scene(new Group());

        Stage stage = new Stage();
        stage.setTitle("Work Order Report");

        int completedWO = instanceDB.getCompletedWO("Completed");
        int startedWO = instanceDB.getCompletedWO("Started");
        int notStartedWO = instanceDB.getCompletedWO("Not Started");

        System.out.println("Completed : " + completedWO + " Started " + startedWO + " Not Started " + notStartedWO);

        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Not Started", notStartedWO),
                        new PieChart.Data("Started", startedWO),
                        new PieChart.Data("Completed", completedWO));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Work Order Status");

        final Label caption = new Label("");
        caption.setTextFill(Color.WHITE);
        caption.setStyle("-fx-font: 24 arial;");

        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    System.out.println("Went In");
                    caption.setTranslateX(e.getSceneX());
                    caption.setTranslateY(e.getSceneY());
                    caption.setText(String.valueOf(data.getPieValue()));
                }
            });
        }

        ((Group) scene.getRoot()).getChildren().addAll(chart, caption);
        stage.setScene(scene);
        stage.show();

//        ((Group) scene.getRoot()).getChildren().addAll(chart, caption);
//        TermProject.getPrimaryStage().setScene(scene);
//        TermProject.getPrimaryStage().show();
    }

    @FXML
    void handleDeleteWOButtonAction(ActionEvent event) throws SQLException {

        Crew selectedItem = tableView.getSelectionModel().getSelectedItem();
        String value = tableView.getSelectionModel().getSelectedItem().getCrew_id();
        String wo = tableView.getSelectionModel().getSelectedItem().getWork_id();

        crewMember.setCrew_id(value);
        crewMember.setWork_id(wo);

        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete work order", ButtonType.YES, ButtonType.NO);
        //alert.show();

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            instanceDB.DeleteWO(crewMember);
        } else {
            System.out.println("User Changed Mind");
        }

        System.out.println(value);
        System.out.println(wo);
        //instanceDB.Delete(value);
        //tableView.getItems().remove(selectedItem);
    }

    @FXML
    void handleDeleteCrewButtonAction(ActionEvent event) throws SQLException {

        Crew selectedItem = tableView.getSelectionModel().getSelectedItem();
        String value = tableView.getSelectionModel().getSelectedItem().getCrew_id();
        String wo = tableView.getSelectionModel().getSelectedItem().getWork_id();

        crewMember.setCrew_id(value);
        crewMember.setWork_id(wo);

        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete crew member", ButtonType.YES, ButtonType.NO);
        //alert.show();

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            instanceDB.DeleteCrew(crewMember);
        } else {
            System.out.println("User Changed Mind");
        }

        System.out.println(value);
        System.out.println(wo);
        //instanceDB.Delete(value);
        //tableView.getItems().remove(selectedItem);
    }

    @FXML
    void handleResetButtonAction(ActionEvent event) {
        txtLogin.setText("");
        txtPassword.setText("");
    }

    @FXML
    void handleShowDetailAction(ActionEvent event) throws SQLException {
        System.out.println("Show detail of Crew");
    }

    @FXML
    void handleAddCrewResetAction(ActionEvent event) {
        txtFirstName.setText("");
        txtLastname.setText("");
        txtDob.getEditor().clear();
        txtPhone.setText("");
        txtEmail.setText("");
        txtGender.setText("");
        txtAddCrewPassword.setText("");
        txtTitle.setText("");
    }

    @FXML
    void handleCreateWorkOrderAction(ActionEvent event) throws IOException, SQLException {

        Crew crew = tableView.getSelectionModel().getSelectedItem();

        if (tableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Click Show List And Select a Row", ButtonType.OK);
            alert.show();
        } else if (crew != null && crew.getProgress().equals("100")) {
            //true -> No record
            // false -> record
            boolean test = instanceDB.checkActiveWorkOrder(crew);
            if (test) {
                System.out.println("Create Clicked");
                root = FXMLLoader.load(getClass().getResource("FXMLWorkOrder.fxml"));
                TermProject.getPrimaryStage().setScene(new Scene(root));
                TermProject.getPrimaryStage().show();
                TermProject.setTitle("Work Order");

                TextField objref = (TextField) root.lookup("#txtID");
                objref.setText(crew.getCrew_id());
                String user = crew.getFirstname() + crew.getLastname() + "08";
                TextField objref1 = (TextField) root.lookup("#txtInstance");
                objref1.setText(user);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "New Record already created", ButtonType.OK);
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Complete previous work order to create new", ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    void handleWorkOrderLoadCrewAction(ActionEvent event) {
        txtInstance.setText("Kishan");
    }

    @FXML
    void handleWorkOrderSaveAction(ActionEvent event) throws SQLException {
        crewMember.setWorkOrderName(txtWorkOrderName.getText());
        crewMember.setStartDate(datePickerStartDate.getValue());
        crewMember.setLastDate(datePickerEndDate.getValue());
        crewMember.setSummary(txtAreaSummary.getText());
        crewMember.setProgress(txtProgress.getText());
        crewMember.setUsername(txtInstance.getText());
        crewMember.setCrew_id(txtID.getText());
        crewMember.setWork_id(txtWOWorkID.getText());

        String a = txtWOWorkID.getText();

        String pValue = txtProgress.getText();

        LocalDate end = datePickerEndDate.getValue();
        LocalDate start = datePickerStartDate.getValue();

        boolean validRange = end.isAfter(start);

        boolean key = instanceDB.checkActiveWorkOrder(crewMember);
        int crew_id = instanceDB.findcrew(crewMember);

        if ((pValue.isEmpty() || !pValue.matches("[0-9]+"))) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter Valid Progess Info", ButtonType.OK);
            alert.show();
        } else {

            if (!validRange) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Date range is not Valid", ButtonType.OK);
                alert.show();
            } else {
                int progressValue = Integer.parseInt(pValue);

                if (progressValue > 100 || progressValue < 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Should be between 1 and 100", ButtonType.OK);
                    alert.show();
                } else {

                    // true : No record found
                    // False: Record Found
                    boolean switchForWO = instanceDB.checkValidNameForWorkOrder(crewMember);

                    // if key = true -> No Record Found
                    // if !key = false -> Record found
                    if (key && switchForWO) {
                        if (pValue.equals("100")) {
                            instanceDB.saveWorkOrderC(crewMember, crew_id);
                        } else {
                            instanceDB.saveWorkOrder(crewMember, crew_id);
                        }

                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Work Order Created", ButtonType.OK);
                        alert.show();
                    } else if (pValue.equals("100") && !key) {
                        instanceDB.updateStatus(crewMember);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Work Order Saved", ButtonType.OK);
                        alert.show();
                    } else if (!switchForWO && !a.equals("")) {
                        instanceDB.updateWorkOrder(crewMember, crew_id);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Work Order Saved", ButtonType.OK);
                        alert.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Duplicate or Not a Valid Work Order Name", ButtonType.OK);
                        alert.show();
                    }

                }

            }

        }

    }

    @FXML
    void handleNewCrewButtonAction(ActionEvent event) {
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
    void handleLogoutButtonAction(ActionEvent event) {
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
    void handleCrewDetailsAction(ActionEvent event) throws SQLException {
        openCrewDetails();
    }

    @FXML
    void handleSaveAction(ActionEvent event) throws SQLException {

        String firstName = txtFirstName.getText();
        String lastName = txtLastname.getText();
        LocalDate dob = txtDob.getValue();

        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String password = txtAddCrewPassword.getText();
        String gender = txtGender.getText();
        String title = txtTitle.getText();

        String formattedDate = "";

        try {
            formattedDate = dob.format(DateTimeFormatter.ISO_DATE);
        } catch (Exception e) {
            System.out.println("Date Null");
        }

        if (firstName.isEmpty() || !firstName.matches("^[a-zA-Z0-9]*$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter Valid First Name", ButtonType.OK);
            alert.show();
        } else if (lastName.isEmpty() || !lastName.matches("^[a-zA-Z0-9]*$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter Valid Last Name", ButtonType.OK);
            alert.show();
        } else if (formattedDate.isEmpty() || !formattedDate.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter Valid DOB", ButtonType.OK);
            alert.show();
        } else if (phone.isEmpty() || !phone.matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter Valid Phone Number", ButtonType.OK);
            alert.show();
        } else if (email.isEmpty() || !email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter Valid Email Address", ButtonType.OK);
            alert.show();
        } else if (gender.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter Valid Gender", ButtonType.OK);
            alert.show();
        } else if (password.isEmpty() || !password.matches("^[a-zA-Z0-9]*$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter Valid Password", ButtonType.OK);
            alert.show();
        } else if (title.isEmpty() || !title.matches("^[a-zA-Z0-9]*$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter Valid Title", ButtonType.OK);
            alert.show();
        } else {

            if (gender.equals("Male") || gender.equals("Female")) {
                crewMember.setFirstname(txtFirstName.getText());
                crewMember.setLastname(txtLastname.getText());
                crewMember.setDob(txtDob.getValue());
                crewMember.setPhone(txtPhone.getText());
                crewMember.setEmail(txtEmail.getText());
                crewMember.setGender(txtGender.getText());
                crewMember.setPassword(txtAddCrewPassword.getText());
                crewMember.setTitle(txtTitle.getText());
                DBManager queryExecuter = new DBManager();
                queryExecuter.saveData(crewMember);

                txtFirstName.setText("");
                txtLastname.setText("");
                txtDob.getEditor().clear();
                txtPhone.setText("");
                txtEmail.setText("");
                txtGender.setText("");
                txtAddCrewPassword.setText("");
                txtTitle.setText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Enter Valid Gender", ButtonType.OK);
                alert.show();
            }

        }

    }

    @FXML
    void handleRemoveAction(ActionEvent event) {
        System.out.println("in");

        Crew selectedItem = tableView.getSelectionModel().getSelectedItem();
        String value = tableView.getSelectionModel().getSelectedItem().getCrew_id();
        String wo = tableView.getSelectionModel().getSelectedItem().getWorkOrderName();

        System.out.println(value);
        System.out.println(wo);
        //instanceDB.Delete(value);
        tableView.getItems().remove(selectedItem);

    }

    ;

    // ------------  Not the CONTROLLER -----------------------------------
    public void openCrew() {
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

    public void openCrewDetails() {
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
