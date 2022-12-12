#!/bin/bash

i=0;s1="";s2="";s3="";s4="";s5="";s6="";s7="";s8="";s9="";num=0;from=0;to=0;dump=""
while read -r line; do
    if [[ "$i" -lt "9" ]]; then
        s1+="${line:1:1}";s2+="${line:5:1}";s3+="${line:9:1}";s4+="${line:13:1}";s5+="${line:17:1}";s6+="${line:21:1}";s7+="${line:25:1}";s8+="${line:29:1}";s9+="${line:33:1}"
        s1=${s1//" "/""};s2=${s2//" "/""};s3=${s3//" "/""};s4=${s4//" "/""};s5=${s5//" "/""};s6=${s6//" "/""};s7=${s7//" "/""};s8=${s8//" "/""};s9=${s9//" "/""}
    elif [[ "$i" -gt "9" ]]; then
        line=${line/"move "/""};line=${line/" from"/""};line=${line/" to"/""};read -r num from to <<< "$line"
		case $from in
			1) dump=${s1:0:num};s1=${s1/"$dump"/""};;2) dump=${s2:0:num};s2=${s2/"$dump"/""};;3) dump=${s3:0:num};s3=${s3/"$dump"/""};;
			4) dump=${s4:0:num};s4=${s4/"$dump"/""};;5) dump=${s5:0:num};s5=${s5/"$dump"/""};;6) dump=${s6:0:num};s6=${s6/"$dump"/""};;
			7) dump=${s7:0:num};s7=${s7/"$dump"/""};;8) dump=${s8:0:num};s8=${s8/"$dump"/""};;9) dump=${s9:0:num};s9=${s9/"$dump"/""};;
		esac
    
		# Solution for pt.2: Just comment out the following line to stop the code from reversing the stack:
		dump=$(rev <<< $dump)
    
		# If you know bash: I'm sorry for what I'm about to do.
		sto="s$to"; eval ${sto}=$dump${!sto}
		# I can already hear an angry mob of Rust devs forming infront of my house, trying to explain why the line above isn't safe.
    fi
	((i++))
done<$"input"
echo -e "$s1\n$s2\n$s3\n$s4\n$s5\n$s6\n$s7\n$s8\n$s9"
