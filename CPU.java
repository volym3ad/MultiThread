import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CPU implements Runnable
{
	static int iD = 0;
	
	int id;
	boolean flag = false;
	boolean contFlag = false;
	Process process;
	CPUQueue queue;
	Thread th;
	int creat = 100;
	Lock locker = new ReentrantLock();
	Condition cont = locker.newCondition();
	boolean emp = true;
	
	public CPU(CPUQueue q, int mn)
		{

			this.creat += mn;
			this.id = iD++;
			this.queue = q;
			th = new Thread(this, "" + id);
			th.start();
		}
	

	@Override
	public void run()
	{
		
		System.out.println("CPU" + id + " started!");
		while(!flag | emp)
		{			
			this.compute();
		}
		System.out.println("CPU" + id + " has finished!");
	}
	
	public void compute()
	{
		
		// Ниже идет костыль
		
		
		if (process == null) {
			Process p = queue.getProcess();
			if (p == null) {				
				locker.lock();
				try {
					while (process == null)
					{			
						contFlag = true;
						cont.await();						
					}
				}
				catch (InterruptedException e) { e.printStackTrace();} 
				finally {locker.unlock();}
			} else process = p;
		} 
		
		
		//Все что закоментировано ниже является адекватным кодом,
		//но какого-то хуя оно бросает эксепшн...
		
		
//		if (process == null)
//		{
//			if (queue.list.isEmpty()) {
//				locker.lock();
//				try {
//					while (process == null)
//					{			
//						contFlag = true;
//						cont.await();						
//					}
//				}
//				catch (InterruptedException e) { e.printStackTrace();} 
//				finally {locker.unlock();}
//			} else {
//				process = queue.getProcess();
//			}
//		}
		
		
		
		
		
		try
		{
			Thread.sleep(creat);
		} catch (InterruptedException e) { e.printStackTrace();}
		System.out.println("-----Process(" + process.id + ") has finished on CPU" + (this.id+1));
		process = null;		
		try
		{
			Thread.sleep(creat);
		} catch (InterruptedException e) { e.printStackTrace();}
		if (emp & queue.list.isEmpty()) emp = false;
	}		
	
	synchronized void addProcess(Process proc)
	{
		this.process = proc;
		if (contFlag | flag) cont.signal();;
	}
	
	public void upFlaf(boolean a)
	{
		this.flag = true;
		this.emp = a;
	}
}
