
##CFLAGS        = -std=c99 \
##                -pedantic -Wall -Werror -Wimplicit -Wunused -Wcomment -Wchar-subscripts -Wuninitialized \
##                -Wshadow -Wcast-qual -Wcast-align -Wwrite-strings -Wstack-protector -Wno-deprecated-declarations \
##                -Wno-unknown-pragmas -Wformat-security \
##                -fPIC -fstack-protector-all \
##                -O2 \
##                -D_POSIX_C_SOURCE=20112L

CFLAGS        = -std=c99 \
                -pedantic -Wall -Wimplicit -Wunused -Wcomment -Wchar-subscripts -Wuninitialized \
                -Wshadow -Wcast-qual -Wcast-align -Wwrite-strings -Wstack-protector -Wno-deprecated-declarations \
                -Wno-unknown-pragmas -Wformat-security \
								-Wno-dollar-in-identifier-extension \
                -fPIC \
                -O2 \
                -D_POSIX_C_SOURCE=20112L

LFLAGS				= -lpthread
CC            = gcc
LINKER        = gcc

# Targets

.PHONY: clean dirs

dirs:
	mkdir -p target/obj
	mkdir -p target/gen

clean:
	rm -rf target
