package main

import (
	"fmt"
	"sort"
)

type Node struct {
	Index int
	Num int
}

type NodeSlice []Node

func (a NodeSlice) Swap(i, j int) {
	a[i], a[j] = a[j], a[i]
}

func (a NodeSlice) Len() int{
	return len(a)
}

func (a NodeSlice) Less(i, j int) bool {
	return a[i].Num < a[j].Num
}

func twoSum(nums []int, target int) []int {
	node := make([]Node, len(nums))

	for i := 0; i < len(nums); i++{
		node[i].Index = i
		node[i].Num = nums[i]
	}

	sort.Sort(NodeSlice(node))

	for i, j := 0, len(node) - 1; i < j; {
		if node[i].Num + node[j].Num > target {
			j--
		} else if node[i].Num + node[j].Num < target {
			i++
		} else {
				return []int{node[i].Index, node[j].Index}
		}
	}
	return nil
}

func main() {
	var nums []int = []int{3, 2, 4}
	target := 6
	fmt.Println("result:", twoSum(nums, target))
}