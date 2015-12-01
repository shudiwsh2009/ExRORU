package com.iise.shudi.exroru.evaluation;

import com.iise.shudi.exroru.RefinedOrderingRelation;
import com.iise.shudi.exroru.RormSimilarity;
import org.jbpt.petri.NetSystem;
import org.jbpt.petri.io.PNMLSerializer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class SingleTimeAnalysis {
    public static final String ROOT_FOLDER = "C:\\Users\\Shudi\\Desktop\\rorm\\";
//    public static final String ROOT_FOLDER = "E:\\wangshuhao\\Documents\\ExRORU\\";

    public static void main(String[] args) throws Exception {
        RefinedOrderingRelation.SDA_WEIGHT = 0.0;
        RefinedOrderingRelation.IMPORTANCE = false;
        SingleTimeAnalysis sta = new SingleTimeAnalysis();
        String saveFilenamePrefix = "ExRORU_SingleTimeAnalysis_151201b_";
        sta.analyze("DG", saveFilenamePrefix + "DG.csv");
        sta.analyze("TC", saveFilenamePrefix + "TC.csv");
        sta.analyze("SAP", saveFilenamePrefix + "SAP.csv");
    }

    public void analyze(String modelFolderName, String saveFilename) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(ROOT_FOLDER + saveFilename));
        writer.write(",totalTime,cpu1,lc1,causal1,concurrent1,sda1,importance1"
                + ",cpu2,lc2,causal2,concurrent2,sda2,importance2,similarity");
        writer.newLine();
        File[] modelFiles = new File(ROOT_FOLDER + modelFolderName).listFiles();
        List<NetSystem> nets = loadNets(modelFiles);
        computeTime(writer, nets);
        writer.close();
    }

    private List<NetSystem> loadNets(File[] files) throws Exception {
        List<NetSystem> nets = new ArrayList<>();
        PNMLSerializer pnmlSerializer = new PNMLSerializer();
        for(int i = 0; i < files.length; ++i) {
            NetSystem net = pnmlSerializer.parse(files[i].getAbsolutePath());
            net.setName(files[i].getName());
            nets.add(net);
        }
        return nets;
    }

    private void computeTime(BufferedWriter writer, List<NetSystem> nets) throws Exception {
        int totalCount = nets.size() * (nets.size() - 1) / 2, finish = 0;
        RormSimilarity rorm = new RormSimilarity();
        for(int p = 0; p < nets.size(); ++p) {
            for(int q = p + 1; q < nets.size(); ++q) {
                System.out.println((++finish) + "/" + totalCount + " " + nets.get(p).getName() + " & " + nets.get(q).getName());
                long[] times1 = rorm.similarityWithTime(nets.get(p), nets.get(q));
                long[] times2 = rorm.similarityWithTime(nets.get(p), nets.get(q));
                long[] times3 = rorm.similarityWithTime(nets.get(p), nets.get(q));
                long[] times4 = rorm.similarityWithTime(nets.get(p), nets.get(q));
                long[] times5 = rorm.similarityWithTime(nets.get(p), nets.get(q));
                long[] times = new long[times1.length];
                for (int i = 0; i < times.length; ++i) {
                    times[i] = (times1[i] + times2[i] + times3[i] + times4[i] + times5[i]) / 5;
                }
                writer.write(nets.get(p).getName() + " & " + nets.get(q).getName());
                for (long t : times) {
                    writer.write("," + ((double) t) / 1e6);
                }
                writer.newLine();
            }
        }
    }
}
