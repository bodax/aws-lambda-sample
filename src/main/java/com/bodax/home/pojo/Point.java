package com.bodax.home.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Point {

    @JacksonXmlProperty(localName = "position")
    private int position;

    @JacksonXmlProperty(localName = "quantity")
    private int quantity;
}
