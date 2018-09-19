#define RGB(red, green, blue) ( ((red & 31) << 11) | ((green & 63) << 5) | (blue & 31) )

typedef unsigned short int color_t;

void init_graphics();

void exit_graphics();

char getkey();

void sleep_ms(long);

void clear_screen(void *);

void draw_pixel(void *, int, int, color_t);

void draw_line(void *, int, int, int, int, color_t);

void *new_offscreen_buffer();

void blit(void *);

int range_check(int, int);
