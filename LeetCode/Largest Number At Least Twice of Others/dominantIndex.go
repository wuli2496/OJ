package main

func dominantIndex(nums []int) int {
	maxIndex := 0
	arrLen := len(nums)
	for i := 0; i < arrLen; i++ {
		if nums[i] > nums[maxIndex] {
			maxIndex = i
		}
	}

	for i := 0; i < arrLen; i++ {
		if (i != maxIndex) && (nums[maxIndex] < 2 * nums[i]) {
			return -1
		}
	}

	return maxIndex
}
