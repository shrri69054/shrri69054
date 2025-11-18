class QueenAttackCalculator {
    private final boolean canQueensAttackOneAnother;
    QueenAttackCalculator(Queen queen1, Queen queen2) {
        if (queen1 == null || queen2 == null) throw new IllegalArgumentException("You must supply valid positions for both Queens.");
        if (queen1.row == queen2.row && queen1.column == queen2.column) throw new IllegalArgumentException("Queens cannot occupy the same position.");
        canQueensAttackOneAnother = queen1.row == queen2.row || queen1.column == queen2.column || queen1.majorDiagonal == queen2.majorDiagonal || queen1.minorDiagonal == queen2.minorDiagonal;
    }

    boolean canQueensAttackOneAnother() {
        return canQueensAttackOneAnother;
    }

}




