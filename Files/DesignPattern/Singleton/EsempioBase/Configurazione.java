class Configurazione {
    private static final Configurazione INSTANCE = new Configurazione();  // creata una sola volta

    private String urlServer = "http://localhost:8080";

    private Configurazione() { }                 // costruttore privato: nessuno può fare new

    static Configurazione getInstance() {
        return INSTANCE;                         // sempre lo stesso oggetto
    }

    String getUrlServer() { return urlServer; }
}