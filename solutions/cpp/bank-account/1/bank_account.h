
#if !defined(BANK_ACCOUNT_H)
#define BANK_ACCOUNT_H

#include <mutex>

namespace Bankaccount {

    enum class Status {opened, closed};

    class Bankaccount {
        public:
            void open();
            void close();
            void deposit(int amount);
            void withdraw(int amount);
            int balance();
        private:
            Status _status{Status::closed};
            int _balance{0};
            std::mutex _accountProtection;
            void validate_active_account();
    };  // class Bankaccount

}  // namespace Bankaccount

#endif  // BANK_ACCOUNT_H