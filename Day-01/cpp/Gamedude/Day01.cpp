#include "iostream"
#include "fstream"
#include "string"
#include "vector"
#include "algorithm"

std::vector<std::string> getInput(std::string path)
{
    std::vector<std::string> collection;
    std::ifstream fileStream(path);
    if(!fileStream.is_open())
        return collection;
    std::string line;
    while(std::getline(fileStream, line))
        collection.push_back(line);
    return collection;
}

int main()
{
    auto input = getInput("./input.txt");
    std::vector<int32_t> calories;
    int32_t currentCalories = 0;
    for (std::string line: input)
    {
        if(line != "")
            currentCalories = currentCalories + std::stoi(line);
        else
        {
            calories.push_back(currentCalories);
            currentCalories = 0;
        }
    }
    std::sort(calories.begin(), calories.end(), std::greater<>());

    std::cout << "Part 1: " << calories.at(0) << std::endl;
    std::cout << "Part 2: " << calories.at(0) + calories.at(1) + calories.at(2) << std::endl;

    return 0;
}