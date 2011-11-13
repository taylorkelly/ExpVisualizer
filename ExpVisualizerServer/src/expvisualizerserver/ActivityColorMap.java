/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expvisualizerserver;

import java.awt.Color;
import java.awt.Paint;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author taylor
 */
public class ActivityColorMap {
    private static Map<ActivityType, Paint> activityColorMap;
    
    static {
        activityColorMap = new HashMap<ActivityType, Paint>();
        activityColorMap.put(ActivityType.EMAIL, Color.YELLOW);
        activityColorMap.put(ActivityType.LOCATION_CHANGE, Color.GREEN);
        activityColorMap.put(ActivityType.PHOTO, Color.RED);
        activityColorMap.put(ActivityType.MISC, Color.WHITE);
        activityColorMap.put(ActivityType.PHONE_CALL, Color.BLUE);
        activityColorMap.put(ActivityType.PHYSICAL_MOVEMENT, Color.ORANGE);
        activityColorMap.put(ActivityType.TEXT, Color.PINK);
        activityColorMap.put(ActivityType.TWEET, Color.CYAN);
        activityColorMap.put(ActivityType.VIDEO, Color.MAGENTA);
    }
    
    public static Paint colorForActivity(ActivityType activityType) {
        return activityColorMap.get(activityType);
    }
    
}
