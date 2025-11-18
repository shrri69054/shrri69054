import java.util.List;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SplitSecondStopwatch {
    private static enum State {
        READY, RUNNING, STOPPED;
    }

    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
    private State state = State.READY;
    private LocalTime lap = LocalTime.MIDNIGHT;
    private List<LocalTime> laps = new ArrayList<>();

    public void start() {
        if (state == State.RUNNING)
            throw new IllegalStateException("cannot start an already running stopwatch");
        state = State.RUNNING;
    }

    public void stop() {
        if (state != State.RUNNING)
            throw new IllegalStateException("cannot stop a stopwatch that is not running");
        state = State.STOPPED;
    }

    public void reset() {
        if (state != State.STOPPED)
            throw new IllegalStateException("cannot reset a stopwatch that is not stopped");
        state = State.READY;
        lap = LocalTime.MIDNIGHT;
        laps = new ArrayList<>();
    }

    public void lap() {
        if (state != State.RUNNING)
            throw new IllegalStateException("cannot lap a stopwatch that is not running");
        laps.add(lap);
        lap = LocalTime.ofSecondOfDay(0);
    }

    public String state() {
        return switch(state) {
        case READY -> "ready";
        case RUNNING -> "running";
        case STOPPED -> "stopped";
        };
    }

    public String currentLap() {
        return lap.format(fmt);
    }

    public String total() {
        return laps.stream()
            .reduce(lap, (acc, el) -> acc.plusSeconds(el.toSecondOfDay()))
            .format(fmt);
    }

    public List<String> previousLaps() {
        return laps.stream()
            .map(lap_ -> lap_.format(fmt))
            .toList();
    }

    public void advanceTime(String timeString) {
        if (state == State.RUNNING) {
            lap = lap.plusSeconds(LocalTime.parse(timeString).toSecondOfDay());
        }
    }
}