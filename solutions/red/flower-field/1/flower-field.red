Red [
	description: {"Flower Field" exercise solution for exercism platform}
	author: ""
]

annotate: function [
	garden
] [
	rows: length? garden
	result: copy []

	repeat r rows [
		line: pick garden r
		cols: length? line
		new-line: copy ""

		repeat c cols [
			either #"*" = pick line c [
				append new-line #"*"
			] [
				count: 0
				foreach dr [-1 0 1] [
					foreach dc [-1 0 1] [
						nr: r + dr
						nc: c + dc
						if all [
							not all [dr = 0  dc = 0]
							nr >= 1  nr <= rows
							nc >= 1  nc <= cols
						] [
							if #"*" = pick pick garden nr nc [
								count: count + 1
							]
						]
					]
				]
				append new-line either count = 0 [#" "] [first form count]
			]
		]

		append result new-line
	]

	result
]