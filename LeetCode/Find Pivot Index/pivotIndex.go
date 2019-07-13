package main

func pivotIndex(nums []int) int {
	sum := 0
	for _, v := range nums {
		sum += v
	}

	arLen := len(nums)
	tmp := 0
	for i := 0; i < arLen; i++ {
		if tmp == sum - tmp - nums[i] {
			return i
		}

		tmp += nums[i]
	}

	return -1
}