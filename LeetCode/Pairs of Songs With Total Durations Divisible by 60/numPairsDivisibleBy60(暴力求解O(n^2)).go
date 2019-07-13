func numPairsDivisibleBy60(time []int) int {
    algo := &BruteForceAlgo{time}
    return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
}

type BruteForceAlgo struct {
	time []int
}

func (algo *BruteForceAlgo) Execute() int {
	time := algo.time
	
	tLen := len(time)
	ans := 0
	for i := 0; i < tLen; i++ {
		for j := i + 1; j < tLen; j++ {
			if (time[i] + time[j]) % 60 == 0 {
				ans++
			}
		}
	}
	
	return ans
}