using System;
using System.Collections.Generic;
using System.Linq;

public class Tree
{
    public string Value { get; }
    public Tree Parent { get; set; }
    public Tree[] Children { get; }
    public Tree (string root, params Tree[] children)
    {
        this.Value = root;
        this.Children = children;
        foreach (var child in children)
            child.Parent = this;
    }

    public override int GetHashCode() => this.Value.GetHashCode();
    public override bool Equals(object obj) => this.Equals(obj as Tree);
    public bool Equals(Tree other) => other != null && other.Value == this.Value;

    public Tree AsRoot() => AsRoot(null);
    private Tree AsRoot(Tree excluded) => new Tree(
            this.Value,
            new[] { Parent?.AsRoot(this) }
                .Concat(this.Children)
                .Where(c => c != null && c != excluded)
                .ToArray()
        );
}

public static class Pov
{
    public static Tree FromPov(Tree tree, string from)
    {
        var toVisit = new Stack<Tree>(new[] {tree});
        Tree newRoot = null;
        while (toVisit.TryPop(out newRoot))
        {
            if (newRoot.Value == from)
                return newRoot.AsRoot();
            foreach (var child in newRoot.Children)
                toVisit.Push(child);
        }
        throw new ArgumentException("from does not exist");
    }

    public static IEnumerable<string> PathTo(string from, string to, Tree tree)
    {
        var pathStack = new Stack<(string[], Tree)>();
        pathStack.Push((new[] {from}, FromPov(tree, from)));
        while (pathStack.Any())
        {
            var (path, current) = pathStack.Pop();
            if (path[path.Length - 1] == to)
                return path;
            foreach (var child in current.Children)
                pathStack.Push((path.Append(child.Value).ToArray(), child));
        }
        throw new ArgumentException("to does not exist");
    }
}