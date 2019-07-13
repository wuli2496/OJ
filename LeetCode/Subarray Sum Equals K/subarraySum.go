package main

func subarraySum(nums []int, k int) int {
	arLen := len(nums)

	cnt := 0
	for start := 0; start < arLen; start++ {
		for end := start + 1; end <= arLen; end++ {
			sum := 0
			for i := start; i < end; i++ {
				sum += nums[i]
			}
			if sum == k {
				cnt++
			}
		}
	}

	return cnt
}