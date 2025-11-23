using System;

static class AssemblyLine
{
    public static double SuccessRate(int speed)
    {
        double success = 0;
        if (speed == 0)
            success = 0;
        else if (speed >= 1 && speed <= 4)
            success = 1.00;
        else if (speed >= 5 && speed <= 8)
            success = .90;
        else if (speed == 9)
            success = .80;
        else if (speed == 10)
            success = .77;
        return success;
    }
    
    public static double ProductionRatePerHour(int speed)
    {
        double prod = speed * SuccessRate(speed) * 221;
        return prod;
    }

    public static int WorkingItemsPerMinute(int speed)
    {
        return (int)ProductionRatePerHour(speed) / 60;
    }
}