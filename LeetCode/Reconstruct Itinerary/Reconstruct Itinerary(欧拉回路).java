import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Solution 
{
	public List<String> findItinerary(List<List<String>> tickets) 
	{
		buildGraph(tickets);
		List<String> ansStrings = new LinkedList<>();
		String start = "JFK";
		adjListMap.values().forEach(x -> x.sort(String::compareTo));
		dfs(start, ansStrings);
		
		return ansStrings;
    }
	
	private void buildGraph(List<List<String>> tickets)
	{
		adjListMap = new HashMap<>();
		for (List<String> ticket : tickets)
		{
			List<String> list = adjListMap.computeIfAbsent(ticket.get(0), k -> new LinkedList<>()); 
			list.add(ticket.get(1));
		}
		
		return;
	}
	
	private void dfs(String start, List<String> ans)
	{
		List<String> list = adjListMap.get(start); 
		while (list != null && list.size() > 0)
		{
			String dst = list.remove(0);
			dfs(dst, ans);
		}
		
		ans.add(0, start);
		
		return;
	}
	
	private Map<String, List<String>> adjListMap;
}
