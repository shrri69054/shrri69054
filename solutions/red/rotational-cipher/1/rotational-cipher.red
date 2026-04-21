Red [
	description: {"Rotational Cipher" exercise solution for exercism platform}
	author: "LucasSemS"
]

rotate: function [
	text
	shiftKey
] [
	res: copy ""
	repeat i length? text [
		char: text/:i
		case [
			(char >= #"A") and (char <= #"Z") [new-one: (char - #"A" + shiftKey) % 26 + #"A"]
			(char >= #"a") and (char <= #"z") [new-one: (char - #"a" + shiftKey) % 26 + #"a"]
			true [new-one: char]
    ]
		append res to-char new-one
  ]
	res
]