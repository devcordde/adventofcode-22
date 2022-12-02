#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
    char filename[] = "Input_Day2.txt";
    FILE *input = fopen(filename, "r");
    int points1 = 0, points2 = 0;

    if (input != NULL) {
        while(!feof(input)){
            char a,b;
            int i = fscanf(input, "%c %c", &a, &b);

            switch(a) {
                case 'A':
                switch (b) {
                    case 'X': points1 += 4; points2 +=3; break;
                    case 'Y': points1 += 8; points2 +=4; break;
                    case 'Z': points1 += 3; points2 +=8; break;
                }
                break;
                case 'B':
                switch (b) {
                    case 'X': points1 +=1; points2 +=1; break;
                    case 'Y': points1 +=5; points2 +=5; break;
                    case 'Z': points1 +=9; points2 +=9; break;
                }
                break;
                case 'C':
                switch (b) {
                    case 'X': points1 +=7; points2 +=2; break;
                    case 'Y': points1 +=2; points2 +=6; break;
                    case 'Z': points1 +=6; points2 +=7; break;
                }
                break;
            }

            if(a != 'A' || a != 'B' || a != 'C') {
                a = b;
                b = ' ';
            }
            if(b != 'X' || b != 'Y' || b != 'Z') {
                    int j = fscanf(input, "%c",&b);
            }
        }
        fprintf(stdout,"%d %d",points1, points2);
        fclose(input);
    }
    return 0;
}