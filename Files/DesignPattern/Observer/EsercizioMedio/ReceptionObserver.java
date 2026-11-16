package EsercizioMedio;

public class ReceptionObserver implements Observer {

    @Override
    public void update(IngressoSocio evento) {
        if(evento.abbonamentoScaduto()) {
            System.out.println("L'utente " + evento.nome() + " ha l'abbonamento scaduto");
        }
    }
    
}
