/*													
	Ricardo Euan - A01260473
	Euler 3
*/

func highestPrimeFactor() -> Int {
	var current = 600851475143;
	var i = 3;
	var high = 0;
	while(true) {
		while( current % i == 0 ) {	
			current /= i;
			high = i;
		}
	if( current == 1 ){
		break;
	}
	i += 2;
	}
	return high;
}
