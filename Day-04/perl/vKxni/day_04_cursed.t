use strict;
use warnings;
use Data::Dumper;
use List::Util qw( min max );

open(my $f, '<', 'input.txt') or die $!;
my @input = map { chomp; $_ } <$f>;
close($f);

my @range_sets = map { [ map { [min(split('-')), max(split('-'))] } split(',') ] } @input;
my @subset_flags = map { $_->[0][0] <= $_->[1][0] && $_->[0][1] >= $_->[1][1] || $_->[1][0] <= $_->[0][0] && $_->[1][1] >= $_->[0][1] } @range_sets;

print scalar(grep { $_ } @subset_flags), "

";

my @intersections = map { [min($_->[0][1], $_->[1][1]), max($_->[0][0], $_->[1][0])] } grep { $_->[0][1] >= $_->[1][0] && $_->[1][1] >= $_->[0][0] } @range_sets;
print scalar(@intersections), "

";