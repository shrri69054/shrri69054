class Cell(object):
    def __init__(self):
        self.__value__ = 0
        self.dependents = []

    def notify(self):
        for d in self.dependents:
            d.update()
        for d in self.dependents:
            if d.dirty():
                d.report()

    def __set_value__(self, new_val):
        self.__value__ = new_val
        self.notify()

    @property
    def value(self):
        return self.__value__

    @value.setter
    def value(self, new_val):
        self.__set_value__(new_val)


class InputCell(Cell):
    def __init__(self, value):
        Cell.__init__(self)
        self.value = value


class ComputeCell(Cell):
    def __init__(self, inputs, func):
        Cell.__init__(self)
        self.inputs = inputs
        for i in self.inputs:
            i.dependents.append(self)
        self.func = func
        self.callbacks = set()
        self.update()
        self.last_known = self.value
        self.add_callback = self.callbacks.add
        self.remove_callback = (
            lambda cb: self.callbacks.difference_update({cb})
        )

    def __set_value__(self, new_val):
        self.__value__ = new_val

    def update(self):
        self.value = self.func([i.value for i in self.inputs])

    def dirty(self):
        return self.last_known != self.value

    def report(self):
        self.last_known = self.value
        for cb in self.callbacks:
            cb(self.value)
        self.notify()