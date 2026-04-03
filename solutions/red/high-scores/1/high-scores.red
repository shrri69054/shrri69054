Red [
	description: {"High Scores" exercise solution for exercism}
	author: "LucasSemS"
]

scores: function [scores] [scores]

latest: function [scores] [last scores]

personal-best: function [scores][
	best: first scores
	foreach score scores [
		if score > best [best: score]
    ]
	best
]

personal-top-three: function [scores][
	take/part sort/reverse copy scores 3
]