; Verifica se um número é primo
is-prime?: func [x [integer!]] [
    if x < 2 [return false]
    if x = 2 [return true]
    if zero? x // 2 [return false]

    limit: to-integer sqrt x
    i: 3
    while [i <= limit] [
        if zero? x // i [return false]
        i: i + 2
    ]
    true
]

; Retorna o n-ésimo primo
prime: func [n [integer!]] [
    if n < 1 [
        cause-error 'user 'message ["there is no zeroth prime"]
    ]

    count: 0
    candidate: 1
    while [true] [
        candidate: candidate + 1
        if is-prime? candidate [
            count: count + 1
            if count = n [return candidate]
        ]
    ]
]
