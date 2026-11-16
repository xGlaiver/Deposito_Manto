package EsercizioMedio;

public class LogObserver implements Observer{

    @Override
    public void update(IngressoSocio evento) {
        System.out.println("Utente " + evento.nome() + ". Abbonamento scaduto: " + evento.abbonamentoScaduto()) ;
    }
    
}
