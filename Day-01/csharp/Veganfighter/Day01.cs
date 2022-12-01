using System;
using System.Collections.Generic;

namespace AdventOfCode
{
    class Program
    {
        static void Main(string[] args)
        {
            string[] input = File.ReadAllLines(Path.Combine(AppDomain.CurrentDomain.BaseDirectory, @"path"));
            int highestSum = 0;
            int curSum = 0;
            List<int> allAmounts = new List<int>();
            for (int i = 0; i < input.Length; i++)
            {
                if (!String.IsNullOrWhiteSpace(input[i])) curSum += int.Parse(input[i]);
                else
                {
                    if (curSum > highestSum) highestSum = curSum;
                    allAmounts.Add(curSum);
                    curSum = 0;
                }
            }
            Console.WriteLine("Highest amount: " + highestSum);
            allAmounts.Sort();
            Console.WriteLine("Top 3 amounts: " + (allAmounts[allAmounts.Count-1] + allAmounts[allAmounts.Count-2] + allAmounts[allAmounts.Count-3]));
        }
    }
}
