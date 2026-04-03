Red [
    description: {"Grains" exercise solution for exercism platform"}
    author: "David82"
]

square: function [
    square [integer!]
    return: [integer!]
] [
    if (square < 1) or (square > 64) [
    cause-error 'user 'message ["square must be between 1 and 64"]
    ]
    return 2 ** (square - 1)
]

total: function [
    return: [integer!]
] [
    return -1 * (1 - (2 ** 64))
]