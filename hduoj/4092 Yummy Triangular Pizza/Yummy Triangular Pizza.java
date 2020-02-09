import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

interface AlgoPolicy<Result>
{
	Result execute();
}

interface InputPolicy<T>
{
	boolean hasNext();
	T next();
}

interface OutputPolicy
{
	void write();
}

class Data
{
	int n;
}

class LimitInput implements InputPolicy<Data>
{
	public LimitInput(Scanner in)
	{
		cinScanner = in;
		n = cinScanner.nextInt();
	}
	
	public boolean hasNext()
	{
		return n != 0;
	}
	
	public Data next()
	{
		Data data = new Data();
		data.n = cinScanner.nextInt();
		--n;
		return data;
	}
	
	private int n;
	private Scanner cinScanner;
}

class Output implements OutputPolicy
{
	public Output(PrintWriter pw, int ans)
	{
		coutPrintWriter = pw;
		this.ans = ans;
	}
	
	public void write()
	{
		coutPrintWriter.printf("Case #%d: %d\n", caseNo, ans);
		coutPrintWriter.flush();
		++caseNo;
	}
	
	private PrintWriter coutPrintWriter;
	private int ans;
	private static int caseNo = 1;
}

class Triangle implements Comparable<Triangle>
{
	public Triangle()
	{
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Triangle(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Triangle add(int type)
	{
		int mv = (x + y + z == 0 ? 1 : -1);
		if (type == 0)
		{
			return new Triangle(x, y + mv, z + mv);
		}
		else if (type == 1)
		{
			return new Triangle(x + mv, y, z + mv);
		}
		else 
		{
			return new Triangle(x + mv, y + mv, z);
		}
	}
	
	public void move(int x, int y, int z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public void rotate()
	{
		int tmp = x;
		x = y;
		y = z;
		z = tmp;
	}
	
	public void overturn()
	{
		x = 1 - x;
		y = 1 - y;
		z = -z;
	}
	
	public boolean equals(Triangle b)
	{
		return x == b.x && y == b.y && z == b.z;
	}
	
	int x, y, z;

	@Override
	public int compareTo(Triangle o) 
	{
		if (x != o.x)
		{
			return x - o.x;
		}
		
		if (y != o.y)
		{
			return y - o.y;
		}
		
		return z - o.z;
	}
}

class Hash
{
	static int MAXSIZE = 16;
	
	int cnt;
	int[] key;
	
	public Hash() 
	{
		key = new int[MAXSIZE];
	}

	public int hashCode()
	{
		int ans = 0;
		for (int i = 0; i < cnt; ++i)
		{
			ans = ans * 13 + key[i];
		}
		
		return ans;
	}
	
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		
		if (!(o instanceof Hash))
		{
			return false;
		}
		
		Hash hashObjHash = (Hash)o;
		
		if (cnt != hashObjHash.cnt)
		{
			return false;
		}
		
		
		for (int i = 0; i < cnt; ++i)
		{
			if (key[i] != hashObjHash.key[i])
			{
				return false;
			}
		}
		
		return true;
	}
}

class DfsAlgo implements AlgoPolicy<Integer>
{
	public DfsAlgo(Data data)
	{
		this.data = data;
	}
	
	public Integer execute()
	{
		List<Triangle> vList = new ArrayList<>();
		vList.add(new Triangle());
		Set<Hash> set = new HashSet<>();
		Map<Integer, Integer> ansMap = new HashMap<>();
		
		dfs(vList, ansMap, set);
		
		return ansMap.get(data.n);
	}
	
	private void dfs(List<Triangle> v, Map<Integer, Integer> ans, Set<Hash> s)
	{
		if (v.size() > data.n)
		{
			return;
		}
		
		if (search(v, s))
		{
			return;
		}
		
		updateHash(v, s);
		
		Integer value = ans.getOrDefault(v.size(), 0);
		
		ans.put(v.size(), value + 1);
		
		for (int i = 0; i < v.size(); ++i)
		{
			for (int j = 0; j < 3; ++j)
			{
				Triangle tmpTriangle = v.get(i).add(j);
				if (addTriangle(v, tmpTriangle))
				{
					dfs(v, ans, s);
					v.remove(v.size() - 1);
				}
			}
		}
	}
	
	private void updateHash(List<Triangle> v, Set<Hash> s)
	{
		List<Triangle> tmpList = new ArrayList<>();
		tmpList.addAll(v);
		
		for (int i = 0; i < 3; ++i)
		{
			Collections.sort(tmpList);
			
			s.add(getKey(tmpList));
			
			for (int j = 0; j < tmpList.size(); ++j)
			{
				tmpList.get(j).rotate();
			}
		}
	}
	
	private boolean addTriangle(List<Triangle> v, Triangle tri)
	{
		for (int i = 0; i < v.size(); ++i)
		{
			if (v.get(i).compareTo(tri) == 0)
			{
				return false;
			}
		}
		
		v.add(tri);
		return true;
	}
	
	private Hash getKey(List<Triangle> v)
	{
		Hash ansHash = new Hash();
		
		ansHash.cnt = v.size();
		for (int i = 0; i < ansHash.cnt; ++i)
		{
			ansHash.key[i] = v.get(i).x + 8;
			ansHash.key[i] <<= 4;
			ansHash.key[i] += v.get(i).y + 8;
			ansHash.key[i] <<= 4;
			ansHash.key[i] += v.get(i).z + 8;
			ansHash.key[i] <<= 4; 
		}
		
		return ansHash;
	}
	
	private boolean find(List<Triangle> v, Set<Hash> s)
	{
		List<Triangle> tmpList = new ArrayList<>();
		tmpList.addAll(v);
		
		for (int i = 0; i < 3; ++i)
		{
			Collections.sort(tmpList);
			
			if (s.contains(getKey(tmpList)))
			{
				return true;
			}
			
			for (int j = 0; j < tmpList.size(); ++j)
			{
				tmpList.get(j).rotate();
			}
		}
		
		return false;
	}
	
	private boolean search(List<Triangle> v, Set<Hash> s)
	{
		for (int i = 0; i < v.size(); ++i)
		{
			int x = v.get(i).x;
			int y = v.get(i).y;
			int z = v.get(i).z;
			
			List<Triangle> tmpList = new ArrayList<Triangle>();
			tmpList.addAll(v);
			
			if (x + y + z == 0)
			{
				for (int j = 0; j < tmpList.size(); ++j)
				{
					tmpList.get(j).move(-x, -y, -z);
				}
				
				if (find(tmpList, s))
				{
					return true;
				}
			}
			else 
			{
				for (int j = 0; j < tmpList.size(); ++j)
				{
					tmpList.get(j).overturn();
					tmpList.get(j).move(x - 1, y - 1, z);
				}
				
				if (find(tmpList, s))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	private Data data;
}

class TableAlgo implements AlgoPolicy<Integer>
{
	private static int[] table = {0, 1, 1, 1, 4, 6, 19, 43, 120, 307, 866, 2336, 6588, 18373, 52119, 147700, 422016};
	
	public TableAlgo(Data data)
	{
		this.data = data;
	}
	
	@Override
	public Integer execute() 
	{
		return table[data.n];
	}
	
	private Data data;
}

class Solution<T>
{
	public Solution(AlgoPolicy<T> impl)
	{
		pimpl = impl;
	}
	
	 
	public T run()
	{
		return pimpl.execute();
	}
	
	private AlgoPolicy<T> pimpl; 
}

public class Main 
{
	private static final boolean DEBUG = false;
	
    public static void main(String args[]) throws IOException
    {
    	InputStream inputStream = null;
    	if (DEBUG)
    	{
    		inputStream = new BufferedInputStream(new FileInputStream(new File("f:\\OJ\\uva_in.txt")));
    	}
    	else 
    	{
    		inputStream = new BufferedInputStream(System.in);
    	}
    	
        InputPolicy<Data> inputPolicy = new LimitInput(new Scanner(inputStream));
        
        while (inputPolicy.hasNext())
        {
        	Data data = inputPolicy.next();
        	
        	AlgoPolicy<Integer> algoPolicy = new TableAlgo(data);
        	Solution<Integer> solution = new Solution<>(algoPolicy);
        	Integer ansInteger = solution.run();
        	
        	OutputPolicy outputPolicy = new Output(new PrintWriter(new BufferedOutputStream(System.out)), ansInteger);
        	outputPolicy.write();
        }
    }
}



