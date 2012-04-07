package qa.all.amharic;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

/**
 * This module is used to normalize question words question words will not be
 * subjected to stemming and stopword removal this module will create same ..
 * 
 * @author Seid M
 * 
 */

public class LetterNormalization {

	public void Normalize(String directory) throws IOException {
		File dir = new File(directory);
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			String fname = files[i].getAbsolutePath();
			String qwords = FileUtils.readFileToString(new File(fname));
			qwords = qwords.replace("ሥ", "ስ");
			qwords = qwords.replace("ሃ", "ሀ");
			qwords = qwords.replace("ሓ", "ሀ");
			qwords = qwords.replace("ሐ", "ሀ");
			qwords = qwords.replace("ቼ", "ቸ");
			qwords = qwords.replace("ዓ", "አ");
			qwords = qwords.replace("ኣ", "አ");
			qwords = qwords.replace("ዐ", "አ");
			qwords = qwords.replace("ዉ", "ው");
			qwords = qwords.replace("ኅ", "ሀ");
			qwords = qwords.replace("ኀ", "ሀ");
			qwords = qwords.replace("ዬ", "የ");
			qwords = qwords.replace("ጸ", "ፀ");
			qwords = qwords.replace("ሡ", "ሱ");
			qwords = qwords.replace("ሢ", "ሲ");
			qwords = qwords.replace("ሣ", "ሳ");
			qwords = qwords.replace("ሤ", "ሴ");
			qwords = qwords.replace("ሦ", "ሶ");
			qwords = qwords.replace("ኁ", "ሁ");
			qwords = qwords.replace("ኂ", "ሂ");
			qwords = qwords.replace("ኄ", "ሄ");
			qwords = qwords.replace("ኆ", "ሆ");
			qwords = qwords.replace("ኅ", "ህ");
			qwords = qwords.replace("ሑ", "ሁ");
			qwords = qwords.replace("ሒ", "ሂ");
			qwords = qwords.replace("ሔ", "ሄ");
			qwords = qwords.replace("ሕ", "ህ");
			qwords = qwords.replace("ሖ", "ሆ");
			qwords = qwords.replace("ዑ", "ኡ");
			qwords = qwords.replace("ዒ", "ኢ");
			qwords = qwords.replace("ዔ", "ኤ");
			qwords = qwords.replace("ዕ", "እ");
			qwords = qwords.replace("ዖ", "ኦ");
			qwords = qwords.replace("ጽ", "ፅ");
			qwords = qwords.replace("ጹ", "ፁ");
			qwords = qwords.replace("ጺ", "ፂ");
			qwords = qwords.replace("ጻ", "ፃ");
			qwords = qwords.replace("ጼ", "ፄ");
			qwords = qwords.replace("ጾ", "ፆ");
			qwords = qwords.replace("ጪ", "ጭ");
			qwords = qwords.replace("ዲ", "ድ");
			System.out.println(qwords);
			BufferedWriter out = new BufferedWriter(new FileWriter(fname));
			out.write(qwords);
			out.close();

		}
	}

	public void SpaceNormalize(String file) throws IOException {
		String lines = FileUtils.readFileToString(new File(file));
		if(lines.contains("(1)")||lines.contains("(2)")||
				lines.contains("(3)")||lines.contains("(4)"));
		{
		lines=lines.replace("(1)", "");
		lines=lines.replace("(2)", "");
		lines=lines.replace("(3)", "");
		lines=lines.replace("(4)", "");
		}
		StringTokenizer st = new StringTokenizer(lines, "\r\n");
		BufferedWriter out = new BufferedWriter(new FileWriter(
				"C:\\ፓርክ.txt"));
		String nondp="";
		while (st.hasMoreElements()) {
			String line = st.nextToken();
			if (line.contains("ፓርክ"))
			//if (line.contains("ትምህርት ቤት")||line.contains("ትምህርትቤት")
				//	||line.contains("ት/ቤት")||line.contains("ትምርት ቤት")||line.contains("ትምርትቤት"))
				//if (line.contains("ተራራ"))//if (nondp.contains(line)){}else
			{//nondp=nondp+line.trim();
				out.write(line.trim() + "\r\n");}

		}
		out.close();
	}

	public void count(String directory) throws IOException
	{
		File dir = new File(directory);
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			String fname = files[i].getAbsolutePath();
			String content = FileUtils.readFileToString(new File(fname));
			StringTokenizer countit=new StringTokenizer(content,"\r\n");
			//int count=0;
			//System.out.println(countit.countTokens());
			BufferedWriter out = new BufferedWriter(new FileWriter(fname));
			out.write(countit.countTokens() + "\r\n");
			while(countit.hasMoreElements())
			{String line=countit.nextToken();
			out.write(line.trim() + "\r\n");
			}
			out.close();
		}
		
	}
	public static void main(String[] str) throws IOException {
		LetterNormalization qn = new LetterNormalization();
		// qn.Normalize("C:\\new");
		//qn.SpaceNormalize("C:\\placename3.txt");
		qn.count("C:\\Gazetteers");
	}
}
