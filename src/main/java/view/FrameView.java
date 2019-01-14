package view;

import javax.swing.*;
import javax.swing.event.MenuListener;
import java.awt.*;

public abstract class FrameView extends JFrame{
    protected int frameWidth = 1000;
    protected int frameHeight = 600;

    private JMenuBar menuBar;
    private JMenu surveysView;
    private JMenu createSurveyView;

    public FrameView(String name) {
        super(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(null);
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation((screenWidth-frameWidth)/2, (screenHeight-frameHeight)/2);
        this.setSize(frameWidth, frameHeight);
    }

    protected void createMenu(){
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        surveysView = new JMenu("Wszystkie ankiety");
        menuBar.add(surveysView);

        createSurveyView = new JMenu("Stwórz ankietę");
        menuBar.add(createSurveyView);
    }

    public void setSurveysViewMenuListener(MenuListener menuListener){
        surveysView.addMenuListener(menuListener);
    }

    public void setCreateSurveyViewMenuListener(MenuListener menuListener){
        createSurveyView.addMenuListener(menuListener);
    }
}