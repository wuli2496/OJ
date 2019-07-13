package main

func numSubarrayProductLessThanK(nums []int, k int) int {
	algo := &SlideWindowAlgo{nums, k}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
}

type SlideWindowAlgo struct {
	nums []int
	k int
}

func (algo *SlideWindowAlgo) Execute() int {
	nums := algo.nums
	k := algo.k

	if k <= 1 {
		return 0
	}

	ans := 0
	left := 0
	p := 1
	for right, v := range nums {
		p *= v
		for p >= k {
			p /= nums[left]
			left++
		}
		ans += right - left + 1
	}

	return ans
}

