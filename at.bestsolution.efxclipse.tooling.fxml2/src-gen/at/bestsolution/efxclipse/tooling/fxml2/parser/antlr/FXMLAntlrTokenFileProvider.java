/*
* generated by Xtext
*/
package at.bestsolution.efxclipse.tooling.fxml2.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class FXMLAntlrTokenFileProvider implements IAntlrTokenFileProvider {
	
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
    	return classLoader.getResourceAsStream("at/bestsolution/efxclipse/tooling/fxml2/parser/antlr/internal/InternalFXML.tokens");
	}
}
