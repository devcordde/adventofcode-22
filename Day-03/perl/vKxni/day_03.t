use strict;
use warnings;
use Data::Dumper;

sub part1 {
    my @rucksacks = @{$_[0]};
    my $total = 0;

    for my $line (@rucksacks) {
        my @first = split(//, substr($line, 0, length($line) / 2));
        my @second = split(//, substr($line, length($line) / 2, length($line)));

        for my $letter (@second) {
            if (grep {$_ eq $letter} @first) {
                my $priority = ord($letter);

                if (97 <= $priority && $priority <= 122) {
                    $priority = $priority - 96;
                } else {
                    $priority = $priority - 38;
                }

                $total += $priority;

                last;
            }
        }
    }

    return $total;
}

sub part2 {
    open(my $f, "<", "input.txt") or die "Can't open input.txt: $!";
    my @Lines = <$f>;
    my $bestSoFar = 0;
    my @thingsToCount;
    my $counter = 1;
    my $line1 = "";
    my $line2 = "";
    my $line3 = "";
    foreach my $line (@Lines) {
        $line =~ s/\s+//g;
        if ($counter % 3 == 1) {
            $line1 = $line;
        } elsif ($counter % 3 == 2) {
            $line2 = $line;
        } elsif ($counter % 3 == 0) {
            $line3 = $line;
            my %line1 = map { $_ => 1 } split(//, $line1);
            my %line2 = map { $_ => 1 } split(//, $line2);
            my %line3 = map { $_ => 1 } split(//, $line3);
            my @intersection = grep { $line1{$_} && $line2{$_} && $line3{$_} } keys %line1;
            $line1 = "";
            $line2 = "";
            $line3 = "";
            push(@thingsToCount, $intersection[0]);
        }
        $counter += 1;
    }

    foreach my $char (@thingsToCount) {
        my $temp = ord($char);
        if ($temp > 96) {
            $temp -= 96;
        } else {
            $temp -= 38;
        }
        $bestSoFar += $temp;
        $temp = 0;
    }

   return $bestSoFar;
}

sub main {
    open(my $f, "<", "input.txt") or die "Can't open file: $!";
    my @rucksacks = <$f>;
    close($f);

    chomp(@rucksacks);

    print "Part 1: ", part1(\@rucksacks), "

";
    print "Part 2: ", part2(\@rucksacks), "

";
}

main();