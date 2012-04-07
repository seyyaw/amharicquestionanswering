package qa.all.amharic;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class NameExtractor {
	public void Toknizeby(String doc) throws IOException {
		String names = FileUtils.readFileToString(new File(doc));
		StringTokenizer token = new StringTokenizer(names);
		StringBuffer sb = new StringBuffer();
		while (token.hasMoreElements()) {
			String word = token.nextToken();
			sb.append(word + "\r\n");
		}
		String name = null;
		name = sb.toString();
		FileUtils.writeStringToFile(new File("datadir\\Gazetteers\\personname.txt"),
				name);

	}

	/**
	 * This class is used to determine person names in a given sentence
	 * 
	 * @param sentence
	 *            the sentence/paragraph/document to be checked for person name
	 * @param namefle
	 *            a gazetteer which contain person name as a text file RETURN
	 *            person name alongside its document
	 */
	public HashMap<String/* candidate answers */, String/* document */> PersonName(
			String sentence, File namefile) throws IOException {
		String name = FileUtils.readFileToString(namefile);
		String OrigSentence = sentence;// orginal matched sentence
		StringBuffer nameword = new StringBuffer();
		int initindex = 0;
		String x = null;
		Boolean flag = false;// to check if names come in order, no other term
								// in b/n
		Boolean flag2 = false;
		String qword = null;
		StringTokenizer st = new StringTokenizer(AQAMain.Queryword);
		while (st.hasMoreElements() && initindex == 0) {
			qword = st.nextToken();
			if (name.contains(qword)) {
				sentence = sentence.replace(qword, "");// terms in question
														// can't be an answer
			}
		}
		// check that answer sentence contains the question terms and remove it
		// from candidacy
		StringTokenizer sentencetoken = new StringTokenizer(sentence);
		while (sentencetoken.hasMoreElements()) {
			StringTokenizer token = new StringTokenizer(name, "\r\n\\s");
			int count = token.countTokens();// number of unique names in the
											// gazeeter
			String sentenceword = sentencetoken.nextToken();
			if (x == null)
				flag = false;
			else
				flag = true;
			if (flag2 == true)// one name instance in a sentence
								// /paragraph/document
			{
				if (x.endsWith("$"))// no repetition of $
					flag2 = false;
				else {
					nameword.append("$"); // separate names by this single
											// special symbol
					x = nameword.toString().trim();
					flag2 = false;
				}
			}
			while (token.hasMoreElements()) {
				count = count - 1;
				String word = token.nextToken();
				if (sentenceword.equals(word) && x == null) {// first name
					nameword.append(word + " ");
					x = nameword.toString();
					break;
				} else if (sentenceword.equals(word)) {// first name
					nameword.append(word + " ");
					x = nameword.toString();
					break;
				}
				// may be a name starts with Be, በ, ከ, ለ, እነ so include it
				else if ((sentenceword.startsWith("በ")
						|| sentenceword.startsWith("ከ")
						|| sentenceword.startsWith("ለ")
						|| sentenceword.startsWith("እነ") || sentenceword
						.startsWith("ስለ"))
						&& sentenceword.substring(1, sentenceword.length())
								.equals(word)) {
					nameword.append(word + " ");
					x = nameword.toString();
					break;
				}
				// for words that may end with ና, ን, ም as a suffix
				else if ((sentenceword.endsWith("ና")
						|| sentenceword.endsWith("ን") || sentenceword
						.endsWith("ም"))
						&& sentenceword.substring(0, sentenceword.length() - 1)
								.equals(word)) {
					nameword.append(word + " ");
					x = nameword.toString();
					break;
				} else if (sentenceword.equals(word) && flag == true) {
					nameword.append(word + " ");
					x = nameword.toString();
					break;
				} else if (flag == true && count == 0)// end of one name
														// instance
				{
					flag2 = true;
					break;
				}
			}
		}
		HashMap<String, String> answersentence = new HashMap<String, String>();
		if (x == null) {
			answersentence.put("NoAnswer", OrigSentence);
			return answersentence;
		} else {
			answersentence.put(x, OrigSentence);
			return answersentence;
		}
	}

	public static void main(String[] tt) throws IOException {
		NameExtractor pname = new NameExtractor();
		OneDocOneAnswerSelector onans = new OneDocOneAnswerSelector();
		System.out
				.print(onans
						.OneNameOneDoc(
								pname
										.PersonName(
												"ለመኮንን ፈንታው የኢትዮጵያ ጠቅላይ ሚኒስትር ሁሴን ጃርሶ ለጠንካራ ነጋድዎች ሽልማት የሰጡት ለ በልዳ ከበደና",
												new File(
														"Gazetteers\\personname.txt")),
								"የኢትዮጵያ ጠቅላይ ሚኒስትር ማን ነው"));
		// pname.Toknizeby("C:\\backup\\downloads\\Open Source QA toolkit\\name.txt");
		// System.out
		// .println(pname.PersonName(" በጣም ሀብተ ሚካኤል የጫንጮው ተወላጅ  ሊበን በለው እና ጣለው እንዳገዳ ተስፋዬ ",
		// new File(
		// "C:\\Gazetteers\\personname.txt")));
	}
}
