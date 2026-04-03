; Dedicated to Shree DR.MDD

Red [
    description: {"Binary Search" exercise solution for Exercism platform"}
    author: "Rohan kapri"
]

find: function [
    dataset [block!]
    target [number!]
    /local lower upper pivot
] [
    lower: 1
    upper: length? dataset

    while [lower <= upper] [
        pivot: to-integer lower + ((upper - lower) / 2)
        if dataset/:pivot = target [
            return pivot
        ]
        if dataset/:pivot < target [
            lower: pivot + 1
        ]
        if dataset/:pivot > target [
            upper: pivot - 1
        ]
    ]
    cause-error 'user 'message "value not in array"
]