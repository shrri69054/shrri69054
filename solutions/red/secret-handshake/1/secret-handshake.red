Red [
	description: {"Secret Handshake" exercise solution for exercism platform}
	author: "Wtrmute" ; you can write your name here, in quotes
]

commands: function [
	number
] [
	;cause-error 'user 'message "You need to implement commands function."
    result: collect [case/all [
    	not zero? (number and 1) [keep "wink"]
    	not zero? (number and 2) [keep "double blink"]
    	not zero? (number and 4) [keep "close your eyes"]
    	not zero? (number and 8) [keep "jump"]
    ]]
    if not zero? (number and 16) [reverse result]
    result
]
