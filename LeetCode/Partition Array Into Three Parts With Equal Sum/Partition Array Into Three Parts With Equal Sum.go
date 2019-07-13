package main

func canThreePartsEqualSum(A []int) bool {
	algo := LinearAlgo{A}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() bool
}

type LinearAlgo struct {
	a []int
}

func (algo *LinearAlgo) Execute() bool {
	a := algo.a
	
	sum := 0
	for _, v := range a {
		sum += v
	}
	
	if sum % 3 != 0 {
		return false
	}
	
	s := 0
	cnt := 0
	for _, v := range a {
		s += v
		if s == sum / 3 {
			cnt++
			s = 0
		}
	}
	
	return cnt == 3
}
