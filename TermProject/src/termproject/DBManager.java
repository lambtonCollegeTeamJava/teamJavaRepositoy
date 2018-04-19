/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author c0720648
 */
public class DBManager {

    

    /** This is variable address for connecting to Database
     *
     */
    public final String DB_URL = "jdbc:sqlite:crewmanagement.db";
    // public final String DB_URL = "jdbc:sqlite:F://Sem 2//Java EE//TermProject//crewmanagement.db";
    //public final String DB_URL = "jdbc:sqlite://Users/Kishan/Desktop/TermProject/crewmanagement.db";
    //public final String DB_URL = "jdbc:derby://localhost:1527/Database/personAccounts;create=true";

    // Field for the database connection
    private Connection conn;
    Statement stmt;

    /** Constructor which connects to database
     *
     * @throws SQLException
     */
    public DBManager() throws SQLException {
        // Create a connection to the database      
        conn = DriverManager.getConnection(DB_URL);
        System.out.println("Connected DB");
    }

    /**
     * Create Table
     *
     * @throws SQLException
     */
    public void createTableCrew() throws SQLException {
        try {
            stmt = conn.createStatement();
            String sql = "CREATE TABLE crew( crew_id integer primary key autoincrement, firstname VARCHAR(30),  lastname VARCHAR(30), dob VARCHAR(15), phone int(12), email VARCHAR(20),gender varchar(6), password VARCHAR(20), username varchar(40), title varchar(20) )";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            stmt.close();
        }
    }

    /**
     * Create Table : Work order table create statement
     *
     * @throws SQLException
     */
    public void createTableWorkOrder() throws SQLException {
        try {
            stmt = conn.createStatement();
            String sql = "CREATE TABLE workorder( work_order_id integer primary key autoincrement,work_order_name varchar(30), start_date VARCHAR(30),  end_date VARCHAR(30), summary VARCHAR(50), progress varchar(10),active varchar(10),status varchar(20), crew_id integer, foreign key(crew_id) references crew(crew_id) )";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            stmt.close();
        }
    }

    /**
     * Drop the table : Drop Statement for crew & workorder
     *
     * @throws SQLException
     */
    public void dropTable() throws SQLException {
        try {
            stmt = conn.createStatement();
            String sqlCrew = "DROP TABLE crew";
           // stmt.execute(sqlCrew);
            String sqlWO = "DROP TABLE workorder";
            stmt.execute(sqlWO);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            stmt.close();
        }
    }

    /**
     * Close the connection : Close the Connection
     *
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        conn.close();
    }

    /**
     *
     * @param crewMember : instance of Crew.Java for getting all values related to crew
     * @throws SQLException
     */
    public void saveData(Crew crewMember) throws SQLException {
        try {
            stmt = conn.createStatement();
            String userName = crewMember.getFirstname() + crewMember.getLastname() + "08";

            String query = "INSERT INTO crew(firstname, lastname, dob,phone,email,gender,password,username, title) VALUES('"
                    + crewMember.getFirstname() + "', '"
                    + crewMember.getLastname() + "', '" + crewMember.getDob() + "', '"
                    + crewMember.getPhone() + "', '" + crewMember.getEmail() + "'" + ",'" + crewMember.getGender() + "'" + ",'" + crewMember.getPassword() + "'" + ",'" + userName + "','" + crewMember.getTitle() + "')";
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
    PreparedStatement ps = null;
    ResultSet rs = null;

    /**
     *
     * @return : Observable List to display on Table view
     * @throws SQLException
     */
    public ObservableList<Crew> getCrew() throws SQLException {

        String query = "Select lastname, firstname, title from crew";

        String query1 = "select lastname,firstname,title,work_order_name, progress from crew \n"
                + "left join workorder using (crew_id); ";
         String query2 = "select crew_id, lastname,firstname,title, work_order_id,work_order_name, progress, start_date, end_date, summary, status from crew \n"
                + "left join workorder using (crew_id); ";
        
        
        try {
            conn = DriverManager.getConnection(DB_URL);
            ps = conn.prepareStatement(query2);
            rs = ps.executeQuery();
            System.out.println(rs);
            ObservableList<Crew> data = FXCollections.observableArrayList();;
            while (rs.next()) {
                data.add(new Crew(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)));
            }
            return data;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            ps.close();
            rs.close();
        }

    }
    
    /**
     *
     * @param crewMember : instance of Crew.Java for getting all values related to crew
     * @return : Observable List to display on Table view
     * @throws SQLException
     */
    public ObservableList<Crew> getCrewTask(Crew crewMember) throws SQLException {

         String query2 = "select  work_order_name, summary, progress, status from crew join workorder using (crew_id) where username = '" + crewMember.getUsername() + "' and crew_id = '" + crewMember.getCrew_id() + "';";
        System.out.println(query2);
        
        try {
            conn = DriverManager.getConnection(DB_URL);
            ps = conn.prepareStatement(query2);
            rs = ps.executeQuery();
            System.out.println(rs);
            ObservableList<Crew> data = FXCollections.observableArrayList();;
            while (rs.next()) {
                data.add(new Crew(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            return data;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            ps.close();
            rs.close();
        }

    }
    
    /**
     *
     * @param crewMember : instance of Crew.Java for getting all values related to crew
     * @param crew_id 
     * @throws SQLException
     */
    public void saveWorkOrder(Crew crewMember, int crew_id) throws SQLException {
        try {
            stmt = conn.createStatement();
            String query = "INSERT INTO workorder( work_order_name,start_date, end_date, summary, progress,active, status,crew_id) VALUES('"
                  +crewMember.getWorkOrderName() + "','"  + crewMember.getStartDate() + "', '"
                    + crewMember.getLastDate() + "', '" + crewMember.getSummary() + "', '"
                    + crewMember.getProgress() + "'," + "'1', 'Not Started'," + crew_id + ")";
            System.out.println(query);
            stmt.execute(query);
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            stmt.close();
        }
    }
    
    /**
     *
     * @param crewMember : instance of Crew.Java for getting all values related to crew
     * @param crew_id
     * @throws SQLException
     */
    public void saveWorkOrderC(Crew crewMember, int crew_id) throws SQLException {
        try {
            stmt = conn.createStatement();
            String query = "INSERT INTO workorder( work_order_name,start_date, end_date, summary, progress,active, status,crew_id) VALUES('"
                  +crewMember.getWorkOrderName() + "','"  + crewMember.getStartDate() + "', '"
                    + crewMember.getLastDate() + "', '" + crewMember.getSummary() + "', '"
                    + crewMember.getProgress() + "'," + "'1', 'Completed'," + crew_id + ")";
            System.out.println(query);
            stmt.execute(query);
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            stmt.close();
        }
    }

    /**
     *
     * @param crewMember : instance of Crew.Java for getting all values related to crew
     * @return
     * @throws SQLException
     */
    public int findcrew(Crew crewMember) throws SQLException {
        try {
            //stmt = conn.createStatement();
            String query = "select crew_id from crew where username= '" + crewMember.getUsername() + "';";

            conn = DriverManager.getConnection(DB_URL);
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            int season = -1;
            if (rs.next()) {
                season = rs.getInt(1);
                return season;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            ps.close();
            rs.close();
        }
        return -1;
    }

    /**
     *
     * @param crewMember : instance of Crew.Java for getting all values related to crew
     * @return : true if no active work order and false if active work order
     * @throws SQLException
     */
    public boolean checkActiveWorkOrder(Crew crewMember) throws SQLException {
        
        try {
            //stmt = conn.createStatement();
            String query = "select crew_id, active from crew  join workorder using (crew_id) where crew_id = '"  + crewMember.getCrew_id() + "' and active = 1";
            System.out.println(query);
            conn = DriverManager.getConnection(DB_URL);
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            int count = 0;
            if (rs.next()) {
                count++;
            }

            return count == 0;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            ps.close();
            rs.close();
        }
        return false;
    }

    /**
     *
     * @param crewMember : instance of Crew.Java for getting all values related to crew
     * @param crew_id
     * @throws SQLException
     */
    public void updateWorkOrder(Crew crewMember, int crew_id) throws SQLException {
        try {
            stmt = conn.createStatement();
            String query = "update workorder set work_order_name= '" + crewMember.getWorkOrderName() + "', start_date= '" + crewMember.getStartDate() + "', end_date = '" + crewMember.getLastDate() + "', summary = '" + crewMember.getSummary() + "', progress = '" + crewMember.getProgress() + "' where crew_id = " + crewMember.getCrew_id() + " and work_order_id = '" +  crewMember.getWork_id()  +     "';";
            System.out.println(query);
            stmt.execute(query);
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            stmt.close();
        }
        
    }

    /**
     *
     * @param crewMember : instance of Crew.Java for getting all values related to crew
     * @throws SQLException
     */
    public void updateStatus(Crew crewMember) throws SQLException {
        try {
            stmt = conn.createStatement();
            String query = "update workorder set work_order_name= '" + crewMember.getWorkOrderName() + "', start_date= '" + crewMember.getStartDate() + "', end_date = '" + crewMember.getLastDate() + "', summary = '" + crewMember.getSummary() + "', progress = '" + crewMember.getProgress() + "', active = 0, status = 'Completed' where crew_id = " + crewMember.getCrew_id() + " and work_order_id = '" +  crewMember.getWork_id()  +     "';";
            System.out.println(query);
            stmt.execute(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            stmt.close();
        } 
    }
    
    /**
     *
     * @param crewMember : instance of Crew.Java for getting all values related to crew
     * @throws SQLException
     */
    public void updateStatusForCrew(Crew crewMember) throws SQLException {
        try {
            stmt = conn.createStatement();
            String query = "update workorder set work_order_name= '" + crewMember.getWorkOrderName() +  "', summary = '" + crewMember.getSummary() + "', progress = '" + crewMember.getProgress() + "', active = 1, status = '" + crewMember.getStatus() + "' where crew_id = " + crewMember.getCrew_id() + " and work_order_name = '" + crewMember.getWorkOrderName() + "';";
            System.out.println(query);
            stmt.execute(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            stmt.close();
        } 
    }
    
    /**
     *
     * @param crewMember : instance of Crew.Java for getting all values related to crew
     * @throws SQLException
     */
    public void updateStatusForCrewActive(Crew crewMember) throws SQLException {
        try {
            stmt = conn.createStatement();
            String query = "update workorder set work_order_name= '" + crewMember.getWorkOrderName() +  "', summary = '" + crewMember.getSummary() + "', progress = '" + crewMember.getProgress() + "', active = 0, status = '" + crewMember.getStatus() + "' where crew_id = " + crewMember.getCrew_id() + ";";
            System.out.println(query);
            stmt.execute(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            stmt.close();
        } 
    }
    
    /**
     *
     * @param crewMember : instance of Crew.Java for getting all values related to crew
     * @return : true = valid user and false = invalid user
     * @throws SQLException
     */
    public boolean validUser(Crew crewMember) throws SQLException {
        String sql = "SELECT username, password FROM crew where username = '" + crewMember.getUsername() + "' and password ='" + crewMember.getPassword() + "';";
        System.out.println(sql);
       int count = 0;
       try {
           //DriverManager.getConnection(DB_URL);
           stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql);
           // loop through the result set
           while (rs.next()) {
               String dbData = rs.getString("username");
               String dbDataName = rs.getString("password");
               count++;
               if (dbData.equals(crewMember.getUsername()) && dbDataName.equals(crewMember.getPassword()) && count==1) {
                   return true;
               }
               else {
                   System.out.println("Not a Valid User");
                   return false;
               }
           }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            stmt.close();
        }
        return false;
    }

    /**
     *
     * @param crewMember : instance of Crew.Java for getting all values related to crew
     * @throws SQLException
     */
    public void updateProgress(Crew crewMember) throws SQLException {
        
        try {
            stmt = conn.createStatement();
            String query = "update workorder set progress = '" + crewMember.getProgress() +"' where crew_id = " + crewMember.getCrew_id() + " and work_order_name = '" + crewMember.getWorkOrderName() + "';";
            System.out.println(query);
            stmt.execute(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            stmt.close();
        } 
    }

    /**
     *
     * @param crewMember : instance of Crew.Java for getting all values related to crew
     * @throws SQLException
     */
    public void updateProgresswithStatus(Crew crewMember) throws SQLException {
        try {
            stmt = conn.createStatement();
            String query = "update workorder set progress = '" + crewMember.getProgress() +"' , status = 'Completed', active = 0 where crew_id = " + crewMember.getCrew_id() + " and work_order_name = '" + crewMember.getWorkOrderName() + "';";
            System.out.println(query);
            stmt.execute(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            stmt.close();
        } 
    }

    /**
     *
     * @param crewMember : instance of Crew.Java for getting all values related to crew
     * @return : true if no duplicate name and false if duplicate name
     * @throws SQLException
     */
    public boolean checkValidNameForWorkOrder(Crew crewMember) throws SQLException {
        String sql = "SELECT work_order_name FROM workorder where work_order_name = '" + crewMember.getWorkOrderName() + "';";
        System.out.println(sql);
       int count = 0;
       try {
           //DriverManager.getConnection(DB_URL);
           stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql);
           // loop through the result set
           while (rs.next()) {
               count++;              
           }
            return count == 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            stmt.close();
        }
        
    }

    /**
     *
     * @param s : Status of work order
     * @return : count of work order with specific status
     * @throws SQLException
     */
    public int getCompletedWO(String s) throws SQLException {
        
        String sql = "SELECT status from workorder where status = '" + s +"'";  
        System.out.println(sql);
       int count = 0;
       try {
           //DriverManager.getConnection(DB_URL);
           stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql);
           // loop through the result set
           while (rs.next()) {
               count++;
               }
           return count;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            stmt.close();
        }
        return -1;
        
    }

    /**
     *
     * @param crewMember : : instance of Crew.Java for getting all values related to crew
     * @throws SQLException
     */
    public void DeleteWO(Crew crewMember) throws SQLException {
        try {
            stmt = conn.createStatement();
            String query = "delete from workorder where work_order_id = '" + crewMember.getWork_id() + "';" ;
           // String query2 = "delete from crew where crew_id = '" + crewMember.getCrew_id() + "';";
            System.out.println(query);
            //System.out.println(query2);
            stmt.execute(query);
            //stmt.execute(query2);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            stmt.close();
        } 
        
    }

    /**
     *
     * @param crewMember : instance of Crew.Java for getting all values related to crew
     * @throws SQLException
     */
    public void DeleteCrew(Crew crewMember) throws SQLException {
        try {
            stmt = conn.createStatement();
            String query = "delete from workorder where crew_id = '" + crewMember.getCrew_id() + "';";
            String query2 = "delete from crew where crew_id = '" + crewMember.getCrew_id() + "';";
            //System.out.println(query);
            System.out.println(query2);
            stmt.execute(query);
            stmt.execute(query2);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            stmt.close();
        } 
        
    }

    


}
