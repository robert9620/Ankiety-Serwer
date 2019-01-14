package controller.adminpanel;

import model.connectivity.JDBCConnectivityModel;
import model.SurveyModel;
import model.connectivity.ServerTCPModel;
import view.adminpanel.SurveysView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SurveysController extends controller.Controller{
    private SurveysView view;
    private JDBCConnectivityModel con;

    private String activeViewName = "Ankiety";
    private List<SurveyModel> surveys;

    public SurveysController(JDBCConnectivityModel con) {
        this.view = new SurveysView(activeViewName);
        super.addMenuActions(view, activeViewName, con);

        this.con = con;
        this.surveys = new LinkedList<SurveyModel>();

        this.getSurveys();
        this.addColumnsToSurveysTable();
    }

    private void getSurveys() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql="select * from survey";
        try{
            preparedStatement = con.getConn().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                SurveyModel survey = new SurveyModel();
                survey.setName(resultSet.getString("name"));
                surveys.add(survey);
            }
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    private void addColumnsToSurveysTable(){
        Iterator it = surveys.iterator();
        while(it.hasNext()){
            SurveyModel survey = (SurveyModel) it.next();
            view.addColumnToTable(survey.toTable());
        }
    }
}
