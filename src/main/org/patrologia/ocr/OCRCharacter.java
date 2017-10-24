package org.patrologia.ocr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkloeble on 31/01/2017.
 */
public class OCRCharacter {

    List<Point> pointList = new ArrayList<>();

    public void addPoint(int i, int j) {
        pointList.add(new Point(i,j));
    }

    public boolean hasPoints() {
        return pointList.size() > 0;
    }

    private String draw(List<Point> pointList) {
        StringBuilder sb = new StringBuilder();
        int mostLeftX  = getMostLeftX(pointList);
        int mostHighY = getMostHighY(pointList);
        int mostRightX = getMostRightX(pointList);
        int mostLowY = getMostLowY(pointList);
        for(int x = mostLeftX;x<mostRightX;x++) {
            for(int y =  mostHighY;y<mostLowY;y++) {
                if(pointList.contains(new Point(x,y))) {
                    sb.append("X");
                } else {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String draw() {
        return draw(mirror(pointList));
    }

    private List<Point> mirror(List<Point> pointList) {
        List<Point> mirroredList = new ArrayList<>();
        for(Point point : pointList) {
            mirroredList.add(new Point(point.getY(),point.getX()));
        }
        return mirroredList;
    }

    private int getMostLowY(List<Point> pointList) {
        int mostLow = 0;
        for(Point point : pointList) {
            if(point.getY() > mostLow) mostLow = point.getY();
        }
        return mostLow;
    }

    private int getMostRightX(List<Point> pointList) {
        int mostRight = 0;
        for(Point point : pointList) {
            if(point.getX() > mostRight) mostRight = point.getX();
        }
        return mostRight;
    }

    private int getMostHighY(List<Point> pointList) {
        int mostHigh = 10000;
        for(Point point : pointList) {
            if(point.getY() < mostHigh) mostHigh = point.getY();
        }
        return mostHigh;
    }

    private int getMostLeftX(List<Point> pointList) {
        int mostLeft = 10000;
        for(Point point : pointList) {
            if(point.getX() < mostLeft) mostLeft = point.getX();
        }
        return mostLeft;
    }
}
