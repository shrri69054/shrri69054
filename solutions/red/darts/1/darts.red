Red [
    Title: "Darts"
    Author: "LucasSemS"
]
score: function [
	x
	y
] [
	len: sqrt x * x + (y * y)
    case [
        len <= 1  [10]
        len <= 5  [ 5] 
        len <= 10 [ 1] 
        []        [ 0] 
    ]
]


