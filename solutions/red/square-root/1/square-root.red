red [
    Title: "Square Root"
    Author: "LucasSemS"
    File: %square-root.red
]

square-root: func [
    "Calculates the integer square root of a positive number."
    number [integer!]
][
    if number = 0 [return 0]

    low: 1
    high: number
    result: 1

    while [low <= high] [
        mid: to integer! (low + high) / 2   ; garante inteiro
        square: mid * mid

        if square = number [
            return mid
        ]
        if square < number [
            result: mid
            low: mid + 1
        ]
        if square > number [
            high: mid - 1
        ]
    ]

    result
]
