package GUI.helpers;
 
import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
 
/**
 * @author Mark George <mark.george@otago.ac.nz>
 *
 * License: FreeBSD (https://opensource.org/licenses/BSD-2-Clause)
 *
 * An facade that simplifies working with OVal and JFormattedTextField components.
 */
public class ValidationHelper {
 
	public boolean isObjectValid(Object domain) {
 
		// create Oval validator
		Validator validator = new Validator();
 
		// validate the object
		List<ConstraintViolation> violations = validator.validate(domain);
 
		// were there any violations?
		if (violations.isEmpty()) {
			// nope
			return true;
		} else {
			// yes, so show constraint messages to user
			StringBuilder message = new StringBuilder();
 
			//	loop through the violations extracting the message for each
			for (ConstraintViolation violation : violations) {
				message.append(violation.getMessage()).append("\n");
			}
 
			// show a message box to the user with all the violation messages
			JOptionPane.showMessageDialog(null, message.toString(),
				"Input Problem", JOptionPane.WARNING_MESSAGE);
 
			return false;
		}
	}
 
	public void addTypeFormatter(JFormattedTextField textField,
		String format, Class<? extends Number> type) {
 
		// create a format object using our desired format
		DecimalFormat df = new DecimalFormat(format);
		df.setGroupingUsed(false);
 
		// create a formatter object that will apply the format
		NumberFormatter formatter = new NumberFormatter(df);
 
		// define the type that the formatter will return
		formatter.setValueClass(type);
		formatter.setAllowsInvalid(true);
 
		// install a new verifier that is type-aware
		textField.setInputVerifier(new NumberVerifier(type));
 
		// create a factory for the formatter
		DefaultFormatterFactory factory = new DefaultFormatterFactory(formatter);
 
		// install the factory in the text field
		textField.setFormatterFactory(factory);
	}
 
}
 
class NumberVerifier extends InputVerifier {
 
	private static Color originalColour;
	private static final Color errorColour = new Color(1, 0.3f, 0.3f);
 
	private final Class type;
 
	public NumberVerifier(Class type) {
		this.type = type;
	}
 
	@Override
	public boolean verify(JComponent component) {
		JFormattedTextField ftf = (JFormattedTextField) component;
 
		if (originalColour == null) {
			originalColour = ftf.getBackground();
		}
 
		boolean valid = false;
 
		String text = ftf.getText();
 
		if (type == Long.class) {
			try {
				Long.parseLong(text);
				valid = true;
			} catch (NumberFormatException nfe) {
				valid = false;
			}
		} else if (type == Integer.class) {
			try {
				Long val = Long.parseLong(text);
				valid = val < Integer.MAX_VALUE;
			} catch (NumberFormatException nfe) {
				valid = false;
			}
		} else if (type == Double.class || type == BigDecimal.class) {
			try {
				Double.parseDouble(text);
				valid = true;
			} catch (NumberFormatException nfe) {
				valid = false;
			}
		} else {
			throw new IllegalArgumentException("Can only use Numeric types");
		}
 
		if (!valid) {
			ftf.setBackground(errorColour);
 
		} else {
			ftf.setBackground(originalColour);
		}
 
		return valid;
	}
 
	@Override
	public boolean shouldYieldFocus(JComponent input) {
		return verify(input);
	}
 
}