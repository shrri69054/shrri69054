Red [
	description: {"Raindrops" exercise solution for exercism platform}
	author: "dander"
]

convert: function [
	number
] [
	sounds: rejoin collect [
		foreach [factor sound] factor-sounds [
			if zero? number % factor [keep sound]
        ]
    ]
	either empty? sounds [to string! number][sounds]
]

factor-sounds: [
    3 "Pling"
    5 "Plang"
    7 "Plong"
]