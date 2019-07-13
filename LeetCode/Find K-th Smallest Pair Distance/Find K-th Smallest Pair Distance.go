package main

import (
	"container/heap"
	"sort"
)

func smallestDistancePair(nums []int, k int) int {
    var algo AlgoStrategy
    algo = NewObjectFactory().get("heap")
    algo.SetData(nums, k)
    
    return algo.Execute()
}

type Node struct {
	root int
	nei int
	nums []int
}

type PriorityQueue []*Node

func NewPQueue(capacity int) PriorityQueue {
	if capacity < 0 {
		capacity = 1
	}	
	
	return make(PriorityQueue, 0, capacity)
}

func (pq PriorityQueue) Len() int {
	return len(pq)
}

func (pq PriorityQueue) Swap(i, j int) {
	pq[i], pq[j] = pq[j], pq[i]
}

func (pq PriorityQueue) Less(i, j int) bool {
	a := pq[i].nums[pq[i].nei] - pq[i].nums[pq[i].root]
	b := pq[j].nums[pq[j].nei] - pq[j].nums[pq[j].root]
	
	return a < b
}

func (pq *PriorityQueue) Push(x interface{}) {
	node := x.(*Node)
	*pq = append(*pq, node)	
}

func (pq *PriorityQueue) Pop() interface{} {
	old := *pq
	n := len(old)
	node := old[n - 1]
	
	*pq = old[0:n-1]
	
	return node
}

type AlgoStrategy interface {
	Execute() int
	SetData(nums []int, k int)
}

type HeapAlgo struct {
	nums[] int
	k int
}

func (algo *HeapAlgo) SetData(nums []int, k int) {
	algo.nums = nums
	algo.k = k	
}

func (algo *HeapAlgo) Execute() int {
	nums := algo.nums
	k := algo.k
	sort.Ints(nums)
	pq := NewPQueue(1)
	
	heap.Init(&pq)
	n := len(nums)
	for i := 0; i + 1 < n; i++ {
		heap.Push(&pq, &Node{i, i + 1, nums})
	}
	
	var node *Node = nil
	for ; k > 0; k-- {
		node = heap.Pop(&pq).(*Node)
		if node.nei + 1 < n {
			heap.Push(&pq, &Node{node.root, node.nei + 1, nums})
		}
	}
	
	return node.nums[node.nei] - node.nums[node.root]
}

type ObjectFactory struct {
	factoryMap map[string] func() AlgoStrategy;
}

func (objectFactory *ObjectFactory) register(name string, algo func() AlgoStrategy) {
	objectFactory.factoryMap[name] = algo
}

func (objectFactory *ObjectFactory) get(name string) AlgoStrategy {
	if f, ok := objectFactory.factoryMap[name]; ok {
		return f()
	}
	
	return nil
}

func NewObjectFactory() *ObjectFactory {
	objFactory := &ObjectFactory{factoryMap:make(map[string] func() AlgoStrategy, 0)}
	objFactory.register("heap", func() AlgoStrategy {return new(HeapAlgo)})
	
	return objFactory
}
