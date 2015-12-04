package com.iise.shudi.util;

import org.processmining.framework.log.LogEvent;
import org.processmining.framework.models.petrinet.PetriNet;
import org.processmining.framework.models.petrinet.Transition;
import org.processmining.framework.models.petrinet.algorithms.PnmlWriter;
import org.processmining.importing.pnml.PnmlImport;

import java.io.*;

/**
 * Created by Shudi on 2015/11/25.
 */
public class AddLogEventBatch {
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        AddLogEventBatch batch = new AddLogEventBatch();
        batch.modModels("C:\\Users\\Shudi\\Desktop\\rorm\\scalability\\depth\\");
        System.exit(0);
    }

    public void modModels(String path) throws Exception {
        PnmlImport pnmlImport = new PnmlImport();
        File folder = new File(path);
        File[] files = folder.listFiles();
        for(File file : files) {
            FileInputStream input = new FileInputStream(file);
            System.out.println(file.getAbsolutePath());
            PetriNet pn = pnmlImport.read(input);
            input.close();
            pn.setName(file.getName());
            for(Transition t : pn.getTransitions()) {
                t.setLogEvent(new LogEvent(t.getIdentifier(), "auto"));
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            PnmlWriter.write(false, true, pn, writer);
            writer.close();
        }
    }
}
