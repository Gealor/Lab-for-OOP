package threads;

import functions.Function;
import functions.basic.Log;

import java.util.Random;

public class SimpleGenerator implements Runnable{
    private final Task task;

    public SimpleGenerator(Task task){
        this.task = task;
    }

    @Override
    public void run(){
        Random random = new Random();
        for (int i = 0; i < task.getTaskCount(); i++) {
            synchronized (task) {
                // Генерация случайного основания для логарифма [2.0, 10.0)
                double base = 2.0 + 8.0 * random.nextDouble();
                Function logFunction = new Log(base);
                task.setFunction(logFunction);

                // Генерация случайных границ и шага
                double left = 100.0 * random.nextDouble(); // [0, 100)
                task.setLeftBound(left);

                double right = 100.0 + 100.0 * random.nextDouble(); // [100, 200)
                task.setRightBound(right);

                double step = random.nextDouble();
                task.setStep(step);

                System.out.printf("%d. Source: %.4f %.4f %.4f%n", i, left, right, step);
            }
        }
    }
//    @Override
//    public void run(){
//        Random random = new Random();
//        for (int i = 0; i < task.getTaskCount(); i++) {
//            synchronized(task) {
//                // Генерация случайного основания для логарифма [2.0, 10.0)
//                double base = 2.0 + 8.0 * random.nextDouble();
//                Function logFunction = new Log(base);
//                task.setFunction(logFunction);
//
//                // Генерация случайных границ и шага
//                double left = 100.0 * random.nextDouble(); // [0, 100)
//                task.setLeftBound(left);
//
//                double right = 100.0 + 100.0 * random.nextDouble(); // [100, 200)
//                task.setRightBound(right);
//
//                double step = random.nextDouble();
//                task.setStep(step);
//
//                // Вывод сообщения в консоль
//                System.out.printf("%d. Source: %.4f %.4f %.4f%n", i, left, right, step);
//
//                try{
//                    task.wait();
//                    task.notify();
//                } catch (InterruptedException e){
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }
}
