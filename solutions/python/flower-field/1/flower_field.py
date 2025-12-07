def annotate(garden):
    grid = []
    grid = [
        [0 for _ in row]
        for row in garden
    ]
    for r, row in enumerate(garden):
        if len(row) != len(grid[0]):
            raise ValueError("The board is invalid with current input.")
        for c, spot in enumerate(row):
            if spot == " ": continue
            elif spot != "*":
                raise ValueError("The board is invalid with current input.")
            grid[r][c] = "*"
            for dr, dc in [
                (-1, -1), (-1, 0), (-1, 1),
                (0, -1), (0, 1),
                (1, -1), (1, 0), (1, 1)
            ]:
                dr += r
                if dr < 0 or dr >= len(grid):
                    continue
                dc += c
                if dc < 0 or dc >= len(grid[dr]):
                    continue
                if grid[dr][dc] != "*":
                    grid[dr][dc] += 1
                
    return [
        "".join(
            " " if spot == 0
            else str(spot)
            for spot in row
        )
        for row in grid
    ]