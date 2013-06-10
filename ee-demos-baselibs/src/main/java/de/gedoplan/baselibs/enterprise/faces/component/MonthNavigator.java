package de.gedoplan.baselibs.enterprise.faces.component;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.model.SelectItem;

/**
 * Backing Bean zur Composite Component monthNavigator.
 * 
 * @author dw
 */
@FacesComponent("de.gedoplan.MonthNavigator")
public class MonthNavigator extends UIInput implements NamingContainer
{
  private Locale           locale;

  private UISelectOne      currentMonthField;
  private List<SelectItem> monthSelectList;

  private UIInput          currentYearField;

  /**
   * Konstruktor.
   */
  public MonthNavigator()
  {
    // Locale von View übernehmen
    this.locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    // Auswahlliste für die Monats-Drop-Down-Box erstellen
    Calendar calendar = Calendar.getInstance();
    calendar.clear();
    int minMonth = calendar.getMinimum(Calendar.MONTH);
    int maxMonth = calendar.getMaximum(Calendar.MONTH);

    this.monthSelectList = new ArrayList<SelectItem>();
    SimpleDateFormat formatter = new SimpleDateFormat("MMM", this.locale);
    for (int i = minMonth; i <= maxMonth; ++i)
    {
      calendar.set(Calendar.MONTH, i);
      this.monthSelectList.add(new SelectItem(i, formatter.format(calendar.getTime())));
    }
  }

  public List<SelectItem> getMonthSelectList()
  {
    return this.monthSelectList;
  }

  public UISelectOne getCurrentMonthField()
  {
    return this.currentMonthField;
  }

  public void setCurrentMonthField(UISelectOne currentMonthField)
  {
    this.currentMonthField = currentMonthField;
  }

  public UIInput getCurrentYearField()
  {
    return this.currentYearField;
  }

  public void setCurrentYearField(UIInput currentYearField)
  {
    this.currentYearField = currentYearField;
  }

  /**
   * Aktionsmethode für den Down-Button.
   */
  public void decMonth()
  {
    addMonth(-1);
  }

  /**
   * Aktionsmethode für den Up-Button.
   */
  public void incMonth()
  {
    addMonth(1);
  }

  /**
   * Helfermethode zum Verändern des Monats.
   * 
   * @param amount Monatsdifferenz
   */
  private void addMonth(int amount)
  {
    // Aktuelle Werte aus den Monats- und Jahres-Feldern holen und um amount Monate verändern
    Calendar cal = getMonthAndYearFieldAsCalendar();
    cal.add(Calendar.MONTH, amount);

    // Ergebnis in Komponentenwert und die beiden Felder speichern
    setValue(cal.getTime());
    updateMonthAndYearFields(cal);
  }

  /**
   * Aktuelle Werte aus Monats- und Jahreskomponente auslesen.
   * 
   * Als Tag wird 15 eingesetzt, der Zeitanteil enhält 0.
   * 
   * @return Ermitteltes Datum
   */
  private Calendar getMonthAndYearFieldAsCalendar()
  {
    Calendar cal = Calendar.getInstance();
    cal.clear();

    // Die aktuellen Werte können hier mit getValue abgeholt werden, da die Komponenten immediate sind
    int currentMonth = getIntValue(this.currentMonthField.getValue());
    int currentYear = getIntValue(this.currentYearField.getValue());
    cal.set(currentYear, currentMonth, 15);
    return cal;
  }

  /**
   * Aktuellen Wert der Komponente in die Monats- und Jahreskomponenten schreiben.
   */
  private void updateMonthAndYearFields()
  {
    Calendar cal = getValueAsCalendar();
    updateMonthAndYearFields(cal);
  }

  /**
   * Neue Werte in Monats- und Jahreskomponente schreiben.
   * 
   * @param cal Einzutragendes Datum
   */
  private void updateMonthAndYearFields(Calendar cal)
  {
    this.currentMonthField.setValue(cal.get(Calendar.MONTH));
    this.currentYearField.setValue(cal.get(Calendar.YEAR));
  }

  /**
   * Aktuellen Wert der komponente liefern.
   * 
   * @return Ermitteltes Datum
   */
  private Calendar getValueAsCalendar()
  {
    Calendar cal = Calendar.getInstance();
    Date date = (Date) getValue();
    if (date != null)
    {
      cal.setTime(date);
    }
    return cal;
  }

  /**
   * Helfermethode zur Konvertierung der Monats- und Jahreswerte.
   * 
   * @param value Wert aus der UI
   * @return konvertierter Wert
   */
  private int getIntValue(Object value)
  {
    try
    {
      // Falls ein Integer geliefert wird, nur un-boxen
      if (value instanceof Integer)
      {
        return (Integer) value;
      }

      // Fals ein String geliefert wird, Zahl daraus parsen
      if (value instanceof String)
      {
        return Integer.valueOf((String) value);
      }
    }
    catch (Exception exception)
    {
      // Exceptions führen zu FacesMessage unten
    }

    // Applikations-Message-Bundle bestimmen
    String bundleName = FacesContext.getCurrentInstance().getApplication().getMessageBundle();
    if (bundleName == null)
    {
      bundleName = "javax.faces.Messages";
    }
    ResourceBundle bundle = ResourceBundle.getBundle(bundleName);

    // Meldung des Integer-Konverters (miss)brauchen
    String messagePattern = bundle.getString("javax.faces.converter.IntegerConverter.INTEGER");
    FacesMessage facesMessage = new FacesMessage(MessageFormat.format(messagePattern, value, 2011, getClientId()));
    facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
    throw new ConverterException(facesMessage);
  }

  /**
   * {@inheritDoc}
   * 
   * @see javax.faces.component.UIInput#getFamily()
   */
  @Override
  public String getFamily()
  {
    return "javax.faces.NamingContainer";
  }

  /**
   * Im Request übertragene Werte liefern.
   * 
   * Das Ergebnis dieser Methode wird - wenn nicht <code>null</code> - an {@link #getConvertedValue(FacesContext, Object)}
   * übergeben.
   * 
   * {@inheritDoc}
   * 
   * @see javax.faces.component.UIInput#getSubmittedValue()
   */
  @Override
  public Object getSubmittedValue()
  {
    return getMonthAndYearFieldAsCalendar();
  }

  /**
   * Eingabewert in Zieltyp Date konvertieren.
   * 
   * {@inheritDoc}
   * 
   * @see javax.faces.component.UIInput#getConvertedValue(javax.faces.context.FacesContext, java.lang.Object)
   */
  @Override
  protected Object getConvertedValue(FacesContext context, Object newSubmittedValue)
  {
    return ((Calendar) newSubmittedValue).getTime();
  }

  /**
   * {@inheritDoc}
   * 
   * @see javax.faces.component.UIComponentBase#encodeBegin(javax.faces.context.FacesContext)
   */
  @Override
  public void encodeBegin(FacesContext context) throws IOException
  {
    // Komponentenwert in Monats- und Jahresfeld schreiben
    updateMonthAndYearFields();

    super.encodeBegin(context);
  }
}
