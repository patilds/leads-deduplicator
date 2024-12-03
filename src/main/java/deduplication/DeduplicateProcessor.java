package deduplication;

import jsontypes.Lead;
import jsontypes.Leads;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeduplicateProcessor {
    protected static final Logger LOGGER = LogManager.getLogger();
    private static final String ID_PREFIX = "ID_";
    private static final String EMAIL_PREFIX = "EMAIL_";

    /**
     *
     * @param leads
     * @return
     */
    public Leads processLeadsForDedupes(Leads leads) {
        Map<String, Lead> idEmailLookup = new HashMap<String, Lead>();
        for(Lead lead : leads.getLeads()) {
            if(idEmailLookup.containsKey(ID_PREFIX + lead.get_id())){
                Lead existingLead = idEmailLookup.get(ID_PREFIX + lead.get_id());
                LOGGER.info("Duplicate entry found with id: {}", lead.get_id());
                processDuplicates(existingLead, lead, idEmailLookup);

            }
            else if(idEmailLookup.containsKey(EMAIL_PREFIX + lead.getEmail())){
                Lead existingLead = idEmailLookup.get(EMAIL_PREFIX + lead.getEmail());
                LOGGER.info("Duplicate entry found with email: {}", lead.getEmail());
                processDuplicates(existingLead, lead, idEmailLookup);
            } else {
                idEmailLookup.put(ID_PREFIX + lead.get_id(), lead);
                idEmailLookup.put(EMAIL_PREFIX + lead.getEmail(), lead);
            }
        }
        List<Lead> result = new ArrayList<>();
        for(String key : idEmailLookup.keySet()){
            if(key.startsWith(ID_PREFIX)){
                result.add(idEmailLookup.get(key));
            }
        }
        return new Leads(result);
    }

    private void processDuplicates(Lead oldLead, Lead newLead, Map<String, Lead> idEmailLookup) {
        Instant oldDateInstance = Instant.parse(oldLead.getEntryDate());
        Instant newDateInstance = Instant.parse(newLead.getEntryDate());

        if(oldDateInstance.compareTo(newDateInstance) <= 0){
            idEmailLookup.remove(ID_PREFIX + oldLead.get_id());
            idEmailLookup.remove(EMAIL_PREFIX + oldLead.getEmail());

            idEmailLookup.put(ID_PREFIX + newLead.get_id(), newLead);
            idEmailLookup.put(EMAIL_PREFIX + newLead.getEmail(), newLead);

            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("Old object: ").append(oldLead).append("\n");
            sb.append("New object: ").append(newLead).append("\n");;
            sb.append("Field differences: \n").append(getFieldDifferences(oldLead, newLead)).append("\n");
            sb.append("------------------");
            LOGGER.info(sb);
        }
    }

    private String getFieldDifferences(Lead oldObject, Lead newObject){
        StringBuilder sb = new StringBuilder();
        if(!oldObject.get_id().equals(newObject.get_id())){
            sb.append("Id: From: ").append(oldObject.get_id()).append(", To: ").append(newObject.get_id()).append(", ");
        }
        if(!oldObject.getEmail().equals(newObject.getEmail())){
            sb.append("Email: From: ").append(oldObject.getEmail()).append(", To: ").append(newObject.getEmail()).append(", ");
        }
        if(!oldObject.getAddress().equals(newObject.getAddress())){
            sb.append("Address: From: ").append(oldObject.getAddress()).append(", To: ").append(newObject.getAddress()).append(", ");
        }
        if(!oldObject.getFirstName().equals(newObject.getFirstName())){
            sb.append("First name: From: ").append(oldObject.getFirstName()).append(", To: ").append(newObject.getFirstName()).append(", ");
        }
        if(!oldObject.getLastName().equals(newObject.getLastName())){
            sb.append("Last Name: From: ").append(oldObject.getLastName()).append(", To: ").append(newObject.getLastName()).append(", ");
        }
        if(!oldObject.getEntryDate().equals(newObject.getEntryDate())){
            sb.append("Entry date: From: ").append(oldObject.getEntryDate()).append(", To: ").append(newObject.getEntryDate());
        }
        return sb.toString();
    }
}
