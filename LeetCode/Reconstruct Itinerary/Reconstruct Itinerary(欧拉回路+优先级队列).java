import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class Solution 
{
	public List<String> findItinerary(List<List<String>> tickets) 
	{
		buildGraph(tickets);
		List<String> ansStrings = new LinkedList<>();
		String start = "JFK";
		dfs(start, ansStrings);
		
		return ansStrings;
    }
	
	private void buildGraph(List<List<String>> tickets)
	{
		adjListMap = new HashMap<>();
		for (List<String> ticket : tickets)
		{
			PriorityQueue<String> list = adjListMap.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<>()); 
			list.add(ticket.get(1));
		}
		
		return;
	}
	
	private void dfs(String start, List<String> ans)
	{
		PriorityQueue<String> pq = adjListMap.get(start); 
		while (pq != null && pq.size() > 0)
		{
			String dst = pq.poll();
			dfs(dst, ans);
		}
		
		ans.add(0, start);
		
		return;
	}
	
	private Map<String, PriorityQueue<String>> adjListMap;
}
