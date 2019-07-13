package main

func isOneBitCharacter(bits []int) bool {
	arLen := len(bits) - 1
	start := 0

	for  start < arLen {
		start += bits[start] + 1
	}

	return start == arLen
}