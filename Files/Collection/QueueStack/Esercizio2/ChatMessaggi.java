import java.util.ArrayDeque;
import java.util.Queue;

public class ChatMessaggi {
    public static void main(String[] args) {
        Queue<String> messaggi = new ArrayDeque<>();

        messaggi.offer("Ci vediamo alle 18?");
        messaggi.offer("Porto i documenti");
        messaggi.offer("Grazie!");

        System.out.println("Primo messaggio: " + messaggi.peek());

        String messaggio;
        while ((messaggio = messaggi.poll()) != null) {
            System.out.println("Letto: " + messaggio);
        }

        System.out.println("Nessun messaggio da leggere");
    }
}
