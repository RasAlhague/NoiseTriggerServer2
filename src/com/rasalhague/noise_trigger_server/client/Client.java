package com.rasalhague.noise_trigger_server.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client implements Runnable
{
    private Socket           socket;
    private BufferedReader   inFromClient;
    private DataOutputStream outToClient;

    public Client(Socket clientSocket)
    {
        this.socket = clientSocket;

        Thread clientThread = new Thread(this);
        clientThread.setName(socket.toString());
        clientThread.start();
    }

    @Override
    public void run()
    {
        initialize();
        startListeningServer();
    }

    private void initialize()
    {
        try
        {
            inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outToClient = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException e)
        {
            try
            {
                socket.close();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }
    }

    private void startListeningServer()
    {
        String inputLine;

        try
        {
            while (!socket.isClosed())
            {
                inputLine = inFromClient.readLine();

                notifyClientInputListenerObservers(inputLine);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Observer realization
     */

    private List<ClientInputListener> ClientInputListenerObservers = new ArrayList<ClientInputListener>();

    public void addClientInputListener(ClientInputListener observer) { ClientInputListenerObservers.add(observer);}

    public void removeClientInputListener(ClientInputListener observer) { ClientInputListenerObservers.remove(observer);}

    public void notifyClientInputListenerObservers(String inputLine)
    {
        for (ClientInputListener listener : ClientInputListenerObservers)
        {
            listener.inputLineEvent(inputLine);
        }
    }

    public interface ClientInputListener
    {
        public void inputLineEvent(String inputLine);
    }

    /**
     * Observer realization END
     */

    @Override
    public String toString()
    {
        return "Client{" +
                "socket=" + socket +
                '}';
    }
}
