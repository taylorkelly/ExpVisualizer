//
//  ImageShareController.h
//  ExpVisualizerClient
//
//  Created by Taylor Kelly on 12/11/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "PeoplePicker.h"

@interface ImageShareController : UIViewController <UIImagePickerControllerDelegate, UINavigationControllerDelegate, PeoplePickerDelegate> {
    IBOutlet UIButton *sendPhoto;
    IBOutlet UIButton *recept;
}

@property (nonatomic, retain) IBOutlet UIImageView * selectedImage;
@property (nonatomic, retain) UIImagePickerController *picker;

- (IBAction) selectPhoto;
- (IBAction) sendPhoto;
- (IBAction) chooseRecept;

@end
