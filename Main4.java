import functions.Function;
import functions.Functions;
import functions.basic.Exp;
import functions.exceptions.InappropriateFunctionPointException;
import functions.Functions.*;

import static functions.Functions.integrate;

public class Main4 {

    public static void main(String[] args) {
        Function expFunction = new Exp();

        double left = 0.0;
        double right = 1.0;

        double theoreticalValue = Math.exp(1) - 1;
        System.out.printf("Теоретическое значение интеграла: %.15f%n", theoreticalValue);


        double step = 0.1;
        double computedValue = 0.0;
        int iteration = 0;
        double tolerance = 1e-7;
        boolean isAccurate = false;

        while (!isAccurate){
            try {
                computedValue = Functions.integrate(expFunction, left, right, step);
                double difference = Math.abs(computedValue - theoreticalValue);
                iteration++;
                System.out.printf("Итерация %d: шаг = %.10f, интеграл = %.15f, разница = %.10f%n",
                                  iteration, step, computedValue, difference);

                if (difference < tolerance) {
                    isAccurate = true;
                    System.out.printf("Необходимый шаг дискретизации достигнут: %.10f%n", step);
                } else {
                    // Уменьшаю шаг дискретизации, например, вдвое
                    step /= 2.0;
                    if (step < 1e-10) {
                        System.out.println("Не удалось достичь требуемой точности с текущим подходом.");
                        break;
                    }
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Ошибка при интегрировании: " + e.getMessage());
                break;
            }
        }
        System.out.println(Functions.integrate(expFunction, left, right, 0.0007812500));
    }
}
