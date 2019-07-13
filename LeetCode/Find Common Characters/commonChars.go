func commonChars(A []string) []string {
	algo := &BruteForceAlgo{A}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() []string
}

type BruteForceAlgo struct {
	A []string
}

func (algo *BruteForceAlgo) Execute() []string {
	a := algo.A
	
	s := a[0]
	hash := make(map[rune]int)
	for _, v := range s {
		c, ok := hash[v]
		if !ok {
			hash[v] = 1
		} else {
			hash[v] = c + 1
		}
	}
	
	
	for i := 1; i < len(a); i++ {
		tmpHash := make(map[rune]int)
		for _, v := range a[i] {
			c, ok := tmpHash[v]
			if !ok {
				tmpHash[v] = 1
			} else {
				tmpHash[v] = c + 1
			}
		}
		
		for k1, v1 := range tmpHash {
			v2, ok := hash[k1]
			if !ok {
				hash[k1] = 0
			} else {
				hash[k1] = int(math.Min(float64(v1), float64(v2)))
			}
		}
		
		for k2, _ := range hash {
			_, ok := tmpHash[k2]
			if !ok {
				hash[k2] = 0
			}
		}
	}
	
	var ans []string
	for k, v := range hash {
		if v > 0 {
			for v > 0 {
				ans = append(ans, string(k))
				v--
			}
		}
		
	}
	
	return ans
}
