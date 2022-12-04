#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
    char filename[] = "Input_Day04.txt";
    FILE *input = fopen(filename, "r");
    int fo = 0,po = 0;

    if (input != NULL) {
        char line[128];
        while(fgets (line, sizeof(line), input) != NULL) {
            char p1[64],p2[64],*temp;
            int s[4];
            strcpy(p1,strtok(line, ","));
            strcpy(p2,strtok(NULL, ","));

            s[0] = strtol(strtok(p1,"-"),&temp,10);
            s[1] = strtol(strtok(NULL,"-"),&temp,10);
            s[2] = strtol(strtok(p2,"-"),&temp,10);
            s[3] = strtol(strtok(NULL,"-"),&temp,10);
            
            if( (s[0] <= s[2] && s[1] >= s[3]) || (s[2] <= s[0] && s[3] >= s[1]) ){
                fo++;
            }
            if( (s[0] >= s[2] && s[0] <= s[3]) || (s[2] >= s[0] && s[2] <= s[1]) ) {
                po++;
            }
        }
    }
    fprintf(stdout,"%d %d\n",fo, po);
    return 0;
}

