my $lines = [];
open my $file, "<", "input.txt";
while (my $line = <$file>) {
  chomp $line;
  push @$lines, $line;
}

for (1..10) {
  push @$lines, "$ cd ..";
}

sub get_cwd_str {
  my $cwd = shift;
  return join "/", @$cwd;
}

my $cwd = [];
my $fs = {};
foreach my $line (@$lines) {
  if ($line =~ /\$ cd/) {
    my $dir = (split " ", $line)[-1];
    if ($dir eq "..") {
      my $s = $fs->{get_cwd_str($cwd)};
      pop @$cwd;
      if (scalar @$cwd == 0) {
        last;
      }

      $fs->{get_cwd_str($cwd)} += $s;
    } else { 
      push @$cwd, $dir;
      $fs->{get_cwd_str($cwd)} = 0;
    }
  } elsif ($line =~ /\$ ls/) {} else {
    if ($line =~ /dir/) {} else {
      $fs->{get_cwd_str($cwd)} += (split " ", $line)[0];
    }
  }
}

my $star_1_sum = 0;
foreach my $key (keys %$fs) {
  if ($fs->{$key} <= 100000) {
    $star_1_sum += $fs->{$key};
  }
}
print "Part1: $star_1_sum\n";

my $disk_size = 70000000;
my $required_space = 30000000;

my $free_space = $fs->{"/"} - $disk_size;
my $required_delete = $free_space + $required_space;

my $candidates = [];
foreach my $key (keys %$fs) {
  if ($fs->{$key} >= $required_delete) {
    push @$candidates, $fs->{$key};
  }
}

print "Part 2: " . (sort {$a <=> $b} @$candidates)[0] . "\n";