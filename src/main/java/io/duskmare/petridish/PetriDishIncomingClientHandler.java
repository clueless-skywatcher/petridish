package io.duskmare.petridish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import io.duskmare.petridish.resp.data.RespObject;
import io.duskmare.petridish.resp.readers.RespReaderFactory;

public class PetriDishIncomingClientHandler extends Thread {
    private Socket clientSocket;

    public PetriDishIncomingClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            PetriDishServer.LOGGER.log(Level.INFO, String.format("Accepted new connection from: %s", clientSocket.getLocalAddress()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            while (true) {
                InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader reader = new BufferedReader(in);
                RespObject resp = RespReaderFactory.read(reader);
                if (resp == null) {
                    continue;
                }
                PetriDishServer.LOGGER.log(Level.INFO, resp.toString());
                out.write("+OK\r\n");    
                out.flush();
            }       
        }
        catch (IOException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            PetriDishServer.LOGGER.log(Level.SEVERE, sw.toString());
        }
             
    }

}
