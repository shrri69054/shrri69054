Red [
    Title: "Balanced Brackets Checker"
    Author: "LucasSemS"
]
is-paired: function [value][
	matched: true
    stack: make block! length? value

    assoc: #[
    	")" "("
        "]" "["
        "}" "{"
    ]

 	 left: make bitset! values-of assoc
     right: make bitset! keys-of assoc

    foreach b value [
		case [
        	left/:b [append stack form b]            	
        	right/:b [
        		if (empty? stack) or (assoc/(form b) <> take/last stack) [
            		matched: false
	           		break
            	]
        	]
        ]    
    ]
    matched and empty? stack
]