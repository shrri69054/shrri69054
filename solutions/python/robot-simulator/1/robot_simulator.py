"""
Exercism solution for "robot-simulator"
"""

NORTH, EAST, SOUTH, WEST = 0 + 1j, 1 + 0j, 0 - 1j, -1 + 0j


class Robot:
    """
    Simple implementation of a Robot, what turns.
    """

    def __init__(self, direction: complex, x: int, y: int):
        """
        Initialize a Robot.
        """
        self.direction = direction
        self.polar = x + y * 1j

    @property
    def coordinates(self) -> tuple[int, int]:
        """
        Access the robot's current coordinates.
        """
        return int(self.polar.real), int(self.polar.imag)

    def move(self, instructions: str) -> None:
        """
        Simulate this robot's movements.
        """
        for instruction in instructions:
            match instruction.upper():
                case "R":
                    self.direction *= -1j
                case "L":
                    self.direction *= 1j
                case "A":
                    self.polar += self.direction
                case _:
                    raise ValueError(f"Instruction {instruction!r} not implemented")