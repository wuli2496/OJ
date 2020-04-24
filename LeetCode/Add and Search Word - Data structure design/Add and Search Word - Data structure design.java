/**
Design a data structure that supports the following two operations:

void addWord(word)
bool search(word)
search(word) can search a literal word or a regular expression string containing only letters a-z or .. A . means it can represent any one letter.

Example:

addWord("bad")
addWord("dad")
addWord("mad")
search("pad") -> false
search("bad") -> true
search(".ad") -> true
search("b..") -> true
 */
class WordDictionary 
{
	class TrieNode
	{
		public TrieNode()
		{
			nodes = new TrieNode[R];
		}
		
		public boolean containsKey(char ch)
		{
			return nodes[ch - 'a'] != null;
		}
		
		public TrieNode get(char ch)
		{
			return nodes[ch - 'a'];
		}
		
		public void put(char ch, TrieNode node)
		{
			nodes[ch - 'a'] = node;
		}
		
		public void setEnd()
		{
			isEnd = true;
		}
		
		public boolean isEnd()
		{
			return isEnd;
		}
		
		private final int R = 26;
		private boolean isEnd;
		private TrieNode[] nodes;
		
	}
    
    public WordDictionary() 
    {
    	root = new TrieNode();
    }
    
    public void addWord(String word) 
    {
    	TrieNode node = root;
    	for (int i = 0; i < word.length(); ++i)
    	{
    		char ch = word.charAt(i);
    		if (!node.containsKey(ch))
    		{
    			node.put(ch, new TrieNode());
    		}
    		
    		node = node.get(ch);
    	}
    	
    	node.setEnd();
    }
    
    public boolean search(String word) 
    {
    	return search(root, word, 0);
    }
    
    private boolean search(TrieNode node, String word, int pos)
    {
    	if (node != null)
    	{
    		if (pos == word.length())
    		{
    			return node.isEnd();
    		}
    		
    		char ch = word.charAt(pos);
    		if (ch == '.')
    		{
    			for (char c = 'a'; c <= 'z'; ++c)
    			{
    				if (node.containsKey(c))
    				{
    					if (search(node.get(c), word, pos + 1))
    					{
    						return true;
    					}
    				}
    			}
    		}
    		else 
    		{
    			return search(node.get(ch), word, pos + 1);
    		}
    	}
    	
    	return false;
    }
    
    private TrieNode root;
}

