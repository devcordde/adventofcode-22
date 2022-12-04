my @assignments = ();
open(my $fh, '<', 'input.txt') or die;
while (my $line = <$fh>) {
    my @pair = ();
    for my $elf (split(',', $line)) {
        push @pair, [split('-', $elf)];
    }
    push @assignments, \@pair;
}
close $fh;

sub contains {
    my ($one, $two) = @_;
    return ($one->[0] <= $two->[0]) && ($one->[1] >= $two->[1]);
}

my $overlaps = 0;
for my $pair (@assignments) {
    if (contains($pair->[0], $pair->[1]) || contains($pair->[1], $pair->[0])) {
        $overlaps += 1;
    }
}
print "Part 1: $overlaps\n";

sub leftof {
    my ($one, $two) = @_;
    return $one->[1] < $two->[0];
}

$overlaps = 0;
for my $pair (@assignments) {
    if (leftof($pair->[0], $pair->[1]) || leftof($pair->[1], $pair->[0])) {
        next;
    } else {
        $overlaps += 1;
    }
}
print "Part 2: $overlaps

";