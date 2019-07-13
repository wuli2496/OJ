package main

func sumEvenAfterQueries(A []int, queries [][]int) []int {
	algo := &LinearAlgo{A, queries}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() []int
}

type LinearAlgo struct {
	a []int
	queries [][]int
}

func (algo *LinearAlgo) Execute() []int {
	a := algo.a
	queries := algo.queries

	sum := 0
	for _, t := range a {
		if t % 2 == 0 {
			sum += t
		}
	}

	var ans []int

	for _, v := range queries {
		if a[v[1]] % 2 == 0 {
			sum -= a[v[1]]
		}

		a[v[1]] += v[0]

		if a[v[1]] % 2 == 0 {
			sum += a[v[1]]
		}

		ans = append(ans, sum)
	}

	return ans
}

