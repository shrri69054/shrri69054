Red [
	description: {"Reverse string" exercise solution for exercism platform}
	author: "Wtrmute" ; you can write your name here, in quotes
]

reverse: function [
	"Reverses a string"
	input [string!] "String to reverse"
	return: [string!]
] [
	res: copy ""
	ptr: tail input
	while [not head? ptr] [
		ptr: back ptr
		res: append res ptr/1
    ]
	res
]