//
//  MomentTableViewController.swift
//  PhotoTracker
//
//  Created by Ricardo Euán Romo on 3/11/16.
//  Copyright © 2016 Ricardo Euán. All rights reserved.
//

import UIKit

class MomentTableViewController: UITableViewController {
    
    // MARK: Properties
    
    var moments = [Moment]()
    

    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Use the edit button item provided by the table view controller.
        navigationItem.leftBarButtonItem = editButtonItem()
        
        if let savedMoments = loadMoments() {
            moments += savedMoments
        } else {
            loadSampleMoments()
        }

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem()
    }
    
    func loadSampleMoments() {
        
        let photo1 = UIImage(named: "moment1")!
        let moment1 = Moment(name: "Eating Salad", photo: photo1, rating: 4)!
        
        let photo2 = UIImage(named: "moment2")!
        let moment2 = Moment(name: "Eating chicken", photo: photo2, rating: 3)!
        
        let photo3 = UIImage(named: "moment3")!
        let moment3 = Moment(name: "Eating pasta", photo: photo3, rating: 5)!
        
        moments += [moment1, moment2, moment3]
        
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
        return moments.count
    }


    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        
        // Table view cells are reused and should be dequeued using a call identifier
        let cellIdentifier = "MomentTableViewCell"
        let cell = tableView.dequeueReusableCellWithIdentifier(cellIdentifier, forIndexPath: indexPath) as! MomentTableViewCell
        
        // Fetches the appropiate moment for the datasource layout
        let moment = moments[indexPath.row]

        cell.nameLabel.text = moment.name
        cell.photoImageView.image = moment.photo
        cell.ratingControl.rating = moment.rating

        return cell
    }
    
    @IBAction func unwindToMealList(sender: UIStoryboardSegue) {
        if let sourceViewController = sender.sourceViewController as?
            MomentViewController, moment = sourceViewController.moment {
                if let selectedIndexPath = tableView.indexPathForSelectedRow {
                    // Update existing moment
                    moments[selectedIndexPath.row] = moment
                    tableView.reloadRowsAtIndexPaths([selectedIndexPath], withRowAnimation: .None)
                } else {
                // Add a new moment.
                let newIndexPath = NSIndexPath(forRow: moments.count, inSection: 0)
                moments.append(moment)
                tableView.insertRowsAtIndexPaths([newIndexPath], withRowAnimation: .Bottom)
            }
                // Save the moments
                saveMoments()
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
            moments.removeAtIndex(indexPath.row)
            saveMoments()
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

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        if segue.identifier == "ShowDetail" {
            let momentDetailViewController = segue.destinationViewController as! MomentViewController
            if let selectedMomentCell = sender as? MomentTableViewCell {
                let indexPath = tableView.indexPathForCell(selectedMomentCell)
                let selectedMoment = moments[indexPath!.row]
                momentDetailViewController.moment = selectedMoment
            }
        }
        else if segue.identifier == "AddItem" {
            
        }
    }
    
    // MARK: NSCoding

    func saveMoments() {
        let isSuccessfulSave = NSKeyedArchiver.archiveRootObject(moments, toFile: Moment.ArchiveURL.path!)
        if !isSuccessfulSave {
            print("Failed to save moments...")
        }
    }
    
    func loadMoments() -> [Moment]? {
        return NSKeyedUnarchiver.unarchiveObjectWithFile(Moment.ArchiveURL.path!) as? [Moment]
    }

}
