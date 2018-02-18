How to run?
	The main class is runner.SimpleEditor
	Just run this class with no arguments
	All commands described on doc are available.

Some assumptions.
	Example
	We have canvas 4*4
	 "------"
	 "|    |"
	 "|    |"
	 "|    |"
	 "|    |"
	 "------"

	 The I try draw a line
	 L 1, 1, 4, 4
		First possible solution is
			 "------"
			 "|x   |"
			 "| x  |"
			 "|  x |"
			 "|   x|"
			 "------"
			 But if I try to fill line with colour "y" then result should be
			 "------"
			 "|y   |"
			 "| y  |"
			 "|  y |"
			 "|   y|"
			 "------"
			 Note: When filling we should check colour by diagonal.
			 But according to this assumption when I try
			 B 4 1 z
			 The result should be
			 "------"
			 "|yxxx|"
			 "|xyxx|"
			 "|xxyx|"
			 "|xxxy|"
			 "------"
			 I think that it is not correct when filling check coordinates by diagonal.
		Second possible solution is
			 "------"
			 "|xx  |"
			 "| xx |"
			 "|  xx|"
			 "|   x|"
			 "------"
			 Means that each line coordinate couldn't connect by diagonal only.
			 Then each filling area with colour gives predicatable result
			 B 4 1 z
			 "------"
			 "|xxyy|"
			 "| xxy|"
			 "|  xx|"
			 "|   x|"
			 "------"
			 And filling area checks only coordinate by X-axis or Y-axis

Special cases
  1. When creating line when (x1, y1) = (x2, y2) then only one point should be coloured
  2. When creating rectangle when (x1, y1) = (x2, y2) then only one point should be coloured
  3. When creating rectangle when (x1, y1) and (x2, y2) lies on the same line
     which are parallel X-axis or Y-axis then only one line will be drawn
	 For ex
	 R 1 1 4 1
	 "------"
	 "|xxxx|"
	 "|    |"
	 "|    |"
	 "|    |"
	 "------"

More commands
  I think that filling area can be done in parallel.(Parallel option is under construction and should be optimized)

  To do this you should create concurrent canvas

    CN 20 4
    and fill area
    BN 1 1 x 4(when new last parameter is thread count)

  All commands in doc works the same.

Tests
  There are 3 kind of tests. I couldn't covering all code with test + benchmarks(shame on me). Then I cover only several special cases:
		1. Unit tests (test/unitTest/).
		2. Intergration test (test/intergrationTest/). I think that I cover all available cases + tests on concurrent filling area.
		3. Benchmarks (\Benchmarks\src\main\java). I made some benchmarks but hasn't yet finished. (Concurrent cases works too log to known problems(described in code TODO)
