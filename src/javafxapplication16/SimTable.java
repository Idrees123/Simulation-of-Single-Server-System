/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication16;

/**
 *
 * @author IDREESS SHABBIR
 */
class SimTable {

    public SimTable(int event, int status, double time, int queueL) {
        if (event == 1) {
            this.event = "arrival";
        } else {
            this.event = "departure";
        }
        if (status == 1) {
            this.status = "buzy";
        } else {
            this.status = "idle";
        }
        this.time = time;
        this.queueL = queueL;
    }

    public SimTable() {
    }
    String event, status;
    double time;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getQueueL() {
        return queueL;
    }

    public void setQueueL(int queueL) {
        this.queueL = queueL;
    }
    int queueL;

}
