Red [
	description: {"Conway's Game of Life" exercise solution for Exercism}
	author: ""
]

tick: function [
	matrix
	/local rows result r c row cols new-row count dr dc nr nc cell
] [
	rows: length? matrix
	result: copy []

	repeat r rows [
		row: matrix/:r
		cols: length? row
		new-row: copy []

		repeat c cols [
			;; count live neighbours among the 8 surrounding cells
			count: 0
			foreach dr [-1 0 1] [
				foreach dc [-1 0 1] [
					unless all [dr = 0 dc = 0] [
						nr: r + dr
						nc: c + dc
						if all [nr >= 1  nr <= rows  nc >= 1  nc <= cols] [
							count: count + pick matrix/:nr nc
						]
					]
				]
			]

			;; apply the rules
			cell: pick row c
			append new-row either any [
				count = 3
				all [cell = 1  count = 2]
			] [1] [0]
		]

		append/only result new-row
	]

	result
]