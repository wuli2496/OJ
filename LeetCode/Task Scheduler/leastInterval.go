package main

import "sort"

const N = 26

func leastInterval(tasks []byte, n int) int {
	c := make([]int, N)
	for _, v := range tasks {
		c[v - 'A']++
	}
	sort.Ints(c)

	time := 0
	for c[N  - 1] > 0 {
		i := 0
		for i <= n {
			if c[N - 1] == 0 {
				break
			}

			if i < N && c[N - 1 - i] > 0 {
				c[N - 1 - i]--
			}

			time++
			i++
		}
		sort.Ints(c)
	}

	return time
}
