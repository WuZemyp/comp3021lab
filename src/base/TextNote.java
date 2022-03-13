package base;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class TextNote extends Note {
	private String content;
	private static final long serialVersionUID = 1L;
	public TextNote(String title)
	{
		super(title);
	}
	public TextNote(File f)
	{
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());
	}
	public TextNote(String title,String content)
	{
		super(title);
		this.content=content;
	}
	public String getcontent()
	{
		return content;
	}
	private String getTextFromFile(String absolutePath)
	{
		String result = "";
		// TODO
		try{
			File filename=new File(absolutePath);
			FileInputStream fis=new FileInputStream(filename);
			InputStreamReader reader=new InputStreamReader(fis);
			BufferedReader br=new BufferedReader(reader);
			String line=br.readLine();

			while(line!=null)
			{
				result+=line;
				line=br.readLine();

			}

			fis.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}

		return result;
	}
	public void exportTextToFile(String pathFolder) {
		//TODO
		if(pathFolder=="")
		{
			pathFolder=".";
		}
		String [] arrfilename=this.getTitle().split(" ");
		String filename="";
		if(arrfilename.length==1)
		{
			filename=arrfilename[0];
		}
		else
		{
			for(int i=0;i<arrfilename.length-1;++i)
			{
				filename+=arrfilename[i]+"_";
			}
			filename+=arrfilename[arrfilename.length-1];
		}
		try{
			File file = new File( pathFolder + File.separator + filename+ ".txt");
			FileWriter fw=new FileWriter(file);
			BufferedWriter bw=new BufferedWriter(fw);
			bw.write(this.content);
			fw.close();

		}catch(Exception e)
		{
			e.printStackTrace();
		}

		// TODO
	}

}
