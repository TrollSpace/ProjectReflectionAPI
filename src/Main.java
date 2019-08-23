import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        start(MyClass.class);
    }

    static void start(Class<?> c) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Method[] methods = c.getDeclaredMethods();
        ArrayList<Method> listMethod = new ArrayList<>();
        Method methodBefore = null, methodAfter = null;
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                int priority = method.getAnnotation(Test.class).priority();
                if (priority < 1 || priority > 10) {
                    throw new RuntimeException("Illegal priority: " + priority + ".");
                }
                listMethod.add(method);

            }
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (methodBefore != null) {
                    throw new RuntimeException("BeforeSuite is exist.");
                } else {

                    methodBefore = method;
                }
            }
            if (method.isAnnotationPresent(AfterSuite.class)) {
                if (methodAfter != null) {
                    throw new RuntimeException("AfterSuite is exist.");
                } else {

                    methodAfter = method;
                }
            }
        }
        listMethod.sort(Comparator.comparingInt(o -> o.getAnnotation(Test.class).priority()));
        if (methodAfter != null) {
            listMethod.add(methodAfter);
        }
        if (methodBefore != null) {
            listMethod.add(0, methodBefore);
        }
        Object object = c.newInstance();
        for (Method m:listMethod){
            m.invoke(object);
        }
    }


}
