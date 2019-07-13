package main

func checkPossibility(nums []int) bool {
	p := -1
	arLen := len(nums)

	for i := 0; i < arLen - 1; i++ {
		if nums[i] > nums[i + 1] {
			if p != -1 {
				return false
			}

			p = i
		}
	}

	return p == -1 || p == 0 || p == arLen - 2 || nums[p - 1] <= nums[p + 1] || nums[p] <= nums[p + 2]
}