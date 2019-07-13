package main

import "container/list"

func maxAreaOfIsland(grid [][]int) int {
	var algo AlgoStrategy
	algo = &DfsAlgo{grid}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
}

type Node struct {
	X, Y int
}

type DfsAlgo struct {
	grid [][]int
}

func (algo *DfsAlgo) Execute() int {
	row := len(algo.grid)
	col := len(algo.grid[0])

	vis := make([][]bool, row)
	for i := 0; i < row; i++ {
		vis[i] = make([]bool, col)
	}

	dr := []int{1, -1, 0, 0}
	dc := []int{0, 0, 1, -1}
	stack := NewStack()
	ans := 0
	for i := 0; i < row; i++ {
		for j := 0; j < col; j++ {
			if !vis[i][j] && algo.grid[i][j] == 1{

				stack.Push(Node{i, j})
				vis[i][j] = true
				step := 0
				for !stack.Empty() {
					node := stack.Pop().(Node)
					step++
					for i := 0; i < 4; i++ {
						x := node.X + dr[i]
						y := node.Y + dc[i]
						if x >= 0 && x < row && y >= 0 && y < col && algo.grid[x][y] == 1 && !vis[x][y] {
							vis[x][y] = true
							stack.Push(Node{x, y})
						}
					}
				}

				if step > ans {
					ans = step
				}
			}
		}
	}

	return ans
}

type Stack struct {
	list *list.List
}

func NewStack() *Stack {
	list := list.New()
	return &Stack{list}
}

func (stack *Stack) Push(value interface{}) {
	stack.list.PushBack(value)
}

func (stack *Stack) Pop() interface{} {
	e := stack.list.Back()
	if e != nil {
		stack.list.Remove(e)
		return e.Value
	}

	return nil
}

func (stack *Stack) Peak() interface{} {
	e := stack.list.Back()
	if e != nil {
		return e.Value
	}

	return nil
}

func (stack *Stack) Len() int {
	return stack.list.Len()
}

func (stack *Stack) Empty() bool {
	return stack.list.Len() == 0
}