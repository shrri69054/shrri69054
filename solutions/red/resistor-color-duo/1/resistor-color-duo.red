Red [
	description: {"Resistor Color Duo" exercise solution for exercism platform}
	author: "dander"
]

value: function [
	colors
] [
	values: collect [foreach color colors [
		keep (index? find color-list color) % 10
    ]]

	to integer! rejoin take/part values 2
]

color-list: [
    "brown"
    "red"
    "orange"
    "yellow"
    "green"
    "blue"
    "violet"
    "grey"
    "white"
    "black"
]
