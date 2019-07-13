package main

import (
	"fmt"
)

type ListNode struct {
	Val int
	Next *ListNode
}

func mergeKLists(lists []*ListNode) *ListNode {
	if nil == lists || 0 == len(lists){
		return nil
	} else if 1 == len(lists) {
		return lists[0]
	}

	l1 := lists[0]
	for i := 1; i < len(lists); i++ {
		l1 = merge2Lists(l1, lists[i])
	}
	return l1
}

func merge2Lists(l1 *ListNode, l2 *ListNode) *ListNode {

	var head, tail *ListNode
	head = new(ListNode)
	tail = head

	for l1 != nil && l2 != nil {
		if l1.Val < l2.Val {
			tail.Next = l1
			tail = tail.Next
			l1 = l1.Next
		} else {
			tail.Next = l2
			tail = tail.Next
			l2 = l2.Next
		}
	}

	if l1 != nil {
		tail.Next = l1
	} else if l2 != nil {
		tail.Next = l2
	}

	return head.Next
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

	l1 := []int{}
	l2 := []int{1}
	//l3 := []int{2, 6}

	list = append(list, createListNode(l1))
	list = append(list, createListNode(l2))
	//list = append(list, createListNode(l3))

	ans := mergeKLists(list)

	for ans != nil {
		fmt.Println(ans.Val)
		ans = ans.Next
	}
}