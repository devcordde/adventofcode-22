#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
	char line[128];
	int items[8][100], operator[8], opValue[8], test[8], trueMonkey[8], falseMonkey[8], itemCount[8] = {0}, monkeyCount = 0, inspections[8] = {0}, monkeys = 8;
	FILE *input = fopen("Input_Day11.txt", "r");
	if( input != NULL) {
		while( fgets(line, sizeof(line), input) != NULL) {
			if(line[0] == '\n') {monkeyCount++;}
			if(line[2] == 'S') {
				char *temp, *rest, *token;
				temp = strtok(line, ":");
				temp = strtok(NULL, ":");
				rest = temp;
				while((token = strtok_r(rest, ",", &rest)) != NULL) {
					items[monkeyCount][itemCount[monkeyCount]] = strtol(token,&temp,10);
					itemCount[monkeyCount]++;
				}
			}
			if(line[2] == 'O') {
				switch(line[23]) {
					case '+': operator[monkeyCount] = 0; break;
					case '*': operator[monkeyCount] = 1; break;
				}
				if(line[25] == 'o') {
					opValue[monkeyCount] = -1;
				} else {
					char *temp;
					temp = strtok(line, ":");
					for( int i = 1; i <= 4; i++) {temp = strtok(NULL, " ");}
					opValue[monkeyCount] = strtol(strtok(NULL, " "), &temp, 10);
				}
			}
			if(line[2] == 'T') {
				char *temp, *token;
				token = strtok(line, "y");
				test[monkeyCount] = strtol(strtok(NULL, " "), &temp, 10);
			}
			if(line[4] == 'I' && line[7] == 't') {
				char *temp, *token;
				token = strtok(line, "y");
				trueMonkey[monkeyCount] = strtol(strtok(NULL, " "), &temp, 10);
			}
			if(line[4] == 'I' && line[7] == 'f') {
				char *temp, *token;
				token = strtok(line, "y");
				falseMonkey[monkeyCount] = strtol(strtok(NULL, " "), &temp, 10);
			}
		}
	}
	
	for( int round = 1; round <= 20; round++) {
		for( int i = 0; i< monkeys; i++) {
			int count = itemCount[i];
			for( int j = 0; j < count; j++) {
				int tempIndex;
				if(opValue[i] == -1) {
					items[i][j] = (operator[i] == 0) ? (items[i][j] += items[i][j]): (items[i][j] *= items[i][j]);
				} else {
					items[i][j] = (operator[i] == 0) ? (items[i][j] += opValue[i]): (items[i][j] *= opValue[i]);
				}				
				items[i][j] /= 3;
				tempIndex = (items[i][j] % test[i] == 0) ? trueMonkey[i]:falseMonkey[i];
				itemCount[tempIndex]++;
				items[tempIndex][itemCount[tempIndex]-1] = items[i][j];
				itemCount[i]--;
				inspections[i]++;
			}
		}
	}
	int monkeyBusiness = 1;
	for( int chases = 0; chases < 2; chases++) {
		int maxCompare = 0, maxIndex;
		for( int i = 0; i<monkeys; i++) {
			if(maxCompare < inspections[i]) {
				maxCompare = inspections[i];
				maxIndex = i;
			}
		}
		monkeyBusiness *= inspections[maxIndex];
		inspections[maxIndex] = 0;
	}
	fprintf(stdout, "Business: %d\n", monkeyBusiness);
}
