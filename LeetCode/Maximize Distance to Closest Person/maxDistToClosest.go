package main

import "math"

func maxDistToClosest(seats []int) int {
	left := make([]int, len(seats))
	right := make([]int, len(seats))

	for i := 0; i < len(left); i++ {
		left[i] = len(seats)
		right[i] = len(seats)
	}

	for i, v := range seats {
		if 1 == v {
			left[i] = 0
		} else if i > 0{
			left[i] = left[i - 1] + 1
		}
	}

	for i := len(seats) - 1; i >= 0; i-- {
		if 1 == seats[i] {
			right[i] = 0
		} else if i < len(seats) - 1 {
			right[i] = right[i + 1] + 1
		}
	}

	ans := 0
	for i := 0; i < len(seats); i++ {
		if 0 == seats[i] {
			ans = int(math.Max(float64(ans), math.Min(float64(left[i]), float64(right[i]))))
		}
	}

	return ans
}
