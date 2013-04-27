package de.gedoplan.buch.eedemos.jsf.model;

import de.gedoplan.buch.eedemos.jsf.converter.TextToIntConverter;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.convert.Converter;
import javax.inject.Named;

@Named
@RequestScoped
public class ConverterModel
{

  private Date       someDate   = new Date();
  private BigDecimal someNumber = new BigDecimal(348.12345);
  private int        someInt    = 1;
  private Color      someColor  = Color.red;

  public Date getSomeDate()
  {
    return this.someDate;
  }

  public void setSomeDate(Date someDate)
  {
    this.someDate = someDate;
  }

  public BigDecimal getSomeNumber()
  {
    return this.someNumber;
  }

  public void setSomeNumber(BigDecimal someNumber)
  {
    this.someNumber = someNumber;
  }

  public int getSomeInt()
  {
    return this.someInt;
  }

  public void setSomeInt(int someInt)
  {
    this.someInt = someInt;
  }

  public Color getSomeColor()
  {
    return this.someColor;
  }

  public void setSomeColor(Color someColor)
  {
    this.someColor = someColor;
  }

  public Converter getTextToIntConverter()
  {
    return new TextToIntConverter();
  }
}
