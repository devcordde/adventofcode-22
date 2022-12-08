#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int compare ( const void * a, const void * b) {
    return ( *(int*)a - *(int*)b );
}

int main() {
    FILE *input = fopen("Input_Day07.txt", "r");
    char line[256];
    int folderCount = -1, parentIndex[200], currentIndex = 0, maxSize = 100000;
    long folderSize[200] = {0}, sub100kSum = 0, delDirSize, driveTarget = 40000000;
    if(input != NULL) {
        while(fgets(line, sizeof(line), input) != NULL) {
            switch(line[0]) {
                 case '$': {
                    if( line[2] == 'c' && line[5] == '.') {
                        if(folderSize[currentIndex] <= maxSize) {
                            sub100kSum += folderSize[currentIndex];
                        }
                        folderSize[parentIndex[currentIndex]] += folderSize[currentIndex];
                        currentIndex = parentIndex[currentIndex];
                    } else if ( line[2] == 'c' && line[5] != '.') {
                        folderCount++;
                        parentIndex[folderCount] = currentIndex;
                        currentIndex = folderCount;
                    }
                    break;
                 }
                 case 'd': break;
                 default: {
                    char *temp;
                    int size = strtol(strtok(line," "),&temp,10);
                    folderSize[currentIndex] += size;
                 } break;
            }
        }
    }

    qsort(folderSize, sizeof(folderSize)/sizeof(long), sizeof(long),compare);
    for( int i = 0; i < 200; i++) {
        if(folderSize[199] - folderSize[i] <= driveTarget) {
            delDirSize = folderSize[i];
            break;
       }
    }
    
    fprintf(stdout, "%d %ld\n", sub100kSum, delDirSize);
    return 0;
}
