def line_up(name, number):
    if 10 <= number % 100 <= 13:
        suffix = "th"
    else:
        if number % 10 == 1:
            suffix = "st"
        elif number % 10 == 2:
            suffix = "nd"
        elif number % 10 == 3:
            suffix = "rd"
        else:
            suffix = "th"

    return f"{name}, you are the {number}{suffix} customer we serve today. Thank you!"