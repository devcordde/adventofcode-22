#include <stdio.h>
#include <limits.h>

int grid[66][66],targetX, targetY;
int dist[66][66];

void findDistance(int x, int y, int distance) {
    if( x < 0 || y < 0 || x > 66 || y > 66) {return;}
    if(dist[x][y] > distance) {
        dist[x][y] = distance;
        distance++;   
        if ((grid[x-1][y] - grid[x][y]) <= 1) { findDistance(x-1,y,distance); }
        if ((grid[x][y-1] - grid[x][y]) <= 1) { findDistance(x,y-1,distance); }
        if ((grid[x+1][y] - grid[x][y]) <= 1) { findDistance(x+1,y,distance); }
        if ((grid[x][y+1] - grid[x][y]) <= 1) { findDistance(x,y+1,distance); }
    }
    return;
}

int main() {
    FILE *input = fopen( "Input_Day12.txt", "r");
    int row = 0, startX, startY, gridSize = 66;
    if(input != NULL) {
        char line[80];
        while( fgets(line, sizeof(line), input) != NULL) {
            for( int i = 0; i<gridSize; i++)  { 
                if( line[i] == 'S') { startX = i; startY = row; }
                if( line[i] == 'E') { targetX = i; targetY = row; }
                grid[row][i] = (line[i] == 'S') ? 0 : (line[i] == 'E') ? 25 : line[i] - 97;;
            }
            row++;
        }
    }
    for( int i = 0; i<gridSize; i++) { 
        for( int j = 0; j<gridSize; j++) { dist[i][j] = INT_MAX; }
    }

    findDistance(startY, startX, 0);
    fprintf(stdout,"%d\n", dist[targetY][targetX]);

    int lowestDist = INT_MAX;
    for(int i = 0; i<67; i++) {
        for( int j = 0; j<67; j++) {
            if( grid[j][i] == 0) {
                findDistance(j,i,0);
                if(dist[targetY][targetX] < lowestDist) {
                    lowestDist = dist[targetY][targetX];
                }
            }
        }
    }
    fprintf(stdout, "%d", lowestDist);
    return 0;
}
