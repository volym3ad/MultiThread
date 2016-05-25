
public class CPUProcess implements Runnable
{
	static int allProcesses = 0;
	static int deleted = 0;	
	static int iD = 0;
	
	int id;
	CPU cpu1;
	CPU cpu2;
	Process process;
	CPUQueue queue;
	Thread th;
	boolean inter = false;
	int creat = 100;
	
	
	public CPUProcess(CPUQueue q, int mn, CPU cpu01, CPU cpu02)
		{
			cpu1 = cpu01;
			cpu2 = cpu02;
			this.creat += mn;
			this.id = iD++;
			this.queue = q;
			th = new Thread(this);
			th.start();
		}


	@Override
	public void run()
	{		
		if (id == 0) this.runProc1();
		else this.runProc2();		
	}
	
	public void runProc1 ()
	{
		System.out.println("Producer" + id + " begin with time = " + creat);
		while(!inter)
			{
			process = Process.createProcess(this);
			allProcesses++;
			if (cpu1.process == null) {
				cpu1.addProcess(process);
				System.out.println("Process:" + process + " added on CPU1");
			}
			else {
				if (cpu1.process.myProc == 0 & cpu1.process == null)
				{
					cpu2.addProcess(process);
					System.out.println("Process:" + process + " added on CPU2");
				} else if (cpu1.process.myProc == 0 & cpu2.process != null)
				{
					System.out.println("Process:" + process + " deleted");
					process = null;					
					deleted++;
				} else if (cpu1.process.myProc == 1)
				{
					System.out.println("Process:" + process + " deleted");
					process = null;					
					deleted++;
				}
				try	{
					Thread.sleep(creat);
				}
				catch (InterruptedException e) {e.printStackTrace();}
			}
			}
		System.out.println("Producer" + id + " has finished!");
	}
	
	public void runProc2 ()
	{
		System.out.println("Producer" + id + " begin with time = " + creat);
		while(!inter)
			{
			process = Process.createProcess(this);
			allProcesses++;
			if (cpu2.process == null) 
				{
				cpu2.addProcess(process);
				System.out.println("Process:" + process + " added on CPU2");
				}
			else {
				queue.addProcess(process);
				System.out.println("Process:" + process + " added to Queue");
			}
			try	{
				Thread.sleep(creat);
			} catch (InterruptedException e) {e.printStackTrace();}
			}
		System.out.println("Producer" + id + " has finished!");
	}

	public void setInter()
	{
		this.inter = true;
	}
	
	public String toString()
	{
		return  "" + id;
	}
}
