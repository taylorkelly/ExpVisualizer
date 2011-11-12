//
//  DetailedPrivacySettings.m
//  ExpVisualizerClient
//
//  Created by Taylor Kelly on 12/11/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import "DetailedPrivacySettings.h"



@implementation DetailedPrivacySettings
@synthesize settingsList = _settingsList;

- (id)initWithUser:(NSString *)user {
    self = [super initWithStyle:UITableViewStyleGrouped];
    if(self) {
        self.title = user;
        self.settingsList = [NSArray arrayWithObjects:
                             @"Location", @"Accelerometer", @"Photos", 
                             @"Videos", @"Tweets", @"Email Activity", 
                             @"Text Message Activity", @"Phone Call Activity", nil];
    }
    return self;
}

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];

    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
 
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    if([self.title isEqualToString:@"Default Settings"]) {
        return 1;
    } else {
        return 2;
    }
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if([self.title isEqualToString:@"Default Settings"]) {
        return self.settingsList.count;
    } else {
        if(section == 0) {
            return 2;
        } else {
            return self.settingsList.count;
        }
    }
}

- (void)toggleSwitch:(UISwitch *)switcher
{
    int row = switcher.tag;
    
    [[NSUserDefaults standardUserDefaults] setBool:switcher.on forKey:[NSString stringWithFormat:@"privacy-%@-%i", self.title, row]];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    int section = indexPath.section;
    if([self.title isEqualToString:@"Default Settings"]) {
        section = 1;
    }
    
    if(section == 0) {
        UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
        if(indexPath.row == 0) {
            [cell textLabel].text = @"Name";
            [cell detailTextLabel].text = self.title;
        } else {
            [cell textLabel].text = @"Device ID";
            [cell detailTextLabel].text = @"7A394G";
        }
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        return cell;
    } else {
        static NSString *CellIdentifier = @"DetailedPrivacyCell";
        
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
        UISwitch *switcher;
        if (cell == nil) {
            cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
            switcher = [[[UISwitch alloc] initWithFrame:CGRectZero] autorelease];
            switcher.tag = indexPath.row;
            [switcher addTarget:self action:@selector(toggleSwitch:)
               forControlEvents:UIControlEventValueChanged];
            cell.accessoryView = switcher;
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
        } else {
            switcher = (UISwitch *)cell.accessoryView;
        }
        
        switcher.on = [[NSUserDefaults standardUserDefaults] boolForKey:[NSString stringWithFormat:@"privacy-%@-%i", self.title, indexPath.row]];;
        cell.textLabel.text  = [self.settingsList objectAtIndex:indexPath.row];
        
        return cell;
    }
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if(indexPath.section == 0) {
        
    } else {

    }
}

@end
