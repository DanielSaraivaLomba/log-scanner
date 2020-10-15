package dn.com.model;

import javax.xml.bind.annotation.XmlElement;

public class Summary {

    private Integer count;
    private Integer duplicates;
    private Integer unnecessary;

    public Summary(final Integer count, final Integer duplicates, final Integer unnecessary) {
        this.count = count;
        this.duplicates = duplicates;
        this.unnecessary = unnecessary;
    }

    public Summary() {
    }

    public Integer getCount() {
        return count;
    }

    @XmlElement
    public void setCount(final Integer count) {
        this.count = count;
    }

    public Integer getDuplicates() {
        return duplicates;
    }

    @XmlElement
    public void setDuplicates(final Integer duplicates) {
        this.duplicates = duplicates;
    }

    public Integer getUnnecessary() {
        return unnecessary;
    }

    @XmlElement
    public void setUnnecessary(final Integer unnecessary) {
        this.unnecessary = unnecessary;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "count=" + count +
                ", duplicates=" + duplicates +
                ", unnecessary=" + unnecessary +
                "}\n";
    }
}