import math


def color_value(color: str) -> int:
    return [
        "black", "brown", "red", "orange", "yellow",
        "green", "blue", "violet", "grey", "white"
    ].index(color)

def scale_value(value: int, unit: str) -> (int, str):
    for mult, prefix in [
        (1_000_000_000, "giga"),
        (1_000_000, "mega"),
        (1_000, "kilo"),
    ]:
        if value > mult:
            return (value / mult, f"{prefix}{unit}")
    return (value, unit)
    

def label(colors):
    match colors:
        case [first, second, mult, *_]:
            base_value = 10 * color_value(first) + color_value(second)
            mult = math.pow(10, color_value(mult))
            value = base_value * mult
            scaled_value, units = scale_value(value, "ohms")
            return f"{scaled_value:.0f} {units}"
        case _:
            raise ValueError("not a trio")