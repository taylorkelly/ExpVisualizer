//
//  ImageShareController.m
//  ExpVisualizerClient
//
//  Created by Taylor Kelly on 12/11/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import "ImageShareController.h"

@implementation ImageShareController
@synthesize selectedImage = _selectedImage;
@synthesize picker = _picker;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
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

-(IBAction) sendPhoto {
    [self.navigationController popViewControllerAnimated:YES];
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Photo sent!" 
                                                    message:@"Your photo was successfully sent." 
                                                   delegate:nil 
                                          cancelButtonTitle:@"OK"
                                          otherButtonTitles:nil];
    [alert show];
}

-(IBAction) selectPhoto {
    self.picker = [[UIImagePickerController alloc] init];
    self.picker.delegate = self;
    
    if ([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
        self.picker.sourceType = UIImagePickerControllerSourceTypeCamera;
        
    } else if ([UIImagePickerController isSourceTypeAvailable:
                UIImagePickerControllerSourceTypePhotoLibrary]) {
        self.picker.sourceType =UIImagePickerControllerSourceTypePhotoLibrary;
        
    }
    
    [self presentModalViewController:self.picker animated:YES];
    
}

- (void)imagePickerControllerDidCancel: (UIImagePickerController *)imagePicker {
    [self dismissModalViewControllerAnimated:YES];
    
}

- (void)imagePickerController:(UIImagePickerController *) imagePicker 
didFinishPickingMediaWithInfo:(NSDictionary *)info {
    self.selectedImage.image = [info objectForKey:UIImagePickerControllerOriginalImage];
    [self dismissModalViewControllerAnimated:YES];
    sendPhoto.enabled = YES;
}

- (IBAction) chooseRecept {
    PeoplePicker *pp = [[PeoplePicker alloc] init];
    pp.delegate = self;
    
    [self presentModalViewController:pp animated:YES];
}

- (void)peoplePickerDidCancel: (PeoplePicker *)peoplePicker {
    [self dismissModalViewControllerAnimated:YES];

}
- (void)peoplePickerDidPickPerson: (PeoplePicker *)peoplePicker withPerson:(NSString *)person {
    recept.titleLabel.text = person;
    [self dismissModalViewControllerAnimated:YES];
}


#pragma mark - View lifecycle

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView
{
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad
{
    [super viewDidLoad];
    sendPhoto.enabled = NO;
}


- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

@end
