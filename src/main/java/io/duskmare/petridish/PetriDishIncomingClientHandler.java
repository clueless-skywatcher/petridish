package io.duskmare.petridish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.logging.Level;

import io.duskmare.petridish.exceptions.InvalidRespCommandException;
import io.duskmare.petridish.resp.RespCommandsHandler;
import io.duskmare.petridish.resp.commands.RespCommand;
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
            PetriDishServer.LOGGER.log(Level.INFO, "New thread started: " + this.getName());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            while (true) {
                InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader reader = new BufferedReader(in);
                RespObject resp = RespReaderFactory.read(reader);
                if (resp == null) {
                    continue;
                }
                try {
                    RespCommand command = RespCommandsHandler.generateCommand(resp);
                    RespObject result = command.execute();
                    out.write(result.toString());    
                    out.flush();
                }
                catch (InvalidRespCommandException e) {
                    out.write(String.format("+%s\r\n", "ERR " + e.getMessage()));    
                    out.flush();
                }
                
            }       
        }
        catch (IOException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            PetriDishServer.LOGGER.log(Level.SEVERE, sw.toString());
        }
    }

}
