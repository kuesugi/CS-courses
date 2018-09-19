### Hilbert.c (not included here):

##### Running:

- To test `hilbert.c`, use `make test`

##### Usage and note:
- Demo:
<img src="hilbert.gif" alt="hilbert" height="350" width="520"/>

- To make `hilbert` work, try to use `⌘/shift/ctrl` with `+/=` as `+`, or simply `+/=` button
---

### Snake.c ([description](p1.pdf)):

##### Running:

- To test` snake.c`, use `make`

##### Usage:

- Demo:
<img src="snake.gif" alt="snake game" height="350" width="520"/>

- Press `B` to start the game
- Use `W/S/A/D` to control the snake
- Use `Q` to quit the game at anytime
- Do **not** use arrow keys

##### Note:

- Making the snake twisted can lose some pixels

- If there's `modification time in the future` or `Clock time skewe detected ` warning, you can type the following or **ignore the warning if you can enter the program**:
  ```
  gcc --std=gnu99 -c -o library library.c
  gcc --std=gnu99 -o snake snake.c library -lm
  ```
