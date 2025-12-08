def proverb(*thing, qualifier=None):
    lines = [f"For want of a {w} the {l} was lost." for w, l in zip(thing, thing[1:])]
    if len(thing) != 0:
        last = thing[0] if qualifier is None else f"{qualifier} {thing[0]}"
        lines.append(f"And all for the want of a {last}.")
    return lines