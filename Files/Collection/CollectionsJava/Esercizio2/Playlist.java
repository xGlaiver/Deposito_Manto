package CollectionsJava.Esercizio2;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    public static void main(String[] args) {
        List<String> playlist = new ArrayList<>();

        playlist.add("Intro");
        playlist.add("Groove");
        playlist.add("Ballad");
        playlist.add("Finale");

        playlist.remove(2);
        playlist.add(0, "Sorpresa");

        System.out.println(playlist);
        System.out.println("Numero di brani: " + playlist.size());
    }
}
