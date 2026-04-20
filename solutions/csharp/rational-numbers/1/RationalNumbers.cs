using System;
using System.Linq;
using System.Collections.Generic;

public static class RealNumberExtension
{
    public static double Expreal(this int realNumber, RationalNumber r) => Math.Pow(realNumber, (double)r._num / r._den);
}

public struct RationalNumber
{
    public int _num, _den;

    public RationalNumber(int numerator, int denominator)
    {
        _num = numerator;
        _den = denominator;
    }

    public static RationalNumber operator +(RationalNumber r1, RationalNumber r2) => (r1._num * r2._den + r2._num * r1._den != 0) ? new RationalNumber(r1._num * r2._den + r2._num * r1._den, r1._den * r2._den) : new RationalNumber(r1._num * r2._den + r2._num * r1._den, 1);

    public static RationalNumber operator -(RationalNumber r1, RationalNumber r2) => (r1._num * r2._den - r2._num * r1._den != 0) ? new RationalNumber(r1._num * r2._den - r2._num * r1._den, r1._den * r2._den) : new RationalNumber(r1._num * r2._den - r2._num * r1._den, 1);

    public static RationalNumber operator *(RationalNumber r1, RationalNumber r2) => new RationalNumber(r1._num * r2._num, r1._den * r2._den).Reduce();

    public static RationalNumber operator /(RationalNumber r1, RationalNumber r2)
    {
        var result = new RationalNumber(r1._num * r2._den, r2._num * r1._den);

        return result.Any(w => w < 0) ? result.Each(w => w * -1) : result; 
    }

    public RationalNumber Abs() => this.Each(w => Math.Abs(w)).Reduce();

    public RationalNumber Reduce()
    {
        int[] Cut(RationalNumber value) => Enumerable.Range(2, (value._num > value._den ? value._num : value._den)).Where(w => (value.Each(x => x % w == 0))).ToArray();

        return (Cut(this).Length == 0 ? this : new RationalNumber(this._num / Cut(this)[^1], this._den / Cut(this)[^1])).Turn();
    }

    public RationalNumber Exprational(int power) => (power > 0 ? new RationalNumber(this._num, (int)Math.Pow(this._den, power)) : new RationalNumber((int)Math.Pow(this._den, Math.Abs(power)), (int)Math.Pow(this._num, Math.Abs(power)))).Turn();


    #region Private Helpers

    private RationalNumber Each(Func<int, int> changer) => new RationalNumber(changer(this._num), changer(this._den));

    private RationalNumber Turn() => (this._num >= 0 && this._den < 0) ? this.Each(w => w * -1) : this;

    private bool Each(Func<int, bool> changer) => changer(this._num) && changer(this._den);

    private bool Any(Func<int, bool> changer) => changer(this._num) || changer(this._den);

    #endregion
}