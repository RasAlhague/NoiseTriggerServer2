package com.rasalhague.noise_trigger_server;

import com.rasalhague.noise_trigger_server.client.Client;
import com.rasalhague.noise_trigger_server.server_socket.ConnectionsHandler;

public class Main
{
    public static void main(String[] args)
    {
        ConnectionsHandler connectionsHandler = ConnectionsHandler.getInstance();
        connectionsHandler.getConnectedClientsSocketList()
                          .addAddListener(addedItem -> {

                              Client client = (Client) addedItem;
                              client.addClientInputListener(ClientInputEventHandler.getInstance());
                          });
    }
}
