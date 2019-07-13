func maximumSwap(num int) int {
	algo := &BruteForceStrategy{num}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
}

type BruteForceStrategy struct {
	Num int
}

func (algo *BruteForceStrategy) Execute() int {
	s := strconv.Itoa(algo.Num)
	orig := []byte(s)
	tmp := make([]byte, len(orig))
	copy(tmp, orig)

	aLen := len(tmp)
	for i := 0; i < aLen; i++ {
		for j := i + 1; j < aLen; j++ {
			tmp[i], tmp[j] = tmp[j], tmp[i]
			for k := 0; k < aLen; k++ {
				if tmp[k] != orig[k] {
					if tmp[k] > orig[k] {
						copy(orig, tmp)
					}
					break
				}

			}

			tmp[i], tmp[j] = tmp[j], tmp[i]
		}
	}

	v, ok := strconv.Atoi(string(orig))
	if ok == nil {
		return v
	}

	return 0
}