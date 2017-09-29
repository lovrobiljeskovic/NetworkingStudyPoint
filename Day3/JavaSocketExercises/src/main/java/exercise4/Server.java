/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise4;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lovro
 */
public class Server {
    private ServerSocket serverSocket;
 
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true)
            new EchoClientHandler(serverSocket.accept()).run();
    }
 
    public void stop() throws IOException {
        serverSocket.close();
    }
 
    private static class EchoClientHandler {
        private Socket clientSocket;
        private PrintWriter out;
        private Scanner scanner;
 
        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
 
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                scanner = new Scanner(
                        new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String inputLine = scanner.nextLine();
              String[] result = inputLine.split("#");
            while (!inputLine.equals("STOP")) {
                if (result.equals("UPPER#")) {
                    out.println(inputLine.toUpperCase());
                }else if(result.equals("LOWER#")) {
                    out.println(inputLine.toLowerCase());
                }else if(result.equals("REVERSE#")) {
                  StringBuilder input = new StringBuilder();
                  input.append(result);
                  input = input.reverse();
                  for(int i = 0; i < input.length(); i++) {
                      System.out.println(input.charAt(i));
                    }
                }
               
            }
 
            scanner.close();
            out.close();
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
}