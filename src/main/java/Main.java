import controller.connectpanel.ConnectController;
import model.connectivity.JDBCConnectivityModel;
import model.connectivity.ServerTCPModel;

import java.awt.*;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args){

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConnectController();
            }
        });

//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                CreateSurveyView test = new CreateSurveyView("Stwórz nową ankietę");
//                test.addQuestion();
//                test.addQuestion();
//                test.addQuestion();
//                test.addListenerOnHowManyQuestionsAndSurveysNameTextFields();
//            }
//        });
    }
}