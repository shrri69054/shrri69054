import java.util.Deque;
import java.util.LinkedList;

public class CircularBuffer<T> {
  private final int capacity;
  private final Deque<T> storage = new LinkedList<>();

  public CircularBuffer(int capacity) {
    this.capacity = capacity;
  }

  public T read() throws BufferIOException {
    if (storage.isEmpty()) {
      throw new BufferIOException("Tried to read from empty buffer");
    }

    return storage.pop();
  }

  public void write(T entry) throws BufferIOException {
    if (storage.size() == capacity) {
      throw new BufferIOException("Tried to write to full buffer");
    }

    storage.add(entry);
  }

  public void clear() {
    storage.clear();
  }

  public void overwrite(T entry) {
    try {
      write(entry);
    } catch (BufferIOException e) {
      storage.pop();

      try {
        write(entry);
      } catch (BufferIOException e1) {
        // noop
      }
    }
  }
}
