package Calculation;

import java.util.ArrayList;

public interface IVGenarator {
    //Old school
    static ArrayList<Double> generateI(double i0,  int limit, double E1, double E2,double T1, double T2, double icc){
        ArrayList<Double> result = new ArrayList<>();
        result.add(i0);
        for(int i=1; i<limit; i++){
            double I = result.get(i-1) + icc * (E2 / (E1 - 1)) + StaticFactor.a  * (T2 - T1);
            result.add(I);
        }
        return result;
    }

    static ArrayList<Double> generateV(double v0,  double T1, double T2, int limit, ArrayList<Double> intensityList){

        ArrayList<Double> result = new ArrayList<>();
        result.add(v0);

        for(int i=1; i<limit; i++){
            double V = (result.get(i-1) + (T2 - T1) *
                    StaticFactor.b - StaticFactor.Rs *
                    (intensityList.get(i)-intensityList.get(i-1)) - StaticFactor.Kc *
                    intensityList.get(i) * (T2 - T1));
            result.add(V);
        }
        return result;
    }

    //Get Only I2

    static ArrayList<Double> generateI2s(ArrayList<Double> I1s,  double E2, double T2, double icc){
        ArrayList<Double> result = new ArrayList<>();
        for (double I1: I1s){
            double I2 = getI2(I1, E2, T2, icc);
            result.add(I2);
        }
        return result;
    }


    static ArrayList<Double> generateV2s(ArrayList<Double> V1s, ArrayList<Double> I1s, double E2, double T2, double icc){
        ArrayList<Double> result = new ArrayList<>();
        int i=0;
        for (double V1: V1s){
            double I1 = I1s.get(i);
            double I2 = getI2(I1, E2, T2,  icc);
            double V2 = getV2(V1, I1, I2, T2);
            result.add(V2);
            i++;
        }

        return result;
    }


    static double getI2(double I1,  double E2, double T2, double icc){
        double I2 = I1 + icc * (E2 / (StaticFactor.E1 - 1)) + StaticFactor.a  * (T2 - StaticFactor.T1);
        return I2;
    }

    static double getV2(double V1, double I1, double I2,  double T2){
        double V2 = V1 + ((T2 - StaticFactor.T1) * StaticFactor.b) - (StaticFactor.Rs * (I2-I1)) - (StaticFactor.Kc * I2 * (T2 - StaticFactor.T1));
        return V2;
    }



    static ArrayList<Double> generatePs(ArrayList<Double> Is,   ArrayList<Double> Vs){

        ArrayList<Double> result = new ArrayList<>();
        for(int i=0; i<Is.size(); i++){
            double P = Is.get(i) * Vs.get(i);
            result.add(P);
        }
        return result;
    }
}
