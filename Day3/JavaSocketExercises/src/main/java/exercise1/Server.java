/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Lovro
 */
public class Server {

    private static int PORT = 1234;
    private static String IP = "localhost";
    private static ServerSocket serverSocket;
    
    public static void handleClient(Socket s) throws IOException {
      Scanner scanner = new Scanner(s.getInputStream());  
      PrintWriter pw = new PrintWriter(s.getOutputStream(), true); //DONT FORGET autoflush, set to true
      //IMPORTANT BLOCKING
      String messageFromClient = scanner.nextLine();
        System.out.println("Received: " +messageFromClient);
      while(!messageFromClient.equals("STOP")) {
          pw.println(messageFromClient.toUpperCase());
          messageFromClient = scanner.nextLine();
      }
        System.out.println("Stopped");
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            PORT = Integer.parseInt(args[0]);
            IP = args[1];
        }
        serverSocket = new ServerSocket(PORT); //Remember to bind
        serverSocket.bind(new InetSocketAddress(IP, PORT));
        System.out.println("Waiting for a Client");
        while(true) {
       Socket socket =  serverSocket.accept(); //IMPORTANT BLOCKING CALL
        System.out.println("Got a client");
        handleClient(socket);
    }
    }

}

