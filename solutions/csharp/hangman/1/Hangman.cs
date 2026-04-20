using  System;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.Linq;
using System.Reactive;
using System.Reactive.Linq;
using System.Reactive.Subjects;
public class HangmanState
{
    public string MaskedWord { get; }
    public ImmutableHashSet<char> GuessedChars { get; }
    public int RemainingGuesses { get; }
    public HangmanState(string maskedWord, ImmutableHashSet<char> guessedChars, int remainingGuesses)
    {
        MaskedWord = maskedWord;
        GuessedChars = guessedChars;
        RemainingGuesses = remainingGuesses;
    }
}
public class TooManyGuessesException : Exception
{
}
public class Hangman
{
    readonly string _word;
    readonly BehaviorSubject<HangmanState> _subject;
    readonly IObserver<char> _guessObserver;
    int _remainingGuesses = 9;
    readonly HashSet<char> _guessedChars = new HashSet<char>();
    public IObservable<HangmanState> StateObservable 
        => _subject;
    public IObserver<char> GuessObserver 
        => _guessObserver;
    public Hangman(string word)
    {
        _word = word;
        _subject = new BehaviorSubject<HangmanState>(ImmutableState);
        _guessObserver = Observer.Create<char>(value => {
            if (_remainingGuesses <= 0) {
                _subject.OnError(new TooManyGuessesException());
                return;
            }
            if (!_guessedChars.Add(value) || !_word.Contains(value))
                --_remainingGuesses;
            else if (_word.All(p => _guessedChars.Contains(p))) {
                _subject.OnCompleted();
                return;
            }
            _subject.OnNext(ImmutableState);
        });
    }
    HangmanState ImmutableState => new HangmanState(
        new string(_word.Select(p => _guessedChars.Contains(p) ? p : '_').ToArray()),
        _guessedChars.ToImmutableHashSet(),
        _remainingGuesses
    );
}