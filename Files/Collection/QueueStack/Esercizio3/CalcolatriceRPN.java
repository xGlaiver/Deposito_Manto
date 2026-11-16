import java.util.ArrayDeque;
import java.util.Deque;

public class CalcolatriceRPN {

    static int valuta(String espressione) {
        Deque<Integer> pila = new ArrayDeque<>();

        for (String token : espressione.split(" ")) {
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                if (pila.size() < 2) {
                    throw new IllegalArgumentException("Espressione incompleta");
                }
                int b = pila.pop();
                int a = pila.pop();
                if (token.equals("+")) pila.push(a + b);
                if (token.equals("-")) pila.push(a - b);
                if (token.equals("*")) pila.push(a * b);
                if (token.equals("/")) pila.push(a / b);
            } else {
                try {
                    pila.push(Integer.parseInt(token));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Token non valido: " + token);
                }
            }
        }

        if (pila.size() != 1) {
            throw new IllegalArgumentException("Espressione incompleta");
        }
        return pila.pop();
    }

    public static void main(String[] args) {
        System.out.println(valuta("3 4 + 2 *"));

        try {
            valuta("3 x +");
        } catch (IllegalArgumentException e) {
            System.out.println("Errore: " + e.getMessage());
        }

        try {
            valuta("3 +");
        } catch (IllegalArgumentException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}
