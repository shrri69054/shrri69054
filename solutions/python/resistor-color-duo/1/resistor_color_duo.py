from functools import reduce

COLORS = [
    'black', 'brown', 'red', 'orange', 'yellow',
    'green', 'blue', 'violet', 'grey', 'white',
]
COLORS = {
    color: i
    for i, color in enumerate(COLORS)
}


def value(colors):
    return reduce(
        lambda val, color_val: val * 10 + color_val,
        map(COLORS.get, colors[:2]),
        0
    )