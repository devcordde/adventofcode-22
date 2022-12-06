use strict;
use warnings;

sub test_sequence {
    my ($i, $input_seq, $size) = @_;
    my @j = split(//, substr($input_seq, $i, $size));
    foreach my $j (@j) {
        if (scalar(grep { $_ eq $j } @j) > 1) {
            return 0;
        }
    }
    return 1;
}

sub get_sequence {
    my ($input_seq, $size) = @_;
    for (my $i = 0; $i < length($input_seq); $i++) {
        if (test_sequence($i, $input_seq, $size) == 1) {
            return $i + $size;
        }
    }
}


open(my $file, "<", "input.txt") or die "Can't open input.txt: $!";
my $content = do { local $/; <$file> };
my $start1 = get_sequence($content, 4);
my $start2 = get_sequence($content, 14);
print("Part 1 : $start1

");
print("Part 2 : $start2

");
