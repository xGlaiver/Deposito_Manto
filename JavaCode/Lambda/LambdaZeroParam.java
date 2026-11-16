/**
 * InnerLambdaZeroParam
 */
@FunctionalInterface 
public interface ZeroParam {

    void display();
}
public class LambdaZeroParam {
    public static void main(String[] args) {
        ZeroParam zp = () -> System.out.println("Lambda a zero parametri");

        zp.display();
    }
}
