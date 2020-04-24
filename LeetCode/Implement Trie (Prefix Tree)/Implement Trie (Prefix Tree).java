/**
Implement a trie with insert, search, and startsWith methods.

Example:

Trie trie = new Trie();

trie.insert("apple");
trie.search("apple");   // returns true
trie.search("app");     // returns false
trie.startsWith("app"); // returns true
trie.insert("app");   
trie.search("app");     // returns true
Note:

You may assume that all inputs are consist of lowercase letters a-z.
All inputs are guaranteed to be non-empty strings.
 */
class Trie 
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
    /** Initialize your data structure here. */
    public Trie() 
    {
    	root = new TrieNode();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) 
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
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) 
    {
    	TrieNode node = searchPrefix(word);
    	
    	return node != null && node.isEnd();
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) 
    {
    	TrieNode node = searchPrefix(prefix);
    	
    	return node != null;
    }
    
    private TrieNode searchPrefix(String prefix)
    {
    	TrieNode node = root;
    	for (int i = 0; i < prefix.length(); ++i)
    	{
    		char ch = prefix.charAt(i);
    		if (!node.containsKey(ch))
    		{
    			return null;
    		}
    		
    		node = node.get(ch);
    	}
    	
    	return node;
    }
    
    private TrieNode root;
}

