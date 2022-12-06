#include <iostream>
#include <fstream>
#include <string>
#include <map>

int test_sequence(int i, std::string input_seq, int size)
{
    for (int j = i; j < i + size; j++)
    {
        int count = 0;
        for (int k = i; k < i + size; k++)
        {
            if (input_seq.at(j) == input_seq.at(k))
            {
                count++;
            }
        }
        if (count > 1)
        {
            return 0;
        }
    }
    return 1;
}

int get_sequence(std::string input_seq, int size)
{
    for (int i = 0; i < input_seq.length(); i++)
    {
        if (test_sequence(i, input_seq, size) == 1)
        {
            return i + size;
        }
    }
    
    return 0;
}

int main()
{
    std::ifstream input_file;
    input_file.open("input.txt");
    std::string content;
    getline(input_file, content);
    input_file.close();
    int start1 = get_sequence(content, 4);
    int start2 = get_sequence(content, 14);
    std::cout << "Part 1 : " << start1 << std::endl;
    std::cout << "Part 2 : " << start2 << std::endl;
    return 0;
}