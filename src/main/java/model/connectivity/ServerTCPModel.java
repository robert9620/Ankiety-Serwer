package model.connectivity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerTCPModel {
    private JDBCConnectivityModel con;

    public ServerTCPModel(JDBCConnectivityModel con) {
        this.con = con;
        try {
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        int port = 12367;

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                (new ServerTCPThreadModel(socket, con)).start();
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
        finally{
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
