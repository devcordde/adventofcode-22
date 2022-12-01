#include <stdio.h>
#include <stdlib.h>

int main() {
    char filename[] = "Input_Day1.txt";
    FILE *input = fopen(filename, "r");
    int tempCalories = 0;
    int maxCalories[3] = {0,0,0};
    char* ptr;

    if (input != NULL) {
        char line[9];
        int lineValue;
        while(fgets (line, sizeof(line), input) != NULL) {
            lineValue = strtol(line, &ptr, 10);
            if(lineValue != 0){
                tempCalories += lineValue;
            } else {
                if (tempCalories > maxCalories[0]) {
                    maxCalories[2] = maxCalories[1];
                    maxCalories[1] = maxCalories[0];
                    maxCalories[0] = tempCalories;
                } else if (tempCalories > maxCalories[1]) {
                    maxCalories[2] = maxCalories[1];
                    maxCalories[1] = tempCalories;
                } else if (tempCalories > maxCalories[2]) {
                    maxCalories[2] = tempCalories;
                }
                tempCalories = 0;                               
            }
        }

        fprintf(stdout,"%d\n",maxCalories[0]);
        fprintf(stdout,"%d\n",maxCalories[0] + maxCalories[1] + maxCalories[2]);
        fclose(input);
    }
    return 0;
}