Red [
	description: {"Roman Numerals" exercise solution for exercism platform}
	author: "LucasSemS"
]
roman: function [
	number
] [
	zero: to-integer #"0"
	res: copy ""
	num: to-string number
	repeat i length? num [
		digit: to-integer num/:i - zero
		position: 1 - i + length? num
		append res get-digit digit position
    ]
	return res
]

get-digit: function [digit [integer!] position [integer!]] [
	case [
		position = 1 [unit: "I" next: "X" five: "V"]
		position = 2 [unit: "X" next: "C" five: "L"]
		position = 3 [unit: "C" next: "M" five: "D"]
		position = 4 [unit: "M" next: "" five: ""]
	]

	case [
		digit = 0 [res: ""]
		digit = 1 [res: rejoin [unit]]
		digit = 2 [res: rejoin [unit unit]]
		digit = 3 [res: rejoin [unit unit unit]]		
		digit = 4 [res: rejoin [unit five]]
		digit = 5 [res: rejoin [five]]
		digit = 6 [res: rejoin [five unit]]
		digit = 7 [res: rejoin [five unit unit]]
		digit = 8 [res: rejoin [five unit unit unit ]]
		digit = 9 [res: rejoin [unit next]]
	]
	res
]
