package main

import "fmt"

func lengthOfLongestSubstring(s string) int {
	ans := 0

	for i, strLen := 0, len(s); i < strLen; i++ {
		for j := i + 1; j <= strLen; j++ {
			if unique(s, i, j) {
				if j - i > ans {
					ans = j - i
				}
			}
		}
	}

	return ans
}

func unique(s string, start, end int) bool {
	hash := make(map[byte]int)
	for i := start; i < end; i++ {
		_, ok := hash[s[i]]
		if ok {
			return false
		} else {
			hash[s[i]] = 0
		}
	}

	return true
}

func main() {
	s := "pwwkew"
	fmt.Println("ans:",lengthOfLongestSubstring(s) )
}