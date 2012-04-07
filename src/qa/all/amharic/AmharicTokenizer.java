package qa.all.amharic;


import java.io.*;
import java.io.Reader;
import org.apache.lucene.analysis.*;

/**
 * A AmaharicToenizer is a tokenizer that divides text at whitespace and Amharic
 * Language punctuations. as well as tab,newline and carriage return Adjacent
 * sequences of characters other than the above mensioned one form tokens.
 */

public class AmharicTokenizer extends CharTokenizer {
	/** Construct a new WhitespaceTokenizer. */
	public AmharicTokenizer(Reader in) {

		super(in);
	}

	/**
	 * Collects only characters which do not satisfy
	 * {@link Character#isWhitespace(char)}.
	 */
	protected boolean isTokenChar(char cc) {
		// Character cc=Character.parseChar(c);
		if ((cc == '\u1360') || (cc == '\u1361') || (cc == '\u1362')
				|| (cc == '\u1363') || (cc == '\u1364') || (cc == '\u1365')
				|| (cc == '\u1366') || (cc == '\u0020') || (cc == '\u0021')
				|| (cc == '\u0009') || (cc == '\n') || (cc == '\r'))
			return false;
		else
			return true;
	}

	/**
	 * Replace characters that can be used interchangably in Amharic language
	 */
	protected char normalize(char c) {
		if (c == '\u1210') // replace HMERU HA with HALETAW HA
			return '\u1200';
		else if (c == '\u1211') // replace HU
			return '\u1201';
		else if (c == '\u1212')
			return '\u1202';
		else if (c == '\u1213')
			return '\u1203';
		else if (c == '\u1214')
			return '\u1204';
		else if (c == '\u1215')
			return '\u1205';
		else if (c == '\u1216')
			return '\u1206';
		else if (c == '\u1217')
			return '\u1207';
		else if (c == '\u1203')
			return '\u1200';

		else if (c == '\u1280')
			return '\u1200';
		else if (c == '\u1281')
			return '\u1201';
		else if (c == '\u1282')
			return '\u1202';
		else if (c == '\u1283')
			return '\u1203';
		else if (c == '\u1284')
			return '\u1204';
		else if (c == '\u1285')
			return '\u1205';
		else if (c == '\u1286')
			return '\u1206';
		else if (c == '\u128B')
			return '\u1207';

		else if (c == '\u1220') // Replace NEGUSU SE with ESATU SE
			return '\u1230';
		else if (c == '\u1221')
			return '\u1231';
		else if (c == '\u1222')
			return '\u1232';
		else if (c == '\u1223')
			return '\u1233';
		else if (c == '\u1224')
			return '\u1234';
		else if (c == '\u1225')
			return '\u1235';
		else if (c == '\u1226')
			return '\u1236';
		else if (c == '\u1227')
			return '\u1237';

		else if (c == '\u12D0') // Replace the fidel A
			return '\u12A0';
		else if (c == '\u12D1')
			return '\u12A1';
		else if (c == '\u12D2')
			return '\u12A2';
		else if (c == '\u12D3')
			return '\u12A3';
		else if (c == '\u12D4')
			return '\u12A4';
		else if (c == '\u12D5')
			return '\u12A5';
		else if (c == '\u12D6')
			return '\u12A6';

		else if (c == '\u1340') // Replace the fidel TS
			return '\u1338';
		else if (c == '\u1341')
			return '\u1339';
		else if (c == '\u1342')
			return '\u133A';
		else if (c == '\u1343')
			return '\u133B';
		else if (c == '\u1344')
			return '\u133C';
		else if (c == '\u1345')
			return '\u133D';
		else if (c == '\u1346')
			return '\u133E';
		else if (c == '\u1347')
			return '\u133F';

		else if (c == '\u1310') // Replace GO
			return '\u130E';

		else if (c == '\u12B0') // Replace KO
			return '\u12AE';

		else
			return c;

	}

	public static void main(String[] args) throws Exception {
		System.out.println("hi");
		FileInputStream fin = new FileInputStream(new File("c:/n/name.txt"));
		InputStreamReader read = new InputStreamReader(fin);
		BufferedReader rd = new BufferedReader(read);
		FileOutputStream fout = new FileOutputStream(new File("c:/kio4.txt"));
		OutputStreamWriter rt = new OutputStreamWriter(fout);
		BufferedWriter rtt = new BufferedWriter(rt);
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		String sentence = sb.toString();
		Token token = null;
		AmharicTokenizer tk = new AmharicTokenizer(new StringReader(
				sentence));
		System.out.println("hi");
		while ((token = tk.next()) != null) {
			rtt.write(token.termText()+" ");

		}
		rtt.close();
	}

}
