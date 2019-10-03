package main

func numEquivDominoPairs(dominoes [][]int) int {
    algo := newObjectFactory().get("simple")
    algo.SetData(dominoes)
    
    return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
	SetData(dominoes [][]int)
}

type SimpleAlgo struct {
	dominoes [][]int
}

func (algo *SimpleAlgo) SetData(dominoes [][]int) {
	algo.dominoes = dominoes
}

func (algo *SimpleAlgo) Execute() int {
	dominoes := algo.dominoes

	m := make(map[int]int)
	for _, v1 := range dominoes {
		sum := 0
		sum |= 1 << uint(v1[0])
		sum |= 1 << uint(v1[1])
		cnt, ok := m[sum]
		if ok {
			m[sum] = cnt + 1
		} else {
			m[sum] = 1
		}
	}

	ans := 0
	for _, v := range m {
		ans += v * (v - 1) / 2
	}

	return ans
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


