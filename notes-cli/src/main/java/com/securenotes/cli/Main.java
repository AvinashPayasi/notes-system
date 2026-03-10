package com.securenotes.cli;

public class Main {
    public static void main(String[] args) {

        Client client= new Client();
        ClientService clientService= new ClientService(client);
        UserInteraction userInteraction= new UserInteraction(clientService);
        userInteraction.startCli();
    }
}