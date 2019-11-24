package com.yd.burst.model;

import java.io.Serializable;

/**
 * @Auther: kelly
 * @Date: 2019/11/14 23:42
 * @Description:
 */
public class Poker implements Serializable {
    private String color;
    private String point;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public Poker() {
        super();
    }

    public Poker(String color, String point) {
        super();
        this.color = color;
        this.point = point;
    }

    @Override
    public String toString() {
        return color +  point;
    }

    public int getPoints() {        // 比牛时的点数，A为1,J、Q、K为10
        int points = 0;
        switch (this.point) {
            case "A":
                points = 1;
                break;
            case "2":
                points = 2;
                break;
            case "3":
                points = 3;
                break;
            case "4":
                points = 4;
                break;
            case "5":
                points = 5;
                break;
            case "6":
                points = 6;
                break;
            case "7":
                points = 7;
                break;
            case "8":
                points = 8;
                break;
            case "9":
                points = 9;
                break;
            case "10":
            case "J":
            case "Q":
            case "K":
                points = 10;
                break;
        }
        return points;
    }

    public int getCount() { // 每张牌的实际点数
        int count = 0;
        switch (this.point) {
            case "A":
                count = 1;
                break;
            case "2":
                count = 2;
                break;
            case "3":
                count = 3;
                break;
            case "4":
                count = 4;
                break;
            case "5":
                count = 5;
                break;
            case "6":
                count = 6;
                break;
            case "7":
                count = 7;
                break;
            case "8":
                count = 8;
                break;
            case "9":
                count = 9;
                break;
            case "10":
                count = 10;
                break;
            case "J":
                count = 11;
                break;
            case "Q":
                count = 12;
                break;
            case "K":
                count = 13;
                break;
        }
        return count;
    }

    public int valuesOfColor(Poker poker) { // 花色的权值
        return Integer.parseInt(poker.color);
    }

}
