package main

import "sort"

func heightChecker(heights []int) int {
	var algo AlgoStrategy
	algo = newObjectFactory().get("base")
	algo.SetData(heights)

	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
	SetData(heights []int)
}

type AlgoImpl struct {
	Heights []int
}

func (algo *AlgoImpl) SetData(heights []int) {
	algo.Heights = heights
}

func (algo *AlgoImpl) Execute() int {
	heights := algo.Heights

	tmp := make([]int, len(heights))
	copy(tmp, heights)

	sort.Ints(heights)

	count := 0
	for i := range heights {
		if tmp[i] != heights[i] {
			count++
		}
	}

	return count
}

type ObjectFactory struct {
	algoMap map[string] func() AlgoStrategy
}

func newObjectFactory() *ObjectFactory {
	objFactory := &ObjectFactory{algoMap:make(map[string] func() AlgoStrategy, 0)}
	objFactory.register("base", func() AlgoStrategy {return new(AlgoImpl)})

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


