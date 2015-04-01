package guess.server;

import guess.client.Client;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marcio
 */
public class Game {
    
    private int gameNumber;
    public static boolean isGameOver = false;
    private static final int DEFAULT_START = 1;
    private static final int DEFAULT_END = 10;
    
    public Game () {
        Random randomGenerator = new Random();
        gameNumber = generateRandomInteger(DEFAULT_START, DEFAULT_END, randomGenerator);
        System.out.println("Game number is: " + gameNumber);
    }
    
    private Integer generateRandomInteger(int start, int end, Random random) {
        if (start > end) {
          throw new IllegalArgumentException("Start cannot exceed End.");
        }
        long range = (long)end - (long)start + 1;
        long fraction = (long)(range * random.nextDouble());
        int randomNumber =  (int)(fraction + start);    
        return randomNumber;
    }
    
    public synchronized boolean isGuessRight(Client clientGuess, ObjectOutputStream client) throws IOException {
        System.out.println("CLIENT " + clientGuess.getId() + " ATTEMPTING A GUESS OF " + clientGuess.getGuessNumber() + "!! DO I STOP THE GAME? " + isGameOver);
        if(isGameOver) {
            System.out.println("WE ALREADY HAVE A WINNER! STOP THE GAME FOR CLIENT " + clientGuess.getId());
            client.writeObject("exit");
            return true;
        } else {
            if((isGameOver = clientGuess.getGuessNumber() == gameNumber)) {
                System.out.println("WE HAVE A FIRST WINNER!!! Client " + clientGuess.getId());
                client.writeObject("exit");
                return true;
            }
            System.out.println("Client " + clientGuess.getId() + " wrong guess, try again!");
            client.writeObject(new Tip(clientGuess.getGuessNumber() > gameNumber ? Tip.BELOW : Tip.ABOVE, clientGuess.getGuessNumber()));
            return false;
        }
    }
    
    public int getGameNumber() {
        return gameNumber;
    }
}
