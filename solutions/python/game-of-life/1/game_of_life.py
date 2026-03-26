DIRECTIONS = [(dx, dy) for dx in [-1, 0, 1] for dy in [-1, 0, 1] if dy or dx]


def tick(matrix):
    """Return the next tick in the Game of Life.

    Any live cell with two or three live neighbors lives on.
    Any dead cell with exactly three live neighbors becomes a live cell.
    All other cells die or stay dead.
    """
    height = len(matrix)
    width = len(matrix[0]) if height else 0
    alive = {
        (x, y)
        for y, row in enumerate(matrix)
        for x, v in enumerate(row)
        if v == 1
    }
    out = []
    for y in range(height):
        row = []
        for x in range(width):
            count = sum((x + dx, y + dy) in alive for dx, dy in DIRECTIONS)
            next_alive = (
                ((x, y) in alive and count in [2, 3])
                or ((x, y) not in alive and count == 3)
            )
            row.append(1 if next_alive else 0)
        out.append(row)
    return out
