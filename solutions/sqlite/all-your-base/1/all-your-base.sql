UPDATE "all-your-base"
SET result = (WITH RECURSIVE
    dec(n) AS (
        SELECT IFNULL(CAST(sum(value * pow(input_base, json_array_length(digits) - 1 - key)) AS INT), 0)
        FROM json_each(digits)
    ),
    invalid_digits(n) AS (
        SELECT COUNT(1) FROM json_each(digits) WHERE value NOT BETWEEN 0 AND input_base-1
    ),
    target(idx, n,rest) AS (
        SELECT 0, n % output_base, n / output_base FROM dec
        UNION ALL
        SELECT idx+1, rest % output_base, rest / output_base FROM target
        WHERE rest > 0
    ),
    target_rev(n) AS (
        SELECT n FROM target ORDER BY idx DESC
    )
    SELECT (CASE
        WHEN input_base < 2 THEN json_object('error', 'input base must be >= 2')
        WHEN output_base < 2 THEN json_object('error', 'output base must be >= 2')
        WHEN (SELECT n FROM invalid_digits) > 0 
            THEN json_object('error', 'all digits must satisfy 0 <= d < input base')
        ELSE (SELECT json_object('digits', json_group_array(n)) FROM target_rev)
    END)
);