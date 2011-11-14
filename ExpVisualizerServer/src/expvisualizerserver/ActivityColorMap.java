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
    public static Map<ActivityType, Paint> activityColorMap;

    static {
        activityColorMap = new HashMap<ActivityType, Paint>();
        activityColorMap.put(ActivityType.EMAIL, new Color(163,169,72));
        activityColorMap.put(ActivityType.LOCATION_CHANGE, new Color(255, 204, 153));
        activityColorMap.put(ActivityType.PHOTO, new Color(206,24,54));
        activityColorMap.put(ActivityType.MISC, Color.WHITE);
        activityColorMap.put(ActivityType.PHONE_CALL, new Color(0, 51, 102));
        activityColorMap.put(ActivityType.PHYSICAL_MOVEMENT, new Color(237,185,46));
        activityColorMap.put(ActivityType.TEXT, new Color(242, 0, 86));
        activityColorMap.put(ActivityType.TWEET, new Color(0,153,137));
        activityColorMap.put(ActivityType.VIDEO, new Color(248,89,49));
    }

    public static Paint colorForActivity(ActivityType activityType) {
        return activityColorMap.get(activityType);
    }
}
