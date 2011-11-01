//
//  ViewController.h
//  ExpVisualizerClient
//
//  Created by Taylor Kelly on 31/10/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController {
    IBOutlet UITextField *color;
    IBOutlet UITextField *ipAddress;
}


- (IBAction)sendPacket;

@end
