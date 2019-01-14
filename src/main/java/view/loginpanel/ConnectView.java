package view.loginpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConnectView extends view.FrameView{

    private int frameWidth = 500;
    private int frameHeight = 500;

    private int firstLabelHeight = 50;
    private int firstFieldHeight = 70;
    private int spaceBetween = 70;

    private JLabel labelLogin;
    private JLabel labelPassword;
    private JButton buttonConnect;
    private JTextField inputLogin;
    private JPasswordField inputPassword;

    public ConnectView(String name){
        super(name);

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation((screenWidth-frameWidth)/2, (screenHeight-frameHeight)/2);

        this.setSize(frameWidth, frameHeight);

        labelLogin = new JLabel("Użytkownik:");
        labelLogin.setSize(labelLogin.getPreferredSize());
        labelLogin.setLocation(50, firstLabelHeight);
        this.add(labelLogin);

        inputLogin = new JTextField();
        inputLogin.setSize(frameWidth-100, 40);
        inputLogin.setLocation(50, firstFieldHeight);
        this.add(inputLogin);

        labelPassword = new JLabel("Hasło:");
        labelPassword.setSize(labelPassword.getPreferredSize());
        labelPassword.setLocation(50, firstLabelHeight + spaceBetween);
        this.add(labelPassword);

        inputPassword = new JPasswordField();
        inputPassword.setSize(frameWidth-100, 40);
        inputPassword.setLocation(50, firstFieldHeight + spaceBetween);
        this.add(inputPassword);

        buttonConnect = new JButton("Zaloguj");
        buttonConnect.setSize(100,50);
        buttonConnect.setLocation(350,380);
        this.getRootPane().setDefaultButton(buttonConnect);
        this.add(buttonConnect);
    }

    public void setButtonConnectActionListener(ActionListener actionListener){
        buttonConnect.addActionListener(actionListener);
    }

    public String getPassword() {
        return String.valueOf(inputPassword.getPassword());
    }

    public String getLogin() {
        return inputLogin.getText();
    }

    public void setErrorLabel(String errorMessage){
        JOptionPane.showMessageDialog(this,
                errorMessage,
                "Błędne dane",
                JOptionPane.ERROR_MESSAGE);
    }
}
