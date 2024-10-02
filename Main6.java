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
        // Инициализирую начальную функцию, чтобы избежать вылета ошибки в первой итерации, т.к в начале Function равно null и step=0.0
// ------------------------------------------------------------------------------------------
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
// ------------------------------------------------------------------------------------------

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

        // Ожидание завершения потоков
        try {
            // Использую метод join(), чтобы главный поток ждал завершения обоих потоков.
            generatorThread.join();
            integratorThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted.");
        }

        System.out.println("All tasks have been processed.");
    }
}

// Изначальная проблема заключалась в том,
// что поток SimpleIntegrator пытался обращаться к полям объекта Task,
// которые еще не были установлены потоком SimpleGenerator.
// Это приводило к тому, что некоторые поля, такие как function, были null,
// и попытка вызвать методы на них вызывала NullPointerException.
