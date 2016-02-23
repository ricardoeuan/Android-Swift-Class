
func largestPalindrome() -> Int {
    
    var product: Int = 0
    var largestPalindrome: Int = 0
    
    for left in (100...999).reverse() {
        for right in (100...left).reverse() {
            product = left * right
            if product > largestPalindrome {
                if isPalindrome(String(product)) {
                    largestPalindrome = product
                }
            } else {
                break
            }
        }
    }
    return largestPalindrome
}


func isPalindrome(product: String) -> Bool{
    var revstring = ""
    for character in product.characters {
        revstring = String(character) + revstring
    }
    return (revstring.lowercaseString == product.lowercaseString)
}
