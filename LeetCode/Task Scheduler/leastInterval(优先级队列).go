package main

import (
	"container/heap"
)

const N = 26

func leastInterval(tasks []byte, n int) int {
	cnt := make([]int, N)
	for _, v := range tasks {
		cnt[v - 'A']++
	}

	pq := New(N)
	heap.Init(&pq)
	for _, v := range cnt {
		if v > 0 {
			heap.Push(&pq, &Item{Value:v, Priority:int64(v)})
		}
	}

	times := 0
	for !pq.IsEmpty() {
		i := 0
		tmp := make([]*Item, 0)

		for i <= n {
			if !pq.IsEmpty() {
				item := pq.Peek().(*Item)
				if item.Priority > 1 {
					t := heap.Pop(&pq).(*Item)
					tmp = append(tmp, &Item{Value:t.Value, Priority:t.Priority - 1})
				} else {
					heap.Pop(&pq)
				}
			}
			times++

			if pq.IsEmpty() && len(tmp) == 0 {
				break
			}

			i++
		}

		for _, v := range tmp {
			heap.Push(&pq, v)
		}
	}

	return times
}

type Item struct {
	Value interface{}
	Priority int64
	Index int
}

type PriorityQueue []*Item


func New(capacity int) PriorityQueue {
	if capacity < 0 {
		capacity = 1
	}

	return make(PriorityQueue, 0, capacity)
}

func (pq PriorityQueue) Len() int {
	return len(pq)
}

func (pq PriorityQueue) Less(i, j int) bool {
	return pq[i].Priority > pq[j].Priority
}

func (pq PriorityQueue) Swap(i, j int) {
	pq[i], pq[j] = pq[j], pq[i]
	pq[i].Index = i
	pq[j].Index = j
}

func (pq *PriorityQueue) Push(x interface{}) {
	n := len(*pq)
	c := cap(*pq)

	if n + 1 > c {
		npq := make(PriorityQueue, n, 2 * c)
		copy(npq, *pq)
		*pq = npq
	}

	*pq = (*pq)[0 : n + 1]
	item := x.(*Item)
	item.Index = n
	(*pq)[n] = item
}

func (pq *PriorityQueue) Pop() interface{} {
	n := len(*pq)
	c := cap(*pq)
	if n < (c / 4) && c > 25 {
		npq := make(PriorityQueue, n, c / 2)
		copy(npq, *pq)
		*pq = npq
	}

	item := (*pq)[n - 1]
	item.Index = -1
	*pq = (*pq)[0 : n - 1]

	return item
}

func (pq PriorityQueue) IsEmpty() bool {
	return len(pq) == 0
}

func (pq PriorityQueue) Peek() interface{} {
	n := len(pq)
	if n == 0 {
		return nil
	}

	return pq[0]
}