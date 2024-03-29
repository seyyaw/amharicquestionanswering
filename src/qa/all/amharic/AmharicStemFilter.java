package qa.all.amharic;


import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import java.io.IOException;

/**
 * A filter that stems Amharic words.
 */
public final class AmharicStemFilter extends TokenFilter {
	/**
	 * The actual token in the input stream
	 */
	private Token token = null;
	private AmharicStemmer stemmer = null;

	public AmharicStemFilter(TokenStream in) {
		super(in);
		stemmer = new AmharicStemmer();
	}

	/**
	 * @return Returns the next token in the stream, or null at EOS
	 */
	public final Token next() throws IOException {
		if ((token = input.next()) == null) {
			return null;
		} else {
			String s = stemmer.Stem(token.termText());
			if (!s.equals(token.termText())) {
				return new Token(s, token.startOffset(), token.endOffset(),
						token.type());
			}
			return token;
		}
	}

}