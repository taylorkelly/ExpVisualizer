/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expvisualizerserver;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Color;
import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author taylor
 */
public class Activity {
    private ActivityType type;
    private long birthTime;
    private int length;
    private String details;

    public Activity(String type, int length) {
        this(type, length, null);
    }

    public Activity(String type, int length, String details) {
        this(ActivityType.getActivity(type), System.currentTimeMillis(), length, details);
    }

    public Activity(ActivityType type, int length) {
        this(type, System.currentTimeMillis(), length, null);
    }

    public Activity(ActivityType type, long birthTime, int length, String details) {
        this.birthTime = birthTime;
        this.type = type;
        this.length = length;
        this.details = details;
    }

    public boolean isAlive(long time) {
        return (birthTime + length > time);
    }

    public void drawPulse(Graphics2D g2d, int width, int height, List<Activity> pastPulses) {
        int age = (int) (System.currentTimeMillis() - birthTime);

        int area = age/3;
        int radius = (int) Math.sqrt(area / Math.PI);

        int rectWidth = (int) (width * 0.75 + radius * 2);
        int rectHeight = 10 + radius * 2;
        int arcWidth = radius * 2;
        int arcHeight = radius * 2;


        int x = (width - rectWidth) / 2;
        int y = (height - rectHeight) / 2;

        Paint paint = ActivityColorMap.colorForActivity(type);

        g2d.setPaint(paint);
        g2d.fillRoundRect(x, y, rectWidth, rectHeight, arcWidth, arcHeight);


        if (age > length) {
            int deadArea = (age - length)/3;
            int deadRadius = (int) Math.sqrt(deadArea / Math.PI);


            int deadRectWidth = (int) (width * 0.75 + deadRadius * 2);
            int deadRectHeight = 10 + deadRadius * 2;
            int deadArcWidth = deadRadius * 2;
            int deadArcHeight = deadRadius * 2;


            int deadX = (width - deadRectWidth) / 2;
            int deadY = (height - deadRectHeight) / 2;

            Paint deadColor = VisPanel.BACKGROUND_COLOR;
            for (Activity pastActivity : pastPulses) {
                if(pastActivity.isAlive(birthTime + length)) {
                    deadColor = ActivityColorMap.colorForActivity(pastActivity.type);
                    break;
                }
            }
            g2d.setPaint(deadColor);
            g2d.fillRoundRect(deadX, deadY, deadRectWidth, deadRectHeight, deadArcWidth, deadArcHeight);
        }
        pastPulses.add(0, this);


    }

    public long birthTime() {
        return birthTime;
    }

    public String toString() {
        String str = type.toString();

        if (type == ActivityType.TWEET) {
            str += "\"" + details + "\"";
        }

        return str;
    }

    public ActivityType getActivityType() {
      return this.type;
    }

    public String getDetails() {
      return this.details;
    }

    public void setColor(Color c) {
      ActivityColorMap.activityColorMap.put(this.type, c);
    }

    public static List<Activity> listOfActivities() {
      List<Activity> listOfActivities = new LinkedList<Activity>();
      Object[] activityTypes = ActivityColorMap.activityColorMap.keySet().toArray();

      for (int i=0; i < activityTypes.length; i++) {
        ActivityType type = (ActivityType) activityTypes[i];
        listOfActivities.add(new Activity(type, 0));
      }
      return listOfActivities;
    }

    public String parseActivityType() {
      switch (type) {
        case PHOTO: return "Photo Uploaded";
        case LOCATION_CHANGE: return "Location Change";
        case PHYSICAL_MOVEMENT: return "Physical Movement";
        case VIDEO: return "Video Uploaded";
        case TEXT: return "Text Message Sent";
        case EMAIL: return "Email Sent";
        case TWEET: return "Tweet Sent";
        case PHONE_CALL: return "Used Phone";
        case MISC: return "Miscellaenous Task";
        default: return "default";
      }
    }
}

enum ActivityType {
    LOCATION_CHANGE, PHYSICAL_MOVEMENT, PHOTO, VIDEO,
    TEXT, EMAIL, TWEET, PHONE_CALL, MISC;

    public static ActivityType getActivity(String value) {
        if (value.equalsIgnoreCase("location_change") || value.equalsIgnoreCase("location change")) {
            return LOCATION_CHANGE;
        } else if (value.equalsIgnoreCase("physical_movement") || value.equalsIgnoreCase("physical movement") || value.equalsIgnoreCase("movement")) {
            return PHYSICAL_MOVEMENT;
        } else if (value.equalsIgnoreCase("photo")) {
            return PHOTO;
        } else if (value.equalsIgnoreCase("video")) {
            return VIDEO;
        } else if (value.equalsIgnoreCase("text")) {
            return TEXT;
        } else if (value.equalsIgnoreCase("email")) {
            return EMAIL;
        } else if (value.equalsIgnoreCase("tweet")) {
            return TWEET;
        } else if (value.equalsIgnoreCase("phone_call") || value.equalsIgnoreCase("phone call") || value.equalsIgnoreCase("phone") || value.equalsIgnoreCase("call")) {
            return PHONE_CALL;
        } else {
            return MISC;
        }
    }

    public String toString() {
        switch (this) {
            case LOCATION_CHANGE:
                return "Went to ";
            case PHYSICAL_MOVEMENT:
                return "Was moving around";
            case PHOTO:
                return "Took a photo";
            case VIDEO:
                return "Took a video";
            case TEXT:
                return "Was texting";
            case EMAIL:
                return "Was emailing";
            case TWEET:
                return "Tweeted: ";
            case PHONE_CALL:
                return "Was on the phone";
            default:
                return "Did something";

        }
    }
}
