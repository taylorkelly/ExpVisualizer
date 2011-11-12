//
//  ImageShareController.h
//  ExpVisualizerClient
//
//  Created by Taylor Kelly on 12/11/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ImageShareController : UIViewController <UIImagePickerControllerDelegate, UINavigationControllerDelegate> {
    IBOutlet UIButton *sendPhoto;
}

@property (nonatomic, retain) IBOutlet UIImageView * selectedImage;
@property (nonatomic, retain) UIImagePickerController *picker;

- (IBAction) selectPhoto;
- (IBAction) sendPhoto;

@end
