using System;
using System.Collections.Generic;

namespace AdventOfCode
{
    class Program
    {
        static void Main(string[] args)
        {
            string[] input = File.ReadAllLines(Path.Combine(AppDomain.CurrentDomain.BaseDirectory, @"path"));
            List<int> plays = new List<int>();
            foreach (string curLine in input)
                foreach (string curWord in curLine.Split(' '))
                    plays.Add(int.Parse(curWord.Replace('X', '1').Replace('Y', '2').Replace('Z', '3').Replace('A', '1').Replace('B', '2').Replace('C', '3')));
            int sum = 0;
            int sum2 = 0;
            for (int i = 0; i < plays.Count; i++)
            {
                sum += _getScore(plays[i], plays[i+1]);
                if(plays[i+1] == 1) sum2 += _getScore(plays[i], plays[i] == 1 ? 3 : plays[i]-1);
                else if(plays[i+1] == 2) sum2 += _getScore(plays[i], plays[i]);
                else sum2 += _getScore(plays[i], plays[i] == 3 ? 1 : plays[i] + 1);
                i++;
            }
            Console.WriteLine("Part 1: " + sum);
            Console.WriteLine("Part 2: " + sum2);
        }

        private static int s_getScore(int elf, int player)
        {
            if ((elf < player || (elf == 3 && player == 1)) && !(elf == 1 && player == 3)) return 6 + player;
            if (elf == player) return 3 + player;
            return player;
        }

    }
}
