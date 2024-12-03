package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import deduplication.DeduplicateProcessor;

import jsontypes.Leads;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class Main {
    protected static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) {
        if(args.length == 0 || args[0] == null || args[0].isEmpty()) {
            System.out.println("Please enter input filename.");
            return;
        }
        final String filename = args[0];
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Leads leads = objectMapper.readValue(new File(filename), Leads.class);
            DeduplicateProcessor deduplicateProcessor = new DeduplicateProcessor();
            Leads output = deduplicateProcessor.processLeadsForDedupes(leads);
            objectMapper.writeValue(new File("output.json"), output);
        } catch (IOException e) {
            LOGGER.error("Error reading file: {}", String.valueOf(e));
        }
    }
}