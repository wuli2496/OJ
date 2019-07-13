package main

import "fmt"

func gcd(x, y int) int {
	if 0 == y {
		return x
	} else {
		return gcd(y, x % y)
	}
}

func hasGroupsSizeX(deck []int) bool {
	count := make(map[int]int)

	for _, v := range deck {
		count[v]++
	}

	ans := -1
	for _, v := range count {
		if -1 == ans {
			ans = v
		} else {
			ans = gcd(ans, v)
		}
	}

	return ans >= 2
}

func main() {
	nums := []int{1,1,1,2,2,2,3,3}
	fmt.Println(hasGroupsSizeX(nums))
}