package de.gedoplan.buch.eedemos.jpa.schema;

import de.gedoplan.buch.eedemos.jpa.TestBase;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Test;

public class SchemaTest extends TestBase
{
  @Test
  public void testCreateScript()
  {
    System.out.println("----- testCreateScript -----");

    StringWriter createWriter = new StringWriter();

    Map<String, Object> properties = new HashMap<>();
    properties.put("javax.persistence.schema-generation.scripts.action", "create");
    properties.put("javax.persistence.schema-generation.scripts.create-target", createWriter);

    Persistence.generateSchema("test", properties);

    String createScript = createWriter.toString();
    if (createScript.isEmpty())
    {
      Assert.fail("Script is empty");
    }

    System.out.println(createScript);
  }

}
