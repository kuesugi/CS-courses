#include <stdio.h>
#include <string.h>
#include <ctype.h>

// prints a message, reads a line of input, and chops off the newline.
// call it like
//    read_line("Type something in: ", buffer, sizeof(buffer));
void read_line(const char* message, char* buffer, int length)
{
	printf(message);
	fgets(buffer, length, stdin);
	if(strlen(buffer) != 0)
		buffer[strlen(buffer) - 1] = 0;
}

// sees if two strings are the same, ignoring case.
// call it like
//    if(streq_nocase(one, two))
int streq_nocase(const char* a, const char* b)
{
	for(; *a && *b; a++, b++)
		if(tolower(*a) != tolower(*b))
			return 0;

	return *a == 0 && *b == 0;
}

double weight_conversion(int weight_earth, const char* planet_name)
{
	double weight_on_other_planet;

	if(streq_nocase(planet_name, "mercury") == 1){
		return weight_on_other_planet = weight_earth * 0.38;
	}
	if(streq_nocase(planet_name, "venus") == 1){
		return weight_on_other_planet = weight_earth * 0.91;
	}
	if(streq_nocase(planet_name, "mars") == 1){
		return weight_on_other_planet = weight_earth * 0.38;
	}
	if(streq_nocase(planet_name, "jupiter") == 1){
		return weight_on_other_planet = weight_earth * 2.54;
	}
	if(streq_nocase(planet_name, "saturn") == 1){
		return weight_on_other_planet = weight_earth * 1.08;
	}
	if(streq_nocase(planet_name, "uranus") == 1){
		return weight_on_other_planet = weight_earth * 0.91;
	}
	if(streq_nocase(planet_name, "neptune") == 1){
		return weight_on_other_planet = weight_earth * 1.19;
	}
	else{
		return weight_on_other_planet = -1;
	}
}

int main()
{
	// initialize variables and char arrays
	int weight;
	double weight_converted;
	char buffer[100];
	char planet[100];

	// ask how much the user weigh
	read_line("How much do you weigh? ", buffer, sizeof(buffer));
	sscanf(buffer, "%d", &weight);

	// infinitely ask which planet and convert the number
	while(1){
		read_line("Which planet you wanna go to? ('exit' to EXIT) ", planet, sizeof(planet));
		// break if input "exit"
		if (streq_nocase(planet, "exit") == 1){
			break;
		}
		// dumb answer: earth
		if (streq_nocase(planet, "earth") == 1){
			printf("That's a dumb answer.\n");
		}
		
		else{
			weight_converted = weight_conversion(weight, planet);
			// print "not a planet" if given an invalid input  
			if (weight_converted == -1){
				printf("That's not a planet.\n");
			}
			// print converted weight number
			else{
				printf("You'd weigh %.2f there.\n", weight_converted);
			}
		}
	}
	return 0;
}



