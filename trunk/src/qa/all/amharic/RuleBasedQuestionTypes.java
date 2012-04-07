package qa.all.amharic;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * a rule that is used to match a given question to return the question type.
 */
public class RuleBasedQuestionTypes {
	public String Qtypes(String question) throws IOException {
		String qtypes = null;
		AmharicStemmer stem = new AmharicStemmer();
		String qwords = FileUtils
				.readFileToString(new File(
						"numberrelated.txt"));
		 String[]QfocuseTypes = StringUtils.split(qwords, "\n");					
			StringTokenizer st=new StringTokenizer(QfocuseTypes[0].toString(),"፣[]?");
			StringTokenizer st2=new StringTokenizer(QfocuseTypes[1].toString(),"፣[]?");
			while (st.hasMoreElements()) {
				String x = st.nextToken();
				int ind=question.indexOf(x);
				if (ind >= 0) {
					while(st2.hasMoreElements())
					{
						String y=st2.nextToken();
					if (question.indexOf(y)>=0)
					{qtypes = "numeric";// stem.Stem(question);
					break;
					}
				}
				}
			}
			
		qwords = FileUtils//Question Words to classify a question
				.readFileToString(new File(
						"personrelated.txt"));
		 QfocuseTypes= StringUtils.split(qwords, "\n");					
		 st=new StringTokenizer(QfocuseTypes[0].toString(),"፣[]?");
		 st2=new StringTokenizer(QfocuseTypes[1].toString(),"፣[]?");
		while (st.hasMoreElements()) {
			String x = st.nextToken();
			int ind=question.indexOf(x);
			if (ind >= 0) {
				while(st2.hasMoreElements())
				{
					String y=st2.nextToken();
				if (question.indexOf(y)>=0)
				{qtypes = "person";// stem.Stem(question);
				break;
				}
			}
			}
		}
		
		qwords = FileUtils
		.readFileToString(new File(
						"placerelated.txt"));
		 QfocuseTypes = StringUtils.split(qwords, "\n");					
		st=new StringTokenizer(QfocuseTypes[0].toString(),"፣[]?");
		 st2=new StringTokenizer(QfocuseTypes[1].toString(),"፣[]?");
		while (st.hasMoreElements()) {
			String x = st.nextToken();
			int ind=question.indexOf(x);
			if (ind >= 0) {
				while(st2.hasMoreElements())
				{
					String y=st2.nextToken();
				if (question.indexOf(y)>=0)
				{qtypes = "place";// stem.Stem(question);
				break;
				}
			}
			}
		}
		qwords = FileUtils
		.readFileToString(new File(
						"timerelated.txt"));
		 QfocuseTypes = StringUtils.split(qwords, "\n");					
		st=new StringTokenizer(QfocuseTypes[0].toString(),"፣[]?");
		 st2=new StringTokenizer(QfocuseTypes[1].toString(),"፣[]?");
		while (st.hasMoreElements()) {
			String x = st.nextToken();
			int ind=question.indexOf(x);
			if (ind >= 0) {
				while(st2.hasMoreElements())
				{
					String y=st2.nextToken();
				if (question.indexOf(y)>=0)
				{qtypes = "time";// stem.Stem(question);
				break;
				}
			}
			}
		}
		
		return qtypes;
	}

	public static void main(String[] arg) {
		RuleBasedQuestionTypes qt = new RuleBasedQuestionTypes();
		try {
			System.out.println(qt.Qtypes("ከ አድስ አበባ ባህር ዳር ምን ያክል ይርቃል?"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
  /*class  PersonHierarchy
{
	public String 
}

*/