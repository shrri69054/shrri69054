using System;

public enum Direction
{
    North,
    East,
    South,
    West
}
public class RobotSimulator
{
    Direction direction;
    int x, y;
    public RobotSimulator(Direction direction, int x, int y)
    {
        this.direction = direction;
        this.x = x;
        this.y = y;
    }
    public Direction Direction
    {
        get
        {
            return direction;
        }
    }
    public int X
    {
        get
        {
            return x;
        }
    }
    public int Y
    {
        get
        {
            return y;
        }
    }
    public void Move(string instructions)
    {
        foreach (var step in instructions)
        {
            switch (step)
            {
                case 'A': Advance(); break;
                case 'R': TurnRight(); break;
                case 'L': TurnLeft(); break;
            }
        }
    }
    void Advance()
    {
        switch (direction)
        {
            case Direction.North:
                y++;
                break;
            case Direction.South:
                y--;
                break;
            case Direction.East:
                x++;
                break;
            case Direction.West:
                x--;
                break;
        }
    }
    void TurnRight()
    {
        direction++;
        if ((int)direction > 3) direction = (Direction)0;
    }
    void TurnLeft()
    {
        direction--;
        if ((int)direction < 0) direction = (Direction)3;
    }
}