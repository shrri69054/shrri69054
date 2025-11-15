class NeedForSpeed {

    private final int speed;
    private final int batteryDrain;

    private int distance;
    private int batteryPercent = 100;

    public NeedForSpeed(int speed, int batteryDrain) {
        this.speed = speed;
        this.batteryDrain = batteryDrain;
    }

    public boolean batteryDrained() {
        return batteryPercent - batteryDrain < 0;
    }

    public int distanceDriven() {
        return distance;
    }

    public void drive() {
        if (batteryDrained()) {
            return;
        }

        distance += speed;
        batteryPercent -= batteryDrain;

        batteryPercent = Math.max(0, batteryPercent);
    }

    public static NeedForSpeed nitro() {
        return new NeedForSpeed(50, 4);
    }
}

class RaceTrack {

    private final int distance;

    public RaceTrack(int distance) {
        this.distance = distance;
    }

    public boolean canFinishRace(NeedForSpeed car) {
        while (car.distanceDriven() < distance && !car.batteryDrained()) {
            car.drive();
        }

        return car.distanceDriven() >= distance;
    }
}