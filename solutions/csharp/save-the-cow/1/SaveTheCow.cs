using System.Collections.Immutable;
using System.Reactive.Subjects;
using System.Reactive.Linq;

public class GameState
{
    public string MaskedWord { get; }
    public ImmutableHashSet<char> GuessedChars { get; }
    public int RemainingGuesses { get; }

    public GameState(string maskedWord, ImmutableHashSet<char> guessedChars, int remainingGuesses)
    {
        MaskedWord = maskedWord;
        GuessedChars = guessedChars;
        RemainingGuesses = remainingGuesses;
    }
}

public class TooManyGuessesException : Exception
{
}

public class SaveTheCow
{
    private readonly Subject<char> _guesses = new Subject<char>();
    private readonly BehaviorSubject<GameState> _state;
    
    public IObservable<GameState> StateObservable { get; }
    public IObserver<char> GuessObserver { get => _guesses; }

    public SaveTheCow(string word)
    {
        _state = new BehaviorSubject<GameState>(new GameState(new String('_',word.Length), ImmutableHashSet<char>.Empty, 9));
        StateObservable = _state;

        var actual = _state.Value;
        _guesses.Subscribe(letra =>
        {
            actual = CalcularSiguienteEstado(actual, letra, word);
            if(actual.RemainingGuesses < 0)
            {
                _state.OnError(new TooManyGuessesException());
                return;
            }
            if(!actual.MaskedWord.Contains('_'))
            {
                _state.OnCompleted();
                return;
            }
            _state.OnNext(actual);
        });
    }
    public GameState CalcularSiguienteEstado(GameState anterior, char letra, string word)
    {
        var h = anterior.GuessedChars;
        var c = anterior.RemainingGuesses;
        if(word.Contains(letra) && !h.Contains(letra)) h = h.Add(letra); 
        else c--;
        var masked = word.Select(ch => h.Contains(ch)?ch:'_').ToArray();
        var m = new String(masked);
        return new GameState(m, h, c);
    }
}
