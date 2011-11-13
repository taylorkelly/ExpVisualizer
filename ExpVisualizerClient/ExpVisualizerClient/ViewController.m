//
//  ViewController.m
//  ExpVisualizerClient
//
//  Created by Taylor Kelly on 31/10/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"
#import "AsyncUdpSocket.h"
#import <CoreLocation/CoreLocation.h>

@implementation ViewController

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

- (IBAction) sendMessage:(id)sender {
    NSString *message = messageText.text;
    int length = 300*message.length;
    
    if(sender == textButton) {
        [self sendPacketForActivity:@"text" andLength:length andDetails:message];
    } else if(sender == tweetButton) {
        [self sendPacketForActivity:@"tweet" andLength:length andDetails:message];
    } else if(sender == emailButton) {
        [self sendPacketForActivity:@"email" andLength:length andDetails:message];
    }
}

- (IBAction) makeCall {
    int length = (int)(1000*60*60*phoneCallLength.value);
    [self sendPacketForActivity:@"call" andLength:length andDetails:nil];
}

- (IBAction) takePhoto {
    int length = 1000*6;
    [self sendPacketForActivity:@"photo" andLength:length andDetails:nil];
}
- (IBAction) takeVideo {
    int length = (int)(1000*60*2*videoLength.value);
    [self sendPacketForActivity:@"video" andLength:length andDetails:nil];
}
- (IBAction) updateLocation {
    int length = 1000;
    [self sendPacketForActivity:@"location" andLength:length andDetails:nil];
}


- (void)sendPacketForActivity:(NSString *)activity andLength:(NSInteger)length andDetails:(NSString *)details {
    AsyncUdpSocket *socket = [[AsyncUdpSocket alloc] init];
    NSString *message;
    if(details) {
        message = [NSString stringWithFormat:@"%@:%i:%@", activity, length, details];
    } else {
        message = [NSString stringWithFormat:@"%@:%i", activity, length];        
    }
    
    [[NSUserDefaults standardUserDefaults] setObject:ipAddress.text forKey:@"3750ipaddress"];
    NSData *data = [message dataUsingEncoding:NSASCIIStringEncoding];
    [socket sendData:data toHost:[ipAddress text] port:4445 withTimeout:-1 tag:1];
}


- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    
    return YES;
}

- (void)accelerometer:(UIAccelerometer *)accelerometer
        didAccelerate:(UIAcceleration *)acceleration
{
    double const kThreshold = 1.5;
    if (fabsf(acceleration.x) > kThreshold
        || fabsf(acceleration.y) > kThreshold
        || fabsf(acceleration.z) > kThreshold) {

        int length = (int)((fabsf(acceleration.x) + fabsf(acceleration.y) + fabsf(acceleration.z))*1000);
        [self sendPacketForActivity:@"movement" andLength:length andDetails:nil];
    }
}

- (void)startAccelerometer {
    UIAccelerometer *accelerometer = [UIAccelerometer sharedAccelerometer];
    accelerometer.delegate = self;
    accelerometer.updateInterval = 0.25;
}

- (void)stopAccelerometer {
    UIAccelerometer *accelerometer = [UIAccelerometer sharedAccelerometer];
    accelerometer.delegate = nil;
}

#pragma mark -
#pragma mark IBActions

- (IBAction)hideKeyboard:(id)sender {
	[messageText resignFirstResponder];
	[ipAddress resignFirstResponder];
}

#pragma mark -
#pragma mark Notifications

- (void)keyboardWillShow:(NSNotification *)notification {
	[UIView beginAnimations:nil context:NULL];
	[UIView setAnimationDuration:0.3];
    
	CGRect frame = keyboardToolbar.frame;
	frame.origin.y = self.view.frame.size.height - 260.0;
	keyboardToolbar.frame = frame;
    
	[UIView commitAnimations];
}

- (void)keyboardWillHide:(NSNotification *)notification {
	[UIView beginAnimations:nil context:NULL];
	[UIView setAnimationDuration:0.3];
    
	CGRect frame = keyboardToolbar.frame;
	frame.origin.y = self.view.frame.size.height;
	keyboardToolbar.frame = frame;
    
	[UIView commitAnimations];
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.title = @"Testing";
	// Do any additional setup after loading the view, typically from a nib.
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
    ipAddress.delegate = self;
    ipAddress.text = [[NSUserDefaults standardUserDefaults] stringForKey:@"3750ipaddress"];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillShow:) name:UIKeyboardWillShowNotification object:nil];
	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillHide:) name:UIKeyboardWillHideNotification object:nil];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    [self startAccelerometer];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
    [self stopAccelerometer];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillShowNotification object:nil];
	[[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillHideNotification object:nil];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
    } else {
        return YES;
    }
}

@end
