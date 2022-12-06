#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>
#include <sstream>

class Parser
{
    std::ifstream ifs;
    std::string ln;

public:
    Parser(std::string filename) { ifs = std::ifstream(filename); };
    int next(void) { return !!std::getline(ifs, ln); };
    std::string getLine(void) { return ln; };
};

class Stacks
{
    std::vector<std::string> input;
    std::vector<std::vector<char>> stacks;
    int col;

public:
    Stacks(){};
    Stacks(Stacks &theirs)
    {
        col = theirs.col;
        for (int i = 0; i < theirs.stacks.size(); i++)
        {
            std::vector<char> tmp;
            for (int j = 0; j < theirs.stacks[i].size(); j++)
                tmp.push_back(theirs.stacks[i][j]);
            stacks.push_back(tmp);
        }
    };
    void addLine(std::string ln) { input.push_back(ln); };

    void parseStacks(void)
    {
        std::stringstream ss =
            std::stringstream(input[input.size() - 1]);
        while (ss >> col)
            stacks.push_back(std::vector<char>());

        for (int i = input.size() - 2; i >= 0; i--)
        {
            for (int j = 0; j < col; j++)
            {
                int off;
                std::string crate = std::string(input[i], j * 4, 4);
                off = crate.find('[', 0);
                if (off != std::string::npos)
                    stacks[j].push_back(crate[off + 1]);
            }
        }
    };
    void moveParsed(int num, int from, int to, int byOne)
    {
        int i;
        std::vector<char> crates;

        if (byOne)
        {
            for (i = 0; i < num; i++)
            {
                char c = stacks[from - 1].back();
                stacks[from - 1].pop_back();
                stacks[to - 1].push_back(c);
            }
            return;
        }

        for (i = 0; i < num; i++)
        {
            crates.push_back(stacks[from - 1].back());
            stacks[from - 1].pop_back();
        }
        for (i--; i >= 0; i--)
        {
            stacks[to - 1].push_back(crates[i]);
        }
    }
    void move(std::string ln, int byOne)
    {
        int num, from, to;
        std::stringstream ss = std::stringstream(ln);
        std::string tmp;
        while (ss >> tmp)
        {
            if (tmp == "move")
                ss >> num;
            if (tmp == "from")
                ss >> from;
            if (tmp == "to")
                ss >> to;
        }
        moveParsed(num, from, to, byOne);
    };
    void printTopLine(void)
    {
        for (int i = 0; i < col; i++)
        {
            std::cout << stacks[i].back();
        }
        std::cout << std::endl;
    }
};

int main()
{
    Parser par = Parser("input.txt");
    Stacks st;

    while (par.next())
    {
        std::string ln = par.getLine();
        if (ln.size() == 0)
            break;
        st.addLine(ln);
    }
    st.parseStacks();
    Stacks st1(st);
    while (par.next())
    {
        std::string ln = par.getLine();
        st.move(ln, 1);
        st1.move(ln, 0);
    }
    std::cout << "Part One:" << std::endl;
    st.printTopLine();
    std::cout << "Part Two:" << std::endl;
    st1.printTopLine();

    return 0;
}