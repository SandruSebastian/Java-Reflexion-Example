package utils.threading;

import org.jetbrains.annotations.Contract;
import utils.handlers.ThreadHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadHelper {

    private ExecutorService executorService;

    private static ThreadHelper ourInstance = new ThreadHelper();

    @Contract(pure = true)
    public static ThreadHelper getInstance() {
        return ourInstance;
    }

    private ThreadHelper() {
        executorService = Executors.newFixedThreadPool(100);
    }

    public void execute(ThreadHandler threadHandler) {
        executorService.submit(() -> {
            threadHandler.work();
        });
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            executorService.awaitTermination(1000, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("Interrupted thread !");
        }
    }

}
