def square_root(number):
    for i in range(number + 1):
        if (squared := i * i) == number:
            return i
        if squared > number:
            raise ValueError("no integer square root")