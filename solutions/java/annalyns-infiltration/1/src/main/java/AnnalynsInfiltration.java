class AnnalynsInfiltration {

    // Fast attack is possible if the knight is sleeping
    public static boolean canFastAttack(boolean knightIsAwake) {
        return !knightIsAwake;  // Return true if the knight is asleep
    }

    // Spying is possible if any of the three (knight, archer, prisoner) is awake
    public static boolean canSpy(boolean knightIsAwake, boolean archerIsAwake, boolean prisonerIsAwake) {
        return knightIsAwake || archerIsAwake || prisonerIsAwake;  // At least one awake
    }

    // Can signal the prisoner if the prisoner is awake and the archer is asleep
    public static boolean canSignalPrisoner(boolean archerIsAwake, boolean prisonerIsAwake) {
        return prisonerIsAwake && !archerIsAwake;  // Prisoner awake, archer asleep
    }

    // Can free the prisoner with or without the pet dog depending on conditions
    public static boolean canFreePrisoner(boolean knightIsAwake, boolean archerIsAwake, boolean prisonerIsAwake, boolean petDogIsPresent) {
        if (petDogIsPresent) {
            return !archerIsAwake;  // With dog, free prisoner if the archer is asleep
        } else {
            return prisonerIsAwake && !knightIsAwake && !archerIsAwake;  // Without dog, prisoner must be awake, and both knight and archer must be asleep
        }
    }
}
