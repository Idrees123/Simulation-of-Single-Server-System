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
public class Customer {

    public Customer(double ArrivalTime, double ServiceTime, double AbsArrival, double Departure, double Delay) {
        this.ArrivalTime = ArrivalTime;
        this.ServiceTime = ServiceTime;
        this.AbsArrival = AbsArrival;
        this.Departure = Departure;
        this.Delay = Delay;
    }

    public Customer() {
        this.ArrivalTime =0;
        this.ServiceTime = 0;
        this.AbsArrival=0;
        this.Departure = 0;
        this.Delay =0;
    }

    public double getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(double ArrivalTime) {
        this.ArrivalTime = ArrivalTime;
    }

    public double getServiceTime() {
        return ServiceTime;
    }

    public void setServiceTime(double ServiceTime) {
        this.ServiceTime = ServiceTime;
    }

    public double getAbsArrival() {
        return AbsArrival;
    }

    public void setAbsArrival(double AbsArrival) {
        this.AbsArrival = AbsArrival;
    }

    public double getDeparture() {
        return Departure;
    }

    public void setDeparture(double Departure) {
        this.Departure = Departure;
    }

    public double getDelay() {
        return Delay;
    }

    public void setDelay(double Delay) {
        this.Delay = Delay;
    }
    double ArrivalTime,ServiceTime,AbsArrival,Departure,Delay;
    
    
}
