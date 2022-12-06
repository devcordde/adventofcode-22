#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char stack[9][72];
int top[10] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

void push(char element, int stack);
char pop(int stack);

int main() {
    char filename[] = "Input_Day05.txt";
    FILE *input = fopen(filename, "r");
    int stacknumber = 0, stacklines = 0;
    if (input != NULL) {
        char line[128],*temp;
        char* token, rest;
        while(fgets (line, sizeof(line), input) != NULL) {
            stacklines++;
            if (line[1] == '1') {
                char* token = strtok(line, " ");
                while (token != NULL) {
                    token = strtok(NULL, "   ");
                    stacknumber++;
                }
                break;
            }
        }
    }

    rewind(input);

    if (input != NULL) {
        char line[128];
        int count = 0;
        while( (fgets( line, sizeof(line), input) != NULL) && count < stacklines-1) {
            count++;
            for (int i= 1; i<=stacklines; i++) {
                if(line[1+ ((i-1)*4)] != ' ') {
                    push(line[1+((i-1)*4)],i-1);
                } 
            }
        }

        for( int i = 0; i < stacknumber-1; i++) {
            int a=0;
            for( int j = 0; j <= top[i]; j++){
                stack[i][71-j] = stack[i][j]; 
                a++;              
            }
            for( int j = 0; j <= top[i]; j++) { 
                stack[i][j] = stack[i][71-a+1];
                a--; 
            }
        }
        fgets( line, sizeof(line), input);
    }

    if (input != NULL) {
        char line[128];
        while( fgets( line, sizeof(line), input) != NULL) {
            int a,b,c;
            char* rest;
            char *temp;
            a = strtol(strtok(line,"move "), &temp,10);
            b = strtol(strtok(NULL," from "), &temp,10);
            c = strtol(strtok(NULL," to "), &temp,10);

            char tempstack[72];
            for(int i = 0; i < a; i++) {
                top[10]++;
                tempstack[top[10]] = pop(b-1);
            }
            for(int i = 0; i < a; i++) {
                top[10]--;
                push(tempstack[top[10]+1],c-1);
            }
        }
    }
    for(int i = 0; i < 9; i++) {
        fprintf(stdout, "%c",stack[i][top[i]]);
    }

    scanf("%d");
    return 0; 
}

void push(char element, int stacknumber) {
    top[stacknumber]++;
    stack[stacknumber][top[stacknumber]] = element;
}

char pop(int stacknumber) {
    top[stacknumber]--;
    return stack[stacknumber][top[stacknumber]+1];
}
