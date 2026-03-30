#ifndef SPACE_AGE_H
#define SPACE_AGE_H

namespace space_age {

constexpr double mercury_to_earth_years{0.2408467};
constexpr double venus_to_earth_years{0.61519726};
constexpr double mars_to_earth_years{1.8808158};
constexpr double jupiter_to_earth_years{11.862615};
constexpr double saturn_to_earth_years{29.447498};
constexpr double uranus_to_earth_years{84.016846};
constexpr double neptune_to_earth_years{164.79132};

class space_age {
private:
    long long age_in_seconds;

public:
    explicit space_age(long long secs);

    long long seconds() const;
    double on_earth() const;
    double on_mercury() const;
    double on_venus() const;
    double on_mars() const;
    double on_jupiter() const;
    double on_saturn() const;
    double on_uranus() const;
    double on_neptune() const;
};

} // namespace space_age

#endif // SPACE_AGE_H