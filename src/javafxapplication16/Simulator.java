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
public class Simulator {

    Customer[] customer_table_detail;
    double SimulationClock = 0;
    double ArrivalMean, ServiceMean;
    int CustomerCapacity;
    double longService = 0;
    int maxQL = 0;
    SimTable[] Sim_table_detail;
    double AvgQlength = 0, AvgBuzy = 0;

    Customer[] simulate(double arr, double ser, int cap) {
        ArrivalMean = arr;
        ServiceMean = ser;
        CustomerCapacity = cap;
        customer_table_detail = new Customer[cap];
        double[] arrivals = randexp(cap, arr);
        // {0.4, 1.2, 0.5, 1.7, 0.2, 1.6};
        double[] service = randexp(cap, ser);
        //  {2, 0.7, 0.2, 1.1, 3.7, 0.6};
        for (int i = 0; i < cap; i++) {
            customer_table_detail[i] = new Customer();
            customer_table_detail[i].ArrivalTime = arrivals[i];
            customer_table_detail[i].ServiceTime = service[i];

        }
        customer_table_detail[0].AbsArrival = customer_table_detail[0].ArrivalTime;
        customer_table_detail[0].Departure = customer_table_detail[0].AbsArrival + customer_table_detail[0].ServiceTime;
        for (int i = 1; i < cap; i++) {
            customer_table_detail[i].AbsArrival = customer_table_detail[i].ArrivalTime + customer_table_detail[i - 1].AbsArrival;
            if (customer_table_detail[i - 1].Departure - customer_table_detail[i].AbsArrival > 0) {
                customer_table_detail[i].Delay = customer_table_detail[i - 1].Departure - customer_table_detail[i].AbsArrival;
            }
            customer_table_detail[i].Departure = customer_table_detail[i].AbsArrival + customer_table_detail[i].ServiceTime + customer_table_detail[i].Delay;

        }

        return customer_table_detail;
    }

    double[] randexp(int size, double mean) {
        double[] list = new double[size];
        for (int i = 0; i < size; i++) {
            list[i] = Math.abs(mean * Math.log10(Math.random()));

        }

        return list;
    }

    SimTable[] StartSimulation() {

        event[] future_events = new event[customer_table_detail.length * 2];

        for (int i = 0; i < customer_table_detail.length; i++) {
            future_events[i] = new event();
            future_events[i].eventtype = 1;
            future_events[i].time = customer_table_detail[i].AbsArrival;
            future_events[i + customer_table_detail.length] = new event();
            future_events[i + customer_table_detail.length].eventtype = 2;
            future_events[i + customer_table_detail.length].time = customer_table_detail[i].Departure;
            AvgBuzy += customer_table_detail[i].ServiceTime;
            if (customer_table_detail[i].ServiceTime > longService) {
                longService = customer_table_detail[i].ServiceTime;
            }

        }

        for (int i = 0; i < future_events.length; i++) {
            for (int j = 0; j < future_events.length; j++) {
                if (future_events[i].time < future_events[j].time) {
                    event temp = future_events[i];
                    future_events[i] = future_events[j];
                    future_events[j] = temp;
                }
            }
        }
        Sim_table_detail = new SimTable[future_events.length];
        int ql = 0;
        int status = 1;
        SimulationClock = future_events[0].time;
        Sim_table_detail[0] = new SimTable(future_events[0].eventtype, status, future_events[0].time, ql);

        for (int i = 1; i < Sim_table_detail.length; i++) {
            SimulationClock = future_events[i].time;
            if (future_events[i].eventtype == 2 && ql == 0) {
                status = 0;
            }
            if (future_events[i].eventtype == 1 && status == 1) {
                ql++;
            } else if (future_events[i].eventtype == 1 && status != 1) {
                status = 1;
            } else if (future_events[i].eventtype == 2 && ql > 0) {
                ql--;
            }
            Sim_table_detail[i] = new SimTable(future_events[i].eventtype, status, future_events[i].time, ql);

            AvgQlength += (Sim_table_detail[i].time - Sim_table_detail[i - 1].time) * Sim_table_detail[i - 1].queueL;
            if (Sim_table_detail[i].queueL > maxQL) {
                maxQL = Sim_table_detail[i].queueL;
            }

        }

        return Sim_table_detail;
    }

    String[] reportGeneration() {
        String report[] = new String[8];
        report[0] = "Mean Arrival : " + (ArrivalMean) + "\n";
        report[1] = "Mean Service : " + (ServiceMean) + "\n";
        report[2] = "Total No. Customer : " + CustomerCapacity + "\n";
        report[3] = "Total Time Of Simulation : " + truncDouble(SimulationClock) + "\n";
        report[4] = "Average Queue Length : " + truncDouble(AvgQlength) + "\n";
        report[5] = "Maximum Queue Length : " + maxQL + "\n";
        report[6] = "Average Buzy Time of Server " + truncDouble(AvgBuzy) + "\n";
        report[7] = "Long Service : " + truncDouble(longService) + "\n";

        return report;

    }

    double truncDouble(Double d) {
        String str = Double.toString(d);
        String temp = "";
        boolean isdouble = false;
        for (int a = 0; a < str.length(); a++) {
            temp += str.charAt(a);
            if (str.charAt(a) == '.') {
                isdouble = true;
                if (str.length() > a + 3) {
                    temp += str.charAt(a + 1) + "" + str.charAt(a + 2) + "" + str.charAt(a + 3);
                } else {
                    while (a < str.length()) {
                        temp += str.charAt(a);
                        a++;
                    }
                }
                break;
            }

        }

        if (isdouble) {
            return Double.valueOf(temp);
        }

        return d;
    }
}

class event {

    int eventtype;
    double time;
}
