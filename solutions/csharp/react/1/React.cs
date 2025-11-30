using System;
using System.Linq;
using System.Collections.Generic;

public sealed class Reactor
{
    public InputCell CreateInputCell(int value) => new InputCell(value);

    public ComputeCell CreateComputeCell(IEnumerable<Cell> producers, Func<int[], int> compute) => new ComputeCell(producers.ToArray(), compute);
}

public abstract class Cell
{
    protected int _value;

    public int Value {
        get => _value;
        set {
            if (_value != value) {
                _value = value;
                OnChanged(value);
            }
        }
    }

    public Cell(int value)
    {
        _value = value;
    }

    public EventHandler<int> Changed {get; set;}

    protected void OnChanged(int newValue)
    {
        Changed?.Invoke(this, newValue);
    }
}

public sealed class InputCell : Cell
{
    public InputCell(int value) : base(value) { }
}

public sealed class ComputeCell : Cell
{
    private readonly Cell[] _producers;
    private readonly Func<int[], int> _compute;

    public ComputeCell(Cell[] producers, Func<int[], int> compute)
        :base(ComputeValue(producers, compute))
    {
        _producers = producers;
        _compute = compute;

        SetupCascadingProducersChangedEvents();
        SetupRecomputation();
        SetupProducersChangedCallbacks();
    }

    public EventHandler<int> ProducersChanged {get; set;}

    protected void OnProducersChanged( int val)
    {
        ProducersChanged?.Invoke(sender: this, val);
    }

    private void RecomputeValue()
    {
        Value = ComputeValue(_producers, _compute);
    }

    private static int ComputeValue( Cell[] producers, Func<int[], int> compute)
        => compute(producers.Select(each => each.Value).ToArray());

    private void SetupCascadingProducersChangedEvents()
    {
        ProducersChanged += (Object sender, int val) =>
        {
            foreach (var producer in _producers)
            {
                if (producer is ComputeCell computingProducer)
                {
                    computingProducer.OnProducersChanged(val);
                }
            }
        };
    }

    private void SetupRecomputation()
    {
        ProducersChanged += (Object sender, int val) =>
        {
            RecomputeValue();
        };
    }

    private void SetupProducersChangedCallbacks()
    {
        foreach (var producer in _producers)
        {
            producer.Changed += (Object sender, int val) => OnProducersChanged( val);
        }
    }
}