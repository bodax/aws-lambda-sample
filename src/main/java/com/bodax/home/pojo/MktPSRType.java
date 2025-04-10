package com.bodax.home.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MktPSRType {
    @JacksonXmlProperty(localName = "psrType")
    private String psrType;
}
