package jwxsampler;

import java.util.concurrent.TimeUnit;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class OutSampler 
{
	public static long getTime()
	{
		long ms =  System.currentTimeMillis();
		return ms;
	}
		
	public static void main(String[] args) throws SigarException, InterruptedException 
	{
		String processName="";
		Sigar sigar = new Sigar();
		
		SystemSampler systemSampler = new SystemSampler(sigar);
		ProcessSampler processSampler = new ProcessSampler(sigar, processName);
		
		while(true)
		{
			
			Thread.sleep(1000);
			
			Record oneRecord = new Record(getTime(), 
					double systemMemUsed, double systemMemFreed, 
					double systemSwapUsed, double systemSwapFreed, 
					double processCpuUsed, long processVmemUsed);
			 
			oneRecord.toString();
			
		}
	}
}
