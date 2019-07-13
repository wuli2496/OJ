package main

func isOneBitCharacter(bits []int) bool {
	arLen := len(bits)
	i := arLen - 2
	for i >= 0 && bits[i] > 0 {
		i--
	}

	return (arLen - i) % 2 == 0
}