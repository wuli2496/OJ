package main

import (
	"fmt"
	"math"
)

func lengthOfLongestSubstring(s string) int {
	ans := 0

	hash := make(map[byte]int)
	for i, j := 0, 0; j < len(s); j++ {
		v, ok := hash[s[j]]
		if ok {
			i = int(math.Max(float64(v), float64(i)))
		}

		ans = int(math.Max(float64(ans), float64(j - i + 1)))
		hash[s[j]] = j + 1
	}

	return ans
}


func main() {
	s := "pwwkew"
	fmt.Println("ans:",lengthOfLongestSubstring(s) )
}