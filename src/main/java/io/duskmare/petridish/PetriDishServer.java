package io.duskmare.petridish;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PetriDishServer {
    public final static Logger LOGGER = Logger.getLogger("PetriDish");

    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6379);
        LOGGER.log(Level.INFO, "Starting server");
        LOGGER.log(Level.INFO, "Listening to connections on port 6379");
        while (true) {
            new PetriDishIncomingClientHandler(serverSocket.accept()).start();
        }
    }
}
