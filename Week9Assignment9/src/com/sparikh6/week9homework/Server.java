package com.sparikh6.week9homework;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	//private static final int PORT = 20010;
	 /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(20010);
        } catch (IOException e) {
            System.err.println("[SERVER] Could not listen on port: 20010.");
            System.exit(1);
        }

        System.out.println("[SERVER] Waiting for client connection.");
        Socket clientSocket = null;
        while (true) {
            clientSocket = serverSocket.accept();
            System.out.println("[SERVER] Connnected to client!");
            ClientThread thread = new ClientThread(clientSocket);
            thread.start();
            
        }
    }
}
