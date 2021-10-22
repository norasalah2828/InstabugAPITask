package resources;

//This enum for endpoints
public enum APIResources {

    ServicesAPI("/services"),
    InvalidAPI("/nora");
    private String resource;

    APIResources(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }


}
