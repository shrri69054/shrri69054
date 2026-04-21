Red [
	description: {"Bowling" exercise solution for exercism platform}
	author: "LucasSemS eu mrm"
]

game: make object! [
	frames: []
	prev: -1
]

score: function [
	return: [integer!]
] [
	if ((length? game/frames) < 10) [
		cause-error 'user 'message "Score cannot be taken until the end of the game"
    ]
	if ((length? game/frames/10) < 4) [
		cause-error 'user 'message "Score cannot be taken until the end of the game"
    ]

	probe game/frames
	total: 0
	frames: game/frames

	repeat i 9 [
		frame: frames/:i
		switch frame/1 [
			"open" [total: total + frame/2 + frame/3]
			"spare" [j: i + 1 total: total + 10 + frames/:j/2]
			"strike" [
				j: i + 1
				k: i + 2
				either frames/:j/1 = "strike" [
					total: total + 10 + frames/:j/2 + frames/:k/2
                ][
					total: total + 10 + frames/:j/2 + frames/:j/3
                ]
            ]
        ]
    ]

	total + frames/10/2 + frames/10/3 + frames/10/4
]

roll: function [
	pins [integer!]
] [
	case [
		pins > 10 [
			cause-error 'user 'message "Pin count exceeds pins on the lane"
        ]
		pins < 0 [
			cause-error 'user 'message "Negative roll is invalid"
        ]
		(length? game/frames) = 9 [
			append/only game/frames copy ["fill"]
			append/only game/frames/10 pins
        ]
		(length? game/frames) = 10 [
			if (length? game/frames/10) = 4 [
				cause-error 'user 'message "Cannot roll after game is over"
			]
			append/only game/frames/10 pins
			if ((length? game/frames/10) = 3) and (game/frames/10/2 + pins < 10) [
				append/only game/frames/10 0
            ]
			if (length? game/frames/10) = 4 [
				case [
					(game/frames/10/2 = 10) and (game/frames/10/3 = 10) []
					(game/frames/10/2 = 10) and (game/frames/10/3 < 10) [
						if (game/frames/10/3 + game/frames/10/4) > 10 [cause-error 'user 'message "Pin count exceeds pins on the lane"]
					]
					(game/frames/10/2 < 10) [
						if (game/frames/10/2 + game/frames/10/3) > 10 [cause-error 'user 'message "Pin count exceeds pins on the lane"]
                    ]
                ]
            ]
        ]
		(game/prev < 0) and (pins = 10) [
			comment "Strike"
			append/only game/frames copy ["strike" 10]
        ]
		(game/prev < 0) and (pins < 10) [
			comment "First roll"
			game/prev: pins
        ]
		(pins + game/prev) > 10 [
			cause-error 'user 'message "Pin count exceeds pins on the lane"
        ]
		(pins + game/prev) = 10 [
			comment "Spare"
			frame: copy ["spare"]
			append/only frame game/prev
			append/only frame pins
			append/only game/frames frame
			game/prev: -1
        ]
		(pins + game/prev) < 10 [
			comment "Open Frame"
			frame: copy ["open"]
			append/only frame game/prev
			append/only frame pins
			append/only game/frames frame
			game/prev: -1
        ]
		true [
			cause-error 'user 'message ["Invalid rolls" game/prev roll]
        ]
    ]
]
