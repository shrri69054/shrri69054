Red [
	description: {"Camicia" exercise solution for Exercism}
	author: ""
]

penalty-of: function [card] [
	switch/default card [
		"J" [1]
		"Q" [2]
		"K" [3]
		"A" [4]
	] [0]
]

signature: function [deck] [
	sig: copy ""
	foreach card deck [
		append sig either 0 < penalty-of card [card] ["n"]
	]
	sig
]

state-key: function [decks starter] [
	rejoin [starter "|" signature decks/1 "|" signature decks/2]
]

simulate-game: function [
	playerA
	playerB
] [
	decks: reduce [
		collect [foreach c playerA [keep form c]]
		collect [foreach c playerB [keep form c]]
	]

	seen: make map! []
	put seen state-key decks 1 true

	cur: 1             ; player whose turn it is (1 = A, 2 = B)
	pile: copy []
	debt: 0            ; cards the current player still owes
	creditor: 0        ; player who placed the last payment card
	cards: 0
	tricks: 0
	status: none

	until [
		deck: pick decks cur
		collector: 0

		either empty? deck [
			;; cannot play, so the opponent collects the pile
			collector: 3 - cur
		] [
			card: take deck
			append pile card
			cards: cards + 1
			pen: penalty-of card

			case [
				pen > 0 [
					;; a payment card, so the opponent must now pay
					;; (this also interrupts any penalty being paid)
					debt: pen
					creditor: cur
					cur: 3 - cur
				]
				debt > 0 [
					;; paying with a number card
					debt: debt - 1
					if debt = 0 [collector: creditor]
				]
				true [cur: 3 - cur]
			]
		]

		if collector > 0 [
			append pick decks collector pile
			pile: copy []
			tricks: tricks + 1
			cur: collector
			debt: 0

			key: state-key decks cur
			case [
				empty? pick decks 3 - collector [status: "finished"]
				select seen key                 [status: "loop"]
				true                            [put seen key true]
			]
		]

		string? status
	]

	result: make map! []
	put result 'status status
	put result 'cards cards
	put result 'tricks tricks
	result
]