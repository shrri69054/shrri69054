Red [
    Title: "Two Fer"
    Author: "LucasSemS"
    File: %two-fer.red
]

two-fer: func [
    name [string! none!]  ; pode ser string ou none
][
    if none? name [name: "you"]
    rejoin ["One for " name ", one for me."]
]

; alias com underscore (às vezes os testes chamam assim)
two_fer: :two-fer
