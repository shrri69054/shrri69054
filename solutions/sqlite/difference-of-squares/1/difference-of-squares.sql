UPDATE "difference-of-squares"
SET result = CASE property
    WHEN 'squareOfSum' THEN
        (number * (number + 1) / 2) * (number * (number + 1) / 2)
    WHEN 'sumOfSquares' THEN
        number * (number + 1) * (2 * number + 1) / 6
    WHEN 'differenceOfSquares' THEN
        ((number * (number + 1) / 2) * (number * (number + 1) / 2))
        - (number * (number + 1) * (2 * number + 1) / 6)
END;
