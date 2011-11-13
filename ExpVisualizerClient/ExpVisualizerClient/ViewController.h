//
//  ViewController.h
//  ExpVisualizerClient
//
//  Created by Taylor Kelly on 31/10/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController <UIAccelerometerDelegate, UITextFieldDelegate> {
    IBOutlet UITextField *ipAddress;
    IBOutlet UISlider *phoneCallLength;
    IBOutlet UISlider *videoLength;
    IBOutlet UITextView *messageText;
    IBOutlet UIButton *textButton;
    IBOutlet UIButton *tweetButton;
    IBOutlet UIButton *emailButton;
    UIToolbar *keyboardToolbar;
}

- (IBAction)hideKeyboard:(id)sender;


- (void)sendPacketForActivity:(NSString *)activity andLength:(NSInteger)length andDetails:(NSString *)details;

- (IBAction) sendMessage:(id)sender;
- (IBAction) makeCall;
- (IBAction) takePhoto;
- (IBAction) takeVideo;
- (IBAction) updateLocation;


@end
