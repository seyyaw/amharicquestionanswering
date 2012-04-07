package qa.all.amharic;
import java.io.*;
import java.text.NumberFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public class DocumentNormalization {
	public void readReplace(String directory, String oldPattern,
			String replPattern) {
		File dir = new File(directory);
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			String fname = files[i].getAbsolutePath();
			String line;
			StringBuffer sb = new StringBuffer();
			try {
				FileInputStream fis = new FileInputStream(fname);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(fis));
				while ((line = reader.readLine()) != null) {
					line = line.replaceAll(oldPattern, replPattern);
					sb.append(line + "\n");
				}
				reader.close();
				BufferedWriter out = new BufferedWriter(new FileWriter(fname));
				out.write(sb.toString());
				out.close();
			} catch (Throwable e) {
				System.err.println("*** exception ***");
			}
		}

	}

	public String NormalizeQuery(String norm) {
		norm = norm.replace("ሃ", "ሀ");
		norm = norm.replace("ሐ", "ሀ");
		norm = norm.replace("ሓ", "ሀ");
		norm = norm.replace("ኅ", "ሀ");
		norm = norm.replace("ኃ", "ሀ");
		norm = norm.replace("ኋ", "ኋ");
		norm = norm.replace("ሗ", "ኋ");
		norm = norm.replace("ኁ", "ሁ");
		norm = norm.replace("ኂ", "ሂ");
		norm = norm.replace("ኄ", "ሄ");
		norm = norm.replace("ኅ", "ህ");
		norm = norm.replace("ኆ", "ሆ");
		norm = norm.replace("ሑ", "ሁ");
		norm = norm.replace("ሒ", "ሂ");
		norm = norm.replace("ሔ", "ሄ");
		norm = norm.replace("ሕ", "ህ");
		norm = norm.replace("ሖ", "ሆ");
		norm = norm.replace("ሠ", "ሰ");
		norm = norm.replace("ሡ", "ሱ");
		norm = norm.replace("ሢ", "ሲ");
		norm = norm.replace("ሣ", "ሳ");
		norm = norm.replace("ሤ", "ሴ");
		norm = norm.replace("ሥ", "ስ");
		norm = norm.replace("ሦ", "ሶ");
		norm = norm.replace("ሼ", "ሸ");
		norm = norm.replace("ቼ", "ቸ");
		norm = norm.replace("ዬ", "የ");
		norm = norm.replace("ዲ", "ድ");
		norm = norm.replace("ጄ", "ጀ");
		norm = norm.replace("ጸ", "ፀ");
		norm = norm.replace("ጹ", "ፁ");
		norm = norm.replace("ጺ", "ፂ");
		norm = norm.replace("ጻ", "ፃ");
		norm = norm.replace("ጼ", "ፄ");
		norm = norm.replace("ጽ", "ፅ");
		norm = norm.replace("ጾ", "ፆ");
		norm = norm.replace("ዉ", "ው");
		norm = norm.replace("ዴ", "ደ");
		norm = norm.replace("ቺ", "ች");
		norm = norm.replace("ዪ", "ይ");
		norm = norm.replace("ጪ", "ጭ");
		norm = norm.replace("ዓ", "አ");
		norm = norm.replace("ዑ", "ኡ");
		norm = norm.replace("ዒ", "ኢ");
		norm = norm.replace("ዐ", "አ");
		norm = norm.replace("ኣ", "አ");
		norm = norm.replace("ዔ", "ኤ");
		norm = norm.replace("ዕ", "እ");
		norm = norm.replace("ዖ", "ኦ");
		norm = norm.replace("ኚ", "ኝ");
		norm = norm.replace("ሺ", "ሽ");
		// System.out.println(norm);
		return norm;
	}

	public static void main(String[] args) throws IOException {
		DocumentNormalization norm = new DocumentNormalization();
		 
		double start = new Date().getTime();
		norm.readReplace("datadir\\alldata", "፡፡", "።");//፡፡
		/*String dataDir = "C:\\datadir\\alldata";
		File dir = new File(dataDir);
		if (dir.exists()) {
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				File filePath = new File(files[i].getAbsolutePath());
				String content = FileUtils.readFileToString(filePath);
				content = norm.NormalizeQuery(content);
				FileUtils.writeStringToFile(filePath, content);
			}}*/
			NumberFormat form = NumberFormat.getInstance();
			form.setMinimumFractionDigits(3);
			form.setMaximumFractionDigits(3);
			double end = new Date().getTime();
			double x = (((double) end - (double) start) / 60000);
			String time = form.format(x);
			JOptionPane.showMessageDialog(null, "It takes:" + time
					+ " minute to answer this question");

		

		// ===================this is to change html files to text files

		/*
		 * String dataDir = "C:\\datadir\\htmldata"; File dir = new
		 * File(dataDir); if (dir.exists()) { File[] files = dir.listFiles();
		 * for (int i = 0; i < files.length; i++) { File filePath = new
		 * File(files[i].getAbsolutePath()); String content =
		 * FileUtils.readFileToString(filePath); //
		 * content=norm.NormalizeQuery(content); FileUtils.writeStringToFile(new
		 * File( "C:\\datadir\\testhtml\\"+filePath.getName()+".txt"), content);
		 * }
		 * 
		 * }
		 */
		// this is to convert html files to text files
		/*
		 * String dataDir = "C:\\datadir\\testhtml"; File dir = new
		 * File(dataDir); if (dir.exists()) { File[] files = dir.listFiles();
		 * for (int i = 0; i < files.length; i++) { File filePath = new
		 * File(files[i].getAbsolutePath()); String x =
		 * FileUtils.readFileToString(filePath); if (x.startsWith("<!DOCTYPE"))
		 * { x = x.replace("\n", " "); x = x.replace("Headlines", ""); x =
		 * x.replace("(c)", ""); x = x.replace("The Ethiopian News Agency", "");
		 * x = x .replace(
		 * "㰊瑨汭㰾潢祤㰾楤⁶瑳汹㵥搢獩汰祡渺湯≥㰾晩慲敭猠捲∽瑨灴⼺洯獹祰楦敬⹳湣焯穡獷⽸湩敤⹸桰≰眠摩桴∽㈳∰栠楥桧㵴㈢〴㸢⼼晩慲敭㰾搯癩㰾戯摯㹹⼼瑨汭>"
		 * , ""); x = x.replace("&nbsp;", ""); x = x.replaceAll("<.*?>", "");
		 * StringTokenizer st = new StringTokenizer(x, "END"); while
		 * (st.hasMoreElements()) { FileUtils.writeStringToFile(filePath,
		 * st.nextToken()); break; } }
		 * 
		 * }
		 * 
		 * }
		 */
		// again to normalize text files converted from html
		/*
		 * String dataDir = "C:\\datadir\\testhtml"; File dir = new
		 * File(dataDir); if (dir.exists()) { File[] files = dir.listFiles();
		 * for (int i = 0; i < files.length; i++) { File filePath = new
		 * File(files[i].getAbsolutePath()); String x =
		 * FileUtils.readFileToString(filePath); //"።"); int
		 * lastaratnetb=x.indexOf("።");//0;//x.indexOf("።"); int mostlast=0;
		 * for(int j=0;j<x.length();j++) { if(lastaratnetb>0
		 * &&lastaratnetb<x.length())
		 * {lastaratnetb=x.indexOf("።",lastaratnetb+1); if(lastaratnetb>0)
		 * mostlast=lastaratnetb; } } if(mostlast<x.length()) x=x.substring(0,
		 * mostlast+1); else x=x.substring(0, mostlast);
		 * FileUtils.writeStringToFile(filePath, x); } }
		 */
		// copy all files to one folder
		/*
		 * String dataDir = "C:\\datadir\\textdata";//testhtml";plus the text
		 * files already obtained File dir = new File(dataDir); if
		 * (dir.exists()) { File[] files = dir.listFiles(); int k=7136;//already
		 * present files =7136 for (int i = 0; i < files.length; i++) { k=k+1;
		 * File filePath = new File(files[i].getAbsolutePath()); String x =
		 * FileUtils.readFileToString(filePath); FileUtils.writeStringToFile(new
		 * File("C:\\datadir\\alldata\\"+"File"+k+".txt"), x); } }
		 */
	}

}
