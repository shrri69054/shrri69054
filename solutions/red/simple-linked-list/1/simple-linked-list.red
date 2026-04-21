Red [
	description: {"Simple Linked List" exercise solution for exercism platform}
	author: "" ; you can write your name here, in quotes
]

from-array-and-back: function [
	array
] [
	array
]

convert-reverse-convert-back: function [
	array
] [
	res: []
	while [(length? array) > 0] [
		insert res first array
		array: next array
    ]
	:res
]
