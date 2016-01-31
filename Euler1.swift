/*
	Euler1 / Multiples of 3 and 5
	Author: Ricardo Euan
	ID: A01260473
*/

sumOfMultiples() -> Int {
	var sum = 0;
	for var i = 1; i < 1000; i++ {
		if(i % 3 == 0 || i % 5 == 0) {
		sum += i;
		}
	}
	return sum;
}
