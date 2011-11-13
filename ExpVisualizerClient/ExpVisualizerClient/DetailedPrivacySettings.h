//
//  DetailedPrivacySettings.h
//  ExpVisualizerClient
//
//  Created by Taylor Kelly on 12/11/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface DetailedPrivacySettings : UITableViewController

- (id)initWithUser:(NSString *)user;

@property (retain) NSArray *settingsList;

@end
