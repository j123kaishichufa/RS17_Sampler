package jwxsampler;

import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

public class SystemSampler 
{
	private Sigar sigar;
	
	public SystemSampler(Sigar sigar) 
	{
		// TODO Auto-generated constructor stub
		this.sigar = sigar;
	}
	
	public void getCpuUsage()
	{
		//CpuPerc: CPU percentage usage 
		CpuPerc cpus[];
		try {
			cpus = sigar.getCpuPercList();
			for(int i = 0; i < cpus.length; i++)
			{
				double idle  = cpus[i].getIdle();
				double userUse = cpus[i].getUser();
				double sysUse = cpus[i].getSys();
				double wait = cpus[i].getWait();
				double nice = cpus[i].getNice();
				//sum of user + sys + nice + wait, that is summarizing utilization
				double utilization = cpus[i].getCombined();
				System.out.printf("cpu:%d, use= %.4f, idle= %.4f\n", i, utilization, idle);
			}
		} catch (SigarException e) {
		   // TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	public Map<String, String> getMemUsage()
	{
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			Mem mem = sigar.getMem();

			double usedMem = mem.getUsedPercent();			
			double freeMem = mem.getFreePercent();
			long actualUsedMem = mem.getActualUsed();
			long actualFreeMem = mem.getActualFree();
			long totalMem = mem.getTotal();
			
			Swap swap = sigar.getSwap();
			long usedSwap = swap.getUsed();
			long freeSwap = swap.getFree();
			long totalSwap = swap.getTotal();
			
			resultMap.put("systemMemUsed", Double.toString(usedMem));
			resultMap.put("systemMemFreed", Double.toString(freeMem));
			
			resultMap.put("systemActMemUsed", Long.toString(actualUsedMem));
			resultMap.put("systemActMemFreed", Long.toString(actualFreeMem));
			
			
			System.out.printf("memory: used= %.2f, free= %.2f\n", usedMem, freeMem);
			
			
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
	}
}
