package main

func findMaxAverage(nums []int, k int) float64 {
	arLen := len(nums)

	start := 0
	end := k

	sum := 0
	for i := start; i < end; i++ {
		sum += nums[i]
	}

	ans := float64(sum) / float64(k)

	for end < arLen {
		sum -= nums[start]
		sum += nums[end]
		end++
		start++

		aver := float64(sum) / float64(k)
		if aver > ans {
			ans = aver
		}
	}

	return ans
}