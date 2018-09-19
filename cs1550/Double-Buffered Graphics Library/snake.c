#include <stdio.h>
#include "graphics.h"

#define UP 'w'
#define DOWN 's'
#define LEFT 'a'
#define RIGHT 'd'
#define START 'b'
#define QUIT 'q'
#define CLR_ESP_CODE "\033[2J"
#define POS_CURSOR "\033[1;1H"
#define SNAKE_LENGTH 150

// color of the snake
color_t snake_c = RGB(0, 63, 0);
color_t black = RGB(0, 0, 0);
static int change = 0;

int x_bounce_check(int x){
    if(x == 640)
        return 0;
    if(x == -1)
        return 639;
    return x;
}

int y_bounce_check(int y){
    if(y == 480)
        return 0;
    if(y == -1)
        return 479;
    return y;
}

void start_game() {
    int x_coors[5000], y_coors[5000];
    int x_coor = 319, y_coor = 239;
    int head;
    for(head = 0; head < SNAKE_LENGTH; head++){
        x_coors[head] = x_coor;
        y_coors[head] = y_coor + head;
    }
    void* fb = new_offscreen_buffer();
    // to draw the init shape of snake and wait
    draw_line(fb, x_coor, y_coor, x_coor, y_coor + SNAKE_LENGTH, snake_c);
    // update the y coordination
    y_coor = y_coor + SNAKE_LENGTH;
    blit(fb);
    sleep_ms(5000);
    head = 150;
    do{
        char user_c = getkey();
        int collide = 0;
        if(user_c == UP){
            y_coor--;
            change = 1;
        }
        else if(user_c == DOWN){
            y_coor++;
            change = 2;
        }
        else if(user_c == LEFT){
            x_coor--;
            change = 3;
        }
        else if(user_c == RIGHT){
            x_coor++;
            change = 0;
        }
        else if(user_c == QUIT){
            clear_screen(fb);
            blit(fb);
            break;
        }
        // log the head's coordination
        x_coors[head] = x_coor; y_coors[head] = y_coor;
        x_coor = x_bounce_check(x_coor);
        y_coor = y_bounce_check(y_coor);
        // to draw the new head
        draw_pixel(fb, x_coor, y_coor, snake_c);
        if(head == 300)
            draw_pixel(fb, 319, 389, black);

        draw_pixel(fb, x_coors[head-SNAKE_LENGTH], y_coors[head-SNAKE_LENGTH], black);
        // draw by itself without input
        if (change == 0)
            x_coor++; 
        else if (change == 1)
            y_coor--;
        else if (change == 2)
            y_coor++;
        else if (change ==3)
            x_coor--;
        
        head++;
        sleep_ms(20);
        blit(fb);
    } while(1);

    exit_graphics();
    return;
}

int main() {
    init_graphics();
    printf(CLR_ESP_CODE POS_CURSOR "Usage: WSAD to control the snake; Q to quit\n");
    printf("\t\t\t    * * * B to start * * *\n");
    do{
        char key = getkey();
        if (key == START) {
            printf(CLR_ESP_CODE POS_CURSOR"\n");
            start_game();
            break;
        }
        if (key == QUIT) {
            exit_graphics();
            break;
        }
    } while(1);
    return 0;
}
