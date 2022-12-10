use strict;
use warnings;

open(my $input_fh, '<', 'input.txt')
    or die "Can't open file: $!";

my @grid;
while (my $line = <$input_fh>) {
    chomp $line;
    my @row = split //, $line;
    push @grid, \@row;
}

my $visible_trees = 0;
my $highest_scenic_score = 0;
for my $row_index (0 .. $#grid) {
    if ($row_index == 0 || $row_index == $#grid) {
        $visible_trees += scalar @grid;
        next;
    }
    for my $column_index (0 .. $#{$grid[$row_index]}) {
        if ($column_index == 0 || $column_index == $#{$grid[$row_index]}) {
            $visible_trees++;
            next;
        }

        my $blocked = 0;
        my $sum_trees = 1;
        for my $x (-1, 1) {
            my $trees = 0;
            for my $m (1 .. $#grid) {
                my $cell_x = $row_index + ($x * $m);
                my $cell_y = $column_index;

                if ($cell_x < 0 || $cell_x > $#grid) {
                    last;
                }

                $trees++;
                if ($grid[$cell_x][$cell_y] >= $grid[$row_index][$column_index]) {
                    last;
                }
            }

            $sum_trees *= $trees;
        }

        for my $y (-1, 1) {
            my $trees = 0;
            for my $m (1 .. $#{$grid[$row_index]}) {
                my $cell_x = $row_index;
                my $cell_y = $column_index + ($y * $m);

                if ($cell_y < 0 || $cell_y > $#{$grid[$row_index]}) {
                    last;
                }

                $trees++;
                if ($grid[$cell_x][$cell_y] >= $grid[$row_index][$column_index]) {
                    last;
                }
            }

            $sum_trees *= $trees;
        }

        if ($sum_trees) {
            $blocked = 1;
        }

        if ($blocked) {
            $visible_trees++;
        }

        if ($sum_trees > $highest_scenic_score) {
            $highest_scenic_score = $sum_trees;
        }
    }
}

print "$visible_trees

";
print "$highest_scenic_score

";