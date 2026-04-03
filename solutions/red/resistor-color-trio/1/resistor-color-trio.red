Red [
	description: {"Resistor Color Trio" exercise solution for exercism platform}
	author: "Wtrmute" ; you can write your name here, in quotes
]

label: function [
	colors [series!]
	return: [map!]
] [
	digits: ["black" "brown" "red" "orange" "yellow" "green" "blue" "violet" "grey" "white"]
    units: ["ohms" "kiloohms" "megaohms" "gigaohms"]
	code: function [c] [
		(index? find digits c) - 1
    ]
	mantissa: (code colors/1) * 10 + (code colors/2)
	exponent: (code colors/3)
	if zero? (mantissa // 10) [ mantissa: mantissa / 10 exponent: exponent + 1 ]
	to map! reduce [
		'value mantissa * (10 ** (exponent // 3))
		'unit (pick units (1 + to integer! round/down exponent / 3))
    ]
] 
