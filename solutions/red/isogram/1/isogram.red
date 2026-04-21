is-isogram: function [word [string!]] [
    lower-word: lowercase word  
    seen: copy []  

    foreach ch lower-word [
        either find "abcdefghijklmnopqrstuvwxyz" ch [
            if find seen ch [return false]  
            append seen ch
        ][
            ; ignora espaços e hifens
        ]
    ]
    true
]
