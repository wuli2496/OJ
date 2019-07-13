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

	amount := len(lists)
	interval := 1

	for interval < amount {
		for i := 0; i < amount - interval; i += interval * 2 {
			lists[i] = merge2Lists(lists[i], lists[i + interval])
		}
		interval *= 2
	}
	return lists[0]
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