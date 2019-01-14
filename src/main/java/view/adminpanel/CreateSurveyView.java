package view.adminpanel;

import model.SurveyModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CreateSurveyView extends view.FrameView{

    private int padding = 20;
    private int firstLabelHeight = 20;
    private int spaceBetween = 36;
    private int howMuchSpace = 1;
    private int howManyQuestionsExists = 0;

    private List<JTextField> questionsTextFields;
    JTextField howManyQuestions;
    JTextField surveysName;
    JButton buttonCreateSurvey;
    JButton buttonAccept;

    public CreateSurveyView(String name){
        super(name);

        this.createMenu();

        questionsTextFields = new LinkedList<JTextField>();

        JLabel label = new JLabel("Wpisz nazwę ankiety: ");
        label.setSize(label.getPreferredSize());
        label.setLocation(padding, firstLabelHeight + spaceBetween);
        this.add(label);

        surveysName = new JTextField();
        surveysName.setSize(200, 40);
        surveysName.setLocation(padding+label.getPreferredSize().width, firstLabelHeight + spaceBetween-12);
        this.add(surveysName);

        howMuchSpace++;

        label = new JLabel("Ile pytań ma mieć ankieta: ");
        label.setSize(label.getPreferredSize());
        label.setLocation(padding, firstLabelHeight + (spaceBetween*howMuchSpace));
        this.add(label);

        howManyQuestions = new JTextField();
        howManyQuestions.setSize(200, 40);
        howManyQuestions.setLocation(padding+label.getPreferredSize().width, firstLabelHeight + (spaceBetween*howMuchSpace)-12);
        this.add(howManyQuestions);

        howMuchSpace++;

        buttonAccept = new JButton("Zatwierdź");
        buttonAccept.setSize(buttonAccept.getPreferredSize());
        buttonAccept.setLocation(padding,firstLabelHeight + (spaceBetween*howMuchSpace));
        this.add(buttonAccept);
        this.getRootPane().setDefaultButton(buttonAccept);

        howMuchSpace++;

        buttonCreateSurvey = new JButton("Stwórz ankietę");
        buttonCreateSurvey.setSize(buttonCreateSurvey.getPreferredSize());
        buttonCreateSurvey.setLocation(padding,firstLabelHeight + (spaceBetween*howMuchSpace));
        buttonCreateSurvey.setVisible(false);
        this.add(buttonCreateSurvey);
    }

    public void addQuestion(){
        buttonCreateSurvey.setVisible(true);

        JLabel label = new JLabel("Pytanie" + (++howManyQuestionsExists) + ": ");
        label.setSize(label.getPreferredSize());
        label.setLocation(padding, firstLabelHeight + (spaceBetween*howMuchSpace)+36);
        this.add(label);

        JTextField textField = new JTextField();
        textField.setSize(200, 40);
        textField.setLocation(padding+label.getPreferredSize().width, firstLabelHeight + (spaceBetween*howMuchSpace)+24);
        this.add(textField);
        questionsTextFields.add(textField);

        howMuchSpace++;

        buttonCreateSurvey.setLocation(padding,firstLabelHeight + (spaceBetween*howMuchSpace)+36);
        this.getRootPane().setDefaultButton(buttonCreateSurvey);
    }

    public void showErrorMessage(String errorMessage){
        JOptionPane.showMessageDialog(this,
                errorMessage,
                "Błędne dane",
                JOptionPane.ERROR_MESSAGE);
    }

    public void blockHowManyQuestionsAndSurveysNameAndAcceptButton(){
        howManyQuestions.setEnabled(false);
        surveysName.setEnabled(false);
        buttonAccept.setEnabled(false);
    }

    public void addButtonAcceptActionListener(ActionListener actionListener){
        buttonAccept.addActionListener(actionListener);
    }

    public void removeButtonAcceptActionListener(ActionListener actionListener){
        buttonAccept.removeActionListener(actionListener);
    }

    public String getHowManyQuestions() {
        return howManyQuestions.getText();
    }

    public String getSurveysName() {
        return surveysName.getText();
    }

    public String[] getQuestionsAsString() {
        Iterator it = questionsTextFields.iterator();
        String[] text = new String[questionsTextFields.size()];
        int i = 0;
        while(it.hasNext()){
            text[i] = ((JTextField) it.next()).getText();
            i++;
        }
        return text;
    }

    public void addButtonCreateSurveyActionListener(ActionListener actionListener){
        buttonCreateSurvey.addActionListener(actionListener);
    }

    public int showSuccessMessage(){
        return JOptionPane.showOptionDialog(this,
                "Stworzono nową ankietę",
                "Ankieta utworzona",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);
    }
}