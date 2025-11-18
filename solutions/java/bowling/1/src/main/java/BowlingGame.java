import java.util.ArrayList;
import java.util.List;

public class BowlingGame {

    private final List<Frame> frames = new ArrayList<>();

    private enum State {FIRST, SECOND, TENTH_FIRST, TENTH_SECOND, TENTH_FILL_BALL, GAME_OVER}

    private State state = State.FIRST;
    private Frame currentFrame = null;

    private static final String NEGATIVE_ROLL_IS_INVALID = "Negative roll is invalid";
    private static final String PIN_COUNT_EXCEEDS_PINS_ON_THE_LANE = "Pin count exceeds pins on the lane";
    private static final String CANNOT_ROLL_AFTER_GAME_IS_OVER = "Cannot roll after game is over";
    private static final String SCORE_CANNOT_BE_TAKEN_UNTIL_THE_END_OF_THE_GAME = "Score cannot be taken until the end of the game";

    public void roll(final int pins) {
        if (pins < 0) {
            throw new IllegalStateException(NEGATIVE_ROLL_IS_INVALID);
        }

        if (pins > 10) {
            throw new IllegalStateException(PIN_COUNT_EXCEEDS_PINS_ON_THE_LANE);
        }

        switch (state) {
            case FIRST:
                handleFirst(pins);
                break;
            case SECOND:
                handleSecond(pins);
                break;
            case TENTH_FIRST:
                handleTenthFirst(pins);
                break;
            case TENTH_SECOND:
                handleTenthSecond(pins);
                break;
            case TENTH_FILL_BALL:
                handleTenthFillBall(pins);
                break;
            case GAME_OVER:
                handleGameOver();
        }
    }

    private void handleFirst(final int pins) {
        currentFrame = new Frame(pins);
        frames.add(currentFrame);
        if (pins == 10) {
            if (inNinthFrame()) {
                state = State.TENTH_FIRST;
            } else {
                state = State.FIRST;
            }
        } else {
            state = State.SECOND;
        }
    }

    private void handleSecond(final int pins) {
        currentFrame.second = pins;
        if (!inTenthFrame() && currentFrame.sum() > 10) {
            throw new IllegalStateException(PIN_COUNT_EXCEEDS_PINS_ON_THE_LANE);
        }
        if (inNinthFrame()) {
            state = State.TENTH_FIRST;
        } else {
            state = State.FIRST;
        }
    }

    private void handleTenthFirst(final int pins) {
        currentFrame = new Frame(pins);
        frames.add(currentFrame);
        state = State.TENTH_SECOND;
    }

    private void handleTenthSecond(final int pins) {
        Frame tenthFrame = frames.get(9);
        tenthFrame.second = pins;
        if (tenthFrame.sum() < 10) {
            state = State.GAME_OVER;
        } else {
            state = State.TENTH_FILL_BALL;
        }
    }

    private void handleTenthFillBall(final int pins) {
        if (currentFrame.first == 10 && currentFrame.second != 10 && currentFrame.second + pins > 10) {
            throw new IllegalStateException(PIN_COUNT_EXCEEDS_PINS_ON_THE_LANE);
        }
        currentFrame.fillBall = pins;
        state = State.GAME_OVER;
    }

    private void handleGameOver() {
        throw new IllegalStateException(CANNOT_ROLL_AFTER_GAME_IS_OVER);
    }

    private boolean inNinthFrame() {
        return frames.size() == 9;
    }

    private boolean inTenthFrame() {
        return frames.size() == 10;
    }

    public int score() {
        if (state != State.GAME_OVER) {
            throw new IllegalStateException(SCORE_CANNOT_BE_TAKEN_UNTIL_THE_END_OF_THE_GAME);
        }

        // sum all frames
        int result = frames.stream()
                .mapToInt(Frame::sum)
                .sum();

        // add spares and strikes
        for (int i = 0; i < frames.size() - 1; i++) {
            final Frame frame = frames.get(i);
            Frame nextFrame = frames.get(i + 1);
            if (frame.isSpare()) {
                result += nextFrame.first;
            } else if (frame.isStrike()) {
                if (i == 8) {
                    result += nextFrame.first + nextFrame.second;
                } else {
                    result += nextFrame.sum();
                    if (nextFrame.isStrike()) {
                        result += frames.get(i + 2).first;
                    }
                }
            }
        }

        return result;
    }

    private static class Frame {
        private int first;
        private int second;
        private int fillBall;

        public Frame(final int first) {
            this.first = first;
            this.second = 0;
        }

        int sum() {
            return first + second + fillBall;
        }

        boolean isStrike() {
            return first == 10;
        }

        boolean isSpare() {
            return !isStrike() && sum() == 10;
        }

        @Override
        public String toString() {
            return "(" + first + "," + second + (fillBall == 0 ? "" : (", " + fillBall)) + ')';
        }
    }
}