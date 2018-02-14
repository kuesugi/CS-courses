#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <string.h>
#include <ctype.h>

#pragma pack(1)

// split functions
int streq_nocase(char* a, const char* b);
void invertColor();
void grayNormal();

// 14 bytes long
typedef struct BMPHeader{
	char format_id[2];
	int file_size;
	short reserved_a;
	short reserved_b;
	int offset_start;
} BMPHeader;

// 40 bytes long
typedef struct DIBHeader
{
	int dib_size;
	int width;
	int height;
	short color_planes;
	short bits_per_pixel;
	int compression;
	int img_size;
	int hori_res;	// horizontal resolution
	int vert_res;	// vertical ..
	int palette;
	int important_colors;
} DIBHeader;

// 3 bytes long
typedef struct Pixel
{
	unsigned char blue;
	unsigned char green;
	unsigned char red;
} Pixel;

Pixel pix;

int main(int argc, char** argv){
	int func = -1;	// to record which function the user wants

	// read from argv to use a function: invert or grayscale
	if (streq_nocase(argv[1], "--invert")) func = 0;
	else if (streq_nocase(argv[1], "--grayscale")) func = 1;
	else {
		printf("Only accepts --invert/--grayscale.\n\tExiting...\n");
		exit(1);
	}

	// read the header from the read_file
	FILE* read_file = fopen(argv[2], "rb+");
	BMPHeader bmph;
	fread(&bmph, sizeof(BMPHeader), 1, read_file);

	// Check the format_id
	// since strcmp cannot be used, compared with char values
	if (bmph.format_id[0]!='B' || bmph.format_id[1]!='M') // two char values
	{	
		printf("The format is incorrect!\n\tExiting...\n");
		fclose(read_file);
		exit(1);
	}

	DIBHeader dib;
	fread(&dib, sizeof(dib), 1, read_file);

	// Check the header size
	if (dib.dib_size != 40)
	{
		printf("The size of header is incorrect!\n\tExiting...\n");
		fclose(read_file);
		exit(1);
	}

	// Check number of bits per pixel
	if (dib.bits_per_pixel != 24)
	{
		printf("The number of bits per pixel is incorrect!\n\tExiting...\n");
		fclose(read_file);
		exit(1);
	}

	// No complaints, start to process the image according
	// to user's requirement
	if (func == 0){
		int row_pad = (4-(3*dib.width)%4) % 4;

		fseek(read_file, bmph.offset_start, SEEK_SET);
		
		// for each row and for each pix per row
		for (int i=0; i<dib.height; i++){
			for (int j=0; j<dib.width; j++)
			{
				// read pix from the img
				// and invert colors
				fread(&pix, sizeof(pix), 1, read_file);
				invertColor();
				// fseek backwards
				fseek(read_file, -sizeof(pix), SEEK_CUR);
				// write back
				fwrite(&pix, sizeof(pix), 1, read_file);
			}
			fseek(read_file, row_pad, SEEK_CUR);
		}
	}

	if (func == 1){
		int row_pad = (4- (3*dib.width) %4) % 4;

		fseek(read_file, bmph.offset_start, SEEK_SET);
		
		// for each row and for each pix per row
		for (int i=0; i<dib.height; i++){
			for (int j=0; j<dib.width; j++)
			{
				// read pix from the img
				// and "grayscale" colors in the split function
				fread(&pix, sizeof(pix), 1, read_file);
				grayNormal();
				// fseek backwards
				fseek(read_file, -sizeof(pix), SEEK_CUR);
				// write back
				fwrite(&pix, sizeof(pix), 1, read_file);
			}
			fseek(read_file, row_pad, SEEK_CUR);
		}
	}
	
	fclose(read_file);
	return 0;
}

int streq_nocase(char* a, const char* b)
{
	for(; *a && *b; a++, b++)
		if(tolower(*a) != tolower(*b))
			return 0;

	return *a == 0 && *b == 0;
}

void invertColor(){
	// modify the colors
	pix.blue = (unsigned char) ~pix.blue;
	pix.green = (unsigned char) ~pix.green;
	pix.red = (unsigned char) ~pix.red;
}

void grayNormal(){
	float b_n, g_n, r_n; // normalized colors

	// casting int calculation
	b_n = (float) pix.blue / 255;
	g_n = (float) pix.green / 255;
	r_n = (float) pix.red / 255;

	float y = (0.2126*r_n) + (0.7152*g_n) + (0.0722*b_n);
	if (y > 0.0031308)		
		y = 1.055 * pow(y, 1/2.4) - 0.055;
	else
		y = 12.92 * y;

	// modify the colors
	pix.blue = (unsigned char) (y*255);
	pix.green = (unsigned char) (y*255);
	pix.red = (unsigned char) (y*255);
}
