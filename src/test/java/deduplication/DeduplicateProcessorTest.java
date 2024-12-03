package deduplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import jsontypes.Leads;

import java.io.File;
import java.io.IOException;

class DeduplicateProcessorTest {

    @org.junit.jupiter.api.Test
    void processLeadsForDedupes() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Leads leads = objectMapper.readValue(new File("code_challenge_leads.json"), Leads.class);

        } catch (IOException e) {
            System.out.println("Error reading file");
            System.out.println(e);
        }
    }
}