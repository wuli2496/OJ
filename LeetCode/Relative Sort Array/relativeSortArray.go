package main

import "sort"

func relativeSortArray(arr1 []int, arr2 []int) []int {
    algo := newObjectFactory().get("simple")
    algo.SetData(arr1, arr2)
    
    return algo.Execute()
}

type AlgoStrategy interface {
	Execute() []int
	SetData(arr1 []int, arr2 []int)
}

type SimpleAlgo struct {
	arr1 []int
	arr2 []int
}

func (algo *SimpleAlgo) SetData(arr1 []int, arr2 []int) {
	algo.arr1 = arr1
	algo.arr2 = arr2
}

func (algo *SimpleAlgo) Execute() []int {
	sort.Sort(algo)
	
	return algo.arr1
}

func (algo *SimpleAlgo) Len() int {
	return len(algo.arr1)
}

func (algo *SimpleAlgo) Swap(i, j int) {
	arr1 := algo.arr1
	arr1[i], arr1[j] = arr1[j], arr1[i]
}

func (algo *SimpleAlgo) Less(i, j int) bool {
	a := algo.arr1[i]
	b := algo.arr1[j]
	
	aIndex := -1
	bIndex := -1
	for index, v := range algo.arr2 {
		if v == a {
			aIndex = index
		}
		
		if v == b {
			bIndex = index
		}
	}
	
	if aIndex != -1 && bIndex != -1 {
		return aIndex < bIndex
	}
	
	if aIndex != -1 {
		return true
	}
	
	if bIndex != -1 {
		return false
	}
	
	return a < b
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


