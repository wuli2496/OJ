package main

func canPlaceFlowers(flowerbed []int, n int) bool {
	arLen := len(flowerbed)

	cnt := 0
	for i := 0; i < arLen; i++ {
		if flowerbed[i] == 0 &&
			(i == 0 || flowerbed[i - 1] == 0) &&
			(i == arLen - 1 || flowerbed[i + 1] == 0) {
			flowerbed[i] = 1
			cnt++
		}
	}

	return cnt >= n
}