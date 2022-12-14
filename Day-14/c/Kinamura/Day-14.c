#include <stdio.h>
#include <string.h>
#include <stdlib.h>

char cave[1000][1000];

void drawRocks(int x1, int y1, int x2, int y2) {
    int drawY = 0, from, to;
    if( x1 == x2) { 
        drawY = 1;
        if( y1 - y2 > 0) { from = y2; to = y1;
        } else { from = y1; to = y2; }
    } else {
        if( x1 - x2 > 0) { from = x2; to = x1;
        } else { from = x1; to = x2; }
    }

    for( int i = from; i <= to; i++) {
        if( drawY == 1) { cave[x1][i] = 'X';
        } else { cave[i][y1] = 'X'; }
    }
}

int simulateSand() {
    int x= 500;
    for( int y = 1; y < 1001; y++) {
        if(y < 0 || x > 800) { return 1; }
        if( cave[x][y] != '.' && cave[x-1][y] != '.' && cave[x+1][y] != '.' && cave[x][y-1] == '+') { cave[x][y-1] = 'O'; return 1; }
        if( cave[x][y] != '.' && cave[x-1][y] != '.' && cave[x+1][y] != '.') { cave[x][y-1] = 'O'; return 0; }
        if( cave[x][y] == '.') { continue; } 
        else if( cave[x-1][y] == '.') { x--; continue; }
        else if( cave[x+1][y] == '.') { x++; continue; }
    }
    return 1;
}

int eval() {
    int cycle = 0;
    while(1) {
        cycle++;
        int hasFinished = simulateSand();
        if(hasFinished == 1) {
            break;
        }
    }
    return cycle -1;
}

int main () {
    FILE *input = fopen( "Input_Day14.txt", "r");

    for( int i = 0; i<1000; i++) {
        for( int j = 0; j<1000; j++) {
            cave[j][i] = '.';
        }
    }
    cave[500][0] = '+';

    if( input != NULL ) {
        char line[512], coordinatesRaw[64][512];
        int coordinates[64][2], minY = 0, cycles;
        while( fgets(line, sizeof(line), input) != NULL) {
            char* token; 
            char* rest = line;
            int coordCount = 0;
            while (( token = strtok_r(rest, " -> ", &rest))) {
                strcpy(coordinatesRaw[coordCount],token);
                coordCount++;
            }
            for( int i = 0; i < coordCount; i++) {
                char* temp;
                coordinates[i][0] = strtol(strtok(coordinatesRaw[i],","), &temp, 10);
                coordinates[i][1] = strtol(strtok(NULL,","), &temp, 10);
                if(coordinates[i][1] > minY) { minY = coordinates[i][1]; }
            }
            for( int i = 0; i < coordCount -1; i++) {
                drawRocks(coordinates[i][0], coordinates[i][1], coordinates[i+1][0], coordinates[i+1][1]);
            }
        }

        cycles = eval();
        fprintf(stdout, "%d\n", cycles); 
        drawRocks(0,minY+2,1000,minY+2);
        cycles += eval();   
        fprintf(stdout, "%d\n", cycles+1);     
    }
    return 0;
}
