package main

import (
	"fmt"
	"math"
)

func lengthOfLongestSubstring(s string) int {
	ans := 0

	hash := make(map[byte]int)
	i, j := 0, 0
	for i < len(s) && j < len(s) {
		_, ok := hash[s[j]]
		if !ok {
			hash[s[j]] = 0
			j++

			ans = int(math.Max(float64(ans), float64(j - i)))
		} else {
			delete(hash, s[i])
			i++
		}
	}

	return ans
}


func main() {
	s := "pwwkew"
	fmt.Println("ans:",lengthOfLongestSubstring(s) )
}