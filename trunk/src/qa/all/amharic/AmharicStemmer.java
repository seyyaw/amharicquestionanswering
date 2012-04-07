package qa.all.amharic;


import java.util.Vector;
import java.io.*;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.*;

/**
 * A stemmer for Amharic words.
 */
public class AmharicStemmer {
	private Vector<String> preffixList = new Vector<String>();
	private Vector<String> suffixList = new Vector<String>();
	private String[] Stopword = { "\u12A8", "\u1293\u1278\u12CD",
			"\u1275\u1293\u1295\u1275", "\u1325\u1242\u1275",
			"\u1260\u122D\u12Ab\u1273", "\u1265\u127B", "\u1201\u1209\u121D",
			"\u120C\u120B", "\u120C\u120E\u127D", "\u1201\u1209",
			"\u12A5\u12EB\u1295\u12F3\u1295\u12F1",
			"\u12A5\u12EB\u1295\u12F3\u1295\u12F3\u127D\u12CD",
			"\u12A5\u12EB\u1295\u12F3\u1295\u12F7",
			"\u12A5\u1295\u12F0\u1308\u1293", "\u121B\u1295\u121D",
			"\u12A5\u1263\u12A9\u12Ce", "\u12A5\u1263\u12A9\u1238",
			"\u12A5\u1263\u12A9\u1205", "\u1270\u1328\u121B\u122A",
			"\u1230\u12A0\u1275", "\u12CD\u132A", "\u1293\u1275",
			"\u1290\u1260\u1229", "\u1290\u1260\u1228\u127D", "\\u12EB",
			"\u12C8\u12ED\u12D8\u122E", "\u12C8\u12ED\u12D8\u122A\u1275",
			"\u1290\u1308\u122E\u127D", "\u12A8\u134A\u1275",
			"\u12A8\u120B\u12ED", "\u1273\u127D", "\u12A8\u1273\u127D",
			"\u1260\u1273\u127D", "\u12E8\u1273\u127D",
			"\u12A8\u12CD\u1235\u1325", "\u1260\u12CD\u1235\u1325",
			"\u12E8\u12CD\u1235\u1325", "\u1207\u120B", "\u12A8\u1207\u120B",
			"\u12E8\u1207\u120B", "\u1218\u12AB\u12A8\u120D",
			"\u12A8\u1218\u12AB\u12A8\u120D", "\u1230\u121E\u1291\u1295",
			"\u12A8\u1230\u121E\u1291", "\u1260\u1230\u121E\u1291",
			"\u12E8\u1230\u121E\u1291", "\u1275\u1293\u1295\u1275",
			"\u1275\u1293\u1295\u1275\u1293", "\u130B\u122B",
			"\u12E8\u130B\u122B", "\u12A8\u130B\u122b",
			"\u1270\u1208\u12EB\u12E9", "\u1270\u1208\u12EB\u12E8",
			"\u12F5\u1228\u1235", "\u12A5\u1235\u12A8", "\u1260\u1323\u121D",
			"\u130D\u1295", "\u1232\u1206\u1295", "\u1232\u120D",
			"\u12Cd\u1235\u1325", "\u120B\u12ED", "\u1290\u12ED",
			"\u1290\u12CD", "\u130B\u122D", "\u1293\u1278\u12CD",
			"\u12ED\u1205", "\u1260\u120B\u12Ed", "\u12C8\u12F0",
			"\u12C8\u12D8\u1270", "\u12A5\u1293", "\u12C8\u12ED\u121D",
			"\u12A5\u1295\u12F0", "\u12A0\u1276", "\u134A\u1275",
			"\u12C8\u12F0\u134A\u1275", "\u1290\u1308\u122d",
			"\u1260\u134A\u1275", "\u1260\u1207\u120B", "\u1260\u12A9\u120D",
			"\u1235\u1208", "\u12F0\u130D\u121E", "\u12A5\u1295\u1302",
			"\u12A5\u1295\u12F2\u1201\u121D" };
	private String sufword[] = { "\u1279", "\u12CE\u127D", "\u121D", "\u127D",
			"\u1295", "\u12EC", "\u12CE", "\u1205", "\u1238", "\u12CB",
			"\u127D\u1295", "\u127D\u12CD", "\u12CD", "\u1201", "\u129D",
			"\u1293", "\u1208\u1275", "\u120B\u1275", "\u120B\u1278\u12CD",
			"\u120B\u127D\u1201", "\u1260\u1275", "\u1263\u1275",
			"\u1263\u1278\u12CD", "\u1263\u127D\u1201", "\u1271", "\u123D",
			"\u12ED\u1271", "\u12CE\u1279", "\u12E8\u12CD", "\u129E\u127D",
			"\u12EB\u120D", "\u129B", "\u1278\u12CD" };
	private String preword[] = { "\u12A5\u12E8", "\u1232", "\u12E8",
			"\u12E8\u121A", "\u12A8", "\u1260", "\u1208", "\u12ED",
			"\u12A5\u1295\u12F2", "\u1235\u1208", "\u12A5\u1235\u12AD",
			"\u12A5\u1295\u12F0", "\u12A5\u1235\u12AA", "\u12A5\u1295\u12F5",
			"\u12A8\u1290", "\u12A5\u1295", "\u12A5\u1290", "\u1275" };// "\u1275",

	public String Stem(String term) throws IOException {

		for (String words : sufword)
			suffixList.add(words);
		for (String words : preword)
			preffixList.add(words);
		String stem = term;
		//Person names not to be stemmed
		String name = FileUtils.readFileToString(new File ("datadir\\Gazetteers\\personname.txt"));
		String title = FileUtils.readFileToString(new File ("datadir\\Gazetteers\\titles.txt"));
		if(name.contains(stem)||name.contains(stem.substring(0,stem.length()-1))
				|| name.contains(stem.substring(0,stem.length()-2))
						||name.contains(stem.substring(1,stem.length()))
								||name.contains(stem.substring(2,stem.length()))
								||name.equals(stem))
		{
		//stem = Remove_rep(stem);
		//stem = suffixRemover(stem);
		}
		// titles not to be stemed
		else if (title.contains(stem))
		{}
		else
		{
		stem = preffixRemover(term);
		stem = Remove_rep(stem);
		stem = suffixRemover(stem);
		stem = ChangeToSades(stem);
		}
		return stem;
	}
	private String Remove_rep(String str) {
		if (str.length() > 0) {

			char chararray[] = str.toCharArray();
			for (int i = 0; i < chararray.length - 1; i++) {
				Character temp1 = chararray[i];
				Character temp2 = chararray[i + 1];
				int s1 = temp1.charValue();
				int s2 = temp2.charValue();
				if (s1 == s2 + 3) {
					String str1 = str.substring(0, i);
					String str2 = str.substring(i + 1);
					str = str1 + str2;
					return str;
				}
			}
		}
		return str;
	}

	private boolean check(String str) {
		if (str.length() == 3) {
			int count = 0;

			char chararray[] = str.toCharArray();
			for (int i = 0; i < chararray.length; i++) {
				Character str1 = chararray[i];
				if ((str1 == '\u1200') || (str1 == '\u1208')
						|| (str1 == '\u1218') || (str1 == '\u1230')
						|| (str1 == '\u1228') || (str1 == '\u1238')
						|| (str1 == '\u1240') || (str1 == '\u1260')
						|| (str1 == '\u1270') || (str1 == '\u1268')
						|| (str1 == '\u1278') || (str1 == '\u1290')
						|| (str1 == '\u1298') || (str1 == '\u12A0')
						|| (str1 == '\u12A8') || (str1 == '\u12C8')
						|| (str1 == '\u12D8') || (str1 == '\u12E0')
						|| (str1 == '\u12E8') || (str1 == '\u12F0')
						|| (str1 == '\u1300') || (str1 == '\u1308')
						|| (str1 == '\u1320') || (str1 == '\u1328')
						|| (str1 == '\u1300') || (str1 == '\u1338')
						|| (str1 == '\u1348') || (str1 == '\u1350')
						|| (str1 == '\u1280') || (str1 == '\u1210')
						|| (str1 == '\u1220') || (str1 == '\u12D0')
						|| (str1 == '\u1340'))
					count++;
			}
			if (count == 3)
				return true;
			else
				return false;

		}
		return false;

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

	public String suffixRemover(String str) {
		if (str.length() < 3)
			return str;
		else {
			while (str.length() >= 3) {
				String str1;
				str1 = str.substring(str.length() - 3);
				if (suffixList.contains(str1)) {
					if (context(str1, str)) {

						str = context1(str1, str);
						return str;
					} else {

						str = str.substring(0, str.length() - 3);
						suffixRemover(str);
					}

				} else

				{
					str1 = str.substring(str.length() - 2);
					if (suffixList.contains(str1)) {
						if (context(str1, str)) {

							str = context1(str1, str);
							return str;
						} else {
							str = str.substring(0, str.length() - 2);
							suffixRemover(str);
						}
					}
					else {
						str1 = str.substring(str.length() - 1);
						if (suffixList.contains(str1)) {
							if (context(str1, str)) {
								str = context1(str1, str);
								return str;
							} else {
								str = str.substring(0, str.length() - 1);
								suffixRemover(str);
							}
						} else
							return str;
					}
				}
			}
		}
		return str;

	}

	public String preffixRemover(String str) {
		if (str.length() < 3)
			return str;
		else {
			while (str.length() >= 3) {

				String str1;
				str1 = str.substring(0, 3);
				if (preffixList.contains(str1)) {
					if (context(str1, str)) {

						str = context1(str1, str);
						return str;
					} else {
						str = str.substring(3);
						preffixRemover(str);
					}
				} else {
					str1 = str.substring(0, 2);
					if (preffixList.contains(str1)) {
						if (context(str1, str)) {

							str = context1(str1, str);
							return str;
						} else {

							str = str.substring(2);
							preffixRemover(str);
						}
					} else {
						str1 = str.substring(0, 1);
						if (preffixList.contains(str1)) {
							if (context(str1, str)) {

								str = context1(str1, str);
								return str;
							} else {

								str = str.substring(1);
								preffixRemover(str);
							}
						}

						else
							return str;
					}
				}
			}
		}
		return str;

	}

	private boolean context(String affix, String str) {

		if (str.length() > 2) {

			if (affix.equals("\u12A8")) // the prefix K

				return true;
			else if ((affix.equals("\u1208")) || (affix.equals("\u1260"))) // the
																			// prefix
																			// B
																			// and
																			// L
				return true;
			else if (affix.equals("\u129E\u127D")) // the suffx nno C
				return true;

			else if (affix.equals("\u1295")) // the suffix NE

				return true;

			/*
			 * else if(affix.equals("\u1275")) // the suffix TE
			 * 
			 * return true;
			 * 
			 * 
			 * else if(affix.equals("\u1290\u1275")) // the suffix NTE
			 * 
			 * 
			 * return true;
			 */

			else if (affix.equals("\u12CE\u127D")) // the suffix WO CE

				return true;

			else if (affix.equals("\u127D")) // the suffix CE

				return true;

			else if (affix.equals("\u127D\u1295")) // the suffix CE NE
				return true;

			else if (affix.equals("\u127D\u1201")) // the suffix CE HU
				return true;

			else if (affix.equals("\u1278\u12CD")) // the suffix C WE
				return true;

			else if (affix.equals("\u1271")) // the suffix TU
				return true;

			else if (affix.equals("\u1279")) // the suffix CU
				return true;

			/*
			 * else if(affix.equals("\u12CA")) // the suffix WI return true;
			 * else if(affix.equals("\u12CA\u1275")) // the suffix WI TE return
			 * true; else if(affix.equals("\u12CD\u12EB\u1295")) // the suffix
			 * WE YA NE return true;
			 */
			else if (affix.equals("\u1277\u120D")) // the suffix TWA LE

				return true;

			/*
			 * else if(affix.equals("\u12CD\u12EB\u1295")) // the suffix WE YA
			 * NE return true;
			 */
			else if (affix.equals("\u121D")) // the suffix ME

				return true;

			// else if(affix.equals("\u1275")||affix.equals("\u12A0")) // the
			// suffix TE and the prefix X

			// return true;

			else if (affix.equals("\u129B")) // the suffix nna

				return true;

			else if (affix.equals("\u1290\u1275") || affix.equals("\u12A5")) // the
																				// prefix
																				// XE
																				// and
																				// the
																				// suffix
																				// NTE

				return true;

			else if (affix.equals("\u121D") || affix.equals("\u12A5")) // the
																		// suffix
																		// ME
																		// and
																		// the
																		// prefix
																		// XE

				return true;

			else if (affix.equals("\u1208\u1275")) // the suffix LE TE

				return true;

		}
		return false;

	}

	private String context1(String affix, String str) {
		if (str.length() > 2) {
			if (affix.equals("\u12A8")) // the prefix K
			{
				if (str.equals("\u12A8\u1265\u1275")
						|| str.equals("\u12A8\u1208\u12A8\u1208")
						|| str.equals("\u12A8\u1290\u12A8\u1290")
						|| str.equals("\u12A8\u1228\u12A8\u1228")
						|| str.equals("\u12A8\u1128\u12A8\u1218")
						|| str.equals("\u12A8\u1230\u12A8\u1230")
						|| str.equals("\u12A8\u1270\u12A8\u1270")
						|| str.equals("\u12A8\u1270\u121B")
						|| str.equals("\u12A8\u1228\u1295")
						|| str.equals("\u12A8\u1230\u120D")
						|| str.equals("\u12A8\u134D\u1270\u129B")
						|| str.equals("\u12A8\u1265\u1275"))

					return str;
				else if (str.length() == 3) {
					if (check(str))
						return str;
				} else {
					str = str.substring(1);
					str = preffixRemover(str);
					return str;
				}

			}
			if ((affix.equals("\u1208")) || (affix.equals("\u1260"))) // the
																		// prefix
																		// "B"
																		// and
																		// "L"
			{
				if ((str.equals("\u1260\u123D\u1273"))
						|| (str.equals("\u1260\u1300\u1275"))
						|| (str.equals("\u1260\u1228\u1260\u1228"))
						|| (str.equals("\u1260\u1230\u1260\u1230")))
					return str;
				else if (check(str))
					return str;
				else {
					str = str.substring(1);
					str = preffixRemover(str);
					return str;
				}

			} else if (affix.equals("\u129B")) // the suffix nna
			{
				if (str.equals("\u12A8\u134D\u1270\u129B"))
					return str;
			}

			else if (affix.equals("\u1295")) // the suffix NE
			{
				if (str.equals("\u12D8\u1218\u1295")
						|| str.equals("\u1261\u12F5\u1295")
						|| str.equals("\u123D\u134B\u1295")
						|| str.equals("\u12C8\u1308\u1295")
						|| str.equals("\u132D\u1241\u1295")
						|| str.equals("\u1205\u133B\u1295")
						|| str.equals("\u12A5\u12CD\u1295")
						|| str.equals("\u12A5\u121D\u1295")
						|| str.equals("\u12D8\u1348\u1295"))
					return str;
				else {
					str = str.substring(0, str.length() - 1);
					// str=ChangeToSades(str);
					str = suffixRemover(str);
					return str;
				}
			} else if (affix.equals("\u1208\u1275")) // the suffix L TE
			{
				if (str.equals("\u1201\u1208\u1275")
						|| str.equals("\u12A5\u1208\u1275")
						|| str.equals("\u12A0\u1208\u1275"))
					return str;

				else {
					str = str.substring(0, str.length() - 2);
					str = ChangeToSades(str);
					str = suffixRemover(str);
					return str;

				}
			}
			/*
			 * else if(affix.equals("\u1275")) // the suffix TE {
			 * if(str.equals("\u12A0\u1263\u1275"
			 * )||str.equals("\u12A0\u12EB\u1275"
			 * )||str.equals("\u1205\u12ED\u12C8\u1275"
			 * )||str.equals("\u12A0\u122B\u12CA\u1275"
			 * )||str.equals("\u1230\u12A3\u1275"
			 * )||str.equals("\u1218\u1265\u1275"
			 * )||str.equals("\u1218\u1230\u1228\u1275"
			 * )||str.equals("\u12C8\u1245\u1275"
			 * )||str.equals("\u1325\u1228\u1275"
			 * )||str.equals("\u1275\u122D\u134D"
			 * )||str.equals("\u1201\u1208\u1275"
			 * )||str.equals("\u1236\u1235\u1275"
			 * )||str.equals("\u12A0\u122B\u1275"
			 * )||str.equals("\u12A0\u121D\u1235\u1275"
			 * )||str.equals("\u1235\u12F5\u1235\u1275"
			 * )||str.equals("\u1230\u1263\u1275"
			 * )||str.equals("\u1235\u121D\u1295\u1275"
			 * )||str.equals("\u1275\u121D\u1205\u122D\u1275"
			 * )||str.equals("\u1325\u1245\u121D\u1275"
			 * )||str.equals("\u1233\u121D\u1295\u1275"
			 * )||str.equals("\u12AD\u122B\u12CA\u1275")) return str; else {
			 * str=str.substring(0,str.length()-1); str=ChangeToSades(str);
			 * str=suffixRemover(str); return str; } }
			 */
			/*
			 * else if(affix.equals("\u1290\u1275")) // the suffix NTE {
			 * if(str.equals
			 * ("\u12A5\u12CD\u1290\u1275")||str.equals("\u12A5\u121D\u1290\u1275"
			 * )) { str= str.substring(0,str.length()-1);
			 * str=ChangeToSades(str); str=suffixRemover(str); return str; }
			 * else if (str.equals("\u12A0\u12ED\u1290\u1275")) return str; else
			 * { str=str.substring(0,str.length()-2); str=suffixRemover(str);
			 * return str; }
			 * 
			 * }
			 */
			else if (affix.equals("\u12CE\u127D")) // the suffix WO CE
			{
				if (str.equals("\u1230\u12CE\u127D")) {
					str = str.substring(0, str.length() - 1);
					str = ChangeToSades(str);
					return str;
				} else {
					str = str.substring(0, str.length() - 2);
					// str=ChangeToSades(str);
					str = suffixRemover(str);
					return str;

				}
			} else if (affix.equals("\u129E\u127D")) // the suffix nno CE
			{
				if (str.equals("\u12F3\u129E\u127D")) {
					str = str.substring(0, str.length() - 1);
					str = ChangeToSades(str);
					return str;

				} else {

					str = str.substring(0, str.length() - 1);
					str = ChangeToSades(str);
					str = suffixRemover(str);
					return str;

				}
			} else if (affix.equals("\u127D")) // the suffix CE
			{
				str = str.substring(0, str.length() - 1);
				str = ChangeToSades(str);
				str = suffixRemover(str);
				return str;

			} else if (affix.equals("\u127D\u1295")) // the suffix CE NE
			{
				str = str.substring(0, str.length() - 2);
				str = ChangeToSades(str);
				str = suffixRemover(str);
				return str;

			} else if (affix.equals("\u127D\u1201")) // the suffix CE HU
			{
				str = str.substring(0, str.length() - 2);
				str = ChangeToSades(str);
				str = suffixRemover(str);
				return str;

			} else if (affix.equals("\u1278\u12CD")) // the suffix C WE
			{
				str = str.substring(0, str.length() - 2);
				str = ChangeToSades(str);
				str = suffixRemover(str);
				return str;

			} else if (affix.equals("\u1271")) // the suffix TU
			{
				str = ChangeToSades(str);
				str = suffixRemover(str);
				return str;

			} else if (affix.equals("\u1279")) // the suffix CU
			{
				str = str.substring(0, str.length() - 1);
				str = ChangeToSades(str);
				str = suffixRemover(str);
				return str;

			}
			/*
			 * else if(affix.equals("\u12CA")) // the suffix WI {
			 * str=str.substring(0,str.length()-1); //str=ChangeToSades(str);
			 * str=suffixRemover(str); return str;
			 * 
			 * } else if(affix.equals("\u12CA\u1275")) // the suffix WI TE {
			 * if(str.equals("\u12A0\u122B\u12CA\u1275")) return str; else {
			 * 
			 * str=str.substring(0,str.length()-2); //str=ChangeToSades(str);
			 * str=suffixRemover(str); return str; } } else
			 * if(affix.equals("\u12CD\u12EB\u1295")) // the suffix WE YA NE {
			 * str=str.substring(0,str.length()-3); str=ChangeToSades(str);
			 * str=suffixRemover(str); return str;
			 * 
			 * }
			 */
			else if (affix.equals("\u1277\u120D")) // the suffix TWA LE
			{
				if (str.equals("\u121E\u1277\u120D")) {

					str = str.substring(0, str.length() - 1);
					str = ChangeToSades(str);
					return str;
				} else {
					str = str.substring(0, str.length() - 2);
					str = ChangeToSades(str);
					str = suffixRemover(str);
					return str;

				}
			}

			else if (affix.equals("\u121D")) // the suffix ME
			{
				if (str.equals("\u1308\u12F3\u121D")
						|| str.equals("\u1230\u120B\u121D")
						|| str.equals("\u12A0\u1208\u121D"))
					return str;
				else {

					str = str.substring(0, str.length() - 1);
					str = ChangeToSades(str);
					str = suffixRemover(str);
					return str;
				}
			}

		}
		return str;
	}

	public static void main(String[] args) throws Exception {
		AmharicStemmer r = new AmharicStemmer();
		FileInputStream fin = new FileInputStream(new File("C:/n/name.txt"));
		InputStreamReader read = new InputStreamReader(fin, "utf-16");
		BufferedReader rd = new BufferedReader(read);
		FileOutputStream fout = new FileOutputStream(new File("c:/kio4.txt"));
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
		AmharicTokenizer tk = new AmharicTokenizer(new StringReader(sentence));
		while ((token = tk.next()) != null) {
			rtt.write(r.Stem(token.termText()) + "\r\n");

		}
		rtt.close();
	}

}