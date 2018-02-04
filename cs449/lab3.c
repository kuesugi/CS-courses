#include <stdio.h>
#include <stdlib.h>

typedef struct Node
{
	struct Node* next;
	int value;
} Node;

Node* create_node(int value)
{
	Node* createdNode = NULL;			
	createdNode = malloc(sizeof(createdNode));

	createdNode->value = value;
	createdNode->next = NULL;

	return createdNode;	
}	

void print_list(Node* n)
{
	while(n != NULL){
		printf("%d\n", n->value);
		n = n->next;
	}
}

void free_list(Node* n)
{
	if (n != NULL){
		free(n);
		n = n->next;
		while(n!= NULL){
			free(n);
			n = n->next;
		}
	}
}

int main()
{
	Node* node = NULL;
	Node* node_array[100];
	char number[100];
	int input_number = 0;
	int node_index = 0;

	printf("Enter an integer: ");
	fgets(number, sizeof(number), stdin);
	sscanf(number, "%d", &input_number);

	// THE LOOP
	// IF == -1, BREAK TO THE END OF THIS LOOP
	while (input_number != -1){
		node = create_node(input_number);
		if (node_index == 0)
			node_array[0] = node;
		else {
			node_array[node_index] = node;
			node_array[node_index]->next = node_array[node_index-1];
		}
		node_index++;
		printf("Enter an integer: ");
		fgets(number, sizeof(number), stdin);
		sscanf(number, "%d", &input_number);
	}

	print_list(node);
	free_list(node);
	return 0;
}
