class Darts {
    int score(double xOfDart, double yOfDart) {
        // Calculate the distance from the center (0, 0) to the point (xOfDart, yOfDart)
        double distance = Math.sqrt(xOfDart * xOfDart + yOfDart * yOfDart);
        
        // Determine the score based on the distance
        if (distance <= 1) {
            return 10;  // Inner circle
        } else if (distance <= 5) {
            return 5;   // Middle circle
        } else if (distance <= 10) {
            return 1;   // Outer circle
        } else {
            return 0;   // Outside the target
        }
    }
}
