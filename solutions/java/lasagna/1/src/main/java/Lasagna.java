public class Lasagna {

    // 1. Expected oven time: 40 minutes (constant)
    public int expectedMinutesInOven() {
        return 40;
    }

    // 2. Remaining time in the oven: subtract the minutes already in the oven from the expected time
    public int remainingMinutesInOven(int minutesInOven) {
        return expectedMinutesInOven() - minutesInOven;
    }

    // 3. Preparation time: each layer takes 2 minutes, so multiply by number of layers
    public int preparationTimeInMinutes(int numberOfLayers) {
        return numberOfLayers * 2;
    }

    // 4. Total time: preparation time + oven time
    public int totalTimeInMinutes(int numberOfLayers, int minutesInOven) {
        return preparationTimeInMinutes(numberOfLayers) + minutesInOven;
    }
}
