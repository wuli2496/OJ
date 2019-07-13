const (
	N = 60
)

func numPairsDivisibleBy60(time []int) int {
    algo := &LinearAlgo{time}
    return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
}

type LinearAlgo struct {
	time []int
}

func (algo *LinearAlgo) Execute() int {
	time := algo.time
	
	cnt := make([]int, N)
	
	ans := 0
	for _, v := range time {
		if v % N != 0 {
			ans += cnt[N - v % N]
		} else {
			ans += cnt[0]
		}
		
		cnt[v % N]++
	}
	
	return ans
}