package dn.com.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "report")
public class Report {

    @XmlElement(name = "rendering")
    private List<Rendering> renderings;
    @XmlElement()
    private Summary summary;

    public void setRenderings(final List<Rendering> renderings) {
        this.renderings = renderings;
    }

    public void setSummary(final Summary summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Report{" +
                "renderings=" + renderings +
                "summary=" + summary +
                '}';
    }
}
