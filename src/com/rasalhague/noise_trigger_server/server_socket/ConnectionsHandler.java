package com.rasalhague.noise_trigger_server.server_socket;

import com.rasalhague.noise_trigger_server.client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionsHandler implements Runnable
{
    private Integer                     serverSocketPort           = 4444;
    private ObservableArrayList<Client> connectedClientsSocketList = new ObservableArrayList<>();

    private ConnectionsHandler()
    {
        Thread thread = new Thread(this);
        thread.setName("ConnectionsHandler");
        thread.start();
    }

    public static ConnectionsHandler getInstance()
    {
        return ConnectionHandlerHolder.instance;
    }

    public void setServerSocketPort(Integer serverSocketPort)
    {
        this.serverSocketPort = serverSocketPort;
    }

    public ObservableArrayList<Client> getConnectedClientsSocketList()
    {
        return connectedClientsSocketList;
    }

    @Override
    public void run()
    {
        startServerSocketListening();
    }

    private void startServerSocketListening()
    {
        try (ServerSocket serverSocket = new ServerSocket(serverSocketPort))
        {
            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                Client client = new Client(clientSocket);
                System.out.println(client);

                connectedClientsSocketList.add(client);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static class ConnectionHandlerHolder
    {
        static ConnectionsHandler instance = new ConnectionsHandler();
    }
}

