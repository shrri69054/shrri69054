leap: function [year [integer!]] [
    either zero? year // 400 [
        true
    ][
        either zero? year // 100 [
            false
        ][
            either zero? year // 4 [
                true
            ][
                false
            ]
        ]
    ]
]
