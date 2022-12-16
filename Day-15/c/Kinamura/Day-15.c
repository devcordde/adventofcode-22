#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>
#include <limits.h>

int XonY[32][2], sensor[32][3],distances[32], counter = 0;
const int row = 2000000;

int checkOverlap(int x, int y) {
    if(x < 0 || y < 0 || x > 4000000 || y > 4000000) { return 1; }
    for( int i = 0; i < counter; i++) {
        if(abs(x-sensor[i][0]) + abs(y-sensor[i][1]) <= sensor[i][2]) {
            return 1;
        }
    }
    return 0;
}

void part2() {
    for( int i = 0; i < counter; i++) {
        int dist = sensor[i][2];
        int x = sensor[i][0] - (dist + 1);
        int y = sensor[i][1];
        for( int j = 0; j <= sensor[i][2]; j++) {          
            if(checkOverlap(x,y) == 0) {
                fprintf(stdout, "%lld\n", ((long long)x*4000000) + y );
                return;
            }
            x++;
            y++;
        }
        dist = sensor[i][2];
        x = sensor[i][0] + (dist + 1);
        y = sensor[i][1];
        for( int j = 0; j <= sensor[i][2]; j++) {           
            if(checkOverlap(x,y) == 0) {
                fprintf(stdout, "%lld\n", ((long long)x*4000000) + y );
                return;
            }
            x--;
            y--;
        }
        dist = sensor[i][2];
        x = sensor[i][0];
        y = sensor[i][1] - (dist + 1);
        for( int j = 0; j <= sensor[i][2]; j++) {            
            if(checkOverlap(x,y) == 0) {
                fprintf(stdout, "%lld\n", ((long long)x*4000000) + y );
                return;
            }
            x--;
            y++;
        }   
        dist = sensor[i][2];
        x = sensor[i][0];
        y = sensor[i][1] + (dist + 1);
        for( int j = 0; j <= sensor[i][2]; j++) {
            if(checkOverlap(x,y) == 0) {
                fprintf(stdout, "%lld\n", ((long long)x*4000000) + y );
                return;
            }
            x++;
            y--;
        }
    }
    return;
}

int part1(int count) {
    int sum = -1;
    for( int i = 0; i < count; i++) {
        if(XonY[i][0] != INT_MIN) {
            sum += abs(XonY[i][0] - XonY[i][1]);
        }
    }
    return sum;
}

void adjustRow(int count) {
    for( int i = 0; i < count; i++ ) {
        for( int j = i+1; j < count; j++ ) {
            if( XonY[i][0] == INT_MIN || XonY[j][0] == INT_MIN) { continue; }
            if( XonY[i][0] <= XonY[j][0] && XonY[i][1] >= XonY[j][1]) { XonY[j][0] = INT_MIN; continue; }
            if( XonY[j][0] <= XonY[i][0] && XonY[j][1] >= XonY[i][1]) { XonY[i][0] = INT_MIN; continue; }
            if( XonY[i][0] > XonY[j][0] && XonY[i][0] < XonY[j][1]) { XonY[i][0] = XonY[j][1]-1; continue; }
            if( XonY[j][0] > XonY[i][0] && XonY[j][0] < XonY[i][1]) { XonY[j][0] = XonY[i][1]-1; continue; }
        }
    }
    return;
}

int calculateDistance(int x1, int y1, int x2, int y2, int count) {
    int hitLine = 0;
    int distance = abs(x1-x2) + abs(y1-y2);
    if(abs(y1 - row) < distance) {
        int overshoot = distance - abs(y1 - row);
        XonY[count][0] = x1 - overshoot;
        XonY[count][1] = x1 + overshoot;
        hitLine = 1;
    }
    return hitLine;
}

int main() {
    FILE *input = fopen("Input_Day-15.txt", "r");
    if( input != NULL ) {
        int sensorX, sensorY, beaconX, beaconY, count = 0, beaconsOnLine[25], beacons = 0;
        char line[128];
        while( fgets( line, sizeof(line), input) != NULL ) {
            char *temp, *rest = line;
            strtok_r(rest, "=", &rest );
            sensorX = strtol(strtok_r(rest, ",", &rest ), &temp, 10);
            strtok_r(rest, "=", &rest );
            sensorY = strtol(strtok_r(rest, ":", &rest ), &temp, 10);
            strtok_r(rest, "=", &rest );
            beaconX = strtol(strtok_r(rest, ",", &rest ), &temp, 10);
            strtok_r(rest, "=", &rest );
            beaconY = strtol(strtok_r(rest, ":", &rest ), &temp, 10);
            sensor[counter][0] = sensorX;
            sensor[counter][1] = sensorY;
            sensor[counter][2] = abs(sensorX-beaconX) + abs(sensorY-beaconY);
            counter++;
            count = ( calculateDistance(sensorX, sensorY, beaconX, beaconY, count) == 1) ? count +1 : count;
        }
        
        adjustRow(count);
        fprintf(stdout, "%d\n", part1(count));
        part2();
    }  
    return 0;
}
