package main

func maxSumOfThreeSubarrays(nums []int, k int) []int {
 	algo := &Algo{nums, k}
 	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() []int
}

type Algo struct {
	nums []int
	k int
}

func (algo *Algo) Execute() []int {
	nums := algo.nums
	numLen := len(nums)
	k := algo.k
	w := make([]int, numLen - k + 1)

	sum := 0
	for i := 0; i < numLen; i++ {
		sum += algo.nums[i]

		if i >= k {
			sum -= nums[i - k]
		}

		if i >= k - 1 {
			w[i - k + 1] = sum
		}
	}

	wLen := len(w)
	left := make([]int, wLen)
	right := make([]int, wLen)

	best := 0
	for i := 0; i < wLen; i++ {
		if w[i] > w[best] {
			best = i
		}

		left[i] = best
	}

	best = wLen - 1
	for i := wLen - 1; i >= 0; i-- {
		if w[i] > w[best] {
			best = i
		}

		right[i] = best
	}

	ans := []int{-1, -1, -1}
	for b := k; b < wLen - k; b++ {
		a := left[b - k]
		c := right[b + k]
		if ans[0] == -1 || w[a] + w[b] + w[c] > w[ans[0]] + w[ans[1]] + w[ans[2]] {
			ans[0] = a
			ans[1] = b
			ans[2] = c
		}
	}

	return ans
}

