import functions.Function;
import functions.Functions;
import functions.basic.Log;
import threads.Task;
import java.util.Random;

public class Main5 {

    public static void main(String[] args) {
        nonThread();
    }

    public static void nonThread() {
        Task task = new Task();
        int numberOfTasks = 100;
        task.setTaskCount(numberOfTasks);

        Random random = new Random();

        for (int i = 0; i < numberOfTasks; i++){
            // 1) Создать и поместить в объект задания объект логарифмической функции
            double base = 1.0 + 9.0 * random.nextDouble(); // [1,10)
            Function logFunction = new Log(base);
            task.setFunction(logFunction);

            // 2) Указать в объекте задания левую границу области интегрирования [0,100)
            // nextDouble возвращает случайное число от 0 до 1
            double left = 100.0 * random.nextDouble();

            // 3) Указать в объекте задания правую границу области интегрирования [100,200)
            double right = 100.0 + 100.0 * random.nextDouble();

            // 4) Указать в объекте задания шаг дискретизации [0,1)
            double step = random.nextDouble();

            task.setLeftBound(left);
            task.setRightBound(right);
            task.setStep(step);

            // 5) Вывести сообщение "Source <левая граница> <правая граница> <шаг дискретизации>"
            System.out.printf("Source %.4f %.4f %.4f%n", left, right, step);

            // 6) Вычислить значение интеграла для параметров из объекта задания
            double result;
            try {
                result = Functions.integrate(task.getFunction(), task.getLeftBound(), task.getRightBound(), task.getStep());
            } catch (IllegalArgumentException e){
                System.out.println("Error: " + e.getMessage());
                // Переход к следующему заданию
                continue;
            }

            // 7) Вывести сообщение "Result <левая граница> <правая граница> <шаг дискретизации> <результат интегрирования>"
            System.out.printf("Result %.4f %.4f %.4f %.4f%n", left, right, step, result);

        }
    }
}
