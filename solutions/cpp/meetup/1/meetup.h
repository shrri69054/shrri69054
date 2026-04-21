#pragma once

#include <boost/date_time/gregorian/greg_date.hpp>
namespace meetup {

// TODO: add your solution here
class scheduler {
  public:
    scheduler(boost::date_time::months_of_year, int);

    template <int WEEKDAY>
    boost::gregorian::date teenth() const
    {
        boost::gregorian::date d = first_day + boost::gregorian::days((WEEKDAY - first_wd + 7) % 7);
        while (d.day() < 13) {
            d += boost::gregorian::days(7);
        }
        return d;
    }

    template <int WEEKDAY, int N>
    boost::gregorian::date nth_weekday() const
    {
        return first_day + boost::gregorian::days((WEEKDAY - first_wd + 7) % 7) + boost::gregorian::days(7 * (N - 1));
    }

    template <int WEEKDAY>
    boost::gregorian::date last_weekday() const
    {
        boost::gregorian::date fourth{nth_weekday<WEEKDAY, 4>()};
        return (fourth + boost::gregorian::days(7) <= first_day.end_of_month()) ? fourth + boost::gregorian::days(7)
                                                                                : fourth;
    }

#define WEEKDAY_FUNCS(NAME, WEEKDAY)                                                                                   \
    boost::gregorian::date NAME##teenth() const { return teenth<WEEKDAY>(); }                                          \
    boost::gregorian::date first_##NAME##day() const { return nth_weekday<WEEKDAY, 1>(); }                             \
    boost::gregorian::date second_##NAME##day() const { return nth_weekday<WEEKDAY, 2>(); }                            \
    boost::gregorian::date third_##NAME##day() const { return nth_weekday<WEEKDAY, 3>(); }                             \
    boost::gregorian::date fourth_##NAME##day() const { return nth_weekday<WEEKDAY, 4>(); }                            \
    boost::gregorian::date last_##NAME##day() const { return last_weekday<WEEKDAY>(); }

    WEEKDAY_FUNCS(mon, boost::gregorian::Monday)
    WEEKDAY_FUNCS(tues, boost::gregorian::Tuesday)
    WEEKDAY_FUNCS(wednes, boost::gregorian::Wednesday)
    WEEKDAY_FUNCS(thurs, boost::gregorian::Thursday)
    WEEKDAY_FUNCS(fri, boost::gregorian::Friday)
    WEEKDAY_FUNCS(satur, boost::gregorian::Saturday)
    WEEKDAY_FUNCS(sun, boost::gregorian::Sunday)
#undef WEEKDAY_FUNCS

  private:
    boost::gregorian::date first_day;
    int first_wd;
};

} // namespace meetup
