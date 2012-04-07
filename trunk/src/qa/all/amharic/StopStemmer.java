package qa.all.amharic;
import java.util.Vector;
import java.io.*;
import org.apache.lucene.analysis.*;

/**
 * A stemmer for Amharic words.
 */
public class StopStemmer {
	private Vector<String> stoppreffixList = new Vector<String>();
	private Vector<String> CstopaffixList = new Vector<String>();
	private Vector<String> stopsuffixList = new Vector<String>();
	private Vector<String> stopWordList = new Vector<String>();
	private String[] Stopword = { "\u12A5\u12DA\u1205", "\u12A5\u12DA\u12EB",
			"\u12A8", "\u1293\u1278\u12CD", "\u1275\u1293\u1295\u1275",
			"\u1325\u1242\u1275", "\u1260\u122D\u12Ab\u1273", "\u1265\u127B",
			"\u1201\u1209\u121D", "\u120C\u120B", "\u120C\u120E\u127D",
			"\u1201\u1209", "\u12A5\u12EB\u1295\u12F3\u1295\u12F1",
			"\u12A5\u12EB\u1295\u12F3\u1295\u12F3\u127D\u12CD",
			"\u12A5\u12EB\u1295\u12F3\u1295\u12F7",
			"\u12A5\u1295\u12F0\u1308\u1293", "\u121B\u1295\u121D",
			"\u12A5\u1263\u12A9\u12Ce", "\u12A5\u1263\u12A9\u1238",
			"\u12A5\u1263\u12A9\u1205", "\u1270\u1328\u121B\u122A",
			 "\u12CD\u132A", "\u1293\u1275",
			"\u1290\u1260\u1229", "\u1290\u1260\u1228\u127D", "\\u12EB",
			"\u1290\u1308\u122E\u127D", "\u12A8\u134A\u1275",
			"\u12A8\u120B\u12ED", "\u1273\u127D", "\u12A8\u1273\u127D",
			"\u1260\u1273\u127D", "\u12E8\u1273\u127D",
			"\u12A8\u12CD\u1235\u1325", "\u1260\u12CD\u1235\u1325",
			"\u12E8\u12CD\u1235\u1325", "\u1207\u120B", "\u12A8\u1207\u120B",
			"\u12E8\u1207\u120B", "\u1218\u12AB\u12A8\u120D",
			"\u12A8\u1218\u12AB\u12A8\u120D", "\u1230\u121E\u1291\u1295",
			"\u12A8\u1230\u121E\u1291", "\u1260\u1230\u121E\u1291",
			"\u12E8\u1230\u121E\u1291",
			 "\u130B\u122B",
			"\u12E8\u130B\u122B", "\u12A8\u130B\u122b",
			"\u1270\u1208\u12EB\u12E9", "\u1270\u1208\u12EB\u12E8",
			"\u12F5\u1228\u1235", "\u12A5\u1235\u12A8", "\u1260\u1323\u121D",
			"\u130D\u1295", "\u1232\u1206\u1295", "\u1232\u120D",
			"\u12Cd\u1235\u1325", "\u120B\u12ED", "\u1290\u12ED",
			"\u1290\u12CD", "\u130B\u122D", "\u1293\u1278\u12CD",
			"\u12ED\u1205", "\u12C8\u12F0",
			"\u12C8\u12D8\u1270", "\u12A5\u1293", "\u12C8\u12ED\u121D",
			"\u12A5\u1295\u12F0", "\u134A\u1275",
			"\u12C8\u12F0\u134A\u1275", "\u1290\u1308\u122d",
			 "\u1260\u1207\u120B", "\u1260\u12A9\u120D",
			"\u1235\u1208", "\u12F0\u130D\u121E", "\u12A5\u1295\u1302",
			"\u12A5\u1295\u12F2\u1201\u121D" };
	// private String
	// stopsufword[]={"\u12ED\u1271","\u12EB\u12CA","\u1279","\u121B","\u12EB","\u1278\u1201","\u129E\u1279","\u12A5\u1295\u12F2","\u127D\u1293","\u1278","\u127B","\u127D","\u1275","\u12CE\u127D","\u1295","\u12E8","\u12EC","\u12CE","\u1205","\u1238","\u12CB","\u127D\u1295","\u127D\u1201","\u127D\u12CD","\u1278\u12CD","\u1276","\u12CD","\u1271","\u12A5","\u127D","\u12ED","\u12A9","\u1201","\u12AD","\u1205","\u123D","\u129D","\u1290\u1275","\u12CA\u1275","\u121D","\u1271","\u1273","\u1293","\u12CA","\u129B","\u12EB","\u12A6","\u1246","\u12CB\u120D","\u1260\u1275","\u129E\u127D","\u1208\u1275","\u1209\u1271","u12CD\u12EB\u1295"};
	private String Cstopaffword[] = { "\u12E8", "\u1260", "\u1208", "\u1293",
			"\u12A8" };
	private String stopsufword[] = { "\u1275", "\u12AB\u120D", "\u1262",
			"\u1235", "\u12ED\u1271", "\u12EB\u12CA", "\u1279", "\u121B",
			"\u12EB", "\u1278\u1201", "\u129E\u1279", "\u12A5\u1295\u12F2",
			"\u127D\u1293", "\u1278", "\u127B", "\u127D", "\u12CE\u127D",
			"\u1295", "\u12E8", "\u12EC", "\u12CE", "\u1205", "\u1238",
			"\u12CB", "\u127D\u1295", "\u127D\u1201", "\u127D\u12CD",
			"\u1278\u12CD", "\u1276", "\u12CD", "\u1271", "\u12A5", "\u127D",
			"\u12ED", "\u12A9", "\u1201", "\u12AD", "\u1205", "\u123D",
			"\u129D", "\u1290\u1275", "\u12CA\u1275", "\u121D", "\u1271",
			"\u1273", "\u1293", "\u12CA", "\u129B", "\u12EB", "\u12A6",
			"\u1246", "\u12CB\u120D", "\u1260\u1275", "\u129E\u127D",
			"\u1208\u1275", "\u1209\u1271", "u12CD\u12EB\u1295" };// "\u1275",
	private String stoppreword[] = { "\u120D\u1275", "\u12A5\u1295\u12F5",
			"\u1235\u1208\u121A", "\u1235\u1208\u121D", "\u12AB\u120D",
			"\u1208\u121A", "\u12e8\u121A", "\u12A8\u121A", "\u1260\u121A",
			"\u1208\u121D\u1275", "\u12A8\u121D\u1275", "\u1260\u121D\u1275",
			"\u12E8\u121D\u1275", "\u12A8\u1290", "\u1218", "\u12A5\u1295",
			"\u12A5\u1290", "\u1218", "\u12E8", "\u1260", "\u1208", "\u12A8",
			"\u1275", "\u12Ed", "\u12A5\u1295\u12F0", "\u12A0\u1235", "\u1270",
			"\u12A0\u120D", "\u1235\u1208", "\u12A5\u1235\u12A8",
			"\u12A5\u1235\u12AD", "\u12A5\u1295\u12F2", "\u12E8\u121A",
			"\u12E8\u121B\u12ED", "\u1232", "\u121B", "\u12A5\u1295\u12F3",
			"\u121B", "\u12A8\u1290", "\u12AB\u1208" };

	public boolean isStop(String str) {
		create();
		if (stopWordList.contains(str))
			return true;
		else {
			String str1 = CaffixRemover(str);
			if (stopWordList.contains(str1))
				return true;
			else {
				String stem = stopPreffixRemover(str);
				stem = stopSuffixRemover(stem);
				stem = ChangeToSades(stem);
				if (stopWordList.contains(str))
					return true;
				else if (stem.equals("\u12DA\u12ED")
						|| stem.equals("\u12A0\u120D")
						|| stem.equals("\u12DB\u122D")
						|| stem.equals("\u129B\u12cD")
						|| stem.equals("\u1290\u130D")
						|| stem.equals("\u120C\u120D")
						|| stem.equals("\u121D\u12AD")
						|| stem.equals("\u1206\u1295")
						|| stem.equals("\u12DA\u1205")
						|| stem.equals("\u1290\u1260\u122D"))
					return true;
				else
					return false;
			}
		}
	}

	private void create() {
		for (String words : Stopword)
			stopWordList.add(words);
		for (String words : Cstopaffword)
			CstopaffixList.add(words);
		for (String words : stoppreword)
			stoppreffixList.add(words);
		for (String words : stopsufword)
			stopsuffixList.add(words);
	}

	public String CaffixRemover(String str) {
		if (str.length() > 2)

		{

			String str1 = str.substring(str.length() - 1);

			if (CstopaffixList.contains(str1)) {
				if (str1.equals("\u1293")) {

					if (str.equals("\u12A5\u1293"))
						return str;
				} else {

					str = str.substring(0, str.length() - 1);
					CaffixRemover(str);
				}
			} else {

				str1 = str.substring(0, 1);
				if (str1.equals("\u1260")) {
					if (str.equals("\u1260\u122D\u12AB\u1273")
							|| str.equals("\u1260\u12A9\u120D"))
						return str;
				} else if (CstopaffixList.contains(str1)) {

					str = str.substring(1);
					CaffixRemover(str);
				}

				else
					return str;
			}
			return str;
		}
		return str;
	}

	private String ChangeToSades(String str) {
		if (str.length() > 1) {

			char chararray[] = str.toCharArray();
			Character str1 = chararray[chararray.length - 1];
			int s = str1.charValue();

			// char str1 = str.substring(str.length()-1);
			String str2 = str.substring(0, str.length() - 1);
			if (('\u1200' <= s) && (s <= '\u1206')) {
				str1 = '\u1205';
				str = str2 + str1;
				return str;
			} else if (('\u1208' <= s) && (s <= '\u120F')) {
				str1 = '\u120D';
				str = str2 + str1;
				return str;

			} else if (('\u1218' <= s) && (s <= '\u121F')) {
				str1 = '\u121D';
				str = str2 + str1;
				return str;

			} else if (('\u1230' <= s) && (s <= '\u1237')) {
				str1 = '\u1235';
				str = str2 + str1;
				return str;

			} else if (('\u1228' <= s) && (s <= '\u122F')) {
				str1 = '\u122D';
				str = str2 + str1;
				return str;

			} else if (('\u1238' <= s) && (s <= '\u123F')) {
				str1 = '\u123D';
				str = str2 + str1;
				return str;

			} else if (('\u1240' <= s) && (s <= '\u124B')) {
				str1 = '\u1245';
				str = str2 + str1;
				return str;

			} else if (('\u1260' <= s) && (s <= '\u1267')) {
				str1 = '\u1265';
				str = str2 + str1;
				return str;

			} else if (('\u1270' <= s) && (s <= '\u1277')) {
				str1 = '\u1275';
				str = str2 + str1;
				return str;

			} else if (('\u1268' <= s) && (s <= '\u126F')) {
				str1 = '\u126D';
				str = str2 + str1;
				return str;

			} else if (('\u1278' <= s) && (s <= '\u127F')) {
				str1 = '\u127d';
				str = str2 + str1;
				return str;

			} else if (('\u1290' <= s) && (s <= '\u1297')) {
				str1 = '\u1295';
				str = str2 + str1;
				return str;

			} else if (('\u1298' <= s) && (s <= '\u129F')) {
				str1 = '\u129D';
				str = str2 + str1;
				return str;

			} else if (('\u12A0' <= s) && (s <= '\u12A6')) {
				str1 = '\u12A5';
				str = str2 + str1;
				return str;
			} else if (('\u12A8' <= s) && (s <= '\u12B0')) {
				str1 = '\u12AD';
				str = str2 + str1;
				return str;
			} else if (('\u12C8' <= s) && (s <= '\u12CF')) {
				str1 = '\u12CD';
				str = str2 + str1;
				return str;
			} else if (('\u12D8' <= s) && (s <= '\u12DF')) {
				str1 = '\u12DD';
				str = str2 + str1;
				return str;
			} else if (('\u1350' <= s) && (s <= '\u1357')) {
				str1 = '\u1355';
				str = str2 + str1;
				return str;
			} else if (('\u1348' <= s) && (s <= '\u134F')) {
				str1 = '\u134D';
				str = str2 + str1;
				return str;
			} else if (('\u1338' <= s) && (s <= '\u133F')) {
				str1 = '\u133D';
				str = str2 + str1;
				return str;
			} else if (('\u12E0' <= s) && (s <= '\u12E7')) {
				str1 = '\u12E5';
				str = str2 + str1;
				return str;
			} else if (('\u12E8' <= s) && (s <= '\u12EF')) {
				str1 = '\u12ED';
				str = str2 + str1;
				return str;
			} else if (('\u12F0' <= s) && (s <= '\u12F7')) {
				str1 = '\u12F5';
				str = str2 + str1;
				return str;
			} else if (('\u1300' <= s) && (s <= '\u1307')) {
				str1 = '\u1305';
				str = str2 + str1;
				return str;
			} else if (('\u1308' <= s) && (s <= '\u1310')) {
				str1 = '\u130D';
				str = str2 + str1;
				return str;
			} else if (('\u1320' <= s) && (s <= '\u1327')) {
				str1 = '\u1325';
				str = str2 + str1;
				return str;
			} else if (('\u1328' <= s) && (s <= '\u132F')) {
				str1 = '\u122D';
				str = str2 + str1;
				return str;
			} else if (('\u1330' <= s) && (s <= '\u1337')) {
				str1 = '\u1335';
				str = str2 + str1;
				return str;
			}

		}
		return str;
	}

	public String stopSuffixRemover(String str) {
		if (str.length() < 3)
			return str;
		else {
			while (str.length() >= 3) {
				String str1;
				str1 = str.substring(str.length() - 3);
				if (stopsuffixList.contains(str1)) {

					str = str.substring(0, str.length() - 3);
					stopSuffixRemover(str);
				}

				else

				{
					str1 = str.substring(str.length() - 2);
					if (stopsuffixList.contains(str1)) {

						str = str.substring(0, str.length() - 2);
						stopSuffixRemover(str);
					}

					else {
						str1 = str.substring(str.length() - 1);
						if (stopsuffixList.contains(str1)) {

							str = str.substring(0, str.length() - 1);
							stopSuffixRemover(str);
						} else

							return str;

					}
				}
			}
		}
		return str;

	}

	public String stopPreffixRemover(String str) {
		if (str.length() < 3)
			return str;
		else {
			while (str.length() >= 3) {

				String str1;
				str1 = str.substring(0, 3);
				if (stoppreffixList.contains(str1)) {
					str = str.substring(3);
					stopPreffixRemover(str);

				} else {
					str1 = str.substring(0, 2);
					if (stoppreffixList.contains(str1)) {

						str = str.substring(2);
						stopPreffixRemover(str);
					}

					else {
						str1 = str.substring(0, 1);
						if (stoppreffixList.contains(str1)) {

							str = str.substring(1);
							stopPreffixRemover(str);
						}

						else
							return str;
					}
				}
			}
		}
		return str;

	}

	public static void main(String[] args) throws Exception {
		StopStemmer r = new StopStemmer();
		FileInputStream fin = new FileInputStream(new File("c:/n/name.txt"));
		InputStreamReader read = new InputStreamReader(fin, "utf-16");
		BufferedReader rd = new BufferedReader(read);
		FileOutputStream fout = new FileOutputStream(new File("c:/kio2.txt"));
		OutputStreamWriter rt = new OutputStreamWriter(fout, "utf-16");
		BufferedWriter rtt = new BufferedWriter(rt);
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		String sentence = sb.toString();
		Token token = null;
		//
		AmharicTokenizer tk = new AmharicTokenizer(new StringReader(sentence));
		while ((token = tk.next()) != null) {
			if (!r.isStop(token.termText())) {
				rtt.write(token.termText() + " ");

			}
		}
		rtt.close();
	}

}