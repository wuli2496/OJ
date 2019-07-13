package main

func arrayNesting(nums []int) int {
	arLen := len(nums)

	ans := 0
	for i := 0; i < arLen; i++ {
		start := nums[i]
		cnt := 0
		for {
			start = nums[start]
			cnt++
			if start == nums[i] {
				break
			}
		}

		if cnt > ans {
			ans = cnt
		}
	}

	return ans
}