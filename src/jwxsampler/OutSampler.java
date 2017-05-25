package jwxsampler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
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
		Logger logger = Logger.getLogger(Object.class);
		String processName="eclipse";
		Sigar sigar = new Sigar();
		Boolean isStart = true;
		SystemSampler systemSampler = new SystemSampler(sigar);
		ProcessSampler processSampler = new ProcessSampler(sigar, processName);
		
		while(true)
		{
			Map<String, String> resultMap = new HashMap<String, String>();
			Map<String, String> map1 = systemSampler.getCpuUsage();
			Map<String, String> map2 = systemSampler.getMemUsage();
			Map<String, String> map3 = processSampler.getCpuUsage();
			Map<String, String> map4 = processSampler.getMemUsage();
			resultMap.putAll(map1);
			resultMap.putAll(map2);
			resultMap.putAll(map3);
			resultMap.putAll(map4);
			
			//logging it
			String logString = "";
			String logTitle = "";
			for(Map.Entry<String, String> entry: resultMap.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				logTitle += key;
				logTitle += ";";
				logString += value;
				logString += ";";
				
			}
			//remove the last fenhao
			logTitle = logTitle.substring(0, logTitle.length() - 1); 
			logString = logString.substring(0, logString.length() - 1); 
			if(isStart)
			{
				//System.out.println(logTitle);
				logger.info(logTitle);
				isStart = false;
			}
			//System.out.println(logString);
			logger.info(logString);

			Thread.sleep(1000);
			
			

			
		}
	}
}
