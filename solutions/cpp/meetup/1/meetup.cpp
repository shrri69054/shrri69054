#include "meetup.h"
#include <boost/date_time/gregorian/greg_weekday.hpp>

namespace meetup {

// TODO: add your solution here
scheduler::scheduler(boost::date_time::months_of_year month, int year)
    : first_day(year, month, 1), first_wd(static_cast<int>(first_day.day_of_week()))
{
}

} // namespace meetup





