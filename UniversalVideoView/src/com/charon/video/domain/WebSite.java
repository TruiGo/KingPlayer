
package com.charon.video.domain;

/**
 * Entity of the online video website.
 * 
 * @author CharonChui
 */
public class WebSite {
    private String title;
    private String url;
    private int resourceID;

    public WebSite(String title, String url, int resourceID) {
        super();
        this.title = title;
        this.url = url;
        this.resourceID = resourceID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

}
