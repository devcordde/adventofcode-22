#include <iostream>
#include <fstream>
#include <string>
#include <vector>

constexpr unsigned long kLettersInAlphabet = 26;
constexpr unsigned long kNumberItemTypes = kLettersInAlphabet * 2;

int main()
{
    std::vector<std::string> input_lines;
    std::ifstream f("input.txt");
    if (f.is_open())
    {
        std::string line;
        while (getline(f, line))
        {
            input_lines.push_back(line);
        }
    }
    else
    {
        std::cout << "file dead" << std::endl;
        return -1;
    }

    unsigned long priority_sum = 0;

    for (auto rucksack : input_lines)
    {
        std::vector<bool> seen(kNumberItemTypes, false);

        unsigned long dividing_line = rucksack.length() / 2;
        for (unsigned long i = 0; i < dividing_line; ++i)
        {
            unsigned long pos = rucksack[i] >= 'a' ? rucksack[i] - 'a' : rucksack[i] - 'A' + kLettersInAlphabet;
            seen[pos] = true;
        }

        for (unsigned long i = dividing_line; i < rucksack.length(); ++i)
        {
            unsigned long pos = rucksack[i] >= 'a' ? rucksack[i] - 'a' : rucksack[i] - 'A' + kLettersInAlphabet;
            if (seen[pos])
            {
                priority_sum += pos + 1;
                break;
            }
        }
    }

    std::cout << "Part 1: " << priority_sum << std::endl;
    priority_sum = 0;
    for (unsigned long rucksack_i = 0; rucksack_i < input_lines.size();)
    {
        std::vector<int> group_bits(kNumberItemTypes, 0);
        for (unsigned int i = 0; i < 3; ++i, ++rucksack_i)
        {
            for (auto c : input_lines[rucksack_i])
            {
                unsigned long pos = c >= 'a' ? c - 'a' : c - 'A' + kLettersInAlphabet;
                group_bits[pos] |= 1 << i;
                if (group_bits[pos] == 0b0111)
                {
                    priority_sum += pos + 1;
                    break;
                }
            }
        }
    }

    std::cout << "Part 2: " << priority_sum << std::endl;

    return 0;
}