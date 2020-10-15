package dn.com.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class Rendering {
    private String document;
    private Integer page;
    private String uID;
    private List<String> startRendering;
    private List<String> getRendering;

    public Rendering(final String document, final Integer page, final String uId, final List<String> startRendering, final List<String> getRendering) {
        this.document = document;
        this.page = page;
        this.uID = uId;
        this.startRendering = startRendering;
        this.getRendering = getRendering;
    }

    public Rendering() {

    }

    public String getDocument() {
        return document;
    }

    @XmlElement
    public void setDocument(final String document) {
        this.document = document;
    }

    public Integer getPage() {
        return page;
    }

    @XmlElement
    public void setPage(final Integer page) {
        this.page = page;
    }

    public String getuID() {
        return uID;
    }

    @XmlElement(name = "uid")
    public void setuID(final String uID) {
        this.uID = uID;
    }

    public List<String> getStartRendering() {
        return startRendering;
    }

    @XmlElement(name = "start")
    public void setStartRendering(final List<String> startRendering) {
        this.startRendering = startRendering;
    }

    public void addStart(final String startDatetime) {
        if (this.startRendering == null) {
            this.startRendering = new ArrayList<>();
        }
        this.startRendering.add(startDatetime);

    }

    @XmlElement(name = "get")
    public void setGetRendering(final List<String> getRendering) {
        this.getRendering = getRendering;
    }

    public List<String> getGetRendering() {
        return getRendering;
    }

    public void addGetRendering(final String getDatetime) {
        if (this.getRendering == null) {
            this.getRendering = new ArrayList<>();
        }
        this.getRendering.add(getDatetime);
    }

    @Override
    public String toString() {
        return "Rendering{" +
                "document='" + document + '\'' +
                ", page=" + page +
                ", uID='" + uID + '\'' +
                ", startRendering=" + startRendering +
                ", getRendering=" + getRendering + "}\n";
    }
}
