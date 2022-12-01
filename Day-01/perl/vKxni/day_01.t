#!/usr/bin/perl
use strict;
use warnings;

my @calories;
my $i = 0;

open my $fh, '<', 'input.txt' or die;
my $input = do { local $/; <$fh> };
close $fh;

for my $inventory (split /\n\n/, $input) {
    $calories[$i] = 0;
    for my $line (split /\n/, $inventory) {
        $calories[$i] += $line;
    }
    $i++;
}

my @calories_top3 = sort { $b <=> $a } @calories;
my $calories_top3 = $calories_top3[0] + $calories_top3[1] + $calories_top3[2];

print "Part 1: " . (sort { $b <=> $a } @calories)[0] . "

";

print "Part 2: $calories_top3

";