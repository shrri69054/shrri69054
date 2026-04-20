# TODO: Create a class called "Alien" here

class Alien:
    total_aliens_created = 0
    
    def __init__(self, x, y):
        self.x_coordinate = x
        self.y_coordinate = y
        self.health = 3
        Alien.total_aliens_created += 1

    def hit(self):
        self.health -= 1

    def is_alive(self):
        return self.health > 0

    def teleport(self, new_x, new_y):
        self.x_coordinate = new_x
        self.y_coordinate = new_y

    def collision_detection(self, other_object):
        pass




def new_aliens_collection(positions):
    """Function taking a list of position tuples, creating one Alien instance per position.

    :param positions: list - A list of tuples of (x, y) coordinates.
    :return: list - A list of Alien objects.
    """
    return [Alien(x,y) for x,y in positions]