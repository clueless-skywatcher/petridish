package io.duskmare.petridish;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.duskmare.petridish.datastructs.GenericTable;
import io.duskmare.petridish.datastructs.HashTable;
import io.duskmare.petridish.resp.data.RespObject;

public class PetriDishServer {
    public final static Logger LOGGER = Logger.getLogger("PetriDish");
    public final static GenericTable<String, RespObject> GLOBAL_MAP = new HashTable<>();

    private static ServerSocket serverSocket; 

    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(6379);
        LOGGER.log(Level.INFO, "Starting a new PetriDish on server: " + serverSocket.getInetAddress());
        LOGGER.log(Level.INFO, "Listening to connections on port 6379");
        while (true) {
            new PetriDishIncomingClientHandler(serverSocket.accept()).start();
        }
    }

    public static void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
}
