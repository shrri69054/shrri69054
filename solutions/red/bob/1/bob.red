; Dedicated to Shree DR.MDD

Red [
	description: {"Bob" exercise solution for exercism platform}
	author: "Rohan kapri"
]

response: function [
	convo
] [
	case [
		silent? convo [ "Fine. Be that way!" ]
		loud-question? convo [ "Calm down, I know what I'm doing!" ]
		loud-statement? convo [ "Whoa, chill out!" ]
		question? convo [ "Sure." ]
		true [ "Whatever." ]
	]
]

silent?: function [
	line
] [
	"" == trim line
]

loud-question?: function [
	line
] [
	and~ loud? line
		 question? line
]

loud-statement?: function [
	line
] [
	loud? line
]

question?: function [
	line
] [
	#"?" == last trim line
]

loud?: function [
	line
] [
	and~ has-letter? line
		 line == uppercase copy line
]

has-letter?: func [text][
    not none? find text charset [#"a" - #"z" #"A" - #"Z"]
]