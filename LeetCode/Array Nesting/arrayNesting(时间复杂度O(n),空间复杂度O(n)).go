package main

func arrayNesting(nums []int) int {
	algo := VisAlgo{nums}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
}

type VisAlgo struct {
	nums []int
}

func (algo *VisAlgo) Execute() int {
	nums := algo.nums

	numsLen := len(nums)
	vis := make([]bool, numsLen)
	ans := 0
	for i := 0; i < numsLen; i++ {
		if !vis[i] {
			cnt := 0
			start := nums[i]
			for  {
				cnt++
				vis[start] = true
				start = nums[start]
				if start == nums[i] {
					break
				}
			}

			if cnt > ans {
				ans = cnt
			}
		}
	}

	return ans
}

