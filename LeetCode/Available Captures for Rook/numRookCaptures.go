package main

func numRookCaptures(board [][]byte) int {
	algo := &NormalAlgo{board}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
}

type NormalAlgo struct {
	board [][]byte
}

func (algo *NormalAlgo) Execute() int {
	board := algo.board

	row := len(board)
	col := len(board[0])

	x := 0
	y := 0
	found := false
	for i := 0; i < row && !found; i++ {
		for j := 0; j < col && !found; j++ {
			if board[i][j] == 'R' {
				x = i
				y = j
				found = true
			}
		}
	}

	num := 0
	tmprow := x
	for tmprow >= 0 && board[tmprow][y] != 'B'{
		if board[tmprow][y] == 'p' {
			num++
			break
		}
		tmprow--
	}

	tmprow = x
	for tmprow < row && board[tmprow][y] != 'B' {
		if board[tmprow][y] == 'p' {
			num++
			break
		}
		tmprow++
	}

	tmpcol := y
	for tmpcol >= 0 && board[x][tmpcol] != 'B' {
		if board[x][tmpcol] == 'p' {
			num++
			break
		}
		tmpcol--
	}

	tmpcol = y
	for tmpcol < col && board[x][tmpcol] != 'B' {
		if board[x][tmpcol] == 'p' {
			num++
			break
		}
		tmpcol++
	}

	return num
}

