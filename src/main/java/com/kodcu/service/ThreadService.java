package com.kodcu.service;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Created by usta on 25.12.2014.
 */
@Component
public class ThreadService {

    private final ExecutorService threadPollWorker;

    public ThreadService() {
        int nThreads = Runtime.getRuntime().availableProcessors() * 2;
        threadPollWorker = Executors.newFixedThreadPool((nThreads >= 4) ? nThreads : 4);
    }

    public ThreadService(int poolSize) {
        threadPollWorker = Executors.newFixedThreadPool(poolSize);
    }

    // Runs Task in background thread pool
    public <T> void runTaskLater(Runnable runnable) {

        Task<T> task = new Task<T>() {
            @Override
            protected T call() throws Exception {
                runnable.run();
                return null;
            }
        };

        threadPollWorker.submit(task);
    }

    // Runs task in JavaFX Thread
    public static void runActionLater(Consumer<ActionEvent> consumer) {
        Platform.runLater(() -> consumer.accept(null));
    }

    // Runs task in JavaFX Thread
    public static void runActionLater(Runnable runnable) {
        Platform.runLater(runnable);
    }
}
