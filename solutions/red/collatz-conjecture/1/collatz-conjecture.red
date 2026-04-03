; Dedicated to Shree DR.MDD

Red [
    description: {"Collatz Conjecture" exercise solution for exercism platform"}
    author: "Rohan kapri"
]

steps: function [
    n [integer!]
] [
    if not positive? n [
		do make error! "Only positive integers are allowed"
	]

	if n == 1 [
		return 0
    ]

    counter: 0
    until [
        n: either even? n [n / 2] [3 * n + 1]
        counter: counter + 1
        n = 1
    ]
    return counter
]