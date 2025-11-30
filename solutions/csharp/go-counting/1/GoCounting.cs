using System;
using System.Linq;
using System.Collections.Generic;
using System.Collections;
public enum Owner
{
    None,
    Black,
    White,
	OffBoard
}
public class GoCounting
{
    private IEnumerable<( Owner owner, (int x, int y) coord )> _gameBoard;
    public GoCounting(string input)
    {
        var lines = input.Split(Environment.NewLine.ToCharArray());
		_gameBoard = Enumerable.Range(0, NumRows(lines))
            .SelectMany(rowIndex => Enumerable.Range(0, NumColumns(lines))
                .Select(columnIndex => ( OwnerFromChar(lines[rowIndex][columnIndex]), (columnIndex, rowIndex) )
				)
            );
    }
	
    public Tuple<Owner, HashSet<(int x, int y)>> Territory((int x, int y) coord)
    {
        //edge case: off board
		if (!_gameBoard.Any(i => i.coord == coord))
			throw new ArgumentException($"coordinate ({coord.x},{coord.y}) is outside of the board");
		
		//edge case: not an empty intersection
        var intersectionOwner = IntersectionStone(coord);
		if (intersectionOwner != Owner.None)
			return new Tuple<Owner, HashSet<(int, int)>>(Owner.None, new HashSet<(int, int)>());
		
		var territory = TerritoryIntersections(coord, new HashSet<(int x, int y)>());
		var owner = TerritoryOwner(territory);
        
		return new Tuple<Owner, HashSet<(int, int)>>(owner, territory);
    }
	public Dictionary<Owner, HashSet<(int, int)>> Territories()
    {
		var territories = new Dictionary<Owner, HashSet<(int, int)>>
		{
			{ Owner.Black, new HashSet<(int, int)>() },
			{ Owner.White, new HashSet<(int, int)>() },
			{ Owner.None, new HashSet<(int, int)>() }
		};
    
		_gameBoard
			.Where ( intersection => intersection.owner == Owner.None )
			.Select( intersection => Territory(intersection.coord) )
			.ToList()
			.ForEach( territory => territories[territory.Item1].UnionWith(territory.Item2) );
		return territories;
    }
	//Helper methods
    private static Owner OwnerFromChar(char c) => 
        c switch
        {
            'B' => Owner.Black,
            'W' => Owner.White,
            _ => Owner.None
        };
	
	private static int NumRows(string[] lines) =>
		lines.Length;
	private static int NumColumns(string[] lines) =>
		(lines[0].Contains('\n'))
				? lines[0].Length - 1
				: lines[0].Length;
	private Owner IntersectionStone((int x, int y) coord) => 
		_gameBoard.Any(i => i.coord == coord)
		? _gameBoard.Where(i => i.coord == coord).First().owner
		: Owner.OffBoard;
	private HashSet<(int x, int y)> TerritoryIntersections((int x, int y) coord, HashSet<(int x, int y)> territory)
	{
		if (territory.Contains(coord))
			return territory;
		
		var intersectionOwner = IntersectionStone(coord);
		if (intersectionOwner != Owner.None)
			return territory;
		
		var current = new HashSet<(int x, int y)>(territory);
		current.Add(coord);			
		Neighbors(coord).ForEach( n => current.UnionWith(TerritoryIntersections(n, current)) );
		return current;
	}
	
	private Owner TerritoryOwner(HashSet<(int x, int y)> territory) =>
		DecideOwner(TerritoryOwners(territory));
	
	private Owner DecideOwner(IEnumerable<Owner> owners) =>
		(owners.Contains(Owner.Black) && !owners.Contains(Owner.White)) ? Owner.Black
		: (owners.Contains(Owner.White) && !owners.Contains(Owner.Black)) ? Owner.White
		: Owner.None;
	
	private IEnumerable<Owner> TerritoryOwners(HashSet<(int x, int y)> territory) => 
		territory.SelectMany ( intersection => IntersectionOwners(intersection) );
	
	private IEnumerable<Owner> IntersectionOwners((int x, int y) coord) => 
		Neighbors(coord).Select( neighborCoord => IntersectionStone(neighborCoord) );
	
	private static List<(int x, int y)> Neighbors((int x, int y) coord) =>
		new List<(int x, int y)> 
	{ 
		(coord.x, coord.y - 1),
		(coord.x, coord.y + 1), 
		(coord.x - 1, coord.y), 
		(coord.x + 1, coord.y) 
	};
}