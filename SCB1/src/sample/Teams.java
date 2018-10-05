package sample;

public class Teams {
    private String  name,captain,contact,organization;



    public Teams(  String name, String captain, String contact, String organization)
    {

        this.name = name;
        this.captain = captain;
        this.contact = contact;
        this.organization = organization;
    }
    //getters
    public String getCaptain() {
        return captain;
    }

    public String getContact() {
        return contact;
    }

    public String getOrganization() {
        return organization;
    }

    public String getName() {

        return name;
    }
}
