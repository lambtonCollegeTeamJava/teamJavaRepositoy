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
public class WorkOrder {
    
  private LocalDate startDate;
  private LocalDate lastDate;
  private String summary;
  private String progress;
  private String workOrderName;

    public String getWorkOrderName() {
        return workOrderName;
    }

    public void setWorkOrderName(String workOrderName) {
        this.workOrderName = workOrderName;
    }
  

    public WorkOrder() {
    }
    
    public WorkOrder(String workOrderName, String progress) {
        this.workOrderName = workOrderName;
        this.progress = progress;
    }

    public WorkOrder(LocalDate startDate, LocalDate lastDate, String summary, String progress) {
        this.startDate = startDate;
        this.lastDate = lastDate;
        this.summary = summary;
        this.progress = progress;
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
    
}
