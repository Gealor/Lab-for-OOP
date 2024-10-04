import functions.Function;
import functions.basic.Log;
import threads.SimpleGenerator;
import threads.SimpleIntegrator;
import threads.Task;

import java.util.Random;

public class Main6 {
    public static void main(String[] args) {
        simpleThreads();
    }

    public static void simpleThreads() {
        Random random = new Random();
        Task task = new Task();
        int numberOfTasks = 10; // Минимум 100 заданий
        task.setTaskCount(numberOfTasks);

        // Создание генератора и интегратора
        Runnable generator = new SimpleGenerator(task);
        Runnable integrator = new SimpleIntegrator(task);

        // Создание потоков
        Thread generatorThread = new Thread(generator, "GeneratorThread");
        Thread integratorThread = new Thread(integrator, "IntegratorThread");

        // Установка приоритетов потоков (по желанию)
        generatorThread.setPriority(Thread.NORM_PRIORITY);
        integratorThread.setPriority(Thread.NORM_PRIORITY);

        // Запуск потоков
        generatorThread.start();
        integratorThread.start();

        System.out.println("All tasks have been processed.");
    }
}
