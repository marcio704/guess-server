/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guess.server;

import guess.client.Client;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcio
 */
 public class ServerThread implements Runnable {
 
    private InputStream client;
    private OutputStream server;
    private Game game;

    public ServerThread(InputStream client, OutputStream server, Game game) {
        this.client = client;
        this.server = server;
        this.game = game;
    }
 
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(client);
            ObjectOutputStream oos = new ObjectOutputStream(server);
            
            boolean stopTrying = false;
            do {
                Client clientGuess = (Client) ois.readObject();   
                stopTrying = game.isGuessRight(clientGuess, oos);
            } while (!stopTrying);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 }
