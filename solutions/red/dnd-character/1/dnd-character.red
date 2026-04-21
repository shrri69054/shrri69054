Red [
	description: {"D&D Character" exercise solution for exercism platform}
	author: "LucasSemS"
]

modifier: function [score][round/floor score - 10 / 2]

ability: function [] [
	rolls: copy [0 0 0 0]
    repeat k 4 [rolls/:k: random 6]
    sum next sort copy rolls
]

new-character: function [] [
	chr: [
    	strength 0
        dexterity 0
        constitution 0
        intelligence 0
        wisdom 0
        charisma 0
        hitpoints 0
    ]
    repeat i 6 [chr/(2 * i): ability]
    
    hit: 10 + modifier select chr 'constitution
    chr/14: hit
    to-map chr
]

test-ability: function [] [
	is-valid ability
]

is-valid: function [
	score
] [
	(score >= 3) and (score <= 18)
]

test-random-character-valid: function [][
	char: new-character

	(is-valid char/strength)
	and (is-valid char/dexterity)
	and (is-valid char/constitution)
	and (is-valid char/intelligence)
	and (is-valid char/wisdom)
	and (char/hitpoints == (10 + modifier char/constitution))
]