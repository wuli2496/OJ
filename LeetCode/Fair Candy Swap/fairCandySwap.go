package main

func fairCandySwap(A []int, B []int) []int {
	suma, sumb := 0, 0
	hash := make(map[int]int)

	for _, v := range A {
		suma += v
	}

	for _, v := range B {
		hash[v] = 1
		sumb += v
	}

	for _, v := range A {
		tmp := (sumb - suma) / 2 + v
		_, ok := hash[tmp]
		if ok {
			return []int{v, tmp}
		}
	}

	return []int{}
}


