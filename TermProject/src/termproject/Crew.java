/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.time.LocalDate;

/**
 *
 * @author c0720648
 */
public class Crew {

    private String crew_id;
    private String work_id;
    private String firstname;
    private String lastname;
    private LocalDate dob;
    private String phone;
    private String email;
    private String gender;
    private String password;
    private String title;
    private String username;
    private LocalDate startDate;
    private LocalDate lastDate;
    private String summary;
    private String progress;
    private String workOrderName;
    private String status;

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public String getCrew_id() {
        return crew_id;
    }

    public void setCrew_id(String crew_id) {
        this.crew_id = crew_id;
    }

    private String start;
    private String end;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Crew(String workOrderName, String summary, String progress, String status) {
        this.workOrderName = workOrderName;
        this.summary = summary;
        this.progress = progress;
        this.status = status;

    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getWorkOrderName() {
        return workOrderName;
    }

    public void setWorkOrderName(String workOrderName) {
        this.workOrderName = workOrderName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Crew() {
    }

    public Crew(String crew_id, String lastname, String firstname, String title,String work_id, String workOrderName, String progress, String start, String end, String summary, String status) {
        this.crew_id = crew_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.title = title;
        this.work_id = work_id;
        this.workOrderName = workOrderName;
        this.progress = progress;
        this.start = start;
        this.end = end;
        this.summary = summary;
        this.status = status;
    }
    
    
    public Crew(String crew_id, String lastname, String firstname, String title,String work_id, String workOrderName, String progress, String summary, String status) {
        this.crew_id = crew_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.title = title;
        this.work_id = work_id;
        this.workOrderName = workOrderName;
        this.progress = progress;
        this.summary = summary;
        this.status = status;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
