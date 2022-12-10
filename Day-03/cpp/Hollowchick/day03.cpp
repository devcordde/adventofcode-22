#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>
using namespace std;

int score_return(char val){
	char scoringlow[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	char scoringhigh[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	for (int j = 1; j < 27; j++){
		if (val == scoringlow[j - 1]){
			return j;
		}
	}
	for (int j = 1; j < 27; j++){
		if (val == scoringhigh[j - 1]){
			return j + 26;
		}
	}
	return 0;
}

int main(void){
	string line, arr1, arr2, pt1, pt2, pt3, badges;
	int linelength{0}, score{0}, score2{0};
	ifstream input ("input");
	while (input){
		getline(input, line);
		linelength = line.size();
		arr1.append(line, 0, linelength / 2);
		arr2.append(line, linelength / 2, linelength);
		for (int i = 0; i < linelength / 2; i++) {
			for (int k = 0; k < linelength / 2; k++){
				if (arr1[k] != arr2[i]){
					continue;
				} else {
					score += score_return(arr1[k]);
					goto killLoop;
				}
			}
		}
	killLoop:
		arr1 = "";
		arr2 = "";
	}
	cout << score << endl;
	score = 0;
	ifstream input2 ("input");
	string::size_type starting = 0;
	while (input2){
		getline(input2, pt1);
		getline(input2, pt2);
		getline(input2, pt3);
		for (int i = 0; i < pt1.size(); i++){
			if ((pt2.find(pt1[i], starting) != pt2.npos) && pt3.find(pt1[i], starting) != pt3.npos) {
				badges += pt1[i];
			}
		}
		score += score_return(badges[0]);
		badges = "";
	}
	cout << score << endl;
	return 0;
}

