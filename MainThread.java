
public class MainThread 
{
	
	public static void main (String args[])
	{
		CPUQueue q = new CPUQueue();
		CPU c1 = new CPU(q, 300);
		CPU c2 = new CPU(q, 200);
		CPUProcess p1 = new CPUProcess(q, 200, c1, c2);
		CPUProcess p2 = new CPUProcess(q, 100, c1, c2);
		
		try
			{
				Thread.sleep(3000);
			} catch (InterruptedException e) {e.printStackTrace();}
		
		p1.setInter();
		p2.setInter();
		System.out.println(q.list);
		c1.upFlaf(true);
		c2.upFlaf(true);
		try
			{
				c1.th.join();
				c2.th.join();
			} catch (InterruptedException e) { e.printStackTrace();}
		System.out.println("Percent of deleted processes: " + ((double)CPUProcess.deleted /(double)CPUProcess.allProcesses)*100 + "%");
	}

}
