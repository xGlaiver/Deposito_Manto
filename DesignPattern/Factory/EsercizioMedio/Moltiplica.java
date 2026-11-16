package EsercizioMedio;

public class Moltiplica implements Comando{

    @Override
    public String esegui(int a, int b) {
        int result = a * b;
        return "La moltiplicazione è : " + result;
    }
    
}
