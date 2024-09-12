package io.duskmare.petridish.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class PetriDishTestingClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String host, int port) throws IOException {
        clientSocket = new Socket(host, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String message) throws IOException {
        List<String> msgArray = Arrays.asList(message.split(" "));
        StringJoiner joiner = new StringJoiner("\r\n");

        for (String msg: msgArray) {
            joiner.add(String.format("$%d\r\n%s", msg.length(), msg));
        }

        String msgToSend = String.format("*%d\r\n%s\r\n", msgArray.size(), joiner.toString());
        out.println(msgToSend);
        return in.readLine();
    }

    public void disconnect() throws IOException {
        clientSocket.close();
    }
}
