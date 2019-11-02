filename=`find ./ -name *.cpp`
exename=$(basename ${filename})
exe=${exename%%.cpp}

function genMakefile()
{
    cat /dev/null > Makefile
    echo -e "EXE:=${exe}" >> Makefile
    echo -e "FILE:=${exename}" >> Makefile
    echo -e "CFLAGS:=-std=c++11 -g -o" >> Makefile
    echo -e "CC:=g++" >> Makefile

    echo -e "" >> Makefile
    echo -e '$(EXE):$(FILE)' >> Makefile
    echo -e '\t\t$(CC) $(CFLAGS) $@ $^' >> Makefile
   
    echo -e "" >> Makefile
    echo -e ".PHONY:clean" >> Makefile
    echo -e '\t\trm -rf *.o $(EXE)' >> Makefile
}

genMakefile
