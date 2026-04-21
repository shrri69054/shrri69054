using System;

public class BankAccount
{
    private decimal _balance;
    private bool _isOpen;
    private bool _wasOpened; // Track if the account was ever opened
    private readonly object _lock = new object();

    public void Open()
    {
        lock (_lock)
        {
            if (_isOpen)
                throw new InvalidOperationException("Account is already open.");

            _balance = 0m;
            _isOpen = true;
            _wasOpened = true;
        }
    }

    public void Close()
    {
        lock (_lock)
        {
            if (!_isOpen)
                throw new InvalidOperationException("Account is not open.");

            _isOpen = false;
        }
    }

    public decimal Balance
    {
        get
        {
            lock (_lock)
            {
                EnsureAccountIsOpen();
                return _balance;
            }
        }
    }

    public void Deposit(decimal change)
    {
        if (change < 0)
            throw new InvalidOperationException("Deposit amount must be non-negative.");

        lock (_lock)
        {
            EnsureAccountIsOpen();
            _balance += change;
        }
    }

    public void Withdraw(decimal change)
    {
        if (change < 0)
            throw new InvalidOperationException("Withdraw amount must be non-negative.");

        lock (_lock)
        {
            EnsureAccountIsOpen();
            if (_balance < change)
                throw new InvalidOperationException("Insufficient funds.");

            _balance -= change;
        }
    }

    private void EnsureAccountIsOpen()
    {
        if (!_isOpen)
            throw new InvalidOperationException("Account is not open.");
    }
}