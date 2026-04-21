import java.util.*;

public class MazeGenerator {
    private char[][] maze;
    private boolean[][] visited;
    private final Random rand;

    public MazeGenerator() {
        rand = new Random(); // Random without seed
    }

    // Overloaded constructor with seed
    public MazeGenerator(long seed) {
        rand = new Random(seed);
    }

    public char[][] generatePerfectMaze(int rows, int columns) {
        if (rows < 5 || rows > 100 || columns < 5 || columns > 100) {
            throw new IllegalArgumentException("Rows and columns must be between 5 and 100.");
        }

        int height = rows * 2 + 1;
        int width = columns * 2 + 1;
        maze = new char[height][width];
        visited = new boolean[rows][columns];

        // Initialize the maze with walls
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = '┼'; // Fill with wall character
            }
        }

        // Carve out the maze
        recursiveBacktrack(0, 0);

        // Set entrance and exit
        maze[1][0] = '⇨'; // Entrance
        maze[height - 2][width - 1] = '⇨'; // Exit

        // Replace all visited cells with empty space
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (visited[row][col]) {
                    maze[row * 2 + 1][col * 2 + 1] = ' ';
                }
            }
        }

        // Convert the remaining '┼' to the appropriate box drawing characters
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // Assuming a method to convert '┼' to the correct character based on neighbors
                maze[i][j] = convertToBoxCharacter(maze, i, j);
            }
        }

        return maze;
    }

    private void recursiveBacktrack(int row, int col) {
        visited[row][col] = true;

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        Collections.shuffle(Arrays.asList(directions), rand);

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (isValid(newRow, newCol)) {
                // Knock down the wall if there is one
                if (maze[row * 2 + 1 + dir[0]][col * 2 + 1 + dir[1]] == '┼') {
                    maze[row * 2 + 1 + dir[0]][col * 2 + 1 + dir[1]] = ' ';
                }

                recursiveBacktrack(newRow, newCol);
            }
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < visited.length && col >= 0 && col < visited[0].length && !visited[row][col];
    }

    private char convertToBoxCharacter(char[][] maze, int i, int j) {
        if (maze[i][j] != '┼') {
            return maze[i][j];
        }

        // Create a bitmask for the four neighbor cells
        int mask = 0;
        mask |= (i > 0 && maze[i - 1][j] == ' ') ? 1 : 0;          // Up
        mask |= (i < maze.length - 1 && maze[i + 1][j] == ' ') ? 2 : 0;   // Down
        mask |= (j > 0 && maze[i][j - 1] == ' ') ? 4 : 0;          // Left
        mask |= (j < maze[i].length - 1 && maze[i][j + 1] == ' ') ? 8 : 0; // Right

        // Define a map from masks to box characters
        char[] chars = {
                '┼', // 0000 No neighbors
                '│', // 0001 Up
                '│', // 0010 Down
                '│', // 0011 Up and Down
                '─', // 0100 Left
                '┘', // 0101 Left and Up
                '┐', // 0110 Left and Down
                '┤', // 0111 Left, Up, and Down
                '─', // 1000 Right
                '└', // 1001 Right and Up
                '┌', // 1010 Right and Down
                '├', // 1011 Right, Up, and Down
                '─', // 1100 Left and Right
                '┴', // 1101 Left, Right, and Up
                '┬', // 1110 Left, Right, and Down
                '┼', // 1111 All directions
        };

        return chars[mask];
    }


    // Overloaded method to generate a maze with a seed
    public char[][] generatePerfectMaze(int rows, int columns, int seed) {
        rand.setSeed(seed);
        return generatePerfectMaze(rows, columns);
    }
}