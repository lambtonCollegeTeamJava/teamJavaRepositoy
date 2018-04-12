/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author c0720648
 */
public class DBManager {
    
    // public final String DB_URL = "jdbc:sqlite:F://Sem 2//Java EE//TermProject//crewmanagement.db";
     public final String DB_URL = "jdbc:sqlite:F://Sem 2//Java EE//TermProject//crewmanagement.db";
    //public final String DB_URL = "jdbc:derby://localhost:1527/Database/personAccounts;create=true";

    // Field for the database connection
    private Connection conn;
    Statement stmt;
    
    
     /**
     *
     * @throws SQLException
     */
    public DBManager() throws SQLException {
        // Create a connection to the database      
        conn = DriverManager.getConnection(DB_URL);
        System.out.println("Connected DB");
    }
    
    
    /** Create Table
     *
     * @throws SQLException
     */
    public void createTableCrew() throws SQLException {
        try {
            stmt = conn.createStatement();
            String sql = "CREATE TABLE crew( crew_id integer primary key autoincrement, firstname VARCHAR(30),  lastname VARCHAR(30), dob VARCHAR(15), phone int(12), email VARCHAR(20),gender varchar(6), password VARCHAR(20), username varchar(40) )";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            stmt.close();
        }
    }
    
    
    /** Create Table
     *
     * @throws SQLException
     */
    public void createTableWorkOrder() throws SQLException {
        try {
            stmt = conn.createStatement();
            String sql = "CREATE TABLE workorder( work_order_id integer primary key autoincrement, start_date VARCHAR(30),  end_date VARCHAR(30), summary VARCHAR(50), progress varchar(10),crew_id integer, foreign key(crew_id) references crew(crew_id) )";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            stmt.close();
        }
    }
    
    /** Drop the table
     *
     * @throws SQLException
     */
    public void dropTable() throws SQLException {
        try {
            stmt = conn.createStatement();
            String sqlCrew = "DROP TABLE crew";
            stmt.execute(sqlCrew);
            String sqlWO = "DROP TABLE workorder";
            stmt.execute(sqlWO);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            stmt.close();
        }
    }
    
    /** Close the connection
     *
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        conn.close();
    }

    void saveData(Crew crewMember) throws SQLException {
       try {
            stmt = conn.createStatement();
            String userName = crewMember.getFirstname() + crewMember.getLastname() + "08";

            String query = "INSERT INTO crew(firstname, lastname, dob,phone,email,gender,password,username) VALUES('"
                    + crewMember.getFirstname() + "', '"
                    + crewMember.getLastname() + "', '" + crewMember.getDob() + "', '"
                    + crewMember.getPhone() + "', '" + crewMember.getEmail() + "'" + ",'" + crewMember.getGender() + "'" + ",'" + crewMember.getPassword() + "'" + ",'" + userName +"'" + ")";
            System.out.println(query);
            System.out.println("Data Insertion Completed");
            stmt.execute(query);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Username is: " + userName, ButtonType.OK);
            alert.show();
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            stmt.close();
        }
    }
    
}
