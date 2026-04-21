update "armstrong-numbers"
set result = (
    with
    digits(d) as (
        select substr(number, value, 1)
        from generate_series(1, length(number))
    ),
    avalue as (
        select sum(power(d, length(number)))
        from digits
    )
    select (select * from avalue) = number
);