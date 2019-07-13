package main

func subarraySum(nums []int, k int) int {
	arLen := len(nums)

	cnt := 0
	hash := make(map[int]int)
	hash[0] = 1
	sum := 0
	for i := 0; i < arLen; i++ {
		sum += nums[i]
		v, ok := hash[sum - k]
		if ok {
			cnt += v
		}

		v, ok = hash[sum]
		if ok {
			hash[sum] = v + 1
		} else {
			hash[sum] = 1
		}
	}

	return cnt
}