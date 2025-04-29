package Smart;

public class Task {
	
	private int deadLine;
	private String name;
	private String description;
	
	public Task()
	{
		deadLine =0;
		name="";
		description ="";
	}
	public Task(int d ,String n ,String des)
	{
		deadLine = d;
		name = n;
		description = des;
	}
	public void setDeadLine (int d)
	{
		deadLine = d;
	}
	public void setName(String n)
	{
		name = n;
	}
	public void setDescription (String des)
	{
		description = des;
	}
	public int getDeadLine()
	{
		return deadLine;
	}
	public String getName()
	{
		return name;
	}
	public String getDescription ()
	{
		return description ;
	}
}
