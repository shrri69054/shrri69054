Red [
	description: {"Protein Translation" exercise solution for exercism platform}
	author: "" ; you can write your name here, in quotes
]

mapping: make map! [
	"AUG" "Methionine"
	"UUU" "Phenylalanine"
	"UUC" "Phenylalanine"
	"UUA" "Leucine"
	"UUG" "Leucine"
	"UCU" "Serine"
	"UCC" "Serine"
	"UCA" "Serine"
	"UCG" "Serine"
	"UAU" "Tyrosine"
	"UAC" "Tyrosine"
	"UGU" "Cysteine"
	"UGC" "Cysteine"
	"UGG" "Tryptophan"
	"UAA" "STOP"
	"UAG" "STOP"
	"UGA" "STOP"
]

proteins: function [
	strand
] [
	prts: []
	while [not empty? strand]
	[
		token: take/part strand 3
		prt: select mapping token
		either prt = "STOP" [
			break
        ] [
    		append prts prt
		]
	]
	return prts
]
