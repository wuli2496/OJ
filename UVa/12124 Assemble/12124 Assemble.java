import java.util.Scanner;
import java.util.List;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

interface AlgoStrategy
{
	int execute();
}

class Component implements Comparable<Component>
{
	String type;
	String name;
	int price;
	int quality;
	
	public String toString()
	{
		return "type:" + type + " name:" + name + " price:" + price + " quality:" + quality;
	}
	
	public int compareTo(Component other)
	{
		if (price != other.price)
		{
			return price - other.price;
		}
		
		return quality - other.quality;
	}
}

class BinarySearchAlgo implements AlgoStrategy
{
	public BinarySearchAlgo(int b, int quality, Map<String, List<Component>> assembles)
	{
		this.b = b;
		this.maxQuality = quality;
		this.assembles = assembles;
	}
	
	public int execute()
	{
		int low = 0, high = this.maxQuality;
		while (low < high)
		{
			int mid = low + ((high - low + 1) >> 1);
			if (check(mid))
			{
				low = mid;
			}
			else 
			{
				high = mid - 1;
			}
		}
		
		return low;
	}
	
	private boolean check(int curQuality)
	{
		int sum = 0;
		for (Map.Entry<String, List<Component>> entry: assembles.entrySet())
		{
			List<Component> list = entry.getValue();
			Collections.sort(list);
			int cheapest = b + 1;
			for (Component component : list)
			{
				if (component.quality >= curQuality)
				{
					cheapest = Math.min(cheapest, component.price);
					break;
				}
			}
			if (cheapest == b + 1)
			{
				return false;
			}
			
			sum += cheapest;
			if (sum > b)
			{
				return false;
			}
		}
		
		return true;
	}
	
	private int b;
	private int maxQuality;
	private Map<String, List<Component>> assembles;

}

public class Main 
{
	public static void main(String[] args) throws IOException
	{
		boolean DEBUG = false;
		
		Scanner scanner = null;
		if (DEBUG)
		{
			scanner = new Scanner(new BufferedInputStream(new FileInputStream("f:\\OJ\\uva_in.txt")));
		}
		else 
		{
			scanner = new Scanner(new BufferedInputStream(System.in));
		}
		
		int testCase = scanner.nextInt();
		for (int i = 0; i < testCase; ++i)
		{
			int n = scanner.nextInt();
			int b = scanner.nextInt();
			
			Map<String, List<Component>> components = new HashMap<>();
			int maxQuality = 0;
			for (int j = 0; j < n; ++j)
			{
				Component component = new Component();
				component.type = scanner.next();
				component.name = scanner.next();
				component.price = scanner.nextInt();
				component.quality = scanner.nextInt();
				maxQuality = Math.max(maxQuality, component.quality);
				
				if (components.containsKey(component.type))
				{
					components.get(component.type).add(component);
				}
				else 
				{
					List<Component> list = new ArrayList<>();
					list.add(component);
					components.put(component.type, list);
				}
				
			}
			
			AlgoStrategy algo = new BinarySearchAlgo(b, maxQuality, components);
			int ans = algo.execute();
			System.out.println(ans);
		}
		
	}
}
