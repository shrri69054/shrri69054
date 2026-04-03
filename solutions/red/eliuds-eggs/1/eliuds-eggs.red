Red [
	description: {"Eliud's Eggs" exercise solution for exercism platform}
	author: "João Pedro Silveira" ; you can write your name here, in quotes
]

egg-count: function [
	number
    /local sum
] [
	sum: 0

    while [number > 0] [
    	sum: sum + either even? number [0] [1]

        number: shift-right number 1
    ]

    sum
]
