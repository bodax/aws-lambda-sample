package com.bodax.home.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Period {

    @JacksonXmlProperty(localName = "resolution")
    private String resolution;

    @JacksonXmlProperty(localName = "timeInterval")
    private TimeInterval timeInterval;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Point")
    private List<Point> points;
}
