package main

func constructArray(n int, k int) []int {
	algo := &LinearAlgo{n, k}
	return algo.Execute()
}

type Stategy interface {
	Execute() []int
}

type LinearAlgo struct {
	n, k int
}

func (algo *LinearAlgo) Execute() []int {
	nums := make([]int, algo.n)

	c := 0
	for i := 1; i < algo.n - algo.k; i++ {
		nums[c] = i
		c++
	}

	for i := 0; i <= algo.k; i++ {
		if i % 2 == 0 {
			nums[c] = algo.n - algo.k  + i / 2
		} else {
			nums[c] = algo.n -  i / 2
		}
		c++
	}

	return nums
}


