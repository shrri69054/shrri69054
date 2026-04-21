WITH alpha(c) AS (
    SELECT char(value) FROM generate_series(unicode('a'),unicode('z'))
)
UPDATE anagram
SET result = (
    SELECT json_group_array(value)
    FROM json_each(candidates) WHERE (WITH
        a(s) AS (SELECT lower(subject)),
        b(s) AS (SELECT lower(value))
        SELECT sum(abs(
            length(a.s)-length(replace(a.s,c,''))-
            length(b.s)+length(replace(b.s,c,''))
        )) FROM alpha, a, b
    ) = 0 AND lower(subject) <> lower(value)
);