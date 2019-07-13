package main

func sumEvenAfterQueries(A []int, queries [][]int) []int {
	algo := &BruteForceAlgo{A, queries}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() []int
}

type BruteForceAlgo struct {
	a []int
	queries [][]int
}

func (algo *BruteForceAlgo) Execute() []int {
	a := algo.a
	queries := algo.queries

	var ans []int

	for _, v := range queries {
		a[v[1]] += v[0]

		sum := 0
		for _, t := range a {
			if t % 2 == 0 {
				sum += t
			}
		}

		ans = append(ans, sum)
	}

	return ans
}

