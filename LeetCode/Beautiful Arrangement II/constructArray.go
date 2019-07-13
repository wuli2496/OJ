package main

import (
	"math"
)

func constructArray(n int, k int) []int {
	algo := &BruteForceAlgo{n, k}
	return algo.Execute()
}

type Stategy interface {
	Execute() []int
}

type BruteForceAlgo struct {
	n, k int
}

func (algo *BruteForceAlgo) Execute() []int {
	nums := make([]int, algo.n)

	for i := 0; i < algo.n; i++ {
		nums[i] = i + 1
	}

	ans := make([][]int, 0)
	algo.permutate(&ans, nums, 0)

	for _, v := range ans {
		cnt := 0
		hash := make(map[int]struct{})
		for i := 0; i < len(v) - 1; i++ {
			tmp := int(math.Abs(float64(v[i] - v[i + 1])))
			_, ok := hash[tmp]
			if !ok {
				cnt++
				hash[tmp] = struct{}{}
			}
		}

		if cnt == algo.k {
			return v
		}
	}

	return nil
}

func (algo *BruteForceAlgo) permutate(ans *[][]int, nums []int, start int)  {
	if start >= len(nums) {
		tmp := make([]int, len(nums))
		copy(tmp, nums)
		*ans = append(*ans, tmp)
		return
	}

	for i := start; i < len(nums); i++ {
		nums[start], nums[i] = nums[i], nums[start]
		algo.permutate(ans, nums, start + 1)
		nums[start], nums[i] = nums[i], nums[start]
	}
}


