update acronym
set result = (
    with recursive
    chars as (
        select '' as c, false as capital, 1 as i
        union all
        select upper(substr(phrase, i, 1)),
            c not glob '[A-Z'']' and upper(substr(phrase, i , 1)) glob '[A-Z]',
            i + 1
        from chars
        where substr(phrase, i, 1) != ''
    )
    select group_concat(c, '')
    from chars
    where capital
)