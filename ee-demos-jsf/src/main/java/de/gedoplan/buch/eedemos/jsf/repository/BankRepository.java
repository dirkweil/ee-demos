package de.gedoplan.buch.eedemos.jsf.repository;

import de.gedoplan.buch.eedemos.jsf.entity.Bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BankRepository implements Serializable
{
  private Set<Bank> dbMock = new TreeSet<Bank>();

  public List<Bank> findByExample(String blz, String name, String plz, String ort)
  {
    List<Bank> result = new ArrayList<Bank>();

    for (Bank bank : this.dbMock)
    {
      if ((blz == null || bank.getBlz().startsWith(blz)) && (name == null || bank.getName().contains(name)) && (plz == null || bank.getPlz().startsWith(plz))
          && (ort == null || bank.getOrt().contains(ort)))
      {
        result.add(bank);
      }
    }

    return result;
  }

  public void save(Bank bank)
  {
    Iterator<Bank> iterator;
    if (!this.dbMock.add(bank))
    {
      iterator = this.dbMock.iterator();
      while (iterator.hasNext())
      {
        Bank oldBank = iterator.next();
        if (oldBank.equals(bank))
        {
          oldBank.set(bank);
          break;
        }
      }
    }
  }

  @SuppressWarnings("unused")
  @PostConstruct
  private void init()
  {
    this.dbMock.add(new Bank("10000000", "Bundesbank", "10591", "Berlin"));
    this.dbMock.add(new Bank("10010010", "Postbank", "10916", "Berlin"));
    this.dbMock.add(new Bank("10010111", "SEB", "10789", "Berlin"));
    this.dbMock.add(new Bank("10010222", "ABN AMRO Bank Ndl Deutschland", "10105", "Berlin"));
    this.dbMock.add(new Bank("10010424", "Aareal Bank", "10666", "Berlin"));
    this.dbMock.add(new Bank("10020000", "Berliner Bank Ndl d Landesbank Berlin", "10890", "Berlin"));
    this.dbMock.add(new Bank("10020200", "BHF-BANK", "10117", "Berlin"));
    this.dbMock.add(new Bank("10020400", "Parex Bank Berlin", "10117", "Berlin"));
    this.dbMock.add(new Bank("10020500", "Sozialbank", "10178", "Berlin"));
    this.dbMock.add(new Bank("10020890", "Bayer Hypo- und Vereinsbank", "10896", "Berlin"));
    this.dbMock.add(new Bank("10020890", "Bayer Hypo- und Vereinsbank", "14532", "Kleinmachnow"));
    this.dbMock.add(new Bank("10020890", "Bayer Hypo- und Vereinsbank", "16515", "Oranienburg"));
    this.dbMock.add(new Bank("10020890", "Bayer Hypo- und Vereinsbank", "14776", "Brandenburg an der Havel"));
    this.dbMock.add(new Bank("10020890", "Bayer Hypo- und Vereinsbank", "15711", "Königs Wusterhausen"));
    this.dbMock.add(new Bank("10020890", "Bayer Hypo- und Vereinsbank", "15517", "Fürstenwalde /Spree"));
    this.dbMock.add(new Bank("10022200", "Bankgesellschaft Berlin", "10777", "Berlin"));
    this.dbMock.add(new Bank("10030200", "Berlin-Hannoversche Hypothekenbank", "10773", "Berlin"));
    this.dbMock.add(new Bank("10030400", "ABK-Kreditbank", "10789", "Berlin"));
    this.dbMock.add(new Bank("10030500", "Bankhaus Lübbecke Ndl Berlin, Fasanenstraße", "10623", "Berlin"));
    this.dbMock.add(new Bank("10030600", "Bankhaus Kruber", "13469", "Berlin"));
    this.dbMock.add(new Bank("10030700", "Gries & Heissel - Bankiers", "65189", "Wiesbaden"));
    this.dbMock.add(new Bank("10040000", "Commerzbank Berlin (West)", "10891", "Berlin"));
    this.dbMock.add(new Bank("10040060", "Commerzbank Gf 160", "10891", "Berlin"));
    this.dbMock.add(new Bank("10040061", "Commerzbank Gf 161", "10891", "Berlin"));
    this.dbMock.add(new Bank("10045050", "Commerzbank Service-BZ", "10785", "Berlin"));
    this.dbMock.add(new Bank("10050000", "Landesbank Berlin -Gz- zgl Berliner Sparkasse", "10889", "Berlin"));
    this.dbMock.add(new Bank("10050001", "Landesbank Berlin -Girozentrale-", "10889", "Berlin"));
    this.dbMock.add(new Bank("10050005", "Landesbank Berlin -Girozentrale- E 1", "10889", "Berlin"));
    this.dbMock.add(new Bank("10050006", "Landesbank Berlin -Girozentrale- E 2", "10889", "Berlin"));
    this.dbMock.add(new Bank("10050500", "LBS Ost Berlin", "10405", "Berlin"));
    this.dbMock.add(new Bank("10050600", "WestLB Berlin", "10117", "Berlin"));
    this.dbMock.add(new Bank("10050999", "DekaBank Berlin", "10249", "Berlin"));
    this.dbMock.add(new Bank("10060198", "Pax-Bank", "14005", "Berlin"));
    this.dbMock.add(new Bank("10060237", "Evangelische Darlehnsgenossenschaft", "10503", "Berlin"));
    this.dbMock.add(new Bank("10070000", "Deutsche Bank Fil Berlin", "10883", "Berlin"));
    this.dbMock.add(new Bank("10070024", "Deutsche Bank Privat und Geschäftskunden F 700", "10883", "Berlin"));
    this.dbMock.add(new Bank("10070100", "Deutsche Bank Fil Berlin II", "10883", "Berlin"));
    this.dbMock.add(new Bank("10070124", "Deutsche Bank Privat und Geschäftskd Berlin II", "10883", "Berlin"));
    this.dbMock.add(new Bank("10070989", "Eurohypo Ndl DB", "10117", "Berlin"));
    this.dbMock.add(new Bank("10080000", "Dresdner Bank", "10877", "Berlin"));
    this.dbMock.add(new Bank("10080005", "Dresdner Bank Zw A", "10623", "Berlin"));
    this.dbMock.add(new Bank("10080006", "Dresdner Bank Zw B", "10623", "Berlin"));
    this.dbMock.add(new Bank("10080088", "Dresdner Bank IBLZ", "10623", "Berlin"));
    this.dbMock.add(new Bank("10080090", "Dresdner Bank Gf Berlin", "10877", "Berlin"));
    this.dbMock.add(new Bank("10080900", "Dresdner Bank Otto Scheurmann", "10675", "Berlin"));
    this.dbMock.add(new Bank("10089260", "Dresdner Bank ITGK", "10877", "Berlin"));
    this.dbMock.add(new Bank("10089999", "Dresdner Bank ITGK 2", "10117", "Berlin"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "10892", "Berlin"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16248", "Hohensaaten"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "14612", "Falkensee"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "15711", "Königs Wusterhausen"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "14979", "Großbeeren"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16816", "Neuruppin"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16515", "Oranienburg"));
    this.dbMock.add(new Bank("10090000", "Volksbank Potsdam Zndl d Berliner Volksbank", "14467", "Potsdam"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "15344", "Strausberg"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16792", "Zehdenick"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "14547", "Beelitz, Mark"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank Fil Rheinsberg", "16831", "Rheinsberg"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "14929", "Treuenbrietzen"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "15757", "Halbe"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16321", "Bernau bei Berlin"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "15754", "Heidesee"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "14641", "Nauen, Havelland"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16766", "Kremmen"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16540", "Hohen Neuendorf"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "14542", "Werder (Havel)"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16866", "Kyritz, Prignitz"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16909", "Wittstock, Dosse"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16775", "Gransee"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank Fil Velten", "16727", "Velten"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "14558", "Nuthetal"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "15732", "Eichwalde"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "15562", "Rödersdorf b Berlin"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16348", "Wandlitz"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16761", "Hennigsdorf"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16775", "Löwenberger Land"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16835", "Lindow (Mark)"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16798", "Fürstenberg /Havel"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16247", "Friedrichswalde b Eberswalde"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16247", "Joachimsthal"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16244", "Schorfheide"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16559", "Liebenwalde"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16727", "Oberkrämer"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank (eh Grundkreditbank)", "14513", "Teltow"));
    this.dbMock.add(new Bank("10090000", "Berliner Volksbank", "16225", "Eberswalde"));
    this.dbMock.add(new Bank("10090300", "Bank für Schiffahrt (BFS) Fil d Ostfr VB Leer", "13353", "Berlin"));
    this.dbMock.add(new Bank("10090603", "Deutsche Apotheker- und Ärztebank", "10593", "Berlin"));
    this.dbMock.add(new Bank("10090603", "Deutsche Apotheker- und Ärztebank", "19061", "Schwerin, Meckl"));
    this.dbMock.add(new Bank("10090603", "Deutsche Apotheker- und Ärztebank", "01287", "Dresden"));
    this.dbMock.add(new Bank("10090603", "Deutsche Apotheker- und Ärztebank", "04129", "Leipzig"));
    this.dbMock.add(new Bank("10090603", "Deutsche Apotheker- und Ärztebank", "09111", "Chemnitz, Sachs"));
    this.dbMock.add(new Bank("10090603", "Deutsche Apotheker- und Ärztebank", "99084", "Erfurt"));
    this.dbMock.add(new Bank("10090603", "Deutsche Apotheker- und Ärztebank", "39104", "Magdeburg"));
    this.dbMock.add(new Bank("10090603", "Deutsche Apotheker- und Ärztebank", "14467", "Potsdam"));
    this.dbMock.add(new Bank("10090603", "Deutsche Apotheker- und Ärztebank", "18057", "Rostock"));
    this.dbMock.add(new Bank("10090900", "PSD Bank Berlin-Brandenburg", "12154", "Berlin"));
    this.dbMock.add(new Bank("10110300", "Masel, Dr. - & Co.", "14004", "Berlin"));
    this.dbMock.add(new Bank("10110400", "Investitionsbank Berlin", "10702", "Berlin"));
    this.dbMock.add(new Bank("10110600", "Berliner Effektenbank, Ndl CCB Bank", "10711", "Berlin"));
    this.dbMock.add(new Bank("10120100", "Weberbank Privatbankiers", "10893", "Berlin"));
    this.dbMock.add(new Bank("10120600", "Ge Money Bank", "60313", "Frankfurt am Main"));
    this.dbMock.add(new Bank("10120760", "Bayer Hypo- und Vereinsbank Ndl 260 Bln", "85609", "Aschheim"));
    this.dbMock.add(new Bank("10120800", "VON ESSEN Bankgesellschaft", "10719", "Berlin"));
    this.dbMock.add(new Bank("10120900", "ABC Privatkunden-Bank", "10109", "Berlin"));
    this.dbMock.add(new Bank("10120900", "ABC Privatkunden-Bank", "60313", "Frankfurt am Main"));
    this.dbMock.add(new Bank("10130600", "Isbank Fil Berlin", "13303", "Berlin"));
    this.dbMock.add(new Bank("10130800", "BIW Bank", "47877", "Willich"));
    this.dbMock.add(new Bank("10190100", "Berliner Volksbank (eh Grundkreditbank)", "10892", "Berlin"));
    this.dbMock.add(new Bank("10210900", "Münchener Hypothekenbank Berlin", "10789", "Berlin"));
    this.dbMock.add(new Bank("10220700", "Hanseatic Bank", "10717", "Berlin"));
    this.dbMock.add(new Bank("10320800", "Emporiki Bank - Germany", "10623", "Berlin"));
    this.dbMock.add(new Bank("12016836", "KfW Kreditanstalt für Wiederaufbau", "10117", "Berlin"));
    this.dbMock.add(new Bank("12020300", "BkmU Bank", "10122", "Berlin"));
    this.dbMock.add(new Bank("12030000", "Deutsche Kreditbank Berlin", "10117", "Berlin"));
    this.dbMock.add(new Bank("12030000", "Deutsche Kreditbank", "09111", "Chemnitz, Sachs"));
    this.dbMock.add(new Bank("12030000", "Deutsche Kreditbank", "04109", "Leipzig"));
    this.dbMock.add(new Bank("12030000", "Deutsche Kreditbank", "01069", "Dresden"));
    this.dbMock.add(new Bank("12030000", "Deutsche Kreditbank", "98527", "Suhl"));
    this.dbMock.add(new Bank("12030000", "Deutsche Kreditbank", "07545", "Gera"));
    this.dbMock.add(new Bank("12030000", "Deutsche Kreditbank", "99084", "Erfurt"));
    this.dbMock.add(new Bank("12030000", "Deutsche Kreditbank", "06122", "Halle (Saale)"));
    this.dbMock.add(new Bank("12030000", "Deutsche Kreditbank", "39104", "Magdeburg"));
    this.dbMock.add(new Bank("12030000", "Deutsche Kreditbank", "03046", "Cottbus"));
    this.dbMock.add(new Bank("12030000", "Deutsche Kreditbank", "15230", "Frankfurt (Oder)"));
    this.dbMock.add(new Bank("12030000", "Deutsche Kreditbank", "14471", "Potsdam"));
    this.dbMock.add(new Bank("12030000", "Deutsche Kreditbank", "17034", "Neubrandenburg, Meckl"));
    this.dbMock.add(new Bank("12030000", "Deutsche Kreditbank", "19057", "Schwerin, Meckl"));
    this.dbMock.add(new Bank("12030000", "Deutsche Kreditbank", "18057", "Rostock"));
    this.dbMock.add(new Bank("12030700", "Hanseatic Bank Berlin-Mitte", "10115", "Berlin"));
    this.dbMock.add(new Bank("12030900", "Merck Finck & Co", "10109", "Berlin"));
    this.dbMock.add(new Bank("12040000", "Commerzbank Berlin Ost", "10891", "Berlin"));
    this.dbMock.add(new Bank("12050555", "NLB FinanzIT", "10836", "Berlin"));
    this.dbMock.add(new Bank("12060000", "DZ BANK", "10001", "Berlin"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank Ld Brandenburg", "14405", "Potsdam"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "16252", "Bad Freienwalde (Oder)"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "16311", "Bernau bei Berlin"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "14776", "Brandenburg an der Havel"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "03046", "Cottbus"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "16225", "Eberswalde"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "15873", "Eisenhüttenstadt"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "03232", "Finsterwalde"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "15203", "Frankfurt (Oder)"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "15501", "Fürstenwalde /Spree"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "14902", "Jöterbog"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "15701", "Königs Wusterhausen"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "16856", "Kyritz, Prignitz"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "16801", "Neuruppin"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "16502", "Oranienburg"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "10883", "Berlin"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "16921", "Pritzwalk"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "14712", "Rathenow"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "16285", "Schwedt /Oder"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "15301", "Seelow"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "01968", "Senftenberg, NL"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "03122", "Spremberg, NL"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "02931", "Weißwasser /O.L."));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "03161", "Guben"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "16901", "Wittstock, Dosse"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "15801", "Zossen b Berlin"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "16278", "Angermünde"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "14612", "Falkensee"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "03149", "Forst (Lausitz)"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "16761", "Hennigsdorf"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "14943", "Luckenwalde"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "14974", "Ludwigsfelde"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "15907", "Lübben (Spreewald)"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "01987", "Schwarzheide"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "15344", "Strausberg"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "14513", "Teltow"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "14542", "Werder (Havel)"));
    this.dbMock.add(new Bank("12070000", "Deutsche Bank", "14641", "Nauen, Havelland"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "16278", "Angermünde"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "16252", "Bad Freienwalde (Oder)"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "14405", "Potsdam"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "16311", "Bernau bei Berlin"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden F 704", "14776", "Brandenburg an der Havel"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "03046", "Cottbus"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "16225", "Eberswalde"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "15873", "Eisenhüttenstadt"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "14612", "Falkensee"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "03232", "Finsterwalde"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "03149", "Forst (Lausitz)"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "15203", "Frankfurt (Oder)"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "15501", "Fürstenwalde /Spree"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "03161", "Guben"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "16761", "Hennigsdorf"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "14902", "Jöterbog"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "15701", "Königs Wusterhausen"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "16856", "Kyritz, Prignitz"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "14943", "Luckenwalde"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "14974", "Ludwigsfelde"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "15907", "Lübben (Spreewald)"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "14641", "Nauen, Havelland"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "16801", "Neuruppin"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "16502", "Oranienburg"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "10883", "Berlin"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "16921", "Pritzwalk"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "14712", "Rathenow"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "01987", "Schwarzheide"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "16285", "Schwedt /Oder"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "15301", "Seelow"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "01968", "Senftenberg, NL"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "03122", "Spremberg, NL"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "15344", "Strausberg"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "14513", "Teltow"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "02931", "Weißwasser /O.L."));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "14542", "Werder (Havel)"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "16901", "Wittstock, Dosse"));
    this.dbMock.add(new Bank("12070024", "Deutsche Bank Privat und Geschäftskunden", "15801", "Zossen b Berlin"));
    this.dbMock.add(new Bank("12080000", "Dresdner Bank", "10623", "Berlin"));
    this.dbMock.add(new Bank("12090640", "Deutsche Apotheker- und Arztebank (ZV-Ost)", "10625", "Berlin"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "03046", "Cottbus"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "01099", "Dresden"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "99002", "Erfurt"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "17489", "Greifswald, Hansestadt"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "06112", "Halle (Saale)"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "39104", "Magdeburg"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "19053", "Schwerin, Meckl"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "10407", "Berlin"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "04001", "Leipzig"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "06449", "Aschersleben, Sachs-Anh"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "02625", "Bautzen, Sachs"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "18528", "Bergen auf Rügen"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "06406", "Bernburg (Saale)"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "14774", "Brandenburg an der Havel"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "09111", "Chemnitz, Sachs"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "04509", "Delitzsch"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "06849", "Dessau, Anh"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "99817", "Eisenach, Thür"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "04895", "Falkenberg, Elster"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "15230", "Frankfurt (Oder)"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "07545", "Gera"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "02826", "Görlitz, Neiße"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "99867", "Gotha, Thür"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "18273", "Güstrow"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "38820", "Halberstadt"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "06886", "Lutherstadt Wittenberg"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "98617", "Meiningen"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "99724", "Nordhausen, Thür"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "01796", "Pirna"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "14473", "Potsdam"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "01587", "Riesa"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "18055", "Rostock"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "07318", "Saalfeld /Saale"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "39576", "Stendal"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "18437", "Stralsund"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "99423", "Weimar, Thür"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "06667", "Weißenfels, Saale"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "19322", "Wittenberge, Prignitz"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "08056", "Zwickau"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "23966", "Wismar, Meckl"));
    this.dbMock.add(new Bank("12096597", "Sparda-Bank Berlin", "17033", "Neubrandenburg, Meckl"));
    this.dbMock.add(new Bank("13000000", "Bundesbank", "18003", "Rostock"));
    this.dbMock.add(new Bank("13010111", "SEB", "18055", "Rostock"));
    this.dbMock.add(new Bank("13010111", "SEB", "19055", "Schwerin, Meckl"));
    this.dbMock.add(new Bank("13010111", "SEB", "23966", "Wismar, Meckl"));
    this.dbMock.add(new Bank("13020780", "Bayer Hypo- und Vereinsbank(ehem. Hypo)", "18010", "Rostock"));
    this.dbMock.add(new Bank("13020800", "Hanseatic Bank", "18057", "Rostock"));
    this.dbMock.add(new Bank("13040000", "Commerzbank", "18006", "Rostock"));
    this.dbMock.add(new Bank("13040000", "Commerzbank", "23951", "Wismar, Meckl"));
    this.dbMock.add(new Bank("13040000", "Commerzbank", "18184", "Roggentin b Rostock"));
    this.dbMock.add(new Bank("13050000", "Ostseesparkasse Rostock", "18242", "Bötzow"));
    this.dbMock.add(new Bank("13050000", "Ostseesparkasse Rostock", "18262", "Güstrow"));
    this.dbMock.add(new Bank("13050000", "Ostseesparkasse Rostock", "17161", "Teterow"));
    this.dbMock.add(new Bank("13050000", "Ostseesparkasse Rostock", "18201", "Bad Doberan"));
    this.dbMock.add(new Bank("13050000", "Ostseesparkasse Rostock", "18004", "Rostock"));
    this.dbMock.add(new Bank("13051042", "Kreissparkasse R�gen, Sitz Bergen", "18525", "Bergen auf Rügen"));
    this.dbMock.add(new Bank("13051052", "Sparkasse Hansestadt Stralsund", "18412", "Stralsund"));
    this.dbMock.add(new Bank("13061008", "Volksbank Wolgast", "17438", "Wolgast"));
    this.dbMock.add(new Bank("13061028", "Volksbank Raiffeisenbank ehem VB Greifswald", "17462", "Greifswald, Hansestadt"));
    this.dbMock.add(new Bank("13061078", "Volks- und Raiffeisenbank", "18225", "Kühlungsborn, Ostseebad"));
    this.dbMock.add(new Bank("13061078", "Volks- und Raiffeisenbank", "23952", "Wismar, Meckl"));
    this.dbMock.add(new Bank("13061078", "Volks- und Raiffeisenbank", "23946", "Ostseebad Boltenhagen"));
    this.dbMock.add(new Bank("13061078", "Volks- und Raiffeisenbank", "23942", "Dassow"));
    this.dbMock.add(new Bank("13061078", "Volks- und Raiffeisenbank", "23923", "Schönberg, Meckl"));
    this.dbMock.add(new Bank("13061078", "Volks- und Raiffeisenbank", "23948", "Klötz"));
    this.dbMock.add(new Bank("13061078", "Volks- und Raiffeisenbank", "18233", "Neubukow"));
    this.dbMock.add(new Bank("13061078", "Volks- und Raiffeisenbank", "23932", "Grevesmühlen"));
    this.dbMock.add(new Bank("13061078", "Volks- und Raiffeisenbank", "23996", "Bobitz"));
    this.dbMock.add(new Bank("13061078", "Volks- und Raiffeisenbank", "23974", "Neuburg, NWM"));
    this.dbMock.add(new Bank("13061078", "Volks- und Raiffeisenbank", "23992", "Neukloster, Meckl"));
    this.dbMock.add(new Bank("13061078", "Volks- und Raiffeisenbank", "23999", "Insel Poel"));
    this.dbMock.add(new Bank("13061078", "Volks- und Raiffeisenbank", "19205", "Gadebusch"));
    this.dbMock.add(new Bank("13061078", "Volks- und Raiffeisenbank", "19209", "Lützow"));
    this.dbMock.add(new Bank("13061078", "Volks- und Raiffeisenbank", "19217", "Rehna"));
    this.dbMock.add(new Bank("13061088", "Raiffeisenbank Wismar -alt-", "23952", "Wismar, Meckl"));
    this.dbMock.add(new Bank("13061128", "Raiffeisenbank", "18202", "Bad Doberan"));
  }
}
