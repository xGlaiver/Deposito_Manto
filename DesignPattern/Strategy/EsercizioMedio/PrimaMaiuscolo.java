package EsercizioMedio;

public class PrimaMaiuscolo implements Formattazione{

    @Override
    public String applica(String testo) {
        String[] parole = testo.split(" ");
        StringBuilder risultato = new StringBuilder();

        for (int i = 0; i < parole.length; i++) {
            String parola = parole[i];
            if (!parola.isEmpty()) {
                risultato.append(parola.substring(0, 1).toUpperCase());
                risultato.append(parola.substring(1));
            }
            if (i < parole.length - 1) {
                risultato.append(" ");
            }
        }

        return risultato.toString();
    }

}
