
public class Process
{
	int myProc;
	static int iD = 0;
	int id;
	
	public Process(CPUProcess proc)
		{
			this.myProc = proc.id;
			this.id = iD++;						
		}	

	public String toString()
	{
		return myProc + "x" + this.id; 
	}
	
	public static synchronized Process createProcess(CPUProcess proc)
	{
		return new Process(proc);
	}
}
