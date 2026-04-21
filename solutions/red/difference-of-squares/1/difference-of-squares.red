Red [
	description: {"Difference of Squares" exercise solution for exercism platform}
	author: "" ; you can write your name here, in quotes
]

square-of-sum: function [
	number
    /local sum
] [
	sum: 0
	repeat i number [
    	sum: sum + i
    ]

    sum ** 2
]

sum-of-squares: function [
	number
] [
	sum: 0
	repeat i number [
    	sum: sum + (i ** 2)
    ]

    sum
]

difference-of-squares: function [
	number
] [
	subtract square-of-sum number sum-of-squares number
]
