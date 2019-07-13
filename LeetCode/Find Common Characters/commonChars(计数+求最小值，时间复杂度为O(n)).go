const N = 26

func commonChars(A []string) []string {
	algo := &LinearAlgo{A}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() []string
}

type LinearAlgo struct {
	A []string
}

func (algo *LinearAlgo) Execute() []string {
	a := algo.A
	
	cnt := make([]int, N)
	for i := 0; i < len(cnt); i++ {
		cnt[i] = math.MaxInt32
	}
	
	for i := 0; i < len(a); i++ {
		tmpCnt := make([]int, N)
		for _, v := range a[i] {
			tmpCnt[v - 'a']++
		}
		
		for j := 0; j < N; j++ {
			cnt[j] = int(math.Min(float64(cnt[j]), float64(tmpCnt[j])))
		}
	}
	
	var ans []string
	for i := 0; i < N; i++ {
		if cnt[i] != 0 && cnt[i] != math.MaxInt32 {
			for j := 0; j < cnt[i]; j++ {
				ans = append(ans, string('a' + i))
			}
		}
	}
	
	return ans
}
