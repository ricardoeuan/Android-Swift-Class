//
//  PhotoTrackerTests.swift
//  PhotoTrackerTests
//
//  Created by Ricardo Euán on 2/26/16.
//  Copyright © 2016 Ricardo Euán. All rights reserved.
//

import UIKit
import XCTest
@testable import PhotoTracker

class PhotoTrackerTests: XCTestCase {
    
    /*override func setUp() {
        super.setUp()
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        super.tearDown()
    }
    
    func testExample() {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }
    
    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measureBlock {
            // Put the code you want to measure the time of here.
        }
    }*/
    
    // MARK: FoodTracker Tests
    
    // Tests to confirm that the Moment initializer return when no name or negative rating is provided
    func testMomentInitialization() {

        // Success case.
        let potentialItem = Moment(name: "Newest moment", photo: nil, rating: 5)
        XCTAssertNotNil(potentialItem)
        
        // Failure cases.
        let noName = Moment(name: "", photo: nil, rating: 0)
        XCTAssertNil(noName, "Empty name is invalid")
        
        let badRating = Moment(name: "Really bad rating", photo: nil, rating: -1)
        XCTAssertNil(badRating, "Negative ratings are invalid, be positive")
        
    }
    
}
