source: snake
	./snake

snake: library
	gcc --std=gnu99 -o snake snake.c library -lm

library: library.c
	gcc --std=gnu99 -c -o library library.c

test: hilbert
	./hilbert

hilbert:
	gcc --std=gnu99 -o hilbert hilbert.c