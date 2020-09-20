import Calculation.IVGenarator;
import CorssProjectMethods.CorssProjectMethods;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends WindowModel {
    TextField dataSetLinkTV;
    Button openBtn;
    TextField EnsoleillementTF, TempératureTF;

    Pane VariablesHolder;
    TabPane TabPane;

    Button startBtn, addEsBtn, addTempBtn;
    AnchorPane graphPVHolder, graphIVHolder;

    VBox IHolder, VHolder, PHolder;
    HBox dataHolder;

    LineChart graphIV, graphPV;

    ArrayList<Double> I1s, I2s, V1s, V2s, P1s, P2s, Is, Vs, Ps;
    ArrayList<Double> tempList = new ArrayList<>();
    ArrayList<Double> ensList = new ArrayList<>();
    ArrayList<String> Names = new ArrayList<>();




    double E2, T2, icc;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ui/mainWindow.fxml"));
        initializeComponents(root);
        initilizeAttributes(primaryStage);
        primaryStage.setTitle("Characteristic I-V, P-V Graph tracer");
        root.minHeight(800);
        primaryStage.setScene(new Scene(root));
        //primaryStage.setResizable(false);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    void initializeComponents(Parent root) {
        dataSetLinkTV = (TextField) root.lookup("#dataSetLinkTV");
        openBtn = (Button) root.lookup("#openBtn");

        VariablesHolder = (Pane) root.lookup("#VariablesHolder");
        TabPane = (TabPane) root.lookup("#TabPane");

        EnsoleillementTF = (TextField) root.lookup("#EnsoleillementTF");
        TempératureTF = (TextField) root.lookup("#TempératureTF");
        startBtn = (Button) root.lookup("#StartBtn");

        dataHolder = (HBox) root.lookup("#dataHolder");
        IHolder = (VBox) root.lookup("#IHolder");
        VHolder = (VBox) root.lookup("#VHolder");
        PHolder = (VBox) root.lookup("#PHolder");

        graphIVHolder = (AnchorPane) root.lookup("#graphIVHolder");
        graphPVHolder = (AnchorPane) root.lookup("#graphPVHolder");

        addEsBtn = (Button) root.lookup("#addEsBtn");
        addTempBtn = (Button) root.lookup("#addTempBtn");

    }

    @Override
    void initilizeAttributes(Stage stage) {

        I1s = new ArrayList<>();
        I2s = new ArrayList<>();

        V1s = new ArrayList<>();
        V2s = new ArrayList<>();

        P1s = new ArrayList<>();
        P2s = new ArrayList<>();

        openBtn.setOnAction(event -> {
            File file  = CorssProjectMethods.openFile(stage);
            if(file != null){
                dataSetLinkTV.setText(file.getAbsolutePath());
                TabPane.setVisible(true);
                try {
                    BufferedReader csvReader = new BufferedReader(new FileReader(file.toString()));
                    String row;
                    int i=0;

                    while ((row = csvReader.readLine()) != null) {
                        String[] data = row.split(",");
                        if(i==0){
                            EnsoleillementTF.setText(data[0]);
                            TempératureTF.setText(data[1]);
                            icc = Double.parseDouble(data[2]);
                        }else{
                            /*
                            TextField IHolderTF = new TextField(data[0]);
                            IHolderTF.setEditable(false);
                            IHolderTF.setId(String.valueOf(i));
                             */

                            I1s.add(Double.valueOf(data[0]));
                            V1s.add(Double.valueOf(data[1]));
                            P1s.add(Double.valueOf(data[0]) * Double.valueOf(data[1]));
                        }

                        /*
                        TextField VHolderTF = new TextField(data[1]);
                        VHolderTF.setEditable(false);

                        TextField PHolderTF = new TextField(String.valueOf(Double.parseDouble(data[1]) * Double.parseDouble(data[0])));
                        PHolderTF.setEditable(false);

                        IHolder.getChildren().add(IHolderTF);
                        VHolder.getChildren().add(VHolderTF);
                        PHolder.getChildren().add(PHolderTF);
                         */
                        i++;
                    }
                    ArrayList<ArrayList<Double>> IsList = new ArrayList<>();
                    IsList.add(I1s);

                    ArrayList<ArrayList<Double>> VsList = new ArrayList<>();
                    VsList.add(V1s);

                    ArrayList<ArrayList<Double>> PsList = new ArrayList<>();
                    PsList.add(P1s);

                    ArrayList<String> Names = new ArrayList<>();
                    Names.add("Es 70.2%, T: 35C");
                    P1s = IVGenarator.generatePs(I1s, V1s);
                    E2 = Double.parseDouble(EnsoleillementTF.getText());
                    T2 = Double.parseDouble(TempératureTF.getText());

                    drawIV(VsList, IsList, graphIVHolder, Names);

                    LineChart pvGraph = CustomGraphs.pvGraph(PsList, VsList, Names);

                    drawPV(pvGraph,  graphPVHolder);
                    hillClimbing(IsList.get(0), VsList.get(0), 0.7, 0.2);

                    csvReader.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        startBtn.setOnAction(event -> {
            E2 = Double.parseDouble(EnsoleillementTF.getText());
            T2 = Double.parseDouble(TempératureTF.getText());
            ArrayList<ArrayList<Double>> IsList = new ArrayList<>();
            ArrayList<ArrayList<Double>> VsList = new ArrayList<>();
            ArrayList<ArrayList<Double>> PsList = new ArrayList<>();

            if (ensList.size() == 0 && tempList.size() == 0){
                I2s = IVGenarator.generateI2s(I1s, E2, T2, icc);
                IsList.add(I2s);

                V2s = IVGenarator.generateV2s(V1s, I1s,  E2, T2, icc);
                VsList.add(V2s);

                P2s = IVGenarator.generatePs(I2s, V2s);
                PsList.add(P2s);

                Names.add("Es: "+EnsoleillementTF.getText() + "%");
                Names.add("T:: "+TempératureTF.getText() + "C");
            }else{
                if(ensList.size() == 0){
                    for (double T2:tempList) {
                        I2s = IVGenarator.generateI2s(I1s, E2, T2, icc);
                        IsList.add(I2s);

                        V2s = IVGenarator.generateV2s(V1s, I1s,  E2, T2, icc);
                        VsList.add(V2s);

                        P2s = IVGenarator.generatePs(I2s, V2s);
                        PsList.add(P2s);
                    }
                }else{
                    for (double E2:ensList) {
                        I2s = IVGenarator.generateI2s(I1s, E2, T2, icc);
                        IsList.add(I2s);

                        V2s = IVGenarator.generateV2s(V1s, I1s,  E2, T2, icc);
                        VsList.add(V2s);

                        P2s = IVGenarator.generatePs(I2s, V2s);
                        PsList.add(P2s);
                    }
                }
            }



            drawIV(VsList, IsList, graphIVHolder, Names);
            drawIV(VsList, PsList, graphPVHolder, Names);
        });


        addTempBtn.setOnAction(e ->{
            ensList.clear();
            tempList.add(Double.valueOf(TempératureTF.getText()));
            Names.add("T: "+TempératureTF.getText()+"C");
        });

        addEsBtn.setOnAction(e ->{
            tempList.clear();
            ensList.add(Double.valueOf(EnsoleillementTF.getText()));
            Names.add("Es: "+EnsoleillementTF.getText()+"%");
        });
    }


    private void drawIV(ArrayList<ArrayList<Double>>  Xs, ArrayList<ArrayList<Double>>  Ys, AnchorPane container, ArrayList<String> names){
        ScatterChart graph = CustomGraphs.LineStreaamGraph(Ys, Xs, names);

        graph.setPrefSize(container.getPrefWidth(), container.getPrefHeight());

        container.getChildren().clear();

        container.getChildren().add(graph);
    }

    private void drawPV(LineChart graph, AnchorPane container){
        graph.setPrefSize(container.getPrefWidth(), container.getPrefHeight());

        container.getChildren().clear();

        container.getChildren().add(graph);
    }

    private void hillClimbing(ArrayList<Double>  I1s, ArrayList<Double> V1s, double alpha, double pas){

        int i = 0;  boolean found = false;

        Random rand = new Random();

        while(!found && i < I1s.size()){
            double Ia = I1s.get(i);
            double Va = V1s.get(i);
            double Pa = Ia * Va;

            double In = I1s.get(i+1);
            double Vn = V1s.get(i+1);

            double Pn = In * Vn;

            double diffP = Pn - Pa;

            System.out.println("Ia:" +Ia);
            System.out.println("In:" +In);
            System.out.println("Va:" +Va);
            System.out.println("Vn:" +Vn);
            System.out.println("Pa:" +Pa);
            System.out.println("Pn:" +Pn);
            System.out.println("diff:" +diffP);
             if (diffP <=0 ){

                 ResultsPane dataSetpreview = new ResultsPane(Ia, Va, Pa);
                 Stage previewStage = new Stage();
                 dataSetpreview.start(previewStage);
                 System.out
                         .println(i);
                 System.out.println("***********************************");
                 found = true;
             }else{

             }
            i++;
            System.out.println("---------------------------------------------------------------------------------");
        }


    }

}
