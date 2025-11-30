public class RelativeDistance
{
    private Dictionary<string, List<string>> connections;
    
    public RelativeDistance(Dictionary<string, string[]> familyTree)
    {
        this.connections = new Dictionary<string, List<string>>();
    
        BuildConnections(familyTree);
    }
    private void BuildConnections(Dictionary<string, string[]> familyTree)
    {
        foreach (var kvp in familyTree)
        {
            string parent = kvp.Key;
            string[] children = kvp.Value;
            
            if (!connections.ContainsKey(parent))
                connections[parent] = new List<string>();
            
            foreach (string child in children)
            {
                if (!connections.ContainsKey(child))
                    connections[child] = new List<string>();
                
                connections[parent].Add(child);
                connections[child].Add(parent);
                
                foreach (string sibling in children)
                {
                    if (sibling != child)
                    {
                        connections[child].Add(sibling);
                    }
                }
            }
        }
    }  
    public int DegreeOfSeparation(string personA, string personB)
    {

        var visited = new HashSet<string>();
        var queue = new Queue<(string person, int degree)>();
        queue.Enqueue((personA, 0));
        visited.Add(personA);
        
        while (queue.Count > 0)
        {
            var (currentPerson, degree) = queue.Dequeue();
            
            if (currentPerson == personB)
            {
                return degree;
            }
            
            foreach (var relative in connections[currentPerson])
            {
                if (!visited.Contains(relative))
                {
                    visited.Add(relative);
                    queue.Enqueue((relative, degree + 1));
                }
            }
        }       
        return -1;
    }
}