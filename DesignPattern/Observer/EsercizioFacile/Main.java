public class Main {
    public static void main(String[] args) {
        // Subject
        Termometro termometro = new Termometro();

        DispositivoObserver ventilatore = temperatura -> {
            if (temperatura > 28) {
                System.out.println("Ventilatore acceso");
            }
        };
        DispositivoObserver display = temperatura ->
            System.out.println("Display: " + temperatura + "°C");

        termometro.iscrivi(ventilatore);
        termometro.iscrivi(display);

        // Letture: ogni registra() avvisa automaticamente tutti
        termometro.registra(25);
        termometro.registra(29);
        termometro.registra(27);
    }
}
