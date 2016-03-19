/*
	Euler2 / Fibonacci Numbers
	Author : Ricardo Euán
	ID: A01260473
*/

sumOfEvenFibonnacci() -> Int {
	var sum = 0;
	var numZero = 0;
	var first = 1;
	var second: Int;
	while(sum < 4000000) {
		second = first;
		first = numZero + frist;
		numZero = second;
		if( first % 2 == 0 ) {
			sum = sum + first;
		}
	}
	return sum;
}
