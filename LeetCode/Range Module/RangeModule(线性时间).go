package main

import (
	"math"
)

type Range struct {
	left, right int
}

func (r Range) Less(other Range) bool {
	if r.right == other.right {
		return r.left < other.left
	}

	return r.right < other.right
}

type RangeModule struct {
	ranges []Range
}

func Constructor() RangeModule {
	return RangeModule{}
}

func (this *RangeModule) AddRange(left int, right int) {
	index := this.roundIndex(Range{left:0, right: left})
	if index == -1 {
		this.ranges = append(this.ranges, Range{left, right})
		return
	}

	rangesLen := len(this.ranges)
	endIndex := rangesLen
	curLeft := left
	curRight := right
	for i := index; i < rangesLen; i++ {
		if right < this.ranges[i].left {
			endIndex = i
			break
		} else {
			curLeft = int(math.Min(float64(curLeft), float64(this.ranges[i].left)))
			curRight = int(math.Max(float64(curRight), float64(this.ranges[i].right)))
		}
	}

	tmp := make([]Range, index - 0)
	copy(tmp, this.ranges[0:index])
	tmp = append(tmp, Range{curLeft, curRight})
	tmp = append(tmp, this.ranges[endIndex:]...)
	this.ranges = make([]Range, len(tmp))
	copy(this.ranges, tmp)
}

func (this *RangeModule) QueryRange(left int, right int) bool {
	index := this.roundIndex(Range{left:0, right: left})
	if index == -1 {
		return false
	}

	return left >= this.ranges[index].left && right <= this.ranges[index].right
}

func (this *RangeModule) RemoveRange(left int, right int) {

	index := this.roundIndex(Range{left:0, right: left})
	if index == -1 {
		return
	}

	var tmp []Range
	rangesLen := len(this.ranges)
	endIndex := rangesLen
	for i := index; i < rangesLen; i++ {
		if right < this.ranges[i].left {
			endIndex = i
			break
		}

		if this.ranges[i].left < left {
			tmp = append(tmp, Range{this.ranges[i].left, left})
		}

		if right < this.ranges[i].right  {
			tmp = append(tmp, Range{right, this.ranges[i].right})
		}
	}

	t := make([]Range, index - 0)
	copy(t, this.ranges[0:index])
	if len(tmp) > 0 {
		t = append(t, tmp...)
	}

	if endIndex != rangesLen {
		t = append(t, this.ranges[endIndex:]...)
	}

	this.ranges = make([]Range, len(t))
	copy(this.ranges, t)
}

func (this *RangeModule) roundIndex(r Range) int {
	rangesLen := len(this.ranges)

	for i := 0; i < rangesLen; i++ {
		if r.Less(this.ranges[i]) {
			return i
		}
	}

	return -1
}
