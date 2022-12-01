#include <string>
#include <fstream>
#include <iostream>
#include <list>

#define TOP_LEVELS 3

void part_one()
{
    int act = 0, max = 0;
    std::ifstream input("input.txt");
    std::string line;

    while (std::getline(input, line))
    {
        if (line.compare("") == 0)
        {
            if (act > max)
                max = act;
            act = 0;
        }
        else
            act += std::stoi(line);
    }
    std::cout << "Part One: " << max << std::endl;
}

void part_two()
{
    int act = 0, total = 0, max = 0;
    std::list<int> l;
    std::ifstream input("input.txt");
    std::string line;

    while (std::getline(input, line))
    {
        if (line.compare("") == 0)
        {
            l.push_back(act);
            act = 0;
        }
        else
            act += std::stoi(line);
    }
    l.sort(std::greater<int>());
    for (int i = 0; i < TOP_LEVELS && !l.empty(); l.pop_front())
    {
        total += l.front();
        i++;
    }
    std::cout << "Part Two: " << total << std::endl;
}

int main()
{
    part_one();
    part_two();
    return 0;
}