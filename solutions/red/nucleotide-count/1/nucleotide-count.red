nucleotide-counts: function [strand [string!]] [
    counts: make map! [
        A: 0
        C: 0
        G: 0
        T: 0
    ]

    foreach ch strand [
        either find "ACGT" ch [
            counts/(to word! ch): counts/(to word! ch) + 1
        ][
            cause-error 'user 'message ["Invalid nucleotide in strand"]
        ]
    ]
    counts
]
