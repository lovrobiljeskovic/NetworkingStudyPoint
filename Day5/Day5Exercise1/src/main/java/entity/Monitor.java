/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lovro
 */
public class Monitor implements Runnable{
    Counter counter;
    private Socket monitorSocket;
      private PrintWriter out;
    private Scanner scanner;
    Map<Integer, Turnstile> turnstiles;

    public Monitor(Counter counter, Socket socket, Map<Integer, Turnstile> turnstiles) throws IOException {
        this.counter = counter;
        this.monitorSocket = socket;
        out = new PrintWriter(monitorSocket.getOutputStream(), true);
        scanner = new Scanner(monitorSocket.getInputStream());
        this.turnstiles = turnstiles;
    }
  

    @Override
    public void run() {
       String message = "";
       out.println("Monitor active");
            while (!message.equals("STOP")) {
                message = scanner.nextLine();
                System.out.println("Received: " + message);
                if(message.equals("count")) {
                    out.println("number of spectators: " + counter.getValue());
                }
                 close();
    }
    }
    
      private void close() {
            scanner.close();
            out.close();
            try {
                monitorSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}
