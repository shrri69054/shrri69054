Red [
    Title: "Yacht"
    Author: "LucasSemS"
    File: %yacht.red
]
score: function [dice category][
	counts: [0 0 0 0 0 0]
    foreach roll dice [
    	counts/:roll: counts/:roll + 1
    ]
	switch category [
    	"yacht" [either find counts 5 [50] [0]]
        "ones" [counts/1]
        "twos" [counts/2 * 2]
        "threes" [counts/3 * 3]
        "fours" [counts/4 * 4]
        "fives" [counts/5 * 5]
        "sixes" [counts/6 * 6]
        "full house" [either [2 3 0] = intersect [2 3 0] counts [sum dice] [0]]
        "four of a kind" [
            4 * case [
            	x: find counts 4 [index? x]
                find counts 5 [dice/1]
                yes [0]
            ]
        ]
        "little straight" [either [1 1 1 1 1 0] = counts [30] [0]]
        "big straight" [either [0 1 1 1 1 1] = counts [30] [0]]
        "choice" [sum dice]
    ]
]