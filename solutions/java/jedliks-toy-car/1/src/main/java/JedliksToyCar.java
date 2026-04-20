public class JedliksToyCar {
    private int distance = 0;
    private int battery = 100;

    // Static method to buy a new car (corrected class name)
    public static JedliksToyCar buy() {
        return new JedliksToyCar();  // Return a new instance of JedliksToyCar
    }

    // Method to display the total distance driven
    public String distanceDisplay() {
        return "Driven " + distance + " meters";
    }

    // Method to display the battery status
    public String batteryDisplay() {
        if (battery == 0) {
            return "Battery empty";
        } else {
            return "Battery at " + battery + "%";
        }
    }

    // Method to simulate driving the car
    public void drive() {
        if (battery > 0) {
            distance += 20;  // Car drives 20 meters
            battery -= 1;    // Battery decreases by 1%
        }
    }
}
