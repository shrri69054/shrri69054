namespace targets {
struct Alien
{
    int x_coordinate;
    int y_coordinate;
    inline int get_health() const { return health; }
    inline bool hit() {
        if (is_alive()) health--;
        return true;
    }
    inline bool is_alive() const { return health > 0; }
    inline bool teleport(int x, int y) {
        x_coordinate = x;
        y_coordinate = y;
        return true;
    }
    inline bool collision_detection(const Alien& other) const {
        return ((x_coordinate == other.x_coordinate)
            && (y_coordinate == other.y_coordinate));
    }

    int health = 3;
};

}  // namespace targets