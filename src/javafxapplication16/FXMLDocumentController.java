/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication16;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author IDREESS SHABBIR
 */
public class FXMLDocumentController implements Initializable {

    Simulator simObject;

    @FXML
    private Label label;
    @FXML
    private TextField arrivalmean;
    @FXML
    private TextField servicemean;
    @FXML
    private TextField capacity;
    @FXML
    private Button run;
    @FXML
    private TableView<Customer> Customertable;
    @FXML
    private TableColumn<TableView, Double> arrival;

    @FXML
    private TableColumn<TableView, Double> service;
    @FXML
    private TableColumn<TableView, Double> absolute;
    @FXML
    private TableColumn<TableView, Double> delay;
    @FXML
    private TableColumn<TableView, Double> departure;
    @FXML
    private Button report;
    @FXML
    private Button St;
    @FXML
    private Button Qt;
    @FXML
    private Button E_d;
    Frame f, f1;
    sim_Table s;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (arrivalmean.getText() != null && servicemean.getText() != null && capacity.getText() != null) {
            double arrival_mean = Double.parseDouble(arrivalmean.getText());
            double service_mean = Double.parseDouble(servicemean.getText());
            int custcapacity = Integer.parseInt(capacity.getText());
            simObject = new Simulator();
            Customer[] Customer_table = simObject.simulate(arrival_mean, service_mean, custcapacity);

            ObservableList<Customer> tabledata = FXCollections.observableArrayList(Customer_table);
            Customertable.setItems(tabledata);
            Customertable.setVisible(true);

            SimTable[] t = simObject.StartSimulation();
            //System.out.println("t"+t[].status);
            s = new sim_Table(t);

            s.setTitle("Events Detail");
            String[] xaxis = new String[t.length];
            double[] yaxis = new double[t.length];
            double[] status = new double[t.length];
            for (int i = 0; i < t.length; i++) {
                String str = Double.toString(t[i].time);
                String temp = "";
                boolean isdouble = false;
                for (int a = 0; a < str.length(); a++) {
                    temp += str.charAt(a);
                    if (str.charAt(a) == '.') {
                        isdouble = true;
                        temp += str.charAt(a + 1) + "" + str.charAt(a + 2) + "" + str.charAt(a + 3);
                        break;
                    }

                }

                if (isdouble) {
                    xaxis[i] = "" + temp;
                } else {
                    xaxis[i] = "" + t[i].time;
                }
                yaxis[i] = t[i].queueL;
                if ("idle".equals(t[i].status)) {
                    status[i] = 0;
                } else {
                    status[i] = 1;
                }
            }

            Color[] c = new Color[]{new Color(0x0587D9)};
            com.objectplanet.chart.BarChart chartqt = new com.objectplanet.chart.BarChart();

            chartqt.setSampleCount(xaxis.length);
            chartqt.setSampleValues(0, yaxis);
            chartqt.setSampleColors(c);
            chartqt.setBackground(Color.white);
            chartqt.set3DModeOn(true);
            chartqt.set3DDepth(5);
            chartqt.setRange(0, 5);
            chartqt.setFont("sampleLabelFont", new Font("Arial", Font.BOLD, 12));
            chartqt.setBarLabels(xaxis);
            
            chartqt.setBarLabelsOn(true);
            chartqt.setLabelAngle("barLabelAngle", 270);
            chartqt.setValueLabelsOn(true);
            chartqt.setValueLabelStyle(com.objectplanet.chart.Chart.OUTSIDE);

            f = new Frame();
            f.setTitle("Q(t)");
            f.setSize(400, 300);
            f.add("Center", chartqt);

            c = new Color[]{new Color(0x0587D9)};
            com.objectplanet.chart.BarChart chartst = new com.objectplanet.chart.BarChart();
            chartst.setSampleCount(xaxis.length);
            chartst.setSampleValues(0, status);
            chartst.setSampleColors(c);
            chartst.setBackground(Color.white);
            chartst.set3DModeOn(true);
            chartst.set3DDepth(5);
            chartst.setRange(0, 1);
            chartst.setFont("sampleLabelFont", new Font("Arial", Font.BOLD, 12));
            chartst.setBarLabels(xaxis);
            chartst.setBarLabelsOn(true);
            chartst.setLabelAngle("barLabelAngle", 270);
            chartst.setValueLabelsOn(true);
            chartst.setValueLabelStyle(com.objectplanet.chart.Chart.INSIDE);

            f1 = new Frame();
            f1.setTitle("S(t)");
            f1.setSize(400, 300);
            f1.add("Center", chartst);

            report.setVisible(true);
            Qt.setVisible(true);
            St.setVisible(true);
            E_d.setVisible(true);
            r=null;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Customertable.setVisible(false);
        arrival.setCellValueFactory(new PropertyValueFactory<>("ArrivalTime"));
        service.setCellValueFactory(new PropertyValueFactory<>("ServiceTime"));
        absolute.setCellValueFactory(new PropertyValueFactory<>("AbsArrival"));
        delay.setCellValueFactory(new PropertyValueFactory<>("Delay"));
        departure.setCellValueFactory(new PropertyValueFactory<>("Departure"));
        report.setVisible(false);
        Qt.setVisible(false);
        St.setVisible(false);
        E_d.setVisible(false);
        arrivalmean.setText(null);
        servicemean.setText(null);
        capacity.setText(null);
    }
ReportBox r=null;
    @FXML
    private void handleButtonActionReport(ActionEvent event) {
        if(r==null){
         r = new ReportBox();
        r.writeReport(simObject.reportGeneration());
        r.setVisible(true);}
        else
            r.setVisible(true);

    }

    @FXML
    private void handleButtonActionSt(ActionEvent event) {
        f1.setVisible(true);
        f1.show();

    }

    @FXML
    private void handleButtonActionQt(ActionEvent event) {
        f.setVisible(true);
        f.show();
    }

    @FXML
    private void handleButtonActionEd(ActionEvent event) {
        s.setVisible(true);
    }

}
