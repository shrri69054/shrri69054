Red [
	description: {"Run Length Encoding" exercise solution for exercism platform}
	author: "LucasSemS eu mrm"
]

encode-aux: function [
	accum     [string!]
	cur-char  [char! none!]
	cur-count [integer!]
	rest      [series!]
	return:   [string!]
] [
	case [
		equal? cur-char none [ accum ]
		equal? cur-char first rest [
			encode-aux accum cur-char (add cur-count 1) (skip rest 1)
		] equal? cur-count 1 [
			append accum cur-char
			encode-aux accum (first rest) 1 (skip rest 1)
		] true [
			append append accum to-string cur-count cur-char
			encode-aux accum (first rest) 1 (skip rest 1)
		]
	]
]

encode: function [
	string
] [
	either 0 = length? string [ "" ][
		encode-aux "" (first string) 0 string
	]
]

is-digit?: function [ c [char!] ][ (c >= #"0") and (c <= #"9") ]
to-digit: function [ c [char!] ][ to-integer (c - #"0")]

decode-aux: function [
	accum [string!]
	count [integer!]
	rest  [string!]
	return: [string!]
] [

	probe reduce [accum count rest]

	case [
		empty? rest [ return accum ]
		is-digit? first rest [
			decode-aux accum ((count * 10) + (to-digit first rest)) (skip rest 1)
		] count = 0 [
			append accum (first rest)
			decode-aux accum 0 (skip rest 1)
		] true [
			repeat _ count [append accum first rest]
			decode-aux accum 0 (skip rest 1)
		]
	]
]

decode: function [
	string  [string!]
	return: [string!]
] [
	decode-aux "" 0 string
]

consistency: function [
	"All the tests seem to pass without this function implemented."
	string
] [
	cause-error 'user 'message ["You need to implement this function."]
]
