#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int cycle = 0;
int sStrength = 0;
int cRegister = 1;

void draw(int position) {
    if(cycle % 40 == 0) {
        fprintf(stdout, "\n");
    }
    if(position < cRegister +2 && position > cRegister -2) {
        fprintf(stdout, "#");
    } else {
        fprintf(stdout, ".");
    }
}

void checkCycle() {
    if( ((cycle + 21) % 40) == 0) { 
        sStrength = sStrength + ((cycle+1) * cRegister);
    }
    draw((cycle) % 40);
}

int main() {
    FILE *input = fopen("Input_Day10.txt", "r");
    if( input != NULL) {
        char line[16], instr[8], *temp;
        int value;
        while( fgets(line, sizeof(line), input) != NULL) {
            strcpy(instr, strtok(line, " "));
            if( instr[0] == 'n') {
                checkCycle();
                cycle ++;
            } else {
                value = strtol(strtok(NULL, " "), &temp, 10);
                checkCycle();
                cycle++;
                checkCycle();
                cycle++;
                cRegister += value;
            }
        }
    }
    fprintf(stdout, "\n%d\n", sStrength);
    return 0;
}
