package functions;

import java.lang.reflect.Constructor;

public class TabulatedFunctionFactory {
    public static TabulatedFunction createTabulatedFunction(String className, Class<?>[] paramTypes, Object[] initArgs) throws Exception{
        Class<?> clazz = Class.forName(className);
        if (!TabulatedFunction.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("The class does not implement TabulatedFunction.");
        }
        Constructor<?> constructor = clazz.getConstructor(paramTypes);
        return (TabulatedFunction) constructor.newInstance(initArgs);
    }
}
