#!/bin/bash

score=0
pt1="";pt2=""
arr="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
while read -r line; do
	pt1="${line:0:$((${#line} / 2))}"; pt2="${line:$((${#line} / 2)):${#line}}"
	for (( i=0; i<${#pt1}; i++ )); do
		if [[ "$pt2" =~ .*"${pt1:i:1}".* ]]; then break; fi
	done
	for (( j=0; i<${#arr}; j++ )); do
		if [[ "${pt1:i:1}" =~ .*"${arr:j:1}".* ]];then
			score=$(( score + $j + 1 ))
			break
		fi
	done
done <$"input"
echo -e "$score"; score=0
while read -r line1; do read -r line2; read -r line3
	for (( i=0; i<${#line1}; i++ )); do
		if [[ "$line2" =~ .*"${line1:i:1}".* ]] && [[ "$line3" =~ .*"${line1:i:1}".* ]];then break; fi
	done
	for (( j=0; i<${#arr}; j++ )); do
		if [[ "${line1:i:1}" =~ .*"${arr:j:1}".* ]];then
			score=$(( score + $j + 1 ))
			break
		fi
	done
done <$"input"
echo -e "$score"
