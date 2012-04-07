package qa.all.amharic;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatePatternRule {
	public HashMap<String, String> matchdate(String sentence) {
//match ሰኔ 21.2001, ......
		String patt = "(\\b((ነ[ሀሃሐሓኻ][ሴሤ])|(መ[ሥስ]ከረም)|ጥቅምት|([ህኅሕኽ]ዳር)|(ታህ[ሳሣ][ስሥ])|"
				+ "([ጠጥ]ር)|(የካቲት)|(መጋቢት)|(ሚያዚያ)|(ግንቦት)|([ሰሠ]ኔ)|([ሀሃሐሓኻ]ምሌ))\\b"
				+ "\\s*([1-9]|0[1-9]|1[0-9]|2[0-9]|30)\\s*\\b(ቀን)\\b"
				+ "(\\s)*([,/.፣])*(\\s)*((19|20)\\d\\d))|"
				+ "(\\b((ነ[ሀሃሐሓኻ][ሴሤ])|(መ[ሥስ]ከረም)|ጥቅምት|([ህኅሕኽ]ዳር)|(ታህ[ሳሣ][ስሥ])|"
				+ "([ጠጥ]ር)|(የካቲት)|(መጋቢት)|(ሚያዚያ)|(ግንቦት)|([ሰሠ]ኔ)|([ሀሃሐሓኻ]ምሌ))\\b"
				+ "\\s*([1-9]|0[1-9]|1[0-9]|2[0-9]|30)"
				+ "(\\s)*([/,.፣])*(\\s)*((19|20)\\d\\d))|" 
				+	"(ነሀሴ|መስከረም|ጥቅምት|ህዳር|ታህሳስ|"
				+ "[ጠጥ]ር|የካቲት|መጋቢት|ሚያዚያ|ግንቦት|ሰኔ|ሀምሌ)(\\s*)(\\d)*"
				+"(\\s*)(ሰኞ|ማክሰኞ|ረብኡ|ቀን|ሀሙስ|ቅዳሜ|እሁድ|አርብ)*\\s*(([1-9][0-9])\\d\\d)*";
		//match 01/02/2009, 10-10-1998......
		String patt2 = "((0[1-9]|1[0123])[-/.፣](0[1-9]|[12][0-9]|30)[-/.፣]"
				+ "(19|20)\\d\\d)";// to match date like 01/01/2007-sample ......
		//to match date like 1998-1999, 1900/1901, 2001,2002......
		String patt3 = "\\b((19)\\d\\d)\\b(\\s)*([,/.-፣])*(\\s)*\\b((20)\\d\\d)\\b";
		//to match ዘንድሮ, በሚቀጥለው አመት, በ1991 ......
		String patt4 = "((ዛሬ|ዘንድሮ|ትናንት|ሰኞ|ማክሰኞ|ረብኡ|ሀሙስ|ቅዳሜ|እሁድ|አርብ|እለት)\\s*|" +
				"([በለከ])((([0-9][0-9])\\d\\d)|\\d+)+\\s*|[ከበለ]*(አንድ|ሁለት|ሶስት|አራት|\\d*|አምስት|" +
				"ስድስት|ሰባት|ስምንት|ዘጠኝ|ባለፈው|ሚቀጥለው|መጭው)+\\s*" +
				"(አመት|አመታት|ሳምንት|ሳምንታት|ወር|ቀን|ቀናት|ሰአት|ደቂቃ|ሰከንድ" +
				"|ወራት|ሰአታት|ሰኞ|ማክሰኞ|ረብኡ|ሀሙስ|ቅዳሜ|እሁድ|አርብ)\\s*" +
				"(በፊት|በኋላ)*\\s*|([ከበለ]*አንድ|ሁለት|ሶስት|አራት|አምስት|" +
				"ስድስት|ሰባት|ስምንት|ዘጠኝ|አስር|አስራ)+\\s*" +
				"(አመት|አመታት|ሳምንት|ሳምንታት|ወር|ደቂቃ|ሰከንድ|ቀን|ቀናት|ሰአት|ወራት|ሰአታት)\\s*)+";
		Pattern regPat = Pattern.compile(patt);
		// Pattern regPat1 = Pattern.compile(patt1);
		Pattern regPat2 = Pattern.compile(patt2);
		Pattern regPat3 = Pattern.compile(patt3);
		Pattern regPat4 = Pattern.compile(patt4);
		Matcher matcher = regPat.matcher(sentence);
		Matcher matcher2 = regPat2.matcher(sentence);
		Matcher matcher3 = regPat3.matcher(sentence);
		Matcher matcher4 = regPat4.matcher(sentence);
		StringBuffer sb = new StringBuffer();
		 String st = "";
		Boolean m = matcher.find(), m2 = matcher2.find(), m3 = matcher3.find(), m4 = matcher4.find();
		while (m || m2 || m3 || m4) {// match only one date in a sentence!!!
			if (m) {
				st=st+matcher.group().trim()+"$";//separate multiple candidate answers by this symbol
				m = matcher.find();
			}
			if (m2) {
				st=st+matcher2.group().trim()+"$";
				m2 = matcher2.find();
			}
			if (m3) {
				st=st+matcher3.group().trim()+"$";
				m3 = matcher3.find();
			}
			if (m4) {
				st=st+matcher4.group().trim()+"$";
				m4 = matcher4.find();
			}
		}
		HashMap<String, String> answersentence = new HashMap<String, String>();
		if (st.equals(null)||st.toString().equals("") || st.toString().equals(null))
		{	answersentence.put("NoAnswer", sentence);
			return answersentence;
		} else {
			StringTokenizer stt =new StringTokenizer(st,"$");
			while(stt.hasMoreElements())
			{
				String x=stt.nextToken().toString();
				if(AQAMain.Queryword.contains(x))
						{
					st=st.replace(x, "");
						}
			}
			
			answersentence.put(st, sentence);
	//	System.out.println("========"+answersentence+"++++++++");test
			return answersentence;			
		}
	}
	public static void main(String[] x) {
		DatePatternRule dp = new DatePatternRule();
		System.out.println(dp.matchdate("ከግንቦት 17 እስከ ግንቦት 21 ቀን 2001 ድረስ የአስረኛ ክፍል፣ ከግንቦት 24 ቀን እስከ ግንቦት 26 ቀን 2001 ድረስ ደግሞ የአስራ ሁለተኛ ክፍል እንድሁም ከሰኔ 2 ቀን 2001 እስከ ሰኔ 4 ቀን 2001 ድረስ የስምንተኛ ክፍል ብሄራዊ ፈተናዎች እንደሚሰጡ ሀላፊው አስረድተዋል።"));
	}
}
