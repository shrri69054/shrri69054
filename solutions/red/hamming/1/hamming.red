Red [
    description: {"Hamming" exercise solution for exercism platform"}
    author: "David82"
]

distance: function [
    strand1 [string!]
    strand2 [string!]
    return: [integer!]
] [
    if not-equal? length? strand1 length? strand2 [
        do make error! "strands must be of equal length"
    ]
    distance: 0
    repeat i length? strand1 [
        if not-equal? strand1/:i strand2/:i [
            distance: distance + 1
        ]
    ]
    return distance
]