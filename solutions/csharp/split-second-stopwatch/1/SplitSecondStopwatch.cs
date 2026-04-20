public enum StopwatchState
{
    Ready,
    Running,
    Stopped
}

public class SplitSecondStopwatch(TimeProvider time)
{
	private TimeProvider _time = time;
	private List<TimeSpan> _previousLaps = new List<TimeSpan>();
	private DateTimeOffset? _lapStart;
	private TimeSpan? _elapsed;
	
    public StopwatchState State { get; private set; }
    public TimeSpan CurrentLap => 
		State switch
		{
				StopwatchState.Ready => TimeSpan.Zero,
				StopwatchState.Stopped => _elapsed.Value,
				StopwatchState.Running => (_time.GetUtcNow() - _lapStart.Value) + _elapsed.Value,
				_ => throw new NotImplementedException()
		};
    public TimeSpan Total =>
        _previousLaps.Aggregate(TimeSpan.Zero, (agg, ts) => agg + ts.Duration()) + CurrentLap;
    public IReadOnlyCollection<TimeSpan> PreviousLaps => _previousLaps.AsReadOnly();

    public void Start()
    {
		if(State == StopwatchState.Running) throw new InvalidOperationException();
        if(State == StopwatchState.Ready) _elapsed = new TimeSpan();
		_lapStart = _time.GetUtcNow();
        State = StopwatchState.Running;
    }

    public void Stop()
    {
        if(State != StopwatchState.Running) throw new InvalidOperationException();
        _elapsed = _time.GetUtcNow() - _lapStart.Value;
        _lapStart = null;
        State = StopwatchState.Stopped;
    }

    public void Reset()
    {
        if(State != StopwatchState.Stopped) throw new InvalidOperationException();
		_lapStart = null;
		_elapsed = null;
		_previousLaps.Clear();
		State = StopwatchState.Ready;
    }

    public void Lap()
    {
        if(State != StopwatchState.Running) throw new InvalidOperationException();
        _previousLaps.Add(CurrentLap);
        _lapStart = _time.GetUtcNow();
        _elapsed = new TimeSpan();
    }
}