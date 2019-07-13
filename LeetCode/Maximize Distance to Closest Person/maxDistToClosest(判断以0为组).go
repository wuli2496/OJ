package main

import "math"

func maxDistToClosest(seats []int) int {
	ans := 0
	k := 0
	seatsLen := len(seats)

	for i := 0; i < seatsLen; i++ {
		if 1 == seats[i] {
			k = 0
		} else {
			k++
			ans = int(math.Max(float64(ans), float64((k + 1) / 2)))
		}
	}

	for i := 0; i < seatsLen; i++ {
		if 1 == seats[i] {
			ans = int(math.Max(float64(ans), float64(i)))
			break
		}
	}

	for i := seatsLen - 1; i >= 0; i-- {
		if 1 == seats[i] {
			ans = int(math.Max(float64(ans), float64(seatsLen - 1 - i)))
			break
		}
	}

	return ans
}
