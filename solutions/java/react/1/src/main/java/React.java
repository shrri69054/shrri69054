import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Function;

public class React {
    public static abstract class Cell<T> {
        private T cachedValue;
        private Set<ComputeCell<T>> listeners = new HashSet<>();
        private boolean deprecated = false;

        protected Cell(T cachedValue) {
            this.cachedValue = cachedValue;
        }

        public T getValue() {
            return cachedValue;
        }

        protected boolean isDeprecated() {
            return deprecated;
        }

        protected void deprecate() {
            deprecated = true;
            listeners.forEach(ComputeCell::deprecate);
        }

        protected boolean setCachedValue(T newValue) {
            if (newValue == cachedValue) return false;
            deprecate();

            cachedValue = newValue;
            deprecated = false;

            listeners.forEach(ComputeCell::update);
            return true;
        }

        protected void addListener(ComputeCell<T> listener) {
            listeners.add(listener);
        }

        /*public void removeListener(ComputeCell<T> listener) {
            listeners.remove(listener);
        }*/
    }

    public static class InputCell<T> extends Cell<T> {
        private T value;

        public InputCell(T value) {
            super(value);
        }

        public void setValue(T newValue) {
            setCachedValue(newValue);
        }
    }

    public static class ComputeCell<T> extends Cell<T> {
        private final Function<List<T>, T> function;
        private final List<Cell<T>> inputs;
        private Set<Consumer<T>> callbacks = new HashSet<>();

        public ComputeCell(Function<List<T>, T> function, List<Cell<T>> inputs) {
            super(calculateValue(function, inputs));
            this.function = function;
            this.inputs = inputs;
            for (Cell<T> input : inputs) {
                input.addListener(this);
                // newValue -> setCachedValue(calculateValue(function, cells)));
            }
        }

        public void addCallback(Consumer<T> consumer) {
            callbacks.add(consumer);
        }

        public void removeCallback(Consumer<T> consumer) {
            callbacks.remove(consumer);
        }

        public void update() {
            if (hasFullyChanged() && setCachedValue(calculateValue(function, inputs))) {
                for (Consumer<T> consumer : callbacks) consumer.accept(getValue());
            }
        }

        private boolean hasFullyChanged() {
            for (Cell<T> input : inputs) {
                if (input.isDeprecated()) return false;
            }
            return true;
        }

        private static <T> T calculateValue(Function<List<T>, T> function, List<Cell<T>> inputs) {
            return function.apply(inputs.stream().map(Cell::getValue).toList());
        }
    }

    public static <T> InputCell<T> inputCell(T initialValue) {
        return new InputCell<>(initialValue);
    }

    public static <T> ComputeCell<T> computeCell(Function<List<T>, T> function, List<Cell<T>> cells) {
        return new ComputeCell<>(function, cells);
    }
}