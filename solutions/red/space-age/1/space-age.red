Red [
	description: {"Space Age" exercise solution for exercism platform}
	author: "Wtrmute" ; you can write your name here, in quotes
]

age: function [
	planet [string!]
	seconds [number!]
] [
    planets: #[
    	"Mercury" 0.2408467
    	"Venus"   0.61519726
    	"Earth"   1.0
    	"Mars"    1.8808158
    	"Jupiter" 11.862615
    	"Saturn"  29.447498
    	"Uranus"  84.016846
    	"Neptune" 164.79132
    ]
	round/to (seconds / (31557600 * planets/:planet)) 0.01
]
