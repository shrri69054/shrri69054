Red [
	description: {"Acronym" exercise solution for exercism platform}
	author: "LucasSemS"
]

abbreviate: function [phrase] [
	
    acronym: ""
	replace/all phrase "-" " "
    replace/all phrase "_" " "
	until [
    	trim phrase
        append acronym phrase/1
        not phrase: find phrase " "
    ]
    
    uppercase acronym
]
