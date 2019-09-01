EXE:=usaco
CPPFILES:=$(wildcard *.cpp)
CFLAGS:=-std=c++11 -g -o
CC:=g++

$(EXE):$(CPPFILES)
		 $(CC) $(CFLAGS) $@ $^

.PHONY:clean

clean:
	rm -rf *.o $(EXE)

