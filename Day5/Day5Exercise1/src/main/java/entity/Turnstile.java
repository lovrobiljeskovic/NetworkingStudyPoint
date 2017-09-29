/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lovro
 */
public class Turnstile implements Runnable {

    private final Socket turnstileSocket;
    private PrintWriter out;
    private Scanner scanner;
    int id;
    int localCount = 0;
    Counter counter;

    public Turnstile(Socket turnstileSocket, int id, Counter counter) throws IOException {
        this.turnstileSocket = turnstileSocket;
        this.id = id;
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(turnstileSocket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            scanner = new Scanner(
                    new InputStreamReader(turnstileSocket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Turnstile with id " + id + "was initialized");
        while (true) {
            counter.incr();
            localCount++;
            try {
                Thread.sleep(3000);

            } catch (InterruptedException ex) {
                Logger.getLogger(Turnstile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
