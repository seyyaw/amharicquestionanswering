package qa.all.amharic;
import java.io.*;
import java.util.*;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

/**
 * Title: AmharicAnalyzer Description: build from a AmharicTokenizer, filtered
 * with AmharicStopFilter and AmharicStemFilter.
 * 
 */

public class AmharicAnalyzer extends Analyzer {

	public AmharicAnalyzer() {
	}

	/**
	 * Creates a TokenStream which tokenizes all the text in the provided
	 * Reader.
	 * 
	 * @return A TokenStream build from AmharicTokenizer filtered with
	 *         AmharicstoFilter amd AmharicStemFilter.
	 */
	public final TokenStream tokenStream(String fieldName, Reader reader) {
		TokenStream result = new AmharicTokenizer(reader);
		StringBuffer sb = new StringBuffer();
		try {
			for (Token token = result.next(); token != null; token = result
					.next()) {
				String term = token.termText();
				shortword expand = new shortword();
				term = expand.Expander(term);
				sb.append(term + " ");
			}
		} catch (Exception e) {
		}
		String sentence = sb.toString();
		TokenStream results = new AmharicTokenizer(new StringReader(sentence));
		results = new AmharicStopFilter(results);
		results = new AmharicStemFilter(results);
		return results;
	}
}