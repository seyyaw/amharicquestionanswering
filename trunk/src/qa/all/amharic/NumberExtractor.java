package qa.all.amharic;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.*;
public class NumberExtractor {

	public HashMap<String, String>  Extractanynumber(String Sentence) {
		String numpat2 = "\\b(([በከየለውና])|አንድ|ሁለት|ሶስት|አራት|አምስት|ስድስት|ሰባት"
				+ "|ስምንት|ዘጠኝ|አስር|አስራ|ግማሽ|ሩብ|ሀያ|ሰላሳ|አርባ|አምሳ|ሀምሳ|ስልሳ"
				+ "|ስድሳ|ሰባ|ሰማኒያ|ዘጠና|መቶ|ሽ|ሽህ|ሚሊ[የዮ]ን|ሚልየን|ሚልዮን|ቢሊዮን|ቢልየን|ቢልዮን|ትሪሊዮን|" 
				+"አንደኛ|ሁለተኛ|ሶስተኛ|አራተኛ|አምስተኛ|ስድስተኛ|ሰባተኛ|ስምንተኛ|ዘጠነኛ|አስረኛ|ኛ|"
				+	"\\d|\\.|ነጥብ|\\s)+(\\s+)(በላይ|በታች|አካባቢ|ደቂቃ|ሰአት|ወር|" +
						"አመት|ስአታት|አመታት|ወራት|ብር|ዶላር|ስኩየር ኪሎ ሜትር|ኪሎ ሜትር|ስኩየር|ኪሎ|ሜትር|ዩሮ|ሜትር|ኪ.ሜ|ኪ.ሜ|" +
						")*\\b";
		/*
		 * "((\\d|አንድ|ሁለት|ሶስት|አራት|" +//(\\d)|
		 * "አምስት|ስድስት|ሰባት|ስምንት|ዘጠኝ|ቢሊዮን|ቢልየን|ሚልየን|ሚልዮን" +
		 * "|ሺ|ሽህ|ሺህ|አስር|አስራ|ሀያ|ሰላሳ|አርባ|አምሳ|ሀምሳ|ስልሳ|ስድሳ" +
		 * "|ሰባ|ሰማኒያ|ሰማንያ|ዘጠና|መቶ|ነጥብ|\\s*|[በከለየ]?)+)";
		 */
		Pattern letternumpat = Pattern.compile(numpat2);
		Matcher letternummatch = letternumpat.matcher(Sentence);
		StringBuffer numbuf = new StringBuffer();
		String st = "";
		Boolean b = letternummatch.find();
		while (b) {
			if (b) {
				numbuf.append(letternummatch.group().trim());
				numbuf.append("$");
				st = numbuf.toString();
			}
			b = letternummatch.find();
		}
		HashMap<String, String> answersentence = new HashMap<String, String>();
		if (st.equals(null)||st.toString().equals("") || st.toString().equals(null))
		{	answersentence.put("NoAnswer", Sentence);
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
			
			answersentence.put(st, Sentence);
		//System.out.println("========"+answersentence+"++++++++");//test
			return answersentence;			
		}
	}
	public static void main(String[] arg) {
		NumberExtractor numext = new NumberExtractor();
		System.out.print(numext.Extractanynumber("የሚገነባው መንገድ 8 ነጥብ 2 ኪሎ ሜትር ርዝመትና 25 ሜትር ስፋት እንደሚኖረው ገልፀው፤ የመንገዱ ቀኝ መስመር በ115 ሚሊየን ብር ወጭ በተያዘው አመት መጀመሪያ ወር ላይ አሁን ስምምነቱን በፈረሙት ኮንትራክተር መጠናቀቁን አስታውሰዋል"));// 345
	}
}
