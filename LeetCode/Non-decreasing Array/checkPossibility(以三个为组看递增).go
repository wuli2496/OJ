package main

import (
	"math"
)

func checkPossibility(nums []int) bool {
	arLen := len(nums)
	i := 0
	j := arLen - 1

	for i + 2 < arLen && nums[i] <= nums[i + 1] && nums[i + 1] <= nums[i + 2] {
		i++
	}

	for j - 2 >= 0 && nums[j - 2] <= nums[j - 1] && nums[j - 1] <= nums[j] {
		j--
	}

	if j - i + 1 <= 2 {
		return true
	}

	if j - i + 1 >= 5 {
		return false
	}

	return bruceForce(nums[i:j+1])
}

func bruceForce(nums []int) bool {
	arLen := len(nums)
	tmp := make([]int, arLen)
	copy(tmp, nums)

	for i := 0; i < arLen; i++ {

		t := nums[i]
		if i == 0 {
			tmp[i] = math.MinInt32
		} else {
			tmp[i] = tmp[i - 1]
		}

		if nonDecreasing(tmp) {
			return true
		}

		tmp[i] = t
	}

	return false
}

func nonDecreasing(nums []int) bool {
	arLen := len(nums)
	for i := 0; i < arLen -1; i++ {
		if nums[i] > nums[i + 1] {
			return false
		}
	}

	return true
}