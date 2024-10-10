import threads.Generator;
import threads.Integrator;
import threads.Task;

import java.util.concurrent.Semaphore;

public class Main7 {
    public static void main(String[] args) {
        complicatedThreads();
    }

    public static void complicatedThreads(){
        int numberOfTasks = 100;
        Task task = new Task();
        task.setTaskCount(numberOfTasks);

        Semaphore semaphore = new Semaphore(1, true);

        // Создание и настройка потоков
        Generator generator = new Generator(task, semaphore);
        Integrator integrator = new Integrator(task, semaphore);

        generator.setPriority(Thread.NORM_PRIORITY);
        integrator.setPriority(Thread.NORM_PRIORITY);

        // Запуск потоков
        generator.start();
        integrator.start();

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Прерывание потоков
        generator.interrupt();
        integrator.interrupt();
        System.out.println("The threads were interrupted 50 milliseconds after startup");


        System.out.println("Main thread finished.");
    }
}
