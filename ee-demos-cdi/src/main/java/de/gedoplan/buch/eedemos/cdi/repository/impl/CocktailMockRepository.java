package de.gedoplan.buch.eedemos.cdi.repository.impl;

import de.gedoplan.buch.eedemos.cdi.entity.Cocktail;
import de.gedoplan.buch.eedemos.cdi.entity.CocktailZutat;
import de.gedoplan.buch.eedemos.cdi.repository.CocktailRepository;
import de.gedoplan.buch.eedemos.cdi.stereotype.Mock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

//@Alternative
@Mock
public class CocktailMockRepository implements CocktailRepository
{
  private static Set<Cocktail> COCKTAILS = new HashSet<Cocktail>();

  static
  {
    CocktailZutat cassis = new CocktailZutat("CASS", "Creme de Cassis", 15);

    CocktailZutat erdbeeren = new CocktailZutat("ERDB", "Erdbeeren", 0);

    CocktailZutat erdbeerSirup = new CocktailZutat("ERDBS", "Erdbeer-Sirup", 0);

    CocktailZutat gingerAle = new CocktailZutat("GALE", "Ginger Ale", 0);

    CocktailZutat limette = new CocktailZutat("LIME", "Limette", 0);

    CocktailZutat maracujaSaft = new CocktailZutat("MARA", "Maracuja-Saft", 0);

    CocktailZutat pfirsichMark = new CocktailZutat("PMARK", "Pfirsich-Mark", 0);

    CocktailZutat rum = new CocktailZutat("RUM", "Rum", 40);

    CocktailZutat rohrzucker = new CocktailZutat("RZUC", "Rohrzucker", 0);

    CocktailZutat sekt = new CocktailZutat("SEKT", "Sekt", 11.5);

    CocktailZutat zitronenSaft = new CocktailZutat("ZSAFT", "Zitronen-Saft", 0);

    Cocktail bellini = new Cocktail("BELL", "Bellini");
    bellini.addZutat(sekt);
    bellini.addZutat(pfirsichMark);
    COCKTAILS.add(bellini);

    Cocktail ipanema = new Cocktail("IPA", "Ipanema");
    ipanema.addZutat(gingerAle);
    ipanema.addZutat(limette);
    ipanema.addZutat(rohrzucker);
    ipanema.addZutat(maracujaSaft);
    COCKTAILS.add(ipanema);

    Cocktail kirRoyal = new Cocktail("KIRR", "Kir Royal");
    kirRoyal.addZutat(sekt);
    kirRoyal.addZutat(cassis);
    COCKTAILS.add(kirRoyal);

    Cocktail strawberryDaiquiri = new Cocktail("STDQ", "Strawberry Daiquiri");
    strawberryDaiquiri.addZutat(rum);
    strawberryDaiquiri.addZutat(erdbeeren);
    strawberryDaiquiri.addZutat(erdbeerSirup);
    strawberryDaiquiri.addZutat(zitronenSaft);
    COCKTAILS.add(strawberryDaiquiri);
  }

  @Override
  public void insert(Cocktail cocktail)
  {
    if (!COCKTAILS.add(cocktail))
    {
      throw new IllegalArgumentException("Cocktail dopplelt!");
    }
  }

  @Override
  public Cocktail findById(String id)
  {
    for (Cocktail c : COCKTAILS)
    {
      if (id.equals(c.getId()))
      {
        return c;
      }
    }

    return null;
  }

  @Override
  public List<Cocktail> findAll()
  {
    return new ArrayList<Cocktail>(COCKTAILS);
  }

  @Override
  public void update(Cocktail cocktail)
  {
    COCKTAILS.remove(cocktail);
    COCKTAILS.add(cocktail);
  }

  @Override
  public void delete(String id)
  {
    Iterator<Cocktail> iterator = COCKTAILS.iterator();
    while (iterator.hasNext())
    {
      if (id.equals(iterator.next().getId()))
      {
        iterator.remove();
        break;
      }
    }
  }

}
