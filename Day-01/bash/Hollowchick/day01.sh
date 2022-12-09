#!/bin/bash

calories=0
countarr=0
array=()
num1=0;num2=0;num3=0
while read -r line; do
	if [ "$line" != "" ]; then
		calories=$((calories+$line))
	else
		array+="$calories "; calories=0; countarr+=1
	fi
done <$"input"
for i in ${array[@]}; do
	if [ "$i" -gt "$num1" ]; then num3=$((num2));num2=$((num1));num1=$((i)); 
	elif [ "$i" -gt "$num2" ]; then num3=$((num2));num2=$((i));
	elif [ "$i" -gt "$num3" ]; then num3=$((i)); fi
done
echo -e "$num1"
echo -e "$((num1 + num2 + num3))"

