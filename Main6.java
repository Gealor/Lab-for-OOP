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
        int numberOfTasks = 100; // Минимум 100 заданий
        task.setTaskCount(numberOfTasks);
        // Генерация начального объекта Task, чтобы избежать NullPointerException(либо установить для Генератора максимальный приоритет, а для Интегратора минимальный)
// ------------------------------------------------------------------
//        // Генерация случайного основания для логарифма [2.0, 10.0)
//        double base = 2.0 + 8.0 * random.nextDouble();
//        Function logFunction = new Log(base);
//        task.setFunction(logFunction);
//
//        // Генерация случайных границ и шага
//        double left = 100.0 * random.nextDouble(); // [0, 100)
//        task.setLeftBound(left);
//
//        double right = 100.0 + 100.0 * random.nextDouble(); // [100, 200)
//        task.setRightBound(right);
//
//        double step = random.nextDouble();
//        task.setStep(step);
// ------------------------------------------------------------------

        // Создание генератора и интегратора
        Runnable generator = new SimpleGenerator(task);
        Runnable integrator = new SimpleIntegrator(task);

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

