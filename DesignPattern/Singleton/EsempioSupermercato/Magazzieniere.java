public class Magazzieniere {
    public void aggiugniProdottoAlMagazzino(){
        Magazzino.getInstance().aggiungi(new Prodotto("AAA", "Quaderno", 100, Categorie.CANCELLERIA));
    }
}
