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
        SimpleGenerator generator = new SimpleGenerator(task);
        SimpleIntegrator integrator = new SimpleIntegrator(task);

        // Создание потоков
        Thread generatorThread = new Thread(generator, "GeneratorThread");
        Thread integratorThread = new Thread(integrator, "IntegratorThread");

        // Установка приоритетов потоков (по желанию)
        generatorThread.setPriority(Thread.MAX_PRIORITY);
        integratorThread.setPriority(Thread.MIN_PRIORITY);

        // Запуск потоков
        generatorThread.start();
        integratorThread.start();

        System.out.println("All tasks have been processed.");
    }
}

// Изначальная проблема заключалась в том,
// что поток SimpleIntegrator пытался обращаться к полям объекта Task,
// которые еще не были установлены потоком SimpleGenerator.
// Это приводило к тому, что некоторые поля, такие как function, были null,
// и попытка вызвать методы на них вызывала NullPointerException.
