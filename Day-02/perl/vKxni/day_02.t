sub partone {
    open(my $fh, "<", "input.txt") or die $!;
    my @strategy = <$fh>;
    chomp(@strategy);
    close($fh);

    my %plays = (
        "A X" => 4,
        "A Y" => 8,
        "A Z" => 3,
        "B X" => 1,
        "B Y" => 5,
        "B Z" => 9,
        "C X" => 7,
        "C Y" => 2,
        "C Z" => 6,
    );

    my $score = 0;
    foreach my $i (@strategy) {
        $score += $plays{$i};
    }

    print("Part 1: $score
    
");
}


sub parttwo {
    open(my $fh, "<", "input.txt") or die $!;
    my @strategy = <$fh>;
    chomp(@strategy);
    close($fh);

    my %plays = (
        "A X" => 3,
        "A Y" => 4,
        "A Z" => 8,
        "B X" => 1,
        "B Y" => 5,
        "B Z" => 9,
        "C X" => 2,
        "C Y" => 6,
        "C Z" => 7,
    );

    my $score = 0;
    foreach my $i (@strategy) {
        $score += $plays{$i};
    }

    print("Part 2: $score
    
");
}

partone();
parttwo();