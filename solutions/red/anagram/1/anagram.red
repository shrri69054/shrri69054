Red [
	description: {"Anagram" exercise solution for exercism platform}
	author: "" ; you can write your name here, in quotes
]

find-anagrams: function [
	subject
	candidates
][
    res: []
    subj-sorted: sort copy subject
    foreach candidate candidates [
    	if ((length? subj-sorted) = (length? candidate)) and (subject <> candidate) [
        	cand-sorted: sort copy candidate
        	if subj-sorted = cand-sorted [
            	append res candidate
            ]
        ]
    ]
    res
]
