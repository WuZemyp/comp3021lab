package base;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Folder implements Comparable<Folder>,java.io.Serializable{
	private ArrayList<Note> notes;
	private String name;
	private static final long serialVersionUID = 1L;

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
	@Override
	public int compareTo(Folder o) {
		// TODO Auto-generated method stub
		return this.name.compareTo(o.name);
	}
	public void sortNotes()
	{
		Collections.sort(notes);
	}
	public List<Note> searchNotes(String keywords)
	{
		String [] keys= keywords.split(" ");
		List<Note> aa=new ArrayList<Note>();
		if(keys.length==1)
		{
			for(Note n:notes)
			{
				if(n instanceof ImageNote)
				{
					if(n.getTitle().toLowerCase().contains(keys[0].toLowerCase()))
					{

						aa.add(n);
					}
				}
				else
				{
					if(n.getTitle().toLowerCase().contains(keys[0].toLowerCase())||((TextNote)n).getcontent().toLowerCase().contains(keys[0].toLowerCase()))
					{
						aa.add(n);
					}
				}
			}
			return aa;
		}
		String current;
		List<Note> newone=new ArrayList<Note>();
		boolean indicator=true;
		boolean indicator2=true;
		int j=0;
		List<String> orone=new ArrayList<String>();
		for(Note n:notes)
		{
			indicator=true;
			if(n instanceof ImageNote)
			{
				current=n.getTitle().toLowerCase();
				for(int i=0;i<keys.length;++i)
				{
					if(i+1>=keys.length)
					{
						if(keys[i-1].compareToIgnoreCase("or")==0)
						{
							break;
						}
						if(!current.contains(keys[i].toLowerCase()))
						{
							indicator=false;
						}
						break;
					}
					if(keys[i+1].compareToIgnoreCase("or")==0)
					{
//						if(!(current.contains(keys[i].toLowerCase())||current.contains(keys[i+2].toLowerCase())))
//						{
//							indicator=false;
//							break;
//						}
//						i+=2;
						j=i+3;
						orone.clear();
						orone.add(keys[i]);
						orone.add(keys[i+2]);
						while(j<keys.length&&keys[j].compareToIgnoreCase("or")==0)
						{
							orone.add(keys[j+1]);
							j+=2;
						}
						indicator2=false;
						for(String s:orone)
						{
							if(current.contains(s.toLowerCase()))
							{
								indicator2=true;
							}
						}
						if(j>=keys.length)
						{
							i=keys.length;
						}
						else
						{
							i=j-1;
						}
						if(!indicator2)
						{
							indicator=false;
							break;
						}
					}
					else
					{
						if(!current.contains(keys[i].toLowerCase()))
						{
							indicator=false;
							break;
						}
					}
				}
				if(indicator)
				{
					newone.add(n);
				}
			}
			else
			{
				current=n.getTitle().toLowerCase();
				for(int i=0;i<keys.length;++i)
				{

					if(i+1>=keys.length)
					{
						if(keys[i-1].compareToIgnoreCase("or")==0)
						{
							break;
						}
						if(!current.contains(keys[i].toLowerCase()))
						{
							indicator=false;
						}
						break;
					}
					if(keys[i+1].compareToIgnoreCase("or")==0)
					{
//						if(!(current.contains(keys[i].toLowerCase())||current.contains(keys[i+2].toLowerCase())))
//						{
//							indicator=false;
//							break;
//						}
//						i+=2;
						j=i+3;
						orone.clear();
						orone.add(keys[i]);
						orone.add(keys[i+2]);
						while(j<keys.length&&keys[j].compareToIgnoreCase("or")==0)
						{
							orone.add(keys[j+1]);
							j+=2;
						}
						indicator2=false;
						for(String s:orone)
						{
							if(current.contains(s.toLowerCase()))
							{
								indicator2=true;
							}
						}
						if(j>=keys.length)
						{
							i=keys.length;
						}
						else
						{
							i=j-1;
						}
						if(!indicator2)
						{
							indicator=false;
							break;
						}
					}
					else
					{
						if(!current.contains(keys[i].toLowerCase()))
						{
							indicator=false;
							break;
						}
					}
				}
				if(indicator)
				{
					newone.add(n);
					continue;
				}
				indicator=true;
				TextNote a=(TextNote)n;
				current=a.getcontent().toLowerCase();
				for(int i=0;i<keys.length;++i)
				{

					if(i+1>=keys.length)
					{
						if(keys[i-1].compareToIgnoreCase("or")==0)
						{
							break;
						}
						if(!current.contains(keys[i].toLowerCase()))
						{
							indicator=false;
						}
						break;
					}
					if(keys[i+1].compareToIgnoreCase("or")==0)
					{
//						if(!(current.contains(keys[i].toLowerCase())||current.contains(keys[i+2].toLowerCase())))
//						{
//							indicator=false;
//							break;
//						}
//						i+=2;
						j=i+3;
						orone.clear();
						orone.add(keys[i]);
						orone.add(keys[i+2]);
						while(j<keys.length&&keys[j].compareToIgnoreCase("or")==0)
						{
							orone.add(keys[j+1]);
							j+=2;
						}
						indicator2=false;
						for(String s:orone)
						{
							if(current.contains(s.toLowerCase()))
							{
								indicator2=true;
							}
						}
						if(j>=keys.length)
						{
							i=keys.length;
						}
						else
						{
							i=j-1;
						}

						if(!indicator2)
						{
							indicator=false;
							break;
						}
					}
					else
					{
						if(!current.contains(keys[i].toLowerCase()))
						{
							indicator=false;
							break;
						}
					}
				}
				if(indicator)
				{
					newone.add(n);

				}
			}
		}
		return newone;
	}

}
