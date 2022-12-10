#include <iostream>
#include <algorithm>
#include <fstream>
#include <sstream>
#include <vector>
using namespace std;

int main(){
	string line;
	vector<int> cal_array;
	int cal = 0, int_line;
	ifstream input ("input");
    while (input){
    	getline(input, line);
        if ( line.empty() ) {
    		cal_array.push_back(cal);
    		cal = 0;
    		continue;
    	}
    	stringstream inter{line};
    	inter >> int_line;
    	cal += int_line;
    }
    sort(cal_array.begin(), cal_array.end(), greater <>());
    cout << cal_array[0] << endl;
    cout << cal_array[0] + cal_array[1] + cal_array[2] << endl;
	return 0;
}

