package main

import (
	"fmt"
)

type ListNode struct {
	Val int
	Next *ListNode
}

func mergeKLists(lists []*ListNode) *ListNode {
	var head *ListNode = nil
	var tail *ListNode = nil

	for {
		index := -1
		for i := 0; i < len(lists); i++ {
			if lists[i] != nil && index < 0 {
				index = i
			} else {
				if lists[i] != nil && lists[i].Val < lists[index].Val  {
					index = i
				}
			}
		}

		if index != -1 {
			if head == nil {
				head = lists[index]
				tail = head
			} else {
				tail.Next = lists[index]
				tail = tail.Next
			}
			lists[index] = lists[index].Next
		} else {
			break
		}
	}


	return head
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