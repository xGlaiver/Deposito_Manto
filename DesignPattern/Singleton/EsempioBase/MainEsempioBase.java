public class MainEsempioBase {
    public static void main(String[] args) {
        Configurazione a = Configurazione.getInstance();
        Configurazione b = Configurazione.getInstance();
        System.out.println("A e B sono lo stesso oggetto? " + (a == b));
        System.out.println("URL Server (a): " + a.getUrlServer());
        System.out.println("URL Server (b): " + b.getUrlServer());
    }
}
