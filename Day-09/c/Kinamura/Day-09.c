#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

const int N = 10; //change to 2 for first part
char gorge[1001][1001];
int knot[N][2];

int adjustTailStep(int delta) {
	switch(delta) {
		case -2: return -1;
		case 2: return 1;
	}
	return 0;
}

void checkTail(int n) {
	if(knot[n-1][0] == knot[n][0] && knot[n-1][1] == knot[n][1]) {
		return; 
	} else if (abs(knot[n-1][0] - knot[n][0]) < 2 && abs(knot[n-1][1] - knot[n][1]) < 2) {
		return;
	} else if (abs(knot[n-1][0] - knot[n][0]) == 2 && abs(knot[n-1][1] - knot[n][1]) == 2) {
		knot[n][0] = knot[n-1][0] - adjustTailStep(knot[n-1][0] - knot[n][0]); 
		knot[n][1] = knot[n-1][1] - adjustTailStep(knot[n-1][1] - knot[n][1]);
		return;
	} else if (abs(knot[n-1][0] - knot[n][0]) == 2 && abs(knot[n-1][1] - knot[n][1]) == 1) {
		knot[n][1] = knot[n-1][1];
		knot[n][0] = knot[n-1][0] - adjustTailStep(knot[n-1][0] - knot[n][0]);
		return;
	} else if (abs(knot[n-1][0] - knot[n][0]) == 1 && abs(knot[n-1][1] - knot[n][1]) ==2) {
		knot[n][0] = knot[n-1][0];
		knot[n][1] = knot[n-1][1] - adjustTailStep(knot[n-1][1] - knot[n][1]);
		return;
	} else if (abs(knot[n-1][0] - knot[n][0]) == 2) {
		knot[n][0] = knot[n-1][0] - adjustTailStep(knot[n-1][0] - knot[n][0]);
		return;
	} else if (abs(knot[n-1][1] - knot[n][1]) == 2) {
		knot[n][1] = knot[n-1][1] - adjustTailStep(knot[n-1][1] - knot[n][1]);
		return;
	}
}

void moveHead(int distance, char direction) {
	for(int i = 0; i<distance; i++) {
		switch(direction) {
			case 'U': knot[0][1]--; break;
			case 'D': knot[0][1]++; break;
			case 'L': knot[0][0]--; break;
			case 'R': knot[0][0]++; break;
		}
		for( int j = 1; j<N; j++) {
			checkTail(j);
		}
		gorge[knot[N-1][0]][knot[N-1][1]] = 'X';
	}
}

int main() {
	int positions = 0;
        FILE *input = fopen("Input_Day08.txt", "r");
	gorge[501][501] = 'X';

	for(int i = 0; i < N; i++) {
		knot[i][0] = 501;
		knot[i][1] = 501;
	}

        if(input != NULL) {
                char line[20];
                while(fgets(line, sizeof(line), input) != NULL) {
                        char direction[10], *temp, *ptr;
                        int distance = 0;
			strcpy(direction,strtok(line," "));
                        distance = strtol(strtok(NULL," "),&temp,10);
			moveHead(distance, direction[0]);
                }
        }
	for(int i = 0; i < 1000; i++) {
		for(int j = 0; j < 1000; j++) {
			if(gorge[i][j] == 'X') {
				positions++; 
			}
		}
	}
	fprintf(stdout, "%d\n", positions);
}
