package main

import (
	"fmt"
	"sort"
)

type ListNode struct {
	Val int
	Next *ListNode
}

func mergeKLists(lists []*ListNode) *ListNode {
	tmp := make([]int, 0)
	for _, v := range lists {
		for v != nil {
			tmp = append(tmp, v.Val)
			v = v.Next
		}
	}

	sort.Ints(tmp)

	ans := new(ListNode)
	t1 := ans
	for _, val := range tmp {
		t := new(ListNode)
		t.Val = val
		t1.Next = t
		t1 = t1.Next
	}
	return ans.Next
}

func createListNode(a []int) *ListNode {
	if len(a) < 1 {
		return nil
	}

	ans := new(ListNode)
	tmp := ans
	for _, v := range a {
		t := new(ListNode)
		t.Val = v

		tmp.Next = t
		tmp = tmp.Next
	}

	return ans.Next
}

func main() {
	list := make([]*ListNode, 0)

	l1 := []int{1, 4, 5}
	l2 := []int{1, 3, 4}
	l3 := []int{2, 6}

	list = append(list, createListNode(l1))
	list = append(list, createListNode(l2))
	list = append(list, createListNode(l3))

	ans := mergeKLists(list)


	for ans != nil {
		fmt.Println(ans.Val)
		ans = ans.Next
	}
}