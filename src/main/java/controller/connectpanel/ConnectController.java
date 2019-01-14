package controller.connectpanel;

import controller.adminpanel.SurveysController;
import model.SurveyModel;
import model.connectivity.JDBCConnectivityModel;
import model.connectivity.ServerTCPModel;
import view.loginpanel.ConnectView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ConnectController extends controller.Controller{
    private ConnectView view;
    private JDBCConnectivityModel con;

    public ConnectController() {
        this.view = new ConnectView("Połącz z bazą danych");
        setButtonConnect();
    }

    private void setButtonConnect() {
        view.setButtonConnectActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("connect button clicked");
                connect();
            }
        });
    }

    private void connect() {
        if(view.getLogin().equals("")){
            view.setErrorLabel("Wpisz login");
        }
        else {
            try {
                con = new JDBCConnectivityModel(view.getLogin(), view.getPassword());
                view.dispose();
                new SurveysController(con);
                startTCP sTCP = new startTCP();
                sTCP.start();
            } catch (SQLException e) {
                e.printStackTrace();
                view.setErrorLabel("Błędny login lub hasło");
            } catch (Exception e) {
                e.printStackTrace();
                view.setErrorLabel("Nie udało się połączyć");
            }
        }
    }

    private class startTCP extends Thread {
        @Override
        public void run() {
            new ServerTCPModel(con);
        }
    }
}

