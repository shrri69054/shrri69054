Red [
	description: {"SGF Parsing" exercise solution for exercism platform}
	author: "LucasSemS eu mrm"
]

upper: charset [#"A" - #"Z"]
lower: charset [#"a" - #"z"]
digit: charset [#"0" - #"9"]


parse-sgf: function [
	encoded
] [
    output: copy []
	
	current: []
	stack: copy []
	insert/only stack output
	value: ""

    gametree: [
    	#"("
    	node
    		(insert/only stack current/children)
    	any node
    	any gametree
    	#")"
    		(remove stack)
    ]
    
    node: [
    	#";"
    	(
    		current: make map! copy []
    		current/properties: make map! copy []
    		current/children: copy []
    		append/only stack/1 current
        )
    	any property
    ]
    
    property: [
    	copy key prop-ident
    	(
    		key: to word! key
    		current/properties/:key: copy []
    	)
    	[ some [prop-value (append/only current/properties/:key value)]
    	| (cause-error 'user 'message "properties without delimiter")]
    ]
    
    prop-ident: [some [
    	  upper
    	| lower (cause-error 'user 'message "property must be in uppercase")
    ]]
    
    prop-value: [
    	#"[" (value: copy "")
    	collect into value any [
    	  "\^(5D)" keep (#"^(5D)")
    	| "\t" keep (#" ")
    	| "\\" keep (#"\")
    	| not #"^(5D)" keep skip
    	]
    	#"]"
    ]

	parse encoded [
		  opt #";" end (cause-error 'user 'message "tree missing")
	    | "()" end (cause-error 'user 'message "tree with no nodes")
	    | gametree
	]
	output/1
]
