/**
 * InnerLambdaMultParam
 */
public interface Functional {

    int operation (int a, int b);
}

public class LambdaMultParam {

    public static void main(String[] args) {
        Functional add = (a,b) -> {
            return a + b;
        };
        Functional multiply = (a, b) -> a * b;

        System.out.println(add.operation(6, 10));
        System.out.println(multiply.operation(10, 10));
    }
}

