#include <stdio.h>
#include <stdlib.h>
#include <string.h>


int prioritizeItem(char item) {
    if ((int)item - 97 < 0) {
        return ((int)item -38);
    } else {
        return ((int)item -96);
    }
}

char doubled(char line[], int length) {
    for(int i = 0; i< (length/2); i++) {
        for(int j = length/2; j < length; j++) {
            if(line[i] == line[j]) {
                return line[i];
            }            
        }
    }
}

int main() {
    char filename[] = "Input_day03.txt";
    FILE *input = fopen(filename, "r");
    char* ptr;

    if (input != NULL) {
        int sumPriorites = 0, sumBadges = 0, n = 0;
        char line[127], line2[127], line3[127], badge;
        while(fgets (line, sizeof(line), input) != NULL) {
            int length, length2, length3;
            for(int i = 0; i<(sizeof(line)/sizeof(line[0])); i++) {
                if (line[i] == '\n') {
                    length = i;
                    break;
                }
            }

            if (n == 0) {
                strcpy(line2,line);
                length2 = length;
                n++;
            } else if (n == 1) {
                strcpy(line3, line);
                length3 = length;
                n++;
            } else if (n == 2) {
                n = 0;
                for(int i = 0; i < length2; i++) {
                    for(int j = 0; j < length3; j++) {
                        for(int k = 0; k < length; k++) {
                            if(line2[i] == line3[j] && line3[j] == line[k]) {
                                badge = line2[i];
                                break;
                            }
                        }            
                    }
                }
                sumBadges += prioritizeItem(badge);
            }

            char wrongItem = doubled(line,length);
            sumPriorites = sumPriorites + prioritizeItem(wrongItem);
        }
        
        fprintf(stdout,"%d %d",sumPriorites, sumBadges);
        fclose(input);
    }
    return 0;
}