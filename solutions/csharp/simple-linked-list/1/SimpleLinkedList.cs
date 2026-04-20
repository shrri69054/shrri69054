using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;

public class SimpleLinkedList<T> : IEnumerable<T>
{
    private readonly Stack<T> _stack;

    public SimpleLinkedList() =>     
        _stack = new Stack<T>();
    
    public SimpleLinkedList(IEnumerable<T> source) =>
         _stack = new Stack<T>(source);
    
      public int Count 
    {
       get {return _stack.Count;}
        private set {}
    }
     
    public void Push(T value) => _stack.Push(value);
  
    public T Pop() => _stack.Pop();
        
    public IEnumerator<T> GetEnumerator() => _stack.GetEnumerator();
     
    IEnumerator IEnumerable.GetEnumerator() => _stack.GetEnumerator();
}