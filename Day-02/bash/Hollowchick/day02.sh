#!/bin/bash

score=0
score2=0
while read -r line; do
	case $line in
		"A X") score=$(($score+4));;"B X") score=$(($score+1));;"C X") score=$(($score+7));;
		"A Y") score=$(($score+8));;"B Y") score=$(($score+5));;"C Y") score=$(($score+2));;
		"A Z") score=$(($score+3));;"B Z") score=$(($score+9));;"C Z") score=$(($score+6));;
	esac
	case $line in
		"A X") score2=$(($score2+3));;"B X") score2=$(($score2+1));;"C X") score2=$(($score2+2));;
		"A Y") score2=$(($score2+4));;"B Y") score2=$(($score2+5));;"C Y") score2=$(($score2+6));;
		"A Z") score2=$(($score2+8));;"B Z") score2=$(($score2+9));;"C Z") score2=$(($score2+7));;
	esac
done <$"input"
echo -e "$score"
echo -e "$score2"
