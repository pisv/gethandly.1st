/*
 * generated by Xtext
 */
package org.eclipse.handly.examples.basic.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class FooAntlrTokenFileProvider implements IAntlrTokenFileProvider {

	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResourceAsStream("org/eclipse/handly/examples/basic/parser/antlr/internal/InternalFoo.tokens");
	}
}