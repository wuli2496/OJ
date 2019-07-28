CPPFILES=$(wildcard *.cpp)
CPPDSTFILES=$(filter-out Wormhole.cpp,$(CPPFILES))

usaco:$(CPPDSTFILES)
	g++ -g -o $@ $^

.PHONY:clean

clean:
	rm -rf *.o
