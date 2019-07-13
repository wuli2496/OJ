package main

//时间复杂度O((m+n)*log(min(m,n))*min(m,n))
import (
	"fmt"
	"math"
	"strings"
)

func findLength(A []int, B []int) int {
	var algo AlgoStrategy
	algo = &BinarySearchAlgo{A, B}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
}

type BinarySearchAlgo struct {
	a []int
	b []int
}

func (algo *BinarySearchAlgo) check(length int) bool {
	a := algo.a
	b := algo.b

	set := make(map[string]bool)
	for i := 0; i + length <= len(a); i++ {
		s := strings.Replace(strings.Trim(fmt.Sprint(a[i:i + length]), "[]"), " ", "", -1)
		set[s] = true
	}

	for i := 0; i + length <= len(b); i++ {
		s := strings.Replace(strings.Trim(fmt.Sprint(b[i:i + length]), "[]"), " ", "", -1)
		if _, ok := set[s]; ok {
			return true
		}
	}

	return false
}

func (algo *BinarySearchAlgo) Execute() int {
	a := algo.a
	b := algo.b

	high := int(math.Min(float64(len(a)), float64(len(b))))
	low := 0

	for low < high {
		mid := (low + high + 1) >> 1
		if algo.check(mid) {
			low = mid
		} else {
			high = mid - 1
		}
	}

	return low
}
