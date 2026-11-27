UPDATE "bottle-song" SET result = (
    WITH ns(n, w) AS (
        VALUES (1, 'One'), (2, 'Two'), (3, 'Three'), (4, 'Four'), (5, 'Five'),
               (6, 'Six'), (7, 'Seven'), (8, 'Eight'), (9, 'Nine'), (10, 'Ten')
    ),
    vs(i, n, w1, w2, verse) AS (
        SELECT start_bottles, take_down,
               (SELECT w FROM ns WHERE n = start_bottles),
               (SELECT w FROM ns WHERE n = start_bottles - 1),
               NULL
        UNION
        SELECT i - 1, n - 1, w2, (SELECT w FROM ns WHERE n = i - 2),
            CASE i
                WHEN 1 THEN
                    'One green bottle hanging on the wall,'             || char(10) ||
                    'One green bottle hanging on the wall,'             || char(10) ||
                    'And if one green bottle should accidentally fall,' || char(10) ||
                    'There''ll be no green bottles hanging on the wall.'
                WHEN 2 THEN
                    'Two green bottles hanging on the wall,'            || char(10) ||
                    'Two green bottles hanging on the wall,'            || char(10) ||
                    'And if one green bottle should accidentally fall,' || char(10) ||
                    'There''ll be one green bottle hanging on the wall.'
                ELSE 
                    format('%s green bottles hanging on the wall,', w1) || char(10) ||
                    format('%s green bottles hanging on the wall,', w1) || char(10) ||
                    'And if one green bottle should accidentally fall,' || char(10) ||
                    format('There''ll be %s green bottles hanging on the wall.', lower(w2))
            END
        FROM vs WHERE n > 0
    )
    SELECT group_concat(verse, char(10, 10)) FROM vs WHERE n < take_down
)