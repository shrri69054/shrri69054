using System;
using System.Collections.Generic;
using System.Linq;
public class WordSearch
{
    private string[] _grid;
	private int _gridRows { get => _grid.GetLength(0); }
	private int _gridColumns { get => _grid[0].Length; }
	private (int x, int y, char letter) _invalidLetterLocation = (-1, -1, ' ');
	private List<(int deltaX, int deltaY)> WordDirections = new List<(int deltaX, int deltaY)>
	{
		( 1, 0), //right
		(-1, 0), //left
		( 0,-1), //down
		( 0, 1), //up
		( 1,-1), //down-right
		(-1,-1), //down-left
		( 1, 1), //up-right
		(-1, 1)  //up-left
	};
    public WordSearch(string grid)
    {
        _grid = grid.Split("\n");
    }
    public Dictionary<string, ((int, int), (int, int))?> Search(string[] wordsToSearchFor) =>
		wordsToSearchFor
			.Select(word => Search(word))
			.ToDictionary(result => result.word, result => result.location);
	private (string word, ((int, int) start, (int, int) end)? location) Search(string wordToSearchFor) =>
		FirstLetterLocations(wordToSearchFor[0])
			.SelectMany(startLoc => WordDirections
				.Select(direction => LastLetterLocation(wordToSearchFor, startLoc, direction) ))
			.Where(result => result.location is not null)
			.DefaultIfEmpty((wordToSearchFor, null))
			.First();
	
	private IEnumerable<(int x, int y)> FirstLetterLocations(char firstLetter) =>
		Enumerable.Range(0, _gridRows)
			.SelectMany(lineIndex => Enumerable.Range(0, _gridColumns)
				.Select(letterIndex => (letterIndex, lineIndex))
				.Where(location => _grid[lineIndex][location.Item1] == firstLetter));
	
	private (string word, ((int, int) start, (int, int) end)? location) LastLetterLocation(string wordToSearchFor, (int x, int y) firstLetterLocation, (int deltaX, int deltaY) directionToSearch) 
    {
		(int x, int y, char letter) nextLetter = _invalidLetterLocation;
		for (int i = 1; i < wordToSearchFor.Length; i++)
		{
			nextLetter = NextLetterToSearchFor(firstLetterLocation, directionToSearch, i, wordToSearchFor);
			if ( !LocationIsInsideGrid((nextLetter.x, nextLetter.y)) || !GridHasLetter(nextLetter) )
				return (wordToSearchFor, null);
		}
		return (wordToSearchFor, GetLocationOrNull(firstLetterLocation, nextLetter));
    }
	
	private (int x, int y, char letter) NextLetterToSearchFor((int x, int y) firstLetterLocation, (int deltaX, int deltaY) directionToSearch, int distance, string wordToSearchFor) =>
		( firstLetterLocation.x + (directionToSearch.deltaX * distance), 
		firstLetterLocation.y + (directionToSearch.deltaY * distance),
		wordToSearchFor[distance] );
	private bool LocationIsInsideGrid((int x, int y) location) =>
		location.x >= 0 && location.x < _gridColumns && 
		location.y >= 0 && location.y < _gridRows;
	
	private bool GridHasLetter((int letterIndex, int lineIndex, char letterToSearchFor) nextLetter) =>
		_grid[nextLetter.lineIndex][nextLetter.letterIndex] == nextLetter.letterToSearchFor;
	
	private ((int, int) start, (int, int) end)? GetLocationOrNull((int x, int y) firstLetterLocation, (int x, int y, char letterToSearchFor) nextLetter) => 
		nextLetter == _invalidLetterLocation
		? null
		: (ConvertLocationTo1BasedArray(firstLetterLocation), 
		   ConvertLocationTo1BasedArray((nextLetter.x, nextLetter.y)));
	
	private (int x, int y) ConvertLocationTo1BasedArray((int x, int y) location) => 
		(location.x + 1, location.y + 1);
}