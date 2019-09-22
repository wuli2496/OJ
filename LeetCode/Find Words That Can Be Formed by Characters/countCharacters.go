func countCharacters(words []string, chars string) int {
    algo := newObjectFactory().get("simple")
    algo.SetData(words, chars)
    
    return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
	SetData(words []string, chars string)
}

type SimpleAlgo struct {
	words []string
	chars string
}

func (algo *SimpleAlgo) SetData(words []string, chars string) {
	algo.chars = chars
	algo.words = words
}

func (algo *SimpleAlgo) Execute() int {
	words := algo.words
	
	ans := 0
	for _, word := range words {
		if algo.check(word) {
			ans += len(word)
		}
	}
	
	return ans
}

func (algo *SimpleAlgo) check(word string) bool {
	chars := algo.chars
	
	chMap := make(map[rune]int, 0)
	for _, v := range chars {
		cnt, ok := chMap[v]
		if ok {
			chMap[v] = cnt +1
		} else {
			chMap[v] = 1
		}
	}
	
	for _, v := range word {
		cnt, ok := chMap[v]
		if !ok {
			return false
		}
		
		if cnt == 0 {
			return false
		}
		
		chMap[v] = cnt - 1
	}
	
	return true
}

type ObjectFactory struct {
	algoMap map[string] func() AlgoStrategy
}

func newObjectFactory() *ObjectFactory {
	objFactory := &ObjectFactory{algoMap:make(map[string] func() AlgoStrategy, 0)}
	objFactory.register("simple", func() AlgoStrategy {return new(SimpleAlgo)})

	return objFactory
}

func (objFactory *ObjectFactory) register(name string, creator func()AlgoStrategy) {
	objFactory.algoMap[name] = creator
}

func (objFactory *ObjectFactory) get(name string) AlgoStrategy {
	if c, ok := objFactory.algoMap[name]; ok {
		return c()
	}

	return nil
}
