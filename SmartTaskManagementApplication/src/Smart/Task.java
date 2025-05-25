package Smart;
import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
	public enum Status {COMPLETED,PENDING,OVERDUE};
	public enum Priority {LOW,MEDIUM,HIGH};
	private static final long serialVersionUID = 1L;
	private LocalDate deadLine;
	private String name;
	private String description;
	private Status status;
	private Priority priority;
	
	public Task()
	{
		this.deadLine=LocalDate.now() ;
		this.name="";
		this.description ="";
		this.status=Status.PENDING;
		this.priority=Priority.MEDIUM;
	}
	public Task( String n ,String des,LocalDate d ,Status status2 ,Priority priority2)
	{
		this.deadLine = d;
		this.name = n;
		this.description = des;
		this.status =status2;
		this.priority =priority2;
	}
	public synchronized void  setDeadLine (LocalDate d)
	{
		this.deadLine = d;
	}
	public synchronized void setName(String n)
	{
		name = n;
	}
	public synchronized void setDescription (String des)
	{
		description = des;
	}
	public synchronized LocalDate getDeadLine()
	{
		return deadLine;
	}
	public synchronized String getName()
	{
		return name;
	}
	public synchronized String getDescription ()
	{
		return description ;
	}
	public  synchronized void setStatus (Status s)
	{
		status =s;
	}
	public synchronized void setPriority (Priority p)
	{
		priority =p;
	}
	public synchronized Status getStatus ()
	{
		return status;
	}
	public synchronized Priority getPriority()
	{
		return priority;
	}
}