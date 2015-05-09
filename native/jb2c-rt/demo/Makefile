
include ../generic.mk

CFLAGS += -I../src/

# Targets

all: target/demo

target/demo: target/obj/main.o target/obj/cast.o target/obj/mem.o target/obj/rtlog.o
	$(LINKER) -o target/demo target/obj/main.o target/obj/cast.o target/obj/mem.o target/obj/rtlog.o $(LFLAGS)

target/obj/main.o: dirs main.c
	$(CC) $(CFLAGS) -c main.c -o target/obj/main.o

# JB2C RT

target/obj/cast.o: dirs ../src/rt/cast.c
	$(CC) $(CFLAGS) -c ../src/rt/cast.c -o target/obj/cast.o

target/obj/mem.o: dirs ../src/rt/mem.c
	$(CC) $(CFLAGS) -c ../src/rt/mem.c -o target/obj/mem.o

target/obj/rtlog.o: dirs ../src/rt/rtlog.c
	$(CC) $(CFLAGS) -c ../src/rt/rtlog.c -o target/obj/rtlog.o

