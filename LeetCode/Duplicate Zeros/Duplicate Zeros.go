package main

func duplicateZeros(arr []int)  {
    algoStrategy := newObjectFactory().get("base")
    algoStrategy.SetData(arr)
    algoStrategy.Execute()
}

type AlgoStrategy interface {
	Execute()
	SetData(arr []int)
}

type AlgoFactory interface {
	Create() AlgoStrategy
}

type ObjectFactory struct {
	algoFactorys map[string]AlgoFactory
}

func newObjectFactory() *ObjectFactory {
	objFactory := &ObjectFactory{algoFactorys:make(map[string]AlgoFactory,0)}
	objFactory.registerFactory("base", new(SimpleAlgoFactory))
	
	return objFactory
}

func (objFactory *ObjectFactory) registerFactory(algoName string, algoFactory AlgoFactory) {
	objFactory.algoFactorys[algoName] = algoFactory
}

func (objFactory *ObjectFactory) get(algoName string) AlgoStrategy {
	if algoFact, ok := objFactory.algoFactorys[algoName]; ok {
		return algoFact.Create()
	}
	
	return nil
}

type SimpleAlgoStrategy struct {
	arr []int
}

func (simpleAlgo *SimpleAlgoStrategy) SetData(arr []int) {
	simpleAlgo.arr = arr
}

func (simpleAlgo *SimpleAlgoStrategy) Execute() {
	arr := simpleAlgo.arr
	
	arLen := len(arr)
	for i := 0; i < arLen; i++ {
		if arr[i] == 0 {
			for j := arLen - 2; j > i; j-- {
				arr[j + 1] = arr[j]
			}
			
			if i + 1 < arLen {
				arr[i + 1] = 0
			}
			
			i++
		}
	}
}

type SimpleAlgoFactory struct {
	
}

func (simpleFactory *SimpleAlgoFactory) Create() AlgoStrategy {
	return new(SimpleAlgoStrategy)
}