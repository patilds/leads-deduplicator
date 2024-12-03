package jsontypes;

import java.util.ArrayList;
import java.util.List;

public class Leads {
    private List<Lead> leads;
    Leads() {}
    public Leads(List<Lead> leads) {
        this.leads = leads;
    }

    public List<Lead> getLeads() {
        return leads;
    }

    public void setLeads(List<Lead> leads) {
        this.leads = leads;
    }
}
