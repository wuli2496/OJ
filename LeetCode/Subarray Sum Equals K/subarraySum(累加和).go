package main

func subarraySum(nums []int, k int) int {
	arLen := len(nums)

	sums := make([]int, arLen + 1)
	for i := 1; i <= arLen; i++ {
		sums[i] = sums[i - 1] + nums[i - 1]
	}

	cnt := 0
	for start := 0; start < arLen; start++ {
		for end := start + 1; end <= arLen; end++ {
			if sums[end] - sums[start] == k {
				cnt++
			}
		}
	}

	return cnt
}