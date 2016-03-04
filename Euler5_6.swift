//  Euler 5 and 6
// Warning: Euler 5 is a crazy loop that might make your computer overheat and run low on memory
// Ricardo Euán  A01260473

import UIKit
import Darwin


//Euler 5
func getSmallestDivisible(i:Int) -> Int {
    var n = 1
    while true {
        if n.isDivisibleUpTo(i) {
            return n
        }
        n++
    }
}
extension Int {
    func isDivisibleUpTo(max:Int) -> Bool {
        for i in max.stride(to: 2, by: -1) {
            if self % i != 0 {
                return false
            }
        }
        return false
    }
}

//getSmallestDivisible(20)

// Euler 6
func getSquareDifference(last: Int) -> Double {
    let exponent:Double = 2
    var squareSum:Double = 0
    for i in (1...last) {
        squareSum += pow(Double(i), exponent)
    }
    return pow(Double(last * (last + 1) / 2), exponent) - squareSum
}

//getSquareDifference(10);

