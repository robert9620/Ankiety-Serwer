package model.connectivity;

import model.UserModel;

import java.io.*;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerTCPThreadModel extends Thread {
    Socket socket;
    JDBCConnectivityModel con;

    public ServerTCPThreadModel(Socket socket, JDBCConnectivityModel con) {
        super();
        this.socket = socket;
        this.con = con;
    }

    public void run() {
        try {
            System.out.println("new TCP thread");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out;

            String str;
            do{
                InputStream is = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                UserModel user = (UserModel) ois.readObject();
                String response = checkLogin(user);

                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.println(response);
                out.flush();
            }
            while(!(str = in.readLine()).equals("exit"));

            socket.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    private String checkLogin(UserModel user) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql="select * from user where login=? and password=?";

        boolean status = false;
        try{
            preparedStatement = con.getConn().prepareStatement(sql);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                status = true;
            }
            else {
                status = false;
            }
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        catch (Exception e){
            System.out.println(e);
        }
        finally {
            if (status) {
                return "";
            }
            else {
                return "Błędny login lub hasło";
            }
        }
    }
}