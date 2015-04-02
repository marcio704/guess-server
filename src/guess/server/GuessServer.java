/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guess.server;

import java.io.IOException;
import java.net.*;

/**
 *
 * @author marcio
 */
public class GuessServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new GuessServer(12345).execute();
    }

    private int port;
    
    public GuessServer (int port) {
        this.port = port;
    }
   
    public void  execute () throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket(this.port);
        System.out.println("Connection opened on port 12345!");
        Game game = new Game();
        
        while (true) {
            Socket cliente = server.accept();
            System.out.println("New connection with client " + cliente.getInetAddress().getHostAddress());
            
            ServerThread r = new ServerThread(cliente.getInputStream(), cliente.getOutputStream(), game);
            new Thread(r).start();
        }
    }
}
