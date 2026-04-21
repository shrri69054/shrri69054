namespace hellmath {

enum class AccountStatus {
    troll,
    guest,
    user,
    mod
};

enum class Action {
    read,
    write,
    remove
};

bool display_post(AccountStatus posterStatus, AccountStatus viewerStatus)
{
    switch (posterStatus)
    {
        case AccountStatus::troll: return viewerStatus == AccountStatus::troll;
        default: return true;
    }
}

bool permission_check(Action action, AccountStatus status)
{
    switch (action)
    {
        case Action::read: return true;
        case Action::write:
        {
            switch(status)
            {
                case AccountStatus::troll:
                case AccountStatus::user:
                case AccountStatus::mod: return true;
                default: return false;
            }
        }
        case Action::remove:
        {
            switch(status)
            {
                case AccountStatus::mod: return true;
                default: return false;
            }
        }
        default: return false;
    }
}

bool valid_player_combination(AccountStatus first, AccountStatus second)
{
    if (second < first) return valid_player_combination(second, first);

    switch(first)
    {
        case AccountStatus::troll: return second == AccountStatus::troll;
        case AccountStatus::user:
        case AccountStatus::mod: return true;
        default: return false;
    }
}

bool has_priority(AccountStatus first, AccountStatus second)
{
    return (int)first > (int)second;
}

}  // namespace hellmath