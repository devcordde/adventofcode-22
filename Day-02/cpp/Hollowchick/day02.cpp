#include <iostream>
#include <fstream>
using namespace std;

int main() {
    string line;
    int score {0}, score2{0};
    ifstream input ("input");
    while (input) {
        getline(input, line);
        if (line.empty()){
            break;
        }
        switch (line[2]) {
            case 'X':
                score += 1;
                if (line[0] == 'A') { score += 3; } // Rock - Rock
                else if (line[0] == 'B') { score += 0; } // Paper - Rock
                else if (line[0] == 'C') { score += 6; } // Scissors - Rock
                break;
            case 'Y': score += 2;
                if (line[0] == 'A') { score += 6; } // Rock - Paper
                else if (line[0] == 'B') { score += 3; } // Paper - Paper
                else if (line[0] == 'C') { score += 0; } // Scissors - Paper
                break;
            case 'Z': score += 3;
                if (line[0] == 'A') { score += 0; } // Rock - Scissors
                else if (line[0] == 'B') { score += 6; } // Paper - Scissors
                else if (line[0] == 'C') { score += 3; } // Scissors - Scissors
                break;
        }
        switch (line[0]) {
            case 'A':
                if (line[2] == 'X') { score2 += 3; } // Rock - Scissors
                else if (line[2] == 'Y') { score2 += 4; } // Rock - Rock
                else if (line[2] == 'Z') { score2 += 8; } // Rock - Paper
                break;
            case 'B':
                if (line[2] == 'X') { score2 += 1; } // Paper - Rock
                else if (line[2] == 'Y') { score2 += 5; } // Paper - Paper
                else if (line[2] == 'Z') { score2 += 9; } // Paper - Scissors
                break;
            case 'C':
                if (line[2] == 'X') { score2 += 2; } // Scissors - Paper
                else if (line[2] == 'Y') { score2 += 6; } // Scissors - Scissors
                else if (line[2] == 'Z') { score2 += 7; } // Scissors - Rock
                break;
        }
    }
    cout << score << endl;
    cout << score2 << endl;
    return 0;
}
