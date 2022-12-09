#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

int forest[100][100];

bool checkRow(int row, int column, int size) {
    int highest = 0;
    for( int i = row -1; i >= 0; i--) {
        if(forest[i][column] > highest) {
            highest = forest[i][column];
        }
    }
    if(highest < forest[row][column]) {
        return true;
    }

    highest = 0;
    for( int i = row + 1; i<= size; i++) {                                                                                                                                                    
        if(forest[i][column] > highest) {
            highest = forest[i][column];
        }
    }
    if(highest < forest[row][column]) {
        return true;
    }

    highest = 0;
    for( int i = column - 1; i >= 0; i--) {
        if( forest[row][i] > highest) {
            highest = forest[row][i];
        }
    }
    if( highest < forest[row][column] ) {
            return true;
    }

    highest = 0;
    for( int i = column + 1; i <= size; i++) {
        if( forest[row][i] > highest) {
            highest = forest[row][i];
        }
    }
    if( highest < forest[row][column]) {
        return true;
    }
    return false;
}

int scenicValue( int row, int column, int size) {
    int scenic = 0, scenicTemp = 0;
    for( int i =  column +1; i <= size; i++) {
        if (i == size) {
            scenicTemp++;
            break;
        } else if((forest[row][i] < forest[row][column])) {
            scenicTemp++;
        } else if( forest[row][i] >= forest[row][column]) {
            scenicTemp++;
            break;
        }
    }

    scenic = scenicTemp;
    scenicTemp = 0;

    for( int i =  column -1; i >= 0; i--) {
        if (i == 0) {
            scenicTemp++;
            break;
        } else if((forest[row][i] < forest[row][column])) {
            scenicTemp++;
        } else if( forest[row][i] >= forest[row][column]) {
            scenicTemp++;
            break;
        }
    }

    scenic *= scenicTemp;
    scenicTemp = 0;

    for( int i =  row +1; i <= size; i++) {
        if (i == size) {
            scenicTemp++;
            break;
        } else if((forest[i][column] < forest[row][column])) {
            scenicTemp++;
        } else if( forest[i][column] >= forest[row][column]) {
            scenicTemp++;
            break;
        }
    }

    scenic *= scenicTemp;
    scenicTemp = 0;

    for( int i =  row -1; i >= 0; i--) {
        if (i == 0) {
            scenicTemp++;
            break;
        } else if((forest[i][column] < forest[row][column])) {
            scenicTemp++;
        } else if( forest[i][column] >= forest[row][column]) {
            scenicTemp++;
            break;
        }
    }
    scenic *= scenicTemp;

    return scenic;
}

int main() {
    FILE *input = fopen("Input_Day08.txt", "r");
    int visibleTrees, row = 0, scenicMax = 0, size = 0;
    if(input != NULL) {
        char line[128];
        while( fgets(line, sizeof(line), input) != NULL) {
            for( int i = 0; i < sizeof(line); i++) {
                if(line[i] == '\n') {
                    row++;
                    break;
                }
                forest[row][i] = (int)line[i] -48;
                visibleTrees = 2*(i+1) + 2*(i-1);
                size = row;
            }
        }
    }

    int scenicTemp = 0;
    for( int i = 1; i < size; i++){
        for( int j = 1; j < size; j++) {
            bool isVisible = false;
            isVisible = checkRow(i,j, size);
            if( isVisible == true) {
                visibleTrees++;
            }
            scenicTemp = scenicValue(i,j,size);
            if(scenicTemp > scenicMax) {
                scenicMax = scenicTemp;
            }
        }
    }
    
    fprintf(stdout, "%d %d", visibleTrees, scenicMax);
    return 0;
}
