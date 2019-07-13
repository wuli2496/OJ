package main

import (
	"math"
)

func maximumProduct(nums []int) int {
	min1 := math.MaxInt32
	min2 := math.MaxInt32
	max1 := math.MinInt32
	max2 := math.MinInt32
	max3 := math.MinInt32

	for _, v := range nums {
		if v < min1 {
			min2 = min1
			min1 = v
		} else if v < min2 {
			min2 = v
		}

		if v > max1 {
			max3 = max2
			max2 = max1
			max1 = v
		} else if v > max2 {
			max3 = max2
			max2 = v
		} else if v > max3 {
			max3 = v
		}
	}

	return int(math.Max(float64(min1 * min2 * max1),
		float64(max1 * max2 * max3)))
}