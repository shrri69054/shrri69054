#include "space_age.h"

namespace space_age {

space_age::space_age(long long secs) : age_in_seconds(secs) {}

long long space_age::seconds() const {
    return age_in_seconds;
}

double space_age::on_earth() const {
    return static_cast<double>(age_in_seconds) / (365.25 * 24 * 60 * 60);
}

double space_age::on_mercury() const {
    return on_earth() / mercury_to_earth_years;
}

double space_age::on_venus() const {
    return on_earth() / venus_to_earth_years;
}

double space_age::on_mars() const {
    return on_earth() / mars_to_earth_years;
}

double space_age::on_jupiter() const {
    return on_earth() / jupiter_to_earth_years;
}

double space_age::on_saturn() const {
    return on_earth() / saturn_to_earth_years;
}

double space_age::on_uranus() const {
    return on_earth() / uranus_to_earth_years;
}

double space_age::on_neptune() const {
    return on_earth() / neptune_to_earth_years;
}

} // namespace space_age