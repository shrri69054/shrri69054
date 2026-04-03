Red [
    Title: "Bottle Song - Exercism"
    Author: "LucasSemS"
]

stanza: function [n][

	lines: [
     "" k " green bottle" s " hanging on the wall,^/"
	   	k " green bottle" s " hanging on the wall,^/"
		"And if one green bottle should accidentally fall,^/"
		"There'll be " m " green bottle" t " hanging on the wall."
    ]

    numbers: [One Two Three Four Five Six Seven Eight Nine Ten]
	k: to-string numbers/:n
    m: either n - 1 = 0 ["no"][lowercase to-string numbers/(n - 1)] 
 	s: either n = 1 [""]["s"]
 	t: either n = 2 [""]["s"]
	
    rejoin lines
]

recite: function [start-bottles take-down][
	last-bottle: start-bottles - take-down
	result: copy ""
	loop take-down [
		append result stanza start-bottles
		start-bottles: start-bottles - 1
		if start-bottles > last-bottle [append/dup result newline 2]
	]
	result
]