package main

import "fmt"

func hasGroupsSizeX(deck []int) bool {
	count := make(map[int]int)

	for _, v := range deck {
		count[v]++
	}

	aLen := len(deck)
	LOOP:
	for x := 2; x <= aLen; x++ {
		if 0 == aLen % x {

			for _, v := range count {
				if 0 != v % x {
					continue LOOP
				}
			}
			return true
		}
	}
	return false
}

func main() {
	nums := []int{1,1}
	fmt.Println(hasGroupsSizeX(nums))
}