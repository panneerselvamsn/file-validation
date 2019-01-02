package com.file.validation.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "records")
public class Records
{
    private Record[] record;

    public Record[] getRecord ()
    {
        return record;
    }

    @XmlElement(name = "record")
    public void setRecord (Record[] record)
    {
        this.record = record;
    }

    @Override
    public String toString()
    {
        return "Records [record = "+record+"]";
    }
}
