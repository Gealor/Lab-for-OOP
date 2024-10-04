package threads;

import functions.Function;
import functions.Functions;

public class SimpleIntegrator implements Runnable {
    private final Task task;

    public SimpleIntegrator(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getTaskCount(); i++){
            synchronized (task) {
                // Получаем данные задачи
                Function currentFunction = task.getFunction();
                double left = task.getLeftBound();
                double right = task.getRightBound();
                double step = task.getStep();


                // Обработка задачи
                double result;
                if (currentFunction == null) {
                    result = Double.NaN;
                } else {
                    result = Functions.integrate(currentFunction, left, right, step);
                }

                // Вывод результата в консоль
                System.out.printf("Result: %.4f %.4f %.4f %.4f%n", left, right, step, result);

                try{
                    task.notify();
                    task.wait();
                } catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Integrator interrupted during sleep.");
                return;
            }
        }
    }
}