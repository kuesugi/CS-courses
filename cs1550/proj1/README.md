### Hilbert.c (not included here):

##### Running:

- To test `hilbert.c`, use `make test`

##### Usage and note:

- To make `hilbert` work, try to use `⌘/shift/ctrl` with `+/=` as `+`, or simply `+/=` button

---

### Snake.c:

##### Running:

- To test` snake.c`, use `make`

##### Usage:

- Press `B` to start the game
- Use `W/S/A/D` to control the snake
- Use `Q` to quit the game at anytime
- Do **not** use arrow keys

##### Note:

- Please do **not** make the snake twisted or go along its body line

- If there's `modification time in the future` or `Clock time skewe detected ` warning, you can type

- ```
  gcc --std=gnu99 -c -o library library.c
  gcc --std=gnu99 -o snake snake.c library -lm
  ```

  to test `snake`, **or ignore the warning if you can enter the program**
