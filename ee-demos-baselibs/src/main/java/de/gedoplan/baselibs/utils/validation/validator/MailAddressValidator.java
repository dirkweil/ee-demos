package de.gedoplan.baselibs.utils.validation.validator;

import de.gedoplan.baselibs.utils.validation.constraint.ValidMailAddress;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * BV-Validatorklasse zum Constraint {@link ValidMailAddress}.
 * 
 * @author dw
 */
public class MailAddressValidator implements ConstraintValidator<ValidMailAddress, String>
{
  private static final String REGEXP_FULL          = "[a-zA-Z_.0-9-]+@[a-zA-Z_0-9-]+(\\.[a-zA-Z_0-9-]+)+";
  private static final String REGEXP_NAME_OPTIONAL = "[a-zA-Z_.0-9-]*@[a-zA-Z_0-9-]+(\\.[a-zA-Z_0-9-]+)+";

  private Pattern             pattern;

  @Override
  public void initialize(ValidMailAddress constraint)
  {
    String regexp = constraint.nameOptional() ? REGEXP_NAME_OPTIONAL : REGEXP_FULL;
    this.pattern = Pattern.compile(regexp);
  }

  @Override
  public boolean isValid(String adresse, ConstraintValidatorContext validationContext)
  {
    if (adresse == null)// null ist ok
    {
      return true;
    }

    return this.pattern.matcher(adresse).matches();
  }
}
