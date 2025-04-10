package com.bodax.home.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JacksonXmlRootElement(localName = "GL_MarketDocument")
@Setter
@Getter
public class GenerationData {

    @JacksonXmlProperty(localName = "mRID")
    private String mRID;

    @JacksonXmlProperty(localName = "revisionNumber")
    private int revisionNumber;

    @JacksonXmlProperty(localName = "type")
    private String type;

    @JacksonXmlProperty(localName = "process.processType")
    private String processType;

    @JacksonXmlProperty(localName = "createdDateTime")
    private String createdDateTime;

    @JacksonXmlProperty(localName = "time_Period.timeInterval")
    private TimeInterval timeInterval;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TimeSeries")
    private List<TimeSeries> timeSeries;
}
