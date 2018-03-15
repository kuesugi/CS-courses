#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>

// final version

// to check if a character is a printable ASCII character
// char range between [32, 126] = printable
int printable(char chara);

int main(int argc, char* argv[]){
	int print_length = 0;
	FILE* b_file = fopen(argv[1], "rb"); // b_file for "binary_file"
	char character;

	// exit if the file doesn't exit
	if (b_file == NULL){
		printf("Error: Cannot find this file.\n\tExiting...");
		exit(1);
	}

	// while not end of the file
	while (!feof(b_file)){
		fread(&character, sizeof(character), 1, b_file);
		// to get printable chars until encounter an invalid one
		while (printable(character)){
			fread(&character, sizeof(character), 1, b_file);
			print_length++;
		}
		// if the length of the sequence is >= 4
		// then print the sequence on its OWN LINE
		if (print_length >= 4){
			char* sequence = malloc((print_length+1)*sizeof(character));
			sequence[print_length] = '\0'; // zero-terminated
			// step back to the printable sequence, reread, and
			// store it for printing
			fseek(b_file, ftell(b_file)-print_length-1, 0);
			for (int i = 0; i < print_length; i++){
				fread(&character, sizeof(character), 1, b_file);
				sequence[i] = character;
			}
			printf("%s\n", sequence);
			print_length = 0; free(sequence);
		}
		else
			// length < 4, so find the next printable string
			// with length >= 4
			print_length = 0;
	}
	// close file stream
	fclose(b_file);
	return 0;
}

int printable(char chara){
	if (chara > 31 && chara < 127)
		return 1;
	else return 0;
}
