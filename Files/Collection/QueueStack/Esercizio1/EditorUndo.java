import java.util.ArrayDeque;
import java.util.Deque;

public class EditorUndo {
    public static void main(String[] args) {
        Deque<String> modifiche = new ArrayDeque<>();

        modifiche.push("Ciao");
        modifiche.push("Ciao mondo");
        modifiche.push("Ciao mondo!");

        System.out.println("Testo corrente: " + modifiche.peek());

        modifiche.pop();
        System.out.println("Dopo annulla: " + modifiche.peek());

        modifiche.pop();
        System.out.println("Dopo annulla: " + modifiche.peek());
    }
}
