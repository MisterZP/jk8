package com.jk.demo.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengping on 2017/3/14.
 */
@Data
public class XmlElement {

    private String name;
    private List<XmlElement> elements = new ArrayList<>();
    private List<XmlAttribute> attributes = new ArrayList<>();

    public void addElements(XmlElement element){
        this.elements.add(element);
    }

    public void addAttribute(XmlAttribute attribute){
        this.attributes.add(attribute);
    }
}
