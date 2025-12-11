package com.example.pseudobank;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class TransactionLogger {

    //save all the transactions log in to this log file
    private static final String LOG_FILE = "src/main/java/com/example/pseudobank/PseudoBankLogs/transaction_log.txt";

    //writes message to log file
    public static void log(String message) {

        //we use a thread so that the program doesn't freeze while writing to the file
        new Thread(() -> {
            try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
                writer.write(LocalDateTime.now() + " - " + message + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
