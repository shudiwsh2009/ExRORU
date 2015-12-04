package com.iise.shudi.util;

import org.processmining.framework.models.petrinet.PetriNet;
import org.processmining.importing.pnml.PnmlImport;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Shudi on 2015/12/3.
 */
public class ModelStatics {

    public static final String ROOT_FOLDER = "C:\\Users\\Shudi\\Desktop\\rorm\\";

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        ModelStatics filter = new ModelStatics();
        filter.filt("IBM");
    }

    public void filt(String dataset) throws Exception {
        PnmlImport pnmlImport = new PnmlImport();
        File folder = new File(ROOT_FOLDER + dataset);
        File[] files = folder.listFiles();
        FileInputStream input;
        PetriNet pn;
        int minT = Integer.MAX_VALUE, minP = Integer.MAX_VALUE, minA = Integer.MAX_VALUE;
        int maxT = Integer.MIN_VALUE, maxP = Integer.MIN_VALUE, maxA = Integer.MIN_VALUE;
        long totalT = 0, totalP = 0, totalA = 0;
        for (File file : files) {
            input = new FileInputStream(file);
            System.out.println(file.getAbsolutePath());
            pn = pnmlImport.read(input);
            input.close();
            int curT = pn.getTransitions().size();
            int curP = pn.getPlaces().size();
            int curA = pn.getEdges().size();
            minT = Math.min(minT, curT);
            maxT = Math.max(maxT, curT);
            minP = Math.min(minP, curP);
            maxP = Math.max(maxP, curP);
            minA = Math.min(minA, curA);
            maxA = Math.max(maxA, curA);
            totalT += curT;
            totalP += curP;
            totalA += curA;
        }
        double avgT = ((double) totalT) / files.length;
        double avgP = ((double) totalP) / files.length;
        double avgA = ((double) totalA) / files.length;
        System.out.println(files.length);
        System.out.println(avgT + " " + avgP + " " + avgA);
        System.out.println(minT + " " + minP + " " + minA);
        System.out.println(maxT + " " + maxP + " " + maxA);
    }

}
