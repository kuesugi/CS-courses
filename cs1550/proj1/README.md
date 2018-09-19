# ZHM16-project1

#### Please ignore the _MACOSX folder under ZHM16_project.tar.gz

---

### Hilbert.c:

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

- Please do **not** make the snake twisted or go along its body line because the instructions did **not** us that we do need to implement a collision check of the snake's body; otherwise, everything works perfectly

- I made the snake initially go right, it's just a default choice made by myself, not the system itself

- If there's `modification time in the future` or `Clock time skewe detected ` warnings, you can type

- ```
  gcc --std=gnu99 -c -o library library.c
  gcc --std=gnu99 -o snake snake.c library -lm
  ```

  to test `snake`, **or ignore the warning if you can enter the program**
