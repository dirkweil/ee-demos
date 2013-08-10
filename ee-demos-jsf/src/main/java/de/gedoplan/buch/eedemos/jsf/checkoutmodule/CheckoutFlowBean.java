/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package de.gedoplan.buch.eedemos.jsf.checkoutmodule;

import java.io.Serializable;

import javax.faces.flow.FlowScoped;
import javax.inject.Named;

/**
 * Backing bean for CheckoutFlow.
 */
@Named
@FlowScoped("checkoutFlow")
public class CheckoutFlowBean implements Serializable
{

  private static final long serialVersionUID = 1L;
  private String            name;
  private String            addressLine1;
  private String            addressLine2;
  private String            city;
  private String            state;
  private String            country          = "United States";
  private String            postalCode;
  private String            ccName;
  private String            ccNum;
  private String            ccExpDate;

  public String getReturnValue()
  {
    return "/index";
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getAddressLine1()
  {
    return this.addressLine1;
  }

  public void setAddressLine1(String addressLine1)
  {
    this.addressLine1 = addressLine1;
  }

  public String getAddressLine2()
  {
    return this.addressLine2;
  }

  public void setAddressLine2(String addressLine2)
  {
    this.addressLine2 = addressLine2;
  }

  public String getCity()
  {
    return this.city;
  }

  public void setCity(String city)
  {
    this.city = city;
  }

  public String getState()
  {
    return this.state;
  }

  public void setState(String state)
  {
    this.state = state;
  }

  public String getCountry()
  {
    return this.country;
  }

  public void setCountry(String country)
  {
    this.country = country;
  }

  public String getPostalCode()
  {
    return this.postalCode;
  }

  public void setPostalCode(String postalCode)
  {
    this.postalCode = postalCode;
  }

  public String getCcName()
  {
    return this.ccName;
  }

  public void setCcName(String ccName)
  {
    this.ccName = ccName;
  }

  public String getCcNum()
  {
    return this.ccNum;
  }

  public void setCcNum(String ccNum)
  {
    this.ccNum = ccNum;
  }

  public String getCcExpDate()
  {
    return this.ccExpDate;
  }

  public void setCcExpDate(String ccExpDate)
  {
    this.ccExpDate = ccExpDate;
  }
}
