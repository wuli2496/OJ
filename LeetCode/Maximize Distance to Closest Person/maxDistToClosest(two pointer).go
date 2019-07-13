package main

import "math"

func maxDistToClosest(seats []int) int {
	prev := -1
	future := 0
	seatsLen := len(seats)

	ans := 0
	for i := 0; i < seatsLen; i++ {
		if 1 == seats[i] {
			prev = i
		} else {
			for (future < seatsLen && 0 == seats[future]) || future < i {
				future++
			}

			left := 0
			if -1 == prev {
				left = seatsLen
			} else {
				left = i - prev
			}

			right := 0
			if future == seatsLen {
				right = seatsLen
			} else {
				right = future - i
			}

			ans = int(math.Max(float64(ans), math.Min(float64(left), float64(right))))
		}
	}

	return ans
}
