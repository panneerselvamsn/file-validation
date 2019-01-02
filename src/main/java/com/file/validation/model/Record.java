package com.file.validation.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Record
{
    private String accountNumber;

    private String description;

    private String mutation;

    private String endBalance;

    private String startBalance;

    private String reference;

    public String getAccountNumber ()
    {
        return accountNumber;
    }

    public void setAccountNumber (String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getMutation ()
    {
        return mutation;
    }

    public void setMutation (String mutation)
    {
        this.mutation = mutation;
    }

    public String getEndBalance ()
    {
        return endBalance;
    }

    public void setEndBalance (String endBalance)
    {
        this.endBalance = endBalance;
    }

    public String getStartBalance ()
    {
        return startBalance;
    }

    public void setStartBalance (String startBalance)
    {
        this.startBalance = startBalance;
    }

    public String getReference ()
    {
        return reference;
    }

    @XmlAttribute(name = "reference")
    public void setReference (String reference)
    {
        this.reference = reference;
    }

    @Override
    public String toString()
    {
        return "Record [accountNumber = "+accountNumber+", description = "+description+", mutation = "+mutation+", endBalance = "+endBalance+", startBalance = "+startBalance+", reference = "+reference+"]";
    }
}
