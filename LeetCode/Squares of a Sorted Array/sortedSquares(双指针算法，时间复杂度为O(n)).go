package main

func sortedSquares(A []int) []int {
	algo := &TwoPointerAlgo{A}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() []int
}

type TwoPointerAlgo struct {
	a []int
}

func (algo *TwoPointerAlgo) Execute() []int {
	a := algo.a

	aLen := len(a)

	j := 0
	for j < aLen {
		if a[j] > 0 {
			break
		}
		j++
	}

	i := j - 1

	ans := make([]int, aLen)
	t := 0
	for i >= 0 && j < aLen {
		if a[i] * a[i] < a[j] * a[j] {
			ans[t] = a[i] * a[i]
			i--
		} else {
			ans[t] = a[j] * a[j]
			j++
		}
		t++
	}

	for i >= 0 {
		ans[t] = a[i] * a[i]
		i--
		t++
	}

	for j < aLen {
		ans[t] = a[j] * a[j]
		t++
		j++
	}

	return ans
}

