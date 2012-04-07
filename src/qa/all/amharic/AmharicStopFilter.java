package qa.all.amharic;


import java.io.IOException;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

public class AmharicStopFilter extends TokenFilter

{
	private StopStemmer stemmer = null;

	public AmharicStopFilter(TokenStream input) {
		super(input);
		stemmer = new StopStemmer();
	}
	public final Token next() throws IOException {
		// return the first non-stop word found
		for (Token token = input.next(); token != null; token = input.next()) {
			String termText = token.termText();
			if (!stemmer.isStop(termText))
				return token;
		}
		// reached EOS -- return null
		return null;
	}
}
