// zhm16
#include <stdlib.h>
#include <time.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>

// functions
int random_range(int low_value, int high_value);
int streq_nocase(char* a, const char* b);
int choice_compare(int user_choice, int com_choice);
int user_choice_convert(char user_input[], char* com_input[]);
int game_over_choice(char choice[], int if_again);

int main(){
	srand((unsigned int)time(NULL));
	int result = 0;
	int com_choice = 0;	// computer's choice value
	int com_pts = 0;	// win number (points) of computer
	int user_choice = -1;	// user's choice value 
	int user_pts = 0;	// win number (points) of user
	int round_number = 1;	// which round now
	char user_input[20];	// contains input of user
	// computer's "input", for comparing with user's input
	char* com_input[] = {"rock", "paper", "scissors"}; 
	// to know whether want to continue
	char next_round[10];
	int if_again = 1;

	while(if_again){
		while(com_pts!=3 && user_pts!=3){
			printf("Round %d! What's your choice? ", round_number);
			scanf("%s", user_input);
			// while given a valid input
			while(user_choice == -1){
				user_choice = user_choice_convert(user_input, com_input);
				// if not, ask again until a valid one
				if (user_choice == -1){
					printf("Huh? What's your choice? ");
					scanf("%s", user_input);
					user_choice = user_choice_convert(user_input, com_input);
				}
			}
			// compare with com_value to set the winner
			com_choice = random_range(0,2);
			printf("The computer chooses ");
			printf("%s. ", com_input[com_choice]);
			result = choice_compare(user_choice, com_choice);
			if (result == -2 || result == 1){
				printf("You win this round! ");
				round_number++; user_pts++;
			}
			else if (result == -1 || result == 2){
				printf("You lose this round! ");
				round_number++; com_pts++;
			}
			else{
				printf("It's a tie! ");
				round_number++;
			}
			user_choice = -1;
			printf("You: %d computer: %d\n\n", user_pts, com_pts);
		}

		if (user_pts == 3)
			printf("You win the tournament! Play again? ");
		else
			printf("You lose the tournament. Play again? ");
		scanf("%s", next_round);

		if_again = game_over_choice(next_round, if_again);

		printf("\n");
		// reset!
		round_number = 0; com_pts = 0; user_pts = 0;
		com_choice = 0; user_choice = -1;
	}

	return 0;
}

// give a "random" number from the range of
// low_value to high_value
int random_range(int low_value, int high_value){
	return rand() % (high_value - low_value + 1) + low_value;
}

// for making input case insensitive
int streq_nocase(char* a, const char* b)
{
	for(; *a && *b; a++, b++)
		if(tolower(*a) != tolower(*b))
			return 0;

	return *a == 0 && *b == 0;
}

int user_choice_convert(char user_input[], char* com_input[]){
	// when user chooses rock (case insensitive)
	if(streq_nocase(user_input, com_input[0]) == 1)
		return 0;
	// when user chooses paper (case insensitive)
	else if(streq_nocase(user_input, com_input[1]) == 1)
		return 1;
	// when user chooses scissors (case insensitive)
	else if(streq_nocase(user_input, com_input[2]) == 1)
		return 2;
	else
		return -1;
}

int choice_compare(int user_choice, int com_choice){
	/*  rock - paper = -1, rock - scissors = -2
		paper - rock = 1, paper - scissors = -1
		scissors - rock = 2, scissors - paper = 1
		-1 = lose, -2 = win, 1 = win, 2 = lose
		0 = tie
	*/
	return (user_choice - com_choice);
}

int game_over_choice(char choice[], int if_again){
	if(streq_nocase(choice, "yes") == 1 || streq_nocase(choice, "y") == 1)
		return 1;
	else if(streq_nocase(choice, "no") == 1 || streq_nocase(choice, "n") == 1)
		return 0;	
	else
		return 0;
}
