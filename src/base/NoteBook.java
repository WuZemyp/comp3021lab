package base;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class NoteBook {
	private ArrayList<Folder> folders;
	public NoteBook()
	{
		folders=new ArrayList<Folder>();
	}
	public boolean createTextNote(String folderName, String title)
	{
		TextNote note = new TextNote(title);
		return insertNote(folderName,note);
	}
	public boolean createTextNote(String folderName,String title,String content)
	{
		TextNote note=new TextNote(title,content);
		return insertNote(folderName,note);
	}
	public boolean createImageNote(String folderName, String title)
	{
		ImageNote note = new ImageNote(title);
		return insertNote(folderName,note);
	}
	public ArrayList<Folder> getFolders() {
		return folders;
	}
	public boolean insertNote(String folderName,Note note)
	{
		Folder a=null;
		for(Folder f:folders)
		{
			if(f.getName().equals(folderName))
			{
				a=f;
				break;
			}
		}
		if(a==null)
		{
			a=new Folder(folderName);
			folders.add(a);
		}
		for(Note n:a.getNotes())
		{
			if(n.equals(note))
			{
				System.out.println("Creating note "+note.getTitle()+" under folder "+folderName+" failed");
				return false;
			}
		}
		a.addNote(note);
		return true;
	}
	public void sortFolders()
	{
		for(Folder f:folders)
		{
			Collections.sort(f.getNotes());
		}
		Collections.sort(folders);
	}
	public List<Note> searchNotes(String keywords)
	{
		List<Note> newone=new ArrayList<Note>();
		for(Folder f:folders)
		{
			newone.addAll(f.searchNotes(keywords));
		}
		return newone;
	}
}
