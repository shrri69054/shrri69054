Red [
	description: {"RNA Transcription" exercise solution for exercism platform}
	author: "Wtrmute" ; you can write your name here, in quotes
]

to-rna: function [
	dna
] [
	to string! parse dna [collect any [
    	  "G" keep ("C")
        | "C" keep ("G")
        | "T" keep ("A")
        | "A" keep ("U")
    ]]
]
