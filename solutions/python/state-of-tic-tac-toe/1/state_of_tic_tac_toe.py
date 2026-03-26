"""Determine the state of Tic Tac Toe."""
WIN_PATTERNS = [
    # Columns
    tuple((x, y) for x in range(3)) for y in range(3)
] + [
    # Rows
    tuple((x, y) for y in range(3)) for x in range(3)
    # Diagonals:
] + [tuple((i, i) for i in range(3))] +  [tuple((i, 2 - i) for i in range(3))]


def gamestate(board: list[str]) -> str:
    """Return the state of a Tic Tac Toe game."""
    moves: dict[str, set[tuple[int, int]]] = {c: set() for c in "XO"}
    for y, row in enumerate(board):
        for x, char in enumerate(row):
            if char != " ":
                moves[char].add((x, y))
    counts = {player: len(m) for player, m in moves.items()}

    if counts["X"] < counts["O"]:
        raise ValueError("Wrong turn order: O started")
    if counts["X"] > counts["O"] + 1:
        raise ValueError("Wrong turn order: X went twice")

    winner = {
        player
        for pattern in WIN_PATTERNS
        for player, moved in moves.items()
        if all(p in moved for p in pattern)
    }

    if len(winner) == 2:
        raise ValueError("Impossible board: game should have ended after the game was won")
    if len(winner) == 1:
        return "win"
    if counts["X"] + counts["O"] == 9:
        return "draw"
    return "ongoing"
