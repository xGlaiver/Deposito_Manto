package EsercizioMedio;

public class Somma implements Comando{

    @Override
    public String esegui(int a, int b) {
        int result = a + b;
        return "La somma è : " + result;
    }
    
}
