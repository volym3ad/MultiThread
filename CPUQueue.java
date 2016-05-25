import java.util.LinkedList;

public class CPUQueue
{
	boolean flag = false;
	LinkedList<Process> list;
	
	public CPUQueue()
		{
			list = new LinkedList<>();
		}
	
	synchronized void addProcess (Process a)
	{
		list.addLast(a);
		if (flag) {
			flag = false;
			notifyAll();			
		}
	}
	
	synchronized Process getProcess ()
	{		
		if (list.isEmpty())
		{
			flag = true;
			try
			{
				wait(200);				
			} catch (InterruptedException e) { e.printStackTrace();}
			return null;
		}
		else 
			return list.removeFirst();
	}	
	
}
