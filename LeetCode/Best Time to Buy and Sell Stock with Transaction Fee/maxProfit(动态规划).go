package main

import "math"

func maxProfit(prices []int, fee int) int {
	algo := &DpAlgo{prices, fee}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
}

type DpAlgo struct {
	prices []int
	fee int
}

func (algo *DpAlgo) Execute() int {
	prices := algo.prices
	fee := algo.fee

	dp0 := 0
	dp1 := -prices[0]

	for i := 1; i < len(prices); i++ {
		dp0 = int(math.Max(float64(dp0), float64(dp1 + prices[i] - fee)))
		dp1 = int(math.Max(float64(dp1), float64(dp0 - prices[i])))
	}

	return dp0
}

