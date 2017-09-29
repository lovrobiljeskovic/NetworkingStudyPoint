/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise6;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lovro
 */
public class Server {

    private static ServerSocket serverSocket;
    private static int PORT = 9999;
    private static String IP = "localhost";
    private static Map<String, String> dictionary = new HashMap();

    {
        dictionary.put("hund", "dog");
        dictionary.put("huset", "house");
        dictionary.put("barn", "child");
        dictionary.put("kvinde", "woman");
        dictionary.put("mand", "man");
    }

    private static class EchoClientHandler extends Thread {

        private final Socket clientSocket;
        private PrintWriter out;
        private Scanner scanner;
        private long count = 0;
        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

  public long getValue() {
    return count;
  }
  
  public synchronized void incr() {
    count++;
  }

        @Override
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

            String message = "Start";
            while (!message.equals("STOP")) {
                message = scanner.nextLine();
                System.out.println("Received: " + message);
                String[] splitMessage;
                splitMessage = message.split("#");
                if (splitMessage.length == 2) {
                    String command = splitMessage[0];
                    String text = splitMessage[1];
                    switch (command) {
                        case "UPPER":
                            out.println(text.toUpperCase());
                            break;
                        case "LOWER":
                            out.println(text.toLowerCase());
                            break;
                        case "REVERSE":
                            StringBuilder reverse = new StringBuilder();
                            for (int i = text.length() - 1; i >= 0; i--) {
                                reverse.append(text.charAt(i));
                            }
                            out.println(reverse.toString());
                            break;
                        case "TRANSLATE":
                            String result = dictionary.get(text);
                            if (result == null) {
                                out.println("word doesn't exist, you are kicked out");
                                close();
                            } else {
                                out.println(result);
                            }
                            break;
                        default:
                            close();
                            break;
                    }
                } else {
                    break;
                }
            }
            close();
        }

        private void close() {
            scanner.close();
            out.close();
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            PORT = Integer.parseInt(args[0]);
            IP = args[1];
        }
        serverSocket = new ServerSocket(PORT); //Remember to bind
        //serverSocket.bind(new InetSocketAddress(IP, PORT));
        System.out.println("Waiting for a Client");
        while (true) {
            Socket socket = serverSocket.accept(); //IMPORTANT BLOCKING CALL
            System.out.println("Got a client");
            EchoClientHandler client = new EchoClientHandler(socket);
            client.start();
          
        }
    }

}
