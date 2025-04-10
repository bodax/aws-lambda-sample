package com.bodax.home.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TimeInterval {

    @JacksonXmlProperty(localName = "start")
    private LocalDateTime start;

    @JacksonXmlProperty(localName = "end")
    private LocalDateTime end;
}