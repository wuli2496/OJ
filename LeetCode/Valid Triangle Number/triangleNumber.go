package main

func triangleNumber(nums []int) int {
	arLen := len(nums)
	ans := 0
	for i := 0; i < arLen; i++ {
		for j := i + 1; j < arLen; j++ {
			for k := j + 1; k < arLen; k++ {
				if nums[i] + nums[j] > nums[k] && nums[i] + nums[k] > nums[j] && nums[j] + nums[k] > nums[i] {
					ans++
				}
			}
		}
	}

	return ans
}