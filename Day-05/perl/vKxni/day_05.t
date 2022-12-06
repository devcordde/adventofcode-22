sub create_stacks {
    my $path = $_[0];
    my @stacks = ();
    open(my $file, "<", $path);
    my $count = 0;
    while (my $line = readline($file)) {
        chomp($line);
        if (substr($line, 1, 1) eq '1') {
            last;
        }
        my $start = substr($line, 0, 4);
        my $end = substr($line, 4);
        while (length($start) != 0) {
            if (scalar(@stacks) <= $count) {
                push(@stacks, []);
            }
            if (substr($start, 1, 1) ne ' ') {
                push(@{$stacks[$count]}, substr($start, 1, 1));
            }
            $count += 1;
            $start = substr($end, 0, 4);
            $end = substr($end, 4);
        }
        $count = 0;
    }
    close($file);
    foreach my $entry (@stacks) {
        @{$entry} = reverse(@{$entry});
    }
    return @stacks;
}

sub read_moves {
    my $path = $_[0];
    open(my $file, "<", $path);
    my @moves = ();
    my $read = 0;
    while (my $line = readline($file)) {
        chomp($line);
        if (length($line) == 0) {
            $read = 1;
            next;
        }
        if ($read == 0) {
            next;
        }
        my @split = split(' ', $line);
        push(@moves, [int($split[1]), int($split[3]), int($split[5])]);
    }
    close($file);
    return @moves;
}

sub part_1 {
    my @stacks = @{$_[0]};
    my @moves = @{$_[1]};
    foreach my $move (@moves) {
        for (my $i = 0; $i < $move->[0]; $i++) {
            my $pop = pop(@{$stacks[$move->[1] - 1]});
            push(@{$stacks[$move->[2] - 1]}, $pop);
        }
    }
    return get_result(\@stacks);
}

sub part_2 {
    my @stacks = @{$_[0]};
    my @moves = @{$_[1]};
    foreach my $move (@moves) {
        my @toMove = ();
        for (my $i = 0; $i < $move->[0]; $i++) {
            if (scalar(@{$stacks[$move->[1] - 1]}) > 0) {
                my $pop = pop(@{$stacks[$move->[1] - 1]});
                push(@toMove, $pop);
            }
        }
        if (scalar(@toMove) > 0) {
            @toMove = reverse(@toMove);
            push(@{$stacks[$move->[2] - 1]}, @toMove);
        }
    }
    return get_result(\@stacks);
}

sub get_result {
    my @stacks = @{$_[0]};
    my $result = "";
    foreach my $stack (@stacks) {
        if (scalar(@{$stack}) > 0) {
            $result .= pop(@{$stack});
        }
    }
    return $result;
}

my @stacks = create_stacks("input.txt");
my @moves = read_moves("input.txt");
print("Part 1: ", part_1(\@stacks, \@moves), "\n");
print("Part 2: ", part_2(\@stacks, \@moves), "\n");
