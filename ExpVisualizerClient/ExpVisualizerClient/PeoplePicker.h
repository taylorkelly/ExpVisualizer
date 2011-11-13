//
//  PeoplePicker.h
//  ExpVisualizerClient
//
//  Created by Taylor Kelly on 12/11/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class PeoplePicker;

@protocol PeoplePickerDelegate
- (void)peoplePickerDidCancel: (PeoplePicker *)peoplePicker;
- (void)peoplePickerDidPickPerson: (PeoplePicker *)peoplePicker withPerson:(NSString *)person;
@end

@interface PeoplePicker : UIViewController

@property (retain) id <PeoplePickerDelegate> delegate;

- (IBAction) pickPerson:(UIButton *)sender;
- (IBAction) cancel;

@end
