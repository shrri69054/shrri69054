Red [
	description: {"Scrabble Score" exercise solution for exercism platform}
	author: "" ; you can write your name here, in quotes
]

score: function [
	word [string!]
] [
	; cause-error 'user 'message "You need to implement score function."
	value: 0
	scratch: uppercase copy word ; let's not modify the original parameter
	foreach c scratch [
		value: value + switch c [
			#"A" #"E" #"I" #"O" #"U"
			#"L" #"N" #"R" #"S" #"T" [  1 ]
			#"D" #"G"                [  2 ]
			#"B" #"C" #"M" #"P"      [  3 ]
			#"F" #"H" #"V" #"W" #"Y" [  4 ]
			#"K"                     [  5 ]
			#"J" #"X"                [  8 ]
			#"Q" #"Z"                [ 10 ]
        ]
    ]
	value
]
