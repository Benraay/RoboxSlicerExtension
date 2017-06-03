package com.roboxing.slicerextension.control;

import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * Created by benjaminraaymakers on 03/06/2017.
 */
public class PostProcessor {
    public PostProcessor(String gcodeFileToProcess) throws IOException {

        File input = new File(gcodeFileToProcess);
        File output = new File(gcodeFileToProcess+".tmp");
        Scanner sc = new Scanner(input);
        PrintWriter printer = new PrintWriter(output);

        int layerCount=0;

        while (sc.hasNextLine()) {
            String strLine = sc.nextLine();
            //Matcher m = Pattern.compile("^(;LAYER:)").matcher(strLine);
            if(Pattern.compile("^(;LAYER:)").matcher(strLine).find()){
                layerCount++;
                System.out.println (strLine);
            }
        }
        System.out.println ("total layers : "+layerCount);
        //reset scanner
        sc = new Scanner(input);

        double minExtrusionLenght = 0.3;		// Minimum extrusion length before we will allow a retraction
        double minTravelDistance = 0.01;  // Minimum distance of travel before we actually take the command seriously

        String oldHint = "";
        String hint = "";
        double currentX = 0.00;
        double currentY = 0.00;
        double currentZ = 0.00;
        double commandDistance = 0.00;
        boolean outputZ = false;
        boolean layerChange = false;
        int currentSpeed = 0;
        int retractCount = 0;
        int travelMoveLastFileSize = 0;
        boolean lastMoveWasTravel = false;
        boolean lastMoveWasRetract = false;
        double extrusionAfterRetraction = 0;
        boolean retracted = false;


        String commandX = "false";
        String commandY = "false";
        String commandZ = "false";
        String commandE = "false";
        String commandSpeed = "false";
        String comment = "false";


        while (sc.hasNextLine()) {
            String strLine = sc.nextLine();
            Matcher m = Pattern.compile("(X([\\-0-9\\.]+)\\s+Y([\\-0-9\\.]+))").matcher(strLine);
            if (m.find()){
                double newX = Double.parseDouble(m.group(2));
                double newY = Double.parseDouble(m.group(3));

                commandDistance = Math.sqrt(Math.pow(newX - currentX, 2) + Math.pow(newY - currentY, 2));
            }
            m = Pattern.compile("^(M204\\s+S(\\d+))").matcher(strLine);
            if (m.find()) {
                //printf NEW "M201 X%d Y%d Z%d E2000\n", $2, $2, $2;
                printer.write(String.format("M201 X%d Y%d Z%d E2000\n",m.group(2),m.group(2),m.group(2)));
            } else if (Pattern.compile("^(M190\\s)").matcher(strLine).find()) {
                // Remove bed temperature settings
            } else if (Pattern.compile("^(M104\\s)").matcher(strLine).find()) {
                // Remove nozzle temperature settings
            } else if (Pattern.compile("^(M109\\s)").matcher(strLine).find()) {
                // Remove set temperature and wait
            } else if (Pattern.compile("^(G21\\s)").matcher(strLine).find()) {
                // Remove set units to mm
            } else if (Pattern.compile("^(G90\\s)").matcher(strLine).find()) {
                // Remove use absolute coordinates
            } else if (Pattern.compile("^(M83\\s)").matcher(strLine).find()) {
                // Remove use relative distances for extrusion
            }else{
                System.out.println (strLine);
                printer.write(strLine+"\n");
            }


        }
    }
}
