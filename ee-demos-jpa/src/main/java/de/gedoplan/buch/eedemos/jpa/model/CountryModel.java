package de.gedoplan.buch.eedemos.jpa.model;

import de.gedoplan.buch.eedemos.jpa.entity.Continent;
import de.gedoplan.buch.eedemos.jpa.entity.Country;
import de.gedoplan.buch.eedemos.jpa.repository.CountryRepository;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class CountryModel
{
  @Inject
  private CountryRepository countryRepository;
  private Country           currentCountry;

  public void insertTestData()
  {
    Country country = new Country("DE", "Germany", "49", "D", 81879976, Continent.EUROPE);
    this.countryRepository.insert(country);
  }

  public Country getCurrentCountry()
  {
    fillCurrentCountry();
    return this.currentCountry;
  }

  public void updateCountry()
  {
    fillCurrentCountry();
    if (this.currentCountry != null)
    {
      if (!"Deutschland".equals(this.currentCountry.getName()))
      {
        this.currentCountry.setName("Deutschland");
      }
      else
      {
        this.currentCountry.setName("Germany");
      }
    }
    this.countryRepository.update(this.currentCountry);
  }

  public void deleteCountry()
  {
    this.countryRepository.delete("DE");
  }

  private void fillCurrentCountry()
  {
    if (this.currentCountry == null)
    {
      this.currentCountry = this.countryRepository.findById("DE");
    }
  }

}
