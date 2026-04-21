update "atbash-cipher"
set result = (
  with recursive
    chars(ch, pos) as (
      values (lower(substr(phrase, 1, 1)), 1)
      union all
      select lower(substr(phrase, pos + 1, 1)), pos + 1 from chars where pos < length(phrase)
    ),
    letters(letter) as (
      select iif(ch regexp '[a-z]', char(unicode('a') + unicode('z') - unicode(ch)), ch) from chars where ch regexp '[a-z0-9]'
    )
  select case property
    when 'encode'
      then (
        with
          numbered(pos, letter) as (
            select row_number() over (), letter from letters
          ),
          chunks(grp, chunk) as (
            select (pos - 1) / 5 as grp, group_concat(letter, '')
            from numbered
            group by grp
            order by grp
          )
        select group_concat(chunk, ' ') from chunks
      )
    when 'decode'
      then (
        select group_concat(letter, '') from letters
      )
  end
);
