import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.ArrayList;
class BuildTree {
    Map<Integer,TreeSet<Integer>> map = new HashMap<>();
    
    TreeNode buildTree(ArrayList<Record> records) throws InvalidRecordsException {
    	if (records.isEmpty()) return null;
    	
    	var set = new TreeSet<Integer>();
    	
    	for (var record: records) {
    		int id = record.getRecordId();
    		
    		if (!set.add(id))
    			throw new InvalidRecordsException("Invalid Records");
    		
    		int pid = record.getParentId();
    		
    		if (id == 0) {
    			if (pid != 0)
    				throw new InvalidRecordsException("Invalid Records");
    			continue;
    		}
    		
    		if (pid >= id)
    			throw new InvalidRecordsException("Invalid Records");
    		
    		map.computeIfAbsent(pid,k->new TreeSet<>()).add(id);
    	}
    	
		if (set.first() != 0 || set.last() != records.size()-1)
			throw new InvalidRecordsException("Invalid Records");
		
		return buildNode(0);
    }
    
	private TreeNode buildNode(int n) {
		TreeNode res = new TreeNode(n);
		
		if (map.containsKey(n)) {
			for (int c: map.get(n))
				res.getChildren().add(buildNode(c));
		}
		
		return res;
	}
}
