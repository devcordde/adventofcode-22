use strict;
use warnings;
use constant kLettersInAlphabet => 26;
use constant kNumberItemTypes => kLettersInAlphabet * 2;

my @input_lines;
open(my $f, '<', 'input.txt');
if (defined $f) {
    while (my $line = <$f>) {
        chomp $line;
        push @input_lines, $line;
    }
}
else {
    print("file dead");
    exit(-1);
}

my $priority_sum = 0;

for my $rucksack (@input_lines) {
    my @seen = (0) x kNumberItemTypes;

    my $dividing_line = length($rucksack) / 2;
    for (my $i = 0; $i < $dividing_line; ++$i) {
        my $pos = substr($rucksack, $i, 1) ge 'a' ? ord(substr($rucksack, $i, 1)) - ord('a') : ord(substr($rucksack, $i, 1)) - ord('A') + kLettersInAlphabet;
        $seen[$pos] = 1;
    }

    for (my $i = $dividing_line; $i < length($rucksack); ++$i) {
        my $pos = substr($rucksack, $i, 1) ge 'a' ? ord(substr($rucksack, $i, 1)) - ord('a') : ord(substr($rucksack, $i, 1)) - ord('A') + kLettersInAlphabet;
        if ($seen[$pos]) {
            $priority_sum += $pos + 1;
            last;
        }
    }
}

print("Part 1: $priority_sum

");


$priority_sum = 0;
for (my $rucksack_i = 0; $rucksack_i < @input_lines;)
{
    my @group_bits = (0) x kNumberItemTypes;
    for (my $i = 0; $i < 3; ++$i, ++$rucksack_i)
    {
        for my $c (split //, $input_lines[$rucksack_i])
        {
            my $pos = ord($c) >= ord('a') ?
                      ord($c) - ord('a') :
                      ord($c) - ord('A') + kLettersInAlphabet;
            $group_bits[$pos] |= 1 << $i;
            if ($group_bits[$pos] == 0b0111)
            {
                $priority_sum += $pos + 1;
                last;
            }
        }
    }
}

print "Part 2: $priority_sum

";