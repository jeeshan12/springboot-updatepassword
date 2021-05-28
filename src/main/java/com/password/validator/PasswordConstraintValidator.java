package com.password.validator;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.*;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		PasswordValidator validator = new PasswordValidator(Arrays.asList(
				new LengthRule(18, 30),
		        new CharacterRule(EnglishCharacterData.UpperCase, 1),
		        new CharacterRule(EnglishCharacterData.LowerCase, 1),
		        new CharacterRule(EnglishCharacterData.Digit, 1), 
		        new CharacterRule(EnglishCharacterData.Special, 1),
		        //new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 4, false),
		        //new IllegalSequenceRule(EnglishSequenceData.Numerical, 4, false),
		        //new RepeatCharacterRegexRule(4)
		        new CharacterOccurrencesRule(4)
		        ));

		RuleResult result = validator.validate(new PasswordData(password));
		if (result.isValid()) {
            return true;
        }
		List<String> messages = validator.getMessages(result);
		        String messageTemplate = String.join(",", messages);
		        context.buildConstraintViolationWithTemplate(messageTemplate)
		                .addConstraintViolation()
		                .disableDefaultConstraintViolation();
        return false;
		        
	}

}
