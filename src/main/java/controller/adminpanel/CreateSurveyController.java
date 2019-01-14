package controller.adminpanel;

import model.connectivity.JDBCConnectivityModel;
import view.adminpanel.CreateSurveyView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateSurveyController extends controller.Controller{
    private CreateSurveyView view;
    private JDBCConnectivityModel con;

    private String activeViewName = "Stwórz ankietę";

    public CreateSurveyController(JDBCConnectivityModel con) {
        this.view = new CreateSurveyView(activeViewName);
        super.addMenuActions(view, activeViewName, con);

        this.con = con;

        this.addQuestions();
    }

    private void addQuestions() {
        view.addButtonAcceptActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (CreateSurveyController.this.checkSurveyInfo()) {
                    int howManyQuestionsInt = Integer.parseInt(view.getHowManyQuestions());
                    for (int i = 0; i < howManyQuestionsInt; i++) {
                        view.addQuestion();
                    }
                    view.removeButtonAcceptActionListener(this);
                    view.blockHowManyQuestionsAndSurveysNameAndAcceptButton();
                    CreateSurveyController.this.createSurvey();
                }
            }
        });
    }

    private boolean checkSurveyInfo(){
        String errorMessage = "";

        String name = view.getSurveysName();
        Pattern pattern = Pattern.compile("[^ ]+");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            errorMessage += "Nie wpisałeś poprawnej nazwy ankiety\n";
        }

        String howManyQuestions = view.getHowManyQuestions();
        pattern = Pattern.compile("\\d");
        matcher = pattern.matcher(howManyQuestions);
        if (!matcher.matches()) {
            errorMessage += "Nie wpisałeś poprawnej liczby pytań, maksymalna liczba pytań wynosi 9";
        }

        if(errorMessage.equals("")) {
            return true;
        }
        else {
            view.showErrorMessage(errorMessage);
            return false;
        }
    }

    private void createSurvey(){
        view.addButtonCreateSurveyActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (CreateSurveyController.this.checkQuestions()) {
                    CreateSurveyController.this.addSurveyToDataBase();
                    if(view.showSuccessMessage() == 0){
                        view.dispose();
                        new SurveysController(con);
                    }
                }
            }
        });
    }

    private boolean checkQuestions(){
        String[] questions = view.getQuestionsAsString();
        for(int i=0; i<questions.length; i++){
            if(questions[i].equals("")){
                view.showErrorMessage("Nie wprowadziłeś wszystkich pytań");
                return false;
            }
        }
        return true;
    }

    private void addSurveyToDataBase(){
        try {
            PreparedStatement preparedStatement = null;

            String query="INSERT INTO `survey` (`id`, `name`) VALUES (NULL, ?);";

            preparedStatement = con.getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, view.getSurveysName());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            int auto_id = rs.getInt(1);

            for(int i=0; i<view.getQuestionsAsString().length; i++){
                addQuestionsToDataBase(auto_id, view.getQuestionsAsString()[i]);
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private void addQuestionsToDataBase(int surveyId, String question) throws SQLException {
        PreparedStatement preparedStatement = null;

        String query="INSERT INTO `question` (`id`, `surveyId`, `content`) VALUES (NULL, ?, ?);";

        preparedStatement = con.getConn().prepareStatement(query);

        preparedStatement.setInt(1, surveyId);
        preparedStatement.setString(2, question);

        preparedStatement.executeUpdate();
    }
}
