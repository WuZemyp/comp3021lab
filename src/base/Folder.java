package base;
import java.util.ArrayList;
public class Folder {
	private ArrayList<Note> notes;
	private String name;
	public Folder(String name)
	{
		this.name=name;
		this.notes=new ArrayList<Note>();
	}
	public void addNote(Note a)
	{
		notes.add(a);
	}
	public String getName()
	{
		return this.name;
	}
	public ArrayList<Note> getNotes()
	{
		return this.notes;
	}
	public boolean equals(Folder other)
	{
		if(this.name.equals(other.name))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public String toString()
	{
		int nText=0;
		int nImage=0;
		for(Note note:notes)
		{
			if(note instanceof TextNote)
			{
				++nText;
			}
			else
			{
				++nImage;
			}
		}
		return name + ":" + nText + ":" + nImage;
	}
}
