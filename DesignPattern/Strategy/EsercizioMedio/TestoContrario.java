package EsercizioMedio;

public class TestoContrario implements Formattazione{

    @Override
    public String applica(String testo) {
        char[] textChar= testo.toCharArray();
        String reversedText = "";

        for(int i = textChar.length - 1; i >= 0; i --) {
            reversedText += textChar[i];
        }

        return reversedText;
    } 
    
}
