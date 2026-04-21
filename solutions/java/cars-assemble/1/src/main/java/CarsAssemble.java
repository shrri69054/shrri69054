public class CarsAssemble {

    // Calculates the production rate per hour based on speed and success rate
    public double productionRatePerHour(int speed) {
        double baseProduction = speed * 221;  // Base production without success rate
        double successRate;

        // Determine the success rate based on speed
        if (speed >= 1 && speed <= 4) {
            successRate = 1.0;  // 100% success
        } else if (speed >= 5 && speed <= 8) {
            successRate = 0.9;  // 90% success
        } else if (speed == 9) {
            successRate = 0.8;  // 80% success
        } else {  // speed == 10
            successRate = 0.77;  // 77% success
        }

        // Return the production rate per hour
        return baseProduction * successRate;
    }

    // Calculates the working items produced per minute
    public int workingItemsPerMinute(int speed) {
        // Calculate the production rate per hour and divide by 60 to get per minute
        return (int) (productionRatePerHour(speed) / 60);
    }
}
