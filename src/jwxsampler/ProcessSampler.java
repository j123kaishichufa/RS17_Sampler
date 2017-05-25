package jwxsampler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.ptql.ProcessFinder;

public class ProcessSampler 
{
	private Sigar sigar;
	private long pid;
	private String WINDOWS_PATTERN = "Exe.Name.ct=";
	private String LINUX_PATTERN = "";
	
		
	public ProcessSampler(Sigar sigar, String processName) throws SigarException 
	{
		// TODO Auto-generated constructor stub
		this.sigar = sigar;
		//get pid by process name
		String query = WINDOWS_PATTERN + processName;
		ProcessFinder finder = new ProcessFinder(sigar);
		this.pid = finder.findSingleProcess(query);	
	}
	
	public void getCpuUsage()
	{
		ProcCpu procCpu = null;
		
		try {
			procCpu = sigar.getProcCpu(pid);
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR: sigar.getProcCpu(pid) failed...");
		}
		
		try {
			if(procCpu != null)
			{
				procCpu.gather(sigar, pid);
			}
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR: procCpu.gather(sigar, pid) failed...");
		}
		
		double cpuUse= procCpu.getPercent();
		//double cpuUse = procCpu.getTotal()*100/( (procCpu.getLastTime()-procCpu.getStartTime())*1.0 );
		System.out.printf("PID: %d, cpuuse= %.4f\n", pid, cpuUse);
	}
	
	public void getMemUsage()
	{
		ProcMem procMem = null;
		try {
			procMem = sigar.getProcMem(pid);
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR: sigar.getProcMem(pid) failed ...");
		}
		
		try {
			if(procMem != null)
			{
				procMem.gather(sigar, pid);
			}
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR: procMem.gather(sigar, pid) failed ...");
		}
		
		long memUse = procMem.getSize();
	}
	
	public List<String> getResult()
	{
		List<String> strList = new ArrayList<>();
		
		
		return strList;
	}
}
