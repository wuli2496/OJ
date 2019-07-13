package main

import (
	"strconv"
)

const N = 10

func maximumSwap(num int) int {
	algo := &GreedyAlgo{num}
	return algo.Execute()
}

type AlgoStategy interface {
	Execute() int
}

type GreedyAlgo struct {
	n int
}

func (algo *GreedyAlgo) Execute() int {
	numStr := []byte(strconv.Itoa(algo.n))

	hash := make([]int, N)
	strLen := len(numStr)
	for i := 0; i < strLen; i++ {
		hash[numStr[i] - '0'] = i
	}

	for i := 0; i < strLen; i++ {
		for j := N - 1; j > int(numStr[i] - '0'); j-- {
			if hash[j] > i {
				numStr[i], numStr[hash[j]] = numStr[hash[j]], numStr[i]
				v, err := strconv.Atoi(string(numStr))
				if err == nil {
					return v
				}
				return 0
			}
		}
	}

	return algo.n
}


