package io.duskmare.petridish;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.duskmare.petridish.client.PetriDishTestingClient;

/**
 * Unit test for simple App.
 */
public class PetriDishServerTest {
    private static PetriDishTestingClient client;
    
    @BeforeAll
    public static void startClient() {
        new Thread(() -> {
            try {
                PetriDishServer.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        client = new PetriDishTestingClient();
        try {
            client.connect("0.0.0.0", 6379);
        } catch (IOException e) {
            assertFalse(false, "Failed to connect to server");
        }
    }

    @Test
    public void pingCommand() {
        try {
            assertEquals("+PONG", client.sendMessage("ping"));
        } catch (IOException e) {
            assertFalse(false, "Failed to send message");
        }
    }
}
