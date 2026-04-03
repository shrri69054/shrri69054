Red [
    Title: "Largest Series Product - Exercism"
	author: "LucasSemS" 
]
largest-product: function [
	digits
	span
] [
	if (span = 0) [
		return 1
    ]
	if span <= 0 [
		cause-error 'user 'message "span must be greater than zero"
	]
	if span > length? digits [
		 cause-error 'user 'message "span must be smaller than string length"
	]
	zero: to-integer #"0"
	maximum: 0	
	repeat i (length? digits) - span + 1 [
		temp: 1
		repeat j span [
			index: i + j - 1
			digit: to-integer digits/:index - zero
			if (digit < 0) or (digit > 9) [
				cause-error 'user 'message "digits input must only contain digits"
			]
			temp: temp * digit
		]
		if temp > maximum [
			maximum: temp
		]
	]
	maximum
]