is-pangram: func [sentence [string!]] [
    ; converte toda a frase para minúsculas
    s: lowercase sentence
    
    ; percorre todas as letras do alfabeto
    foreach ch "abcdefghijklmnopqrstuvwxyz" [
        if not find s ch [return false]
    ]
    
    true
]
