public record Tree(char Value, Tree? Left, Tree? Right);

public static class Satellite
{
    public static Tree? TreeFromTraversals(char[] preOrder, char[] inOrder)
    {
        if(preOrder.Length != inOrder.Length) throw new ArgumentException();
        if(preOrder.Length == 0) return null;
        if(!inOrder.Contains(preOrder[0])) throw new ArgumentException();
        if(preOrder.Length == 1) return new Tree(preOrder[0], null, null);
        if(preOrder.GroupBy(x => x).Where(x => x.Count() > 1).Any()
          || inOrder.GroupBy(x => x).Where(x => x.Count() > 1).Any())
            throw new ArgumentException();

        var inOrderIndex = Array.IndexOf(inOrder, preOrder[0]);
        var leftInOrder = inOrder.Take(inOrderIndex).ToArray();
        var rightInOrder = inOrder.Skip(inOrderIndex + 1).ToArray();

        var preOrderIndex = Array.IndexOf(preOrder, inOrder[0]);
        var leftPreOrder = preOrder.Skip(1).Take(preOrderIndex).ToArray();
        var rightPreOrder = preOrder.Skip(preOrderIndex + 1).ToArray();
        
        return new Tree(preOrder[0],
                       TreeFromTraversals(leftPreOrder, leftInOrder),
                       TreeFromTraversals(rightPreOrder, rightInOrder));
    }
}