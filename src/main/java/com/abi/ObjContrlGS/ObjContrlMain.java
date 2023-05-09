package com.abi.ObjContrlGS;

import static com.abi.ObjContrlGS.ObjConst.*;

public class ObjContrlMain {
    static float kObj = kMin;
    static float tObj = tMin;
    static float hObj = hMin;
    static float kObjNext = (kMax - kMin) / 2;
    static float tObjNext = (tMax - tMin) / 2;
    static float hObjNext = (hMax - hMin) / 2;

    public static void main(String[] args) {
        System.out.println(sumX2(20F, 73.59F, 10.7F));

        int i = 1;
        while ( Math.abs(kObjNext - kObj) > kAccuracy ||
                Math.abs(tObjNext - tObj) > tAccuracy ||
                Math.abs(hObjNext - hObj) > hAccuracy) {

            kObj = kObjNext;
            tObj = tObjNext;
            hObj = hObjNext;
            kObjNext = findNextK();
            System.out.println("i=" + i + " K=" + kObjNext + " T=" + tObjNext + " H=" + hObjNext + " X2=" +
                    sumX2(kObjNext, tObjNext, hObjNext));
            tObjNext = findNextT();
            System.out.println("     K=" + kObjNext + " T=" + tObjNext + " H=" + hObjNext + " X2=" +
                    sumX2(kObjNext, tObjNext, hObjNext));
            hObjNext = findNextH();
            System.out.println("     K=" + kObjNext + " T=" + tObjNext + " H=" + hObjNext + " X2=" +
                    sumX2(kObjNext, tObjNext, hObjNext));
            i++;
        }
        kObj = kObjNext;
        tObj = tObjNext;
        hObj = hObjNext;
        System.out.println("K=" + kObj + " T=" + tObj + " H=" + hObj);
    }

    static float findNextK() {
        float kLeft = kMin;
        float kRight = kMax;
        float kObjTemp = kMin;
        float kObjNext = (kMax - kMin) / 2;
        while (Math.abs(kObjNext - kObjTemp) > kAccuracy) {
            kObjTemp = kObjNext;
            float kDeltaGS = (kRight - kLeft) / 1.618034F;
            float kLeftLeft = kRight - kDeltaGS;
            float kRightRight = kLeft + kDeltaGS;
            float sumLeft = sumX2(kLeftLeft, tObjNext, hObjNext);
            float sumRight = sumX2(kRightRight, tObjNext, hObjNext);
            if (sumLeft < sumRight) {
                kRight = kRightRight;
            } else {
                kLeft = kLeftLeft;
            }
                kObjNext = (kRight + kLeft) / 2;
        }
        return kObjNext;
    }

    static float findNextT() {
        float tLeft = tMin;
        float tRight = tMax;
        float tObjTemp = tMin;
        float tObjNext = (tMax - tMin) / 2;
        while (Math.abs(tObjNext - tObjTemp) > tAccuracy) {
            tObjTemp = tObjNext;
            float tDeltaGS = (tRight - tLeft) / 1.618034F;
            float tLeftLeft = tRight - tDeltaGS;
            float tRightRight = tLeft + tDeltaGS;
            if (sumX2(kObjNext, tLeftLeft, hObjNext) < sumX2(kObjNext, tRightRight, hObjNext)) {
                tRight = tRightRight;
            } else {
                tLeft = tLeftLeft;
            }
            tObjNext = (tRight + tLeft) / 2;
        }
        return tObjNext;
    }

    static float findNextH() {
        float hLeft = hMin;
        float hRight = hMax;
        float hObjTemp = hMin;
        float hObjNext = (hMax - hMin) / 2;
        while (Math.abs(hObjNext - hObjTemp) > tAccuracy) {
            hObjTemp = hObjNext;
            float hDeltaGS = (hRight - hLeft) / 1.618034F;
            float hLeftLeft = hRight - hDeltaGS;
            float hRightRight = hLeft + hDeltaGS;
            float sumLeft = sumX2(kObjNext, tObjNext, hLeftLeft);
            float sumRight = sumX2(kObjNext, tObjNext, hRightRight);
            if (sumLeft < sumRight) {
                hRight = hRightRight;
            } else {
                hLeft = hLeftLeft;
            }
            hObjNext = (hRight + hLeft) / 2;
        }
        return hObjNext;
    }

    static float sumX2(float k, float t, float h) {
        float sumX2 = 0F;
//        System.out.println(expTime[3] - h);
//        System.out.println((expTime[3] - h) / t);
//        System.out.println(Math.exp(-(expTime[3] - h) / t));
//        System.out.println(1 - Math.exp(-(expTime[3] - h) / t));
//        System.out.println(k * dZ * (1 - Math.exp(-(expTime[3] - h) / t)));
//        System.out.println(expRez[0] + k * dZ * (1 - Math.exp(-(expTime[3] - h) / t)));
        for (int i = 0; i < expTime.length; i++) {
            float rasRez = 0F;
            if (expTime[i] <= h) {
                rasRez += expRez[0] + 0F;
            } else {
                rasRez += expRez[0] + k * dZ * (1 - Math.exp(-(expTime[i] - h) / t));
            }
//            System.out.println(expTime[i] + "  " + rasRez);
            sumX2 += Math.pow((expRez[i] - rasRez), 2);
    }
        return sumX2;
    }
}
