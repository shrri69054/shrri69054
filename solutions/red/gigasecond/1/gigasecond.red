Red [
    description: {"Gigasecond" exercise solution for exercism platform"}
    author: "David82"
]

add-gigasecond: function [
    moment [date!]
][
    gigasecond: 1'000'000'000 ; Number of seconds in a gigasecond
    gigasecond-as-duration: make time! gigasecond ; Convert gigasecond to a duration value
    new-moment: moment + gigasecond-as-duration ; Add gigasecond to the given moment
    new-moment ; Return the new moment
]