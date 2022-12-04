#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int * sections(char line[127]) {
    char p1[127],p2[127],*temp;
    static int results[4];

    char *ptr = strtok(line, ",");
    strcpy(p1,ptr);
    ptr = strtok(NULL, ",");
    strcpy(p2,ptr);

    ptr = strtok(p1,"-");
    results[0] = strtol(ptr,&temp,10);
    ptr = strtok(NULL,"-");
    results[1] = strtol(ptr,&temp,10);

    ptr = strtok(p2,"-");
    results[2] = strtol(ptr,&temp,10);
    ptr = strtok(NULL,"-");
    results[3] = strtol(ptr,&temp,10);

    return results;
}

int main() {
    char filename[] = "Input_Day04.txt";
    FILE *input = fopen(filename, "r");
    int fo = 0,po = 0;

    if (input != NULL) {
        int *s;
        char line[127];
        while(fgets (line, sizeof(line), input) != NULL) {
            int a1, a2, b1, b2;
            s = sections(line);
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
