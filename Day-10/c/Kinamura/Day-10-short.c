#include <stdio.h>
#include <stdlib.h>

void draw(int position, int cRegister, int cycle) {
    if(cycle % 40 == 0) {fprintf(stdout, "\n");}
    (position < cRegister +2 && position > cRegister -2) ? fprintf(stdout, "#"):fprintf(stdout, ".");
}

int checkCycle(int sStrength, int cycle, int cRegister) {
    if( ((cycle + 21) % 40) == 0) {sStrength = sStrength + ((cycle+1) * cRegister);}
        draw((cycle % 40), cRegister, cycle);
    return sStrength;
}

int main() {
    int sStrength = 0, cycle = 0, cRegister = 1, value;
    FILE *input = fopen("Input_Day10.txt", "r");
    if( input != NULL) {
        char line[16], instr[8], *temp;
        while( fgets(line, sizeof(line), input) != NULL) {
            strcpy(instr, strtok(line, " "));
            if( instr[0] == 'n') {
                sStrength = checkCycle(sStrength, cycle, cRegister);
                cycle ++;
            } else {
                value = strtol(strtok(NULL, " "), &temp, 10);
                sStrength = checkCycle(sStrength, cycle, cRegister);
                cycle++;
                sStrength = checkCycle(sStrength, cycle, cRegister);
                cycle++;
                cRegister += value;
            }
        }
    }
    fprintf(stdout, "\n%d\n", sStrength);
    return 0;
}
