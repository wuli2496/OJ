package main

import (
	"math"
)

func circularArrayLoop(nums []int) bool {
	algo := &AlgoImpl{nums}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() bool
}

type AlgoImpl struct {
	nums []int
}

func (algo *AlgoImpl) Execute() bool {
	nums := algo.nums
	numsLen := len(nums)

	cnt := 0
	for k, v := range nums {
		vis := make([]bool, numsLen)
		if !vis[k] {
			tmp := k
			posdir := (v > 0)
			cnt = 0
			dirchange := false
			for !vis[tmp] {
				vis[tmp] = true
				cnt++

				if (posdir && (nums[tmp] < 0)) || (!posdir && (nums[tmp] > 0)) {
					dirchange = true
				}
				if nums[tmp] > 0 {
					tmp +=  nums[tmp]
				} else {
					tmp -= int(math.Abs(float64(nums[tmp])))
				}

				for tmp < 0 || tmp >= numsLen{
					tmp = (tmp + numsLen) % numsLen
				}
			}

			if !dirchange && cnt > 1  && tmp == k{
				return true
			}
		}
	}

	return false
}

