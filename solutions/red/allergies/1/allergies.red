Red [
	description: {"Allergies" exercise solution for exercism platform}
	author: "" ; you can write your name here, in quotes
]

m: make map! [
   	"eggs" 1
    "peanuts" 2
    "shellfish" 4
    "strawberries" 8
    "tomatoes" 16
    "chocolate" 32
    "pollen" 64
    "cats" 128
]

allergic-to: function [
	item
	score
][
	if score and m/:item <> 0 [return true]
	false
]

list: function [
	score
][
	lst: []
	foreach k keys-of m [
    	if allergic-to k score [
        	append lst k
        ]
	]
    lst
]