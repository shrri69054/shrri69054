import io.reactivex.Observable;
import java.util.AbstractMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

class Hangman {
    Observable<Output> play(Observable<String> words, Observable<String> letters) {
        return Observable
            .combineLatest(
                words,
                letters.startWith(""),
                (word, letter) -> new AbstractMap.SimpleEntry<>(word, letter))
            .scan(
                Output.empty(),
                (game, entry) -> {                    
                    if (game == null || game.status != Status.PLAYING) {
                        return startGame(entry.getKey());
                    } else {
                        return guess(game, entry.getValue());
                    }
                })
            .skip(1); // Skip the first
    }

    private static Output startGame(String word) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            result.append("_");
        }
        return new Output(
            word,
            result.toString(),
            new HashSet<>(),
            new HashSet<>(),
            new ArrayList<>(),
            Status.PLAYING);
    }

    private static Output guess(Output game, String letter) {
        if (game.guess.contains(letter) || game.misses.contains(letter)) {
            throw new IllegalArgumentException("Letter " + letter + " was already played");
        }
        if (game.secret.contains(letter)) {
            Set<String> newGuess = new HashSet<>(game.guess);
            newGuess.add(letter);
            StringBuilder newDiscovered = new StringBuilder();
            for (int i = 0; i < game.discovered.length(); i++) {
                char c = game.discovered.charAt(i);
                char s = game.secret.charAt(i);
                if (String.valueOf(s).equals(letter)) {
                    newDiscovered.append(letter);
                } else {
                    newDiscovered.append(c);
                }
            }
            return new Output(
                game.secret,
                newDiscovered.toString(),
                newGuess,
                game.misses,
                game.parts,
                newDiscovered.toString().contains("_") ? Status.PLAYING : Status.WIN);
        } else {
            Set<String> newMisses = new HashSet<>(game.misses);
            newMisses.add(letter);
            List<Part> newParts = new ArrayList<>(game.parts);
            newParts.add(Part.values()[newParts.size()]);
            Status newStatus = newMisses.size() >= Part.values().length
                ? Status.LOSS
                : Status.PLAYING;

            return new Output(
                game.secret,
                game.discovered,
                game.guess,
                newMisses,
                newParts,
                newStatus);
        }
    }
}