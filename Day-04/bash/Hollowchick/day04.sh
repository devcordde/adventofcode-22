#!/bin/bash

line1="";line2="";num1=0;num2=0;dump1="";dump2="";score1=0;score2=0
while read -r line; do line=${line//['-']/" "};
	IFS=',';read -r line1 line2 <<< "$line"
	for i in "${line1[@]}"; do IFS=" ";read -r num1 num2 <<< "$i";dump1="[$num1]"
		while [ $num1 -lt $num2 ]; do num1=$(( num1 + 1 ));dump1+="[$num1]"; done
	done
	for i in "${line2[@]}"; do read -r num1 num2 <<< "$i"; dump2="[$num1]"
		while [ $num1 -lt $num2 ]; do num1=$(( num1 + 1 )); dump2+="[$num1]"; done
	done
	if [[ "$dump1" == *"$dump2"* ]] || [[ "$dump2" == *"$dump1"* ]]; then ((score1++)); fi
	for i in "${line2[@]}"; do read -r num1 num2 <<< "$i"; ((num1--))
		while [ $num1 -lt $num2 ]; do ((num1++))
			if [[ "$dump1" =~ .*"[$num1]".* ]]; then ((score2++)); break; fi
		done
		if [[ "$dump1" =~ .*"[$num1]".* ]]; then break; fi
	done
done <$"input"
echo -e "$score1"; echo -e "$score2"
