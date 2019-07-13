package main


func prefixesDivBy5(A []int) []bool {
    algo := LinearAlgo{A}
    return algo.Execute()
}

type AlgoStrategy interface {
	Execute() []bool
}

type LinearAlgo struct {
	a []int
}

func (algo *LinearAlgo) Execute() []bool {
	a := algo.a
	
	sum := 0
	ans := make([]bool, 0)
	for _, v := range a {
		sum = (sum * 2 + v) % 5
		ans = append(ans, sum == 0)
	}
	
	return ans
}
