package jwxsampler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class OutSampler
{
	private final long clockdifference;
	private final long offset;
	private final TimeUnit timeUnit;
	
	public OutSampler()
	{
		this.timeUnit = TimeUnit.NANOSECONDS;
		this.clockdifference = System.nanoTime() - (TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()));
		this.offset = this.clockdifference;
	}
	
	//get current timestamp
	public long getTime()
	{
		long ns;
		ns = this.timeUnit.convert(System.nanoTime() - this.offset,  TimeUnit.NANOSECONDS);
		return ns;
		
	}

	public static void main(String[] args) throws SigarException, InterruptedException
	{
		//mainly initialize the timer
		OutSampler outSampler = new OutSampler();
		
		Logger logger = Logger.getLogger(Object.class);
		String processName = args[0];
		Sigar sigar = new Sigar();
		Boolean isStart = true;
		SystemSampler systemSampler = new SystemSampler(sigar);
		ProcessSampler processSampler = new ProcessSampler(sigar, processName);

		System.out.println("process name: " + processName);

		while(true)
		{
			long timestamp = outSampler.getTime();
			Map<String, String> resultMap = new HashMap<String, String>();
			Map<String, String> map1 = systemSampler.getCpuUsage();
			Map<String, String> map2 = systemSampler.getMemUsage();
			Map<String, String> map3 = processSampler.getCpuUsage();
			Map<String, String> map4 = processSampler.getMemUsage();
			resultMap.put("timestamp", Long.toString(timestamp));
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
