package model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String login;
    private String password;

    public UserModel(){

    }

    public UserModel(String login){
        this.login = login;
    }

    public UserModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}