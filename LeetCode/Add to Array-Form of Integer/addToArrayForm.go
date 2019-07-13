package main

func addToArrayForm(A []int, K int) []int {
	algo := &MathAlgo{A, K}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() []int
}

type MathAlgo struct {
	nums []int
	k int
}

func (algo *MathAlgo) Execute() []int {
	nums := algo.nums
	k := algo.k

	numsLen := len(nums)
	var ans []int
	for i := numsLen - 1; i >= 0; i--{
		k += nums[i]
		ans = append(ans, k % 10)
		k /= 10
	}

	for k > 0 {
		ans = append(ans, k % 10)
		k /= 10
	}

	ansLen := len(ans)
	for i := 0; i < ansLen / 2; i++ {
		ans[i], ans[ansLen - 1 - i ] = ans[ansLen - 1 - i ], ans[i]
	}

	return ans
}

