package com.bodax.home.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TimeSeries {

    @JacksonXmlProperty(localName = "mRID")
    private String mRID;

    @JacksonXmlProperty(localName = "businessType")
    private String businessType;

    @JacksonXmlProperty(localName = "objectAggregation")
    private String objectAggregation;

    @JacksonXmlProperty(localName = "curveType")
    private String curveType;

    @JacksonXmlProperty(localName = "MktPSRType")
    private MktPSRType mktPSRType;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Period")
    private Period period;
}
