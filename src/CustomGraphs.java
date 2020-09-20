import Calculation.StaticFactor;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.shape.Line;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public interface CustomGraphs {

    public static ScatterChart LineStreaamGraph(
            ArrayList<ArrayList<Double>> IsList,
            ArrayList<ArrayList<Double>> VsList,
            ArrayList<String> names){

        //defining the axes
        final NumberAxis xAxis = new NumberAxis(); // we are gonna plot against time
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("V");
        xAxis.setSide(Side.BOTTOM);
        xAxis.setAnimated(true); // axis animations are removed
        yAxis.setLabel("I");
        yAxis.setAnimated(false); // axis animations are removed

        //creating the line chart with two axis created above
        final ScatterChart<Number, Number> lineChart = new ScatterChart<>(xAxis, yAxis);
        lineChart.setAnimated(true); // disable animations
        //lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);

        //defining a series to display data
        ArrayList<XYChart.Series<Number, Number>> seriesList = new ArrayList<>();
        for(int i=0; i< IsList.size(); i++){
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            ArrayList<Double> Is = IsList.get(i);
            ArrayList<Double> Vs = VsList.get(i);
            series.setName(names.get(i));
            for(int j=0; j<Is.size(); j++){
                double x = Double.parseDouble(new DecimalFormat("##.#####").format(Vs.get(j)));
                double y = Double.parseDouble(new DecimalFormat("##.####").format(Is.get(j)));
                if(x > 0 & y > 0) {
                    series.getData().add(new XYChart.Data<>(x, y));
                }
            }
            seriesList.add(series);
        }


        // add series to chart
        lineChart.getData().addAll(seriesList);

        return lineChart;
    }

    public static LineChart pvGraph(
            ArrayList<ArrayList<Double>> IsList,
            ArrayList<ArrayList<Double>> VsList,
            ArrayList<String> names){

        //defining the axes
        final NumberAxis xAxis = new NumberAxis(); // we are gonna plot against time
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("V");
        xAxis.setSide(Side.BOTTOM);
        xAxis.setAnimated(true); // axis animations are removed
        yAxis.setLabel("I");
        yAxis.setAnimated(false); // axis animations are removed

        //creating the line chart with two axis created above
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setAnimated(true); // disable animations
        //lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);

        //defining a series to display data
        ArrayList<XYChart.Series<Number, Number>> seriesList = new ArrayList<>();
        for(int i=0; i< IsList.size(); i++){
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            ArrayList<Double> Is = IsList.get(i);
            ArrayList<Double> Vs = VsList.get(i);
            series.setName(names.get(i));
            for(int j=0; j<Is.size(); j++){

                double x = Double.parseDouble(new DecimalFormat("##.#####").format(Vs.get(j)));
                double y = Double.parseDouble(new DecimalFormat("##.####").format(Is.get(j)));
                if(x > 0 & y > 0) {
                    series.getData().add(new XYChart.Data<>(x, y));
                }
            }
            seriesList.add(series);
        }


        // add series to chart
        lineChart.getData().addAll(seriesList);

        return lineChart;
    }
}
