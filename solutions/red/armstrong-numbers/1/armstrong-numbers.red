Red []

is-armstrong-number: function [
	number
][
    str: form number
    cnt: length? str
    blk: []
	foreach char str [
        append blk power (to-integer to-string char) cnt
	]
	number = sum blk
]