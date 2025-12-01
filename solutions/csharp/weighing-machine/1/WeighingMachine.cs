using System;
using System.Globalization;

class WeighingMachine
{
    public int Precision { get; private set; }
    
    public double TareAdjustment { get; set; } = 5;

    private double _weight;
    public double Weight
    {
        get { return _weight; }
        set
        {
            if (value >= 0)
                _weight = value;
            else 
                throw new ArgumentOutOfRangeException("Weight cannot be negative.");
        }
    }

    public string DisplayWeight
    {
        get
        {
            var format = new NumberFormatInfo() { NumberDecimalDigits = this.Precision };
            var weightString = (this.Weight - this.TareAdjustment).ToString("f", format);
            return $"{weightString} kg";
        }
    }

    public WeighingMachine(int precision) => this.Precision = precision;
}