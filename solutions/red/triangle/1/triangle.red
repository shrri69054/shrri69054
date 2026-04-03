Red [
    Title: "Triangle"
    Author: "LucasSemS"
    File: %triangle.red
]

; Função auxiliar: verifica se é triângulo válido
valid-triangle?: func [sides [block!]][
    a: sides/1
    b: sides/2
    c: sides/3
    all [
        a > 0
        b > 0
        c > 0
        a + b >= c
        b + c >= a
        a + c >= b
    ]
]

equilateral: func [sides [block!]][
    if not valid-triangle? sides [return false]
    to logic! all [
        sides/1 = sides/2
        sides/2 = sides/3
    ]
]

isosceles: func [sides [block!]][
    if not valid-triangle? sides [return false]
    to logic! any [
        sides/1 = sides/2
        sides/2 = sides/3
        sides/1 = sides/3
    ]
]

scalene: func [sides [block!]][
    if not valid-triangle? sides [return false]
    to logic! all [
        sides/1 <> sides/2
        sides/2 <> sides/3
        sides/1 <> sides/3
    ]
]
