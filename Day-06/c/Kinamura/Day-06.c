#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

char line[4096];

bool hasDoubles(int i, int length) {
    if( length == 1) {
        return true;
    } else{
        for(int j = i +1; j <= i + length; j++) {
            if( line[i] == line[j]) {
                return false;
            }
        }
        return hasDoubles(i+1, length-1);
    }
}

int chunk(int length) {
    int i,j, doubled;
    bool found;
    for (i = 0; i < 4096 - length; i++){
        if(length == 4){
            found = hasDoubles(i, length);
        } else {
            found = hasDoubles(i, length-1);
        }
        if(found == true) {
            return i+length;
        }
    } 
}

int main() {
    FILE *input = fopen("Input_Day06.txt", "r");
    if(input != NULL) {
            fgets(line, sizeof(line), input);
            fprintf(stdout, "%d %d\n", chunk(4), chunk(14));
    }
    scanf("%d");
    return 0;
}
