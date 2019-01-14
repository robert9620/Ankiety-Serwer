package controller;

import controller.adminpanel.CreateSurveyController;
import controller.adminpanel.SurveysController;
import model.connectivity.JDBCConnectivityModel;
import view.FrameView;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Controller {
    public Controller(){
    }

    protected void addMenuActions(final FrameView activeView, String activeViewName, final JDBCConnectivityModel con){
        activeView.setSurveysViewMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                activeView.dispose();
                new SurveysController(con);
            }

            public void menuDeselected(MenuEvent e) {

            }

            public void menuCanceled(MenuEvent e) {

            }
        });

        activeView.setCreateSurveyViewMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                activeView.dispose();
                new CreateSurveyController(con);
            }

            public void menuDeselected(MenuEvent e) {

            }

            public void menuCanceled(MenuEvent e) {

            }
        });
    }
}