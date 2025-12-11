package com.example.pseudobank;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class TransactionLogger {

    private static final String LOG_FILE = "src/main/java/com/example/pseudobank/PseudoBankLogs/transaction_log.txt";

    public static void log(String message) {
        new Thread(() -> {
            try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
                writer.write(LocalDateTime.now() + " - " + message + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
