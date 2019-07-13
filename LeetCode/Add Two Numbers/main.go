package main

import "fmt"

type ListNode struct {
	Val int
	Next *ListNode
}
func addTwoNumbers(l1 *ListNode, l2 *ListNode) *ListNode {
	head := new(ListNode)
	tail := head

	carry := 0
	var sum int
	for l1 != nil || l2 != nil {
		sum = 0
		if l1 != nil {
			sum += l1.Val
		}

		if l2 != nil {
			sum += l2.Val
		}

		sum += carry
		carry = sum / 10
		sum = sum % 10

		tmp := new(ListNode)
		tmp.Val = sum
		tail.Next = tmp
		tail = tmp

		if l1 != nil {
			l1 = l1.Next
		}

		if l2 != nil {
			l2 = l2.Next
		}
	}

	if carry > 0 {
		tmp := new(ListNode)
		tmp.Val = carry
		tail.Next = tmp
		tail = tmp
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
	a := []int{2, 4, 3}
	b := []int{5, 6, 4}

	aList := createListNode(a)
	bList := createListNode(b)

	result := addTwoNumbers(aList, bList)

	for result != nil {
		fmt.Print("", result.Val)
		result = result.Next
	}
	fmt.Println()

}