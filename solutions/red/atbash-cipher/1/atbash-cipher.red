Red []

cif: #[	
	#"a" #"z" #"b" #"y" #"c" #"x" #"d" #"w" #"e" #"v" #"f" #"u" #"g" #"t" #"h" #"s"
	#"i" #"r" #"j" #"q" #"k" #"p" #"l" #"o" #"m" #"n" #"n" #"m" #"o" #"l" #"p" #"k"
    #"q" #"j" #"r" #"i" #"s" #"h" #"t" #"g" #"u" #"f" #"v" #"e" #"w" #"d" #"x" #"c"
    #"y" #"b" #"z" #"a" #"1" 1 #"2" 2 #"3" 3 #"4" 4 #"5" 5 #"6" 6 #"7" 7 #"8" 8 
    #"9" 9 #"0" 0 
]

encode: function [
	phrase
][
	enc: ""
    lowercase phrase
	foreach ch phrase [
    	if cif/:ch = none [continue]
        if (enc <> "") and ((length? trim/all copy enc) % 5 = 0) [append enc #" "]
    	append enc cif/:ch
    ]
    enc
]

decode: function [
	phrase
][
	dec: ""
    foreach ch phrase [
    	if ch = #" " [continue]
        append dec cif/:ch
    ]
    dec
]