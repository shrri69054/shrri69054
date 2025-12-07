from math import pow

def color_to_value(color: str) -> int:
    return [
        "black", "brown", "red", "orange", "yellow",
        "green", "blue", "violet", "grey", "white"
    ].index(color)

def color_to_tolerance(color: str) -> str:
    return {
        "grey": "0.05",
        "violet": "0.1",
        "blue": "0.25",
        "green": "0.5",
        "brown": "1",
        "red": "2",
        "gold": "5",
        "silver": "10",
    }[color]

def scale_value(value, units: str, digits: int = 3):
    for mult, prefix in [
        (1_000_000, "mega"),
        (1_000, "kilo"),
    ]:
        if value >= mult:
            value /= mult
            units = f"{prefix}{units}"
            break
    return f"{{0:.{digits}g}} {units}".format(value)
    

def format_resistor(value_bands, mult_band, tolerance_band=None) -> str:
    base_value = 0
    for band in value_bands:
        base_value = base_value * 10 + color_to_value(band)
    mult = pow(10, color_to_value(mult_band))
    value = base_value * mult
    scaled_value = scale_value(value, "ohms")
    if tolerance_band is None:
        return scaled_value
    tolerance = color_to_tolerance(tolerance_band)
    return f"{scaled_value} ±{tolerance}%"
    

def resistor_label(colors):
    match colors:
        case [*value_bands, mult, tolerance] if len(value_bands) in [2, 3]:
            return format_resistor(value_bands, mult, tolerance)
        case [single_band]:
            return format_resistor(colors, "black")