#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <dlfcn.h>

// the three essential funcs
unsigned long (*compressBound)(unsigned long length);
int (*compress)(void *dest, unsigned long* destLen, const void* source, unsigned long sourceLen);
int (*uncompress)(void *dest, unsigned long* destLen, const void* source, unsigned long sourceLen);

int main(int argc, char* argv[]){
	// arg # check
	if (argc < 3){
		fprintf(stderr, "Error: Not enough program args\nExiting...\n" );
		exit(1);
	}
	// load zlib
	void* lib = dlopen("libz.so", RTLD_NOW);
	if(lib == NULL) {
		fprintf(stderr, "Error: Cannot load the library\nExiting...\n");
		exit(1);
	}
	FILE* fp = fopen(argv[2], "rb");
	if(fp == NULL){
		fclose(fp);
		fprintf(stderr, "Error: Cannot open the file\nExiting...\n");
		exit(1);
	}
	unsigned long uncps_size = 0;
	unsigned long cps_size = 0;
	// -c mode
	if(strcmp(argv[1], "-c") == 0){
		// load the two essential funcs
		compressBound = dlsym(lib, "compressBound");
		if(compressBound == NULL){
			fclose(fp);
			fprintf(stderr, "Error: Cannot load compressBound()\nExiting...\n");
			exit(1);
		}
		compress = dlsym(lib, "compress");
		if(compress == NULL){
			fclose(fp);
			fprintf(stderr, "Error: Cannot load compress()\nExiting...\n");
			exit(1);
		}
		// to get the size of the file
		fseek(fp, 0, SEEK_END);
		uncps_size = ftell(fp);
		fseek(fp, 0, SEEK_SET);
		// allocate buffer for the file and read the file
		char* uncps_buffer = malloc(uncps_size);
		fread(uncps_buffer, uncps_size, 1, fp);
		// compress and error handle
		cps_size = compressBound(uncps_size);
		char* cps_buffer = malloc(cps_size);
		int cps_check = compress(cps_buffer, &cps_size, uncps_buffer, uncps_size);
		if(cps_check < 0){
			free(uncps_buffer);
			free(cps_buffer);
			fclose(fp);
			fprintf(stderr, "Error: Cannot compress the file\nExiting...\n");
			exit(1);
		}
		// fwrite to stdout
		fwrite(&uncps_size, sizeof(uncps_size), 1, stdout);
		fwrite(&cps_size, sizeof(cps_size), 1, stdout);
		fwrite(cps_buffer, cps_size, 1, stdout);
		// free the buffers and close the file
		free(uncps_buffer);
		free(cps_buffer);
		fclose(fp);
	}
	// -d mode
	else if(strcmp(argv[1], "-d") == 0){
		// load the uncps func
		uncompress = dlsym(lib, "uncompress");
		if(uncompress == NULL) {
			fclose(fp);
			fprintf(stderr, "Error: Cannot load uncompress()\nExiting...\n");
			exit(1);
		}
		// fread the sizes
		fread(&uncps_size, sizeof(uncps_size), 1, fp);
		fread(&cps_size, sizeof(cps_size), 1, fp);
		// allocate buffer for cps file and read the file
		char* cps_buffer = malloc(cps_size);
		fread(cps_buffer, cps_size, 1, fp);
		// allocate buffer for uncps
		char* uncps_buffer = malloc(uncps_size);
		// uncompress and error handle
		int uncps_check = uncompress(uncps_buffer, &uncps_size, cps_buffer, cps_size);
		if(uncps_check < 0) {
			free(uncps_buffer);
			free(cps_buffer);
			fclose(fp);
			fprintf(stderr, "Error: Cannot uncompress the file\nExiting...\n");
			exit(1);		
		}
		// fwrite to stdout
		fwrite(uncps_buffer, uncps_size, 1, stdout);
		// free buffers and close the file
		free(cps_buffer);
		free(uncps_buffer);
		fclose(fp);
	}
	// wrong cmd handler
	else{
		fclose(fp);
		fprintf(stderr, "Error: Wrong cmd (-c/-d only)\nExiting...\n");
		exit(1);
	}
	return 0;
}