#include "bank_account.h"
#include <stdexcept>

namespace Bankaccount {
    void Bankaccount::open() {
        std::lock_guard lock(_accountProtection);
        if(_status == Status::opened) throw std::runtime_error("Account has been opened already.");
        _status=Status::opened;
    }

    void Bankaccount::close()
    {
        std::lock_guard lock(_accountProtection);
        validate_active_account();
        _status=Status::closed;
        _balance=0;
    }

    void Bankaccount::deposit(int amount)
    {
        std::lock_guard lock(_accountProtection);
        if(amount <= 0) throw std::runtime_error("Invalid amount.");
        validate_active_account();
        _balance+= amount;
    }

    void Bankaccount::withdraw(int amount)
    {
        std::lock_guard lock(_accountProtection);
        if(amount <= 0 || _balance < amount) throw std::runtime_error("Invalid amount.");
        validate_active_account();
        _balance-= amount;
    }

    int Bankaccount::balance() {
        std::lock_guard lock(_accountProtection);
        validate_active_account();
        return _balance;
    }

    void Bankaccount::validate_active_account() {
        if(_status != Status::opened) throw std::runtime_error("Account has not been opened or has been closed.");
    }
}



