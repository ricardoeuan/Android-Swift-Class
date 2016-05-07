//
//  PlayerTableViewController.swift
//  PhotoTracker
//
//  Created by Ricardo Euán Romo on 3/11/16.
//  Copyright © 2016 Ricardo Euán. All rights reserved.
//

import UIKit

protocol PlayerTableViewControllerDelegate: class {
    func selectedPlayerName(name: String!)
}

class PlayerTableViewController: UITableViewController {
    
    // MARK: Properties
    
    var players = [Player]()
    weak var delegate: PlayerTableViewControllerDelegate?
    

    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Use the edit button item provided by the table view controller.
        //navigationItem.leftBarButtonItem = editButtonItem()
        
        if let savedPlayers = loadPlayers() {
            players += savedPlayers
        } else {
            loadSamplePlayers()
        }
        
        // tapRecognizer
        let longPressRecognizer = UILongPressGestureRecognizer(target: self, action: "longPress:")
        self.view.addGestureRecognizer(longPressRecognizer)

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem()
    }
    
    func loadSamplePlayers() {
        
        let photo1 = UIImage(named: "player1")!
        let player1 = Player(name: "Ricardo", photo: photo1, rating: 4)!
        
        let photo2 = UIImage(named: "player2")!
        let player2 = Player(name: "Fernanda", photo: photo2, rating: 3)!
        
        let photo3 = UIImage(named: "player3")!
        let player3 = Player(name: "Julio Emilio", photo: photo3, rating: 5)!
        
        players += [player1, player2, player3]
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return players.count
    }


    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        
        // Table view cells are reused and should be dequeued using a call identifier
        let cellIdentifier = "PlayerTableViewCell"
        let cell = tableView.dequeueReusableCellWithIdentifier(cellIdentifier, forIndexPath: indexPath) as! PlayerTableViewCell
        
        // Fetches the appropiate player for the datasource layout
        let player = players[indexPath.row]

        cell.nameLabel.text = player.name
        cell.photoImageView.image = player.photo
        cell.ratingControl.rating = player.rating

        return cell
    }
    
    @IBAction func unwindToMealList(sender: UIStoryboardSegue) {
        if let sourceViewController = sender.sourceViewController as?
            PlayerViewController, player = sourceViewController.player {
                if let selectedIndexPath = tableView.indexPathForSelectedRow {
                    // Update existing player
                    players[selectedIndexPath.row] = player
                    tableView.reloadRowsAtIndexPaths([selectedIndexPath], withRowAnimation: .None)
                } else {
                // Add a new player.
                let newIndexPath = NSIndexPath(forRow: players.count, inSection: 0)
                players.append(player)
                tableView.insertRowsAtIndexPaths([newIndexPath], withRowAnimation: .Bottom)
            }
                // Save the player
                savePlayers()
        }
    }


    
    // Override to support conditional editing of the table view.
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    


    // Override to support editing the table view.
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete {
            // Delete the row from the data source
            players.removeAtIndex(indexPath.row)
            savePlayers()
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        } else if editingStyle == .Insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }


    /*
    // Override to support rearranging the table view.
    override func tableView(tableView: UITableView, moveRowAtIndexPath fromIndexPath: NSIndexPath, toIndexPath: NSIndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(tableView: UITableView, canMoveRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */


    // MARK: - Navigation

    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        if segue.identifier == "ShowDetail" {
            let playerDetailViewController = segue.destinationViewController as! PlayerViewController
            if let selectedPlayerCell = sender as? PlayerTableViewCell {
                let indexPath = tableView.indexPathForCell(selectedPlayerCell)
                let selectedPlayer = players[indexPath!.row]
                playerDetailViewController.player = selectedPlayer
            }
        }
        else if segue.identifier == "AddItem" {
            
        }
    }
    
    //  Long press listener
    func longPress(longPressGestureRecognizer: UILongPressGestureRecognizer) {
        if longPressGestureRecognizer.state == UIGestureRecognizerState.Began {
            let touchPoint = longPressGestureRecognizer.locationInView(self.view)
            if let indexPath = tableView.indexPathForRowAtPoint(touchPoint) {
                self.delegate?.selectedPlayerName(players[indexPath.row].name)
                self.navigationController?.popViewControllerAnimated(true)
            }
        }
    }
    
    
    /*override func willMoveToParentViewController(parent: UIViewController?) {
        super.willMoveToParentViewController(parent)
        
    }*/
    
    // MARK: NSCoding

    func savePlayers() {
        let isSuccessfulSave = NSKeyedArchiver.archiveRootObject(players, toFile: Player.ArchiveURL.path!)
        if !isSuccessfulSave {
            print("Failed to save players...")
        }
    }
    
    func loadPlayers() -> [Player]? {
        return NSKeyedUnarchiver.unarchiveObjectWithFile(Player.ArchiveURL.path!) as? [Player]
    }

}
