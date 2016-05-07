//
//  ViewController.swift
//  PhotoTracker
//
//  Created by Ricardo Euán Romo on 5/4/16.
//  Copyright © 2016 Ricardo Euán. All rights reserved.
//

import UIKit

class ViewController: UIViewController, PlayerTableViewControllerDelegate {
    
    var currentValue: Int = 0
    var targetValue: Int = 0
    var roundCounter = 0
    var score = 0
    
    @IBOutlet weak var slider: UISlider!
    @IBOutlet weak var targetLabel: UILabel!
    @IBOutlet weak var roundLabel: UILabel!
    @IBOutlet weak var scoreLabel: UILabel!
    @IBOutlet weak var playerLabel: UILabel!
    
    @IBAction func startOver(sender: UIButton) {

        startNewGame()
        updateLabels()
    }
    
    func selectedPlayerName(name: String!) {
        playerLabel.text = "Player: \(name)"
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.startNewGame()
        self.updateLabels()
    }
    
    func startNewRound() {
        currentValue = 50
        roundCounter += 1
        targetValue = 1 + Int(arc4random_uniform(100))
        slider.value = Float(currentValue)
    }
    
    func updateLabels() {
        targetLabel.text = String(targetValue)
        roundLabel.text = "\(roundCounter)"
        scoreLabel.text = String(score)
    }
    
    func startNewGame() {
        score = 0
        roundCounter = 0
        startNewRound()
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        let vc = segue.destinationViewController as! PlayerTableViewController
        vc.delegate = self
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func showMessage(sender: AnyObject) {
        
        let difference = abs(targetValue - currentValue)
        let points = 100 - difference
        score += points
        let title: String
        
        if difference == 0 {
            title = "Perfect"
        } else if difference < 5 {
            title = "You almost had it"
        } else if difference < 10 {
            title = "Pretty good"
        } else {
            title = "Not even close"
        }
        
        //Create constant called message
        let message = "You scored \(points) points"
        
        //I'm creating a variable or instantiating an object
        //of type UIAlertController
        //In Swift there is no new keyword
        let alert = UIAlertController(title: title, message: message, preferredStyle: .Alert)
        
        let myClosure: (action: UIAlertAction)->Void = {action in
            self.startNewRound()
            self.updateLabels()
        }
        
        //Buttons of the alert
        let action = UIAlertAction(title: "OK", style: UIAlertActionStyle.Default, handler: {
            action in
            //Calculating new random number
            self.targetValue = 1 + Int(arc4random_uniform(100))
            self.targetLabel.text = "\(self.targetValue)"
            self.scoreLabel.text = "\(self.score)"
            self.slider.value = 50
            
        })
        
        
        alert.addAction(action)
        
        presentViewController(alert, animated: true, completion: nil)
        
        
    }
    
    @IBAction func sliderMoved(sender: AnyObject) {
        let myInt = 30
        let myFloat: Float = Float(myInt) //#cast 1 primitives
        let slider: UISlider = sender as! UISlider // #cast 2 objects
        
        //print("the value of the slider is now: \(slider.value)")
        currentValue = lroundf(slider.value)
        //helloWorldwithString("hello")
    }
    
}
