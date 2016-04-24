package ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos;

/**
 * Created by tco on 16-04-24.
 */
public class FlightReport {

    private String title;
    private String body;
    private int imageId = 0;


    public FlightReport(String title, String body ){
        this.title = title;
        this.body = body;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


}
