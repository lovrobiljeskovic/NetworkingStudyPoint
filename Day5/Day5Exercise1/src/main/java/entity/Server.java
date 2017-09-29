/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lovro
 */
public class Server {
     private static ServerSocket serverSocketTurnstile;
     private static ServerSocket serverSocketMonitor;
    private static int PORT1 = 6666;
    private static int PORT2 = 8888;
    private static String IP = "localhost";
    private static Map<Integer, Turnstile> turnstiles = new HashMap();
   

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            PORT1 = Integer.parseInt(args[0]);
           
            PORT2 = Integer.parseInt(args[0]);
            IP = args[1];
        }
        Counter counter = new Counter();
        serverSocketTurnstile = new ServerSocket(PORT1); 
        serverSocketTurnstile.bind(new InetSocketAddress(IP, PORT1));
        serverSocketMonitor = new ServerSocket(PORT2);
        serverSocketMonitor.bind(new InetSocketAddress(IP, PORT2));
        ExecutorService es = Executors.newCachedThreadPool();
        System.out.println("Waiting for a turnstile");
        System.out.println("Waiting for a monitor");
        while (true) {
            int id = 1;
            Socket socketTurnstile = serverSocketTurnstile.accept();
            Socket socketMonitor = serverSocketMonitor.accept();
            System.out.println("Got a turnstile");
            System.out.println("Got a monitor");
            Turnstile turnstile = new Turnstile(socketTurnstile, id, counter);
            Monitor monitor = new Monitor(counter, socketMonitor, turnstiles);
            turnstiles.put(id, turnstile);
            es.execute(turnstile);
            es.execute(monitor);
            id++;
            
          
        }
        
        
    }
}
