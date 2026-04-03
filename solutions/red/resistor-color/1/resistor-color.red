Red [
	description: {"Resistor Color" exercise solution for exercism platform}
	author: "" ; you can write your name here, in quotes
]

color-code: function [
	color
] [
	(index? find colors color) - 1
]

; no need for colors to be a function
colors: [
    "black"
    "brown"
    "red"
    "orange"
    "yellow"
    "green"
    "blue"
    "violet"
    "grey"
    "white"
]