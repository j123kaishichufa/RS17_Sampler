package jwxsampler;

import java.awt.image.RescaleOp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.DiskUsage;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

public class SystemSampler 
{
	private Sigar sigar;
	DecimalFormat df = new DecimalFormat("#.0000"); //formatting the double value

	Map<String, String> lastDiskUsageMap = new HashMap<String, String>();
	Map<String, String> lastNetworkUsageMap = new HashMap<String, String>();

	public SystemSampler(Sigar sigar) 
	{
		// TODO Auto-generated constructor stub
		this.sigar = sigar;
		lastDiskUsageMap.clear();
		lastNetworkUsageMap.clear();
	}
	
	
	public long getLastReadBytes(String devname)
	{
		long readBytes = 0;
		if(!lastDiskUsageMap.isEmpty())
		{
			 readBytes = Long.parseLong(lastDiskUsageMap.get(devname + "_readbytes"));
		}
		return readBytes;
	}
	public long getLastReads(String devname)
	{
		long reads = 0;
		if(!lastDiskUsageMap.isEmpty())
		{
			reads = Long.parseLong(lastDiskUsageMap.get(devname + "_reads"));
		}
		return reads;
	}
	public long getLastWriteBytes(String devname)
	{
		long writeBytes = 0;
		if(!lastDiskUsageMap.isEmpty())
		{
			writeBytes = Long.parseLong(lastDiskUsageMap.get(devname + "_writebytes"));
		}
		return writeBytes;
	}
	public long getLastWrites(String devname)
	{
		long writes = 0;
		if(!lastDiskUsageMap.isEmpty())
		{
			writes = Long.parseLong(lastDiskUsageMap.get(devname + "_writes"));
		}
		return writes;
	}
	
	
	
	public Map<String, String> getCpuUsage()
	{
		Map<String, String> resultMap = new HashMap<String, String>();
		//CpuPerc: CPU percentage usage 
		CpuPerc cpus[];
		try {
			cpus = sigar.getCpuPercList();
			for(int i = 0; i < cpus.length; i++)
			{
				//double idle  = cpus[i].getIdle();
				//double userUse = cpus[i].getUser();
				//double sysUse = cpus[i].getSys();
				//double wait = cpus[i].getWait();
				//double nice = cpus[i].getNice();
				//sum of user + sys + nice + wait, that is summarizing utilization
				double utilization = cpus[i].getCombined();
				resultMap.put("systemCpuUsed_" + Integer.toString(i), df.format(utilization));
				//System.out.printf("cpu:%d, use= %.4f, idle= %.4f\n", i, utilization, idle);
			}
		} catch (SigarException e) {
		   // TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
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
			
			resultMap.put("systemMemUsed", df.format(usedMem));
			//resultMap.put("systemMemFreed", df.format(freeMem));
			resultMap.put("systemActMemUsed", Long.toString(actualUsedMem));
			//resultMap.put("systemActMemFreed", Long.toString(actualFreeMem));
			//resultMap.put("systemSwapUsed", df.format( (float)(usedSwap * 1.0 /  totalSwap)) );
			//resultMap.put("sytemSwapFreed", df.format( (float)(freeSwap * 1.0 /  totalSwap)) );
			
			
			//System.out.printf("memory: used= %.2f, free= %.2f\n", usedMem, freeMem);
			
			
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
	}
	
	public long getLastNetTxBytes(String intfName)
	{
		long txBytes = 0;
		if(!lastNetworkUsageMap.isEmpty())
		{
			txBytes = Long.parseLong(lastNetworkUsageMap.get(intfName + "_txbytes"));
		}
		return txBytes;
	}
	public long getLastNetTxCarriers(String intfName)
	{
		long txCarrier = 0;
		if(!lastNetworkUsageMap.isEmpty())
		{
			txCarrier = Long.parseLong(lastNetworkUsageMap.get(intfName + "_txcarriers"));
		}
		return txCarrier;
	}
	public long getLastNetTxCollisions(String intfName)
	{
		long txCollisions = 0;
		if(!lastNetworkUsageMap.isEmpty())
		{
			txCollisions = Long.parseLong(lastNetworkUsageMap.get(intfName + "_txcollisions"));
		}
		return txCollisions;
	}
	public long getLastNetTxDropped(String intfName)
	{
		long txDropped = 0;
		if(!lastNetworkUsageMap.isEmpty())
		{
			txDropped = Long.parseLong(lastNetworkUsageMap.get(intfName + "_txdropped"));
		}
		return txDropped;
	}
	public long getLastNetTxErrors(String intfName)
	{
		long txErrors = 0;
		if(!lastNetworkUsageMap.isEmpty())
		{
			txErrors = Long.parseLong(lastNetworkUsageMap.get(intfName + "_txerrors"));
		}
		return txErrors;
	}
	public long getLastNetTxOverruns(String intfName)
	{
		long txOverruns = 0;
		if(!lastNetworkUsageMap.isEmpty())
		{
			txOverruns = Long.parseLong(lastNetworkUsageMap.get(intfName+ "_txoverruns")) ;
		}
		return txOverruns;
	}
	public long getLastNetTxPackets(String intfName)
	{
		long txPackets = 0;
		if(!lastNetworkUsageMap.isEmpty())
		{
			txPackets = Long.parseLong(lastNetworkUsageMap.get(intfName + "_txpackets"));
		}
		return txPackets;
	}

	
	public long getLastNetRxBytes(String intfName)
	{
		long rxBytes = 0;
		if(!lastNetworkUsageMap.isEmpty())
		{
			rxBytes = Long.parseLong(lastNetworkUsageMap.get(intfName + "_rxbytes"));
		}
		return rxBytes;
	}
	public long getLastNetRxDropped(String intfName)
	{
		long rxDropped = 0;
		if(!lastNetworkUsageMap.isEmpty())
		{
			rxDropped = Long.parseLong(lastNetworkUsageMap.get(intfName + "_rxdropped"));
		}
		return rxDropped;
	}
	public long getLastNetRxErrors(String intfName)
	{
		long rxErrors = 0;
		if(!lastNetworkUsageMap.isEmpty())
		{
			rxErrors = Long.parseLong(lastNetworkUsageMap.get(intfName + "_rxerrors") );
		}
		return rxErrors;
	}
	public long getLastNetRxOverruns(String intfName)
	{
		long rxOverruns = 0;
		if(!lastNetworkUsageMap.isEmpty())
		{
			rxOverruns = Long.parseLong(lastNetworkUsageMap.get(intfName + "_rxoverruns"));
		}
		return rxOverruns;
	}
	public long getLastNetRxFrames(String intfName)
	{
		long rxFrames = 0;
		if(!lastNetworkUsageMap.isEmpty())
		{
			rxFrames = Long.parseLong(lastNetworkUsageMap.get(intfName + "_rxframes"));
		}
		return rxFrames;
	}
	public long getLastNetRxPackets(String intfName)
	{
		long rxPackets = 0;
		if(!lastNetworkUsageMap.isEmpty())
		{
			rxPackets = Long.parseLong(lastNetworkUsageMap.get(intfName + "_rxpackets"));
		}
		return rxPackets;
	}
	public Map<String, String> getNetworkUsage()
	{
		Map<String, String> resultMap = new HashMap<String, String>();
		long timeDifference = 1000000000;
		try {
			String[] interflist = sigar.getNetInterfaceList();
			for(String intfName: interflist)
			{
				final NetInterfaceStat interfaceStat = this.sigar.getNetInterfaceStat(intfName);
				final long speed = interfaceStat.getSpeed();
				final long txBytes = interfaceStat.getTxBytes();
				final long txCarriers = interfaceStat.getTxCarrier();
				final long txCollisions = interfaceStat.getTxCollisions();
				final long txDropped = interfaceStat.getTxDropped();
				final long txErrors = interfaceStat.getTxErrors();
				final long txOverruns = interfaceStat.getTxOverruns();
				final long txPackets = interfaceStat.getTxPackets();
				
				final long rxBytes = interfaceStat.getRxBytes();
				final long rxDropped = interfaceStat.getRxDropped();
				final long rxErrors = interfaceStat.getRxErrors();
				final long rxFrame = interfaceStat.getRxFrame();
				final long rxOverruns = interfaceStat.getRxOverruns();
				final long rxPackets = interfaceStat.getRxPackets();
				
				double txBytesPerSecond = (txBytes - getLastNetTxBytes(intfName)) / (double) TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS);
				double txCarrierPerSecond = (txCarriers - getLastNetTxCarriers(intfName)) / (double) TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS);
				double txCollisionsPerSecond = (txCollisions - getLastNetTxCollisions(intfName)) / (double) TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS);
				double txDroppedPerSecond = (txDropped - getLastNetTxDropped(intfName)) / (double) TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS);
				double txErrorsPerSecond = (txErrors - getLastNetTxErrors(intfName)) / (double) TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS);
				double txOverrunsPerSecond = (txOverruns - getLastNetTxOverruns(intfName)) / (double) TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS);
				double txPacketsPerSecond = (txPackets - getLastNetTxPackets(intfName)) / (double)TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS);
				
				double rxBytesPerSecond = (rxBytes - getLastNetRxBytes(intfName)) / (double) TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS);
				double rxDroppedPerSecond = (rxDropped - getLastNetRxDropped(intfName)) / (double) TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS);
				double rxErrorsPerSecond = (rxErrors - getLastNetRxErrors(intfName)) / (double) TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS);
				double rxFramePerSecond = (rxFrame - getLastNetRxFrames(intfName)) / (double)TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS);
				double rxOverrunsPerSecond = (rxOverruns - getLastNetRxOverruns(intfName)) / (double)TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS);
				double rxPacketsPerSecond = (rxPackets - getLastNetRxPackets(intfName)) / (double)TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS);

				resultMap.put(interfaceStat + "_speed", Long.toString(speed));
				resultMap.put(intfName + "_txBytesPerS", df.format(txBytesPerSecond));
				resultMap.put(intfName + "_txCarrierPerS", df.format(txCarrierPerSecond));
				resultMap.put(intfName + "_txCollisionPerS", df.format(txCollisionsPerSecond));
				resultMap.put(intfName + "_txDroppedPerS", df.format(txDroppedPerSecond));
				resultMap.put(intfName + "_txErrorsPerS", df.format(txErrorsPerSecond));
				resultMap.put(intfName + "_txOverrunsPerS", df.format(txOverrunsPerSecond));
				resultMap.put(intfName + "_txPacketsPerS", df.format(txPacketsPerSecond));
				
				resultMap.put(intfName + "_rxBytesPerS", df.format(rxBytesPerSecond));
				resultMap.put(intfName + "_rxDroppedPerS", df.format(rxDroppedPerSecond));
				resultMap.put(intfName + "_rxErrorsPerS", df.format(rxErrorsPerSecond));
				resultMap.put(intfName + "_rxFramePerS", df.format(rxFramePerSecond));
				resultMap.put(intfName + "_rxOverrunsPerS", df.format(rxOverrunsPerSecond));
				resultMap.put(intfName + "_rxPacketsPerS", df.format(rxPacketsPerSecond));
				
				lastNetworkUsageMap.clear();
				lastNetworkUsageMap.put(intfName + "_txbytes", Long.toString(txBytes));
				lastNetworkUsageMap.put(intfName + "_txcarriers", Long.toString(txCarriers));
				lastNetworkUsageMap.put(intfName + "_txcollisions", Long.toString(txCollisions));
				lastNetworkUsageMap.put(intfName + "_txdropped", Long.toString(txDropped));
				lastNetworkUsageMap.put(intfName + "_txerrors",  Long.toString(txErrors));
				lastNetworkUsageMap.put(intfName + "_txoverruns", Long.toString(txOverruns));
				lastNetworkUsageMap.put(intfName + "_txpackets", Long.toString(txPackets));

				lastNetworkUsageMap.put(intfName + "_rxbytes", Long.toString(rxBytes));
				lastNetworkUsageMap.put(intfName + "_rxdropped", Long.toString(rxDropped));
				lastNetworkUsageMap.put(intfName + "_rxerrors", Long.toString(rxErrors));
				lastNetworkUsageMap.put(intfName + "_rxframes", Long.toString(rxFrame));
				lastNetworkUsageMap.put(intfName + "_rxoverruns", Long.toString(rxOverruns));
				lastNetworkUsageMap.put(intfName + "_rxpackets", Long.toString(rxPackets));
			}
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
		
		
	}
	
	

	
	public Map<String, String> getDiskUsage()
	{
		Map<String, String> resultMap = new HashMap<String, String>();
		long timeDifference = 1000000000;
		try {
			FileSystem[] fslist = sigar.getFileSystemList();
			for(FileSystem fs: fslist)
			{
				String devname = fs.getDevName();
				
				DiskUsage diskUsage = sigar.getDiskUsage(devname);
				double queue = diskUsage.getQueue();
				long readBytes = diskUsage.getReadBytes();
				long reads = diskUsage.getReads();
				double serviceTime = diskUsage.getServiceTime();
				long writeBytes = diskUsage.getWriteBytes();
				long writes = diskUsage.getWrites();
				
				double readBytesPerSecond = (readBytes - getLastReadBytes(devname)) / (double) TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS);
				double readsPerSecond = (reads - getLastReads(devname)) / (double) TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS); 
				double writeBytesPerSecond = (writeBytes - getLastWriteBytes(devname)) / (double) TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS);
				double writesPerSecond = (writes - getLastWrites(devname)) / (double) TimeUnit.SECONDS.convert(timeDifference, TimeUnit.NANOSECONDS); 
				
				//resultMap.put("deviceName", devname);
				resultMap.put(devname + "_queue", df.format(queue));
				resultMap.put(devname + "_readbytesPerS", df.format(readBytesPerSecond));
				resultMap.put(devname + "_readsPerS", df.format(readsPerSecond));
				resultMap.put(devname + "_writebytesPerS", df.format(writeBytesPerSecond) );
				resultMap.put(devname + "_writesPerS", df.format(writesPerSecond));
				resultMap.put(devname + "_servicetime", df.format(serviceTime));
				
				lastDiskUsageMap.clear();
				lastDiskUsageMap.put(devname + "_readbytes", Long.toString(readBytes));
				lastDiskUsageMap.put(devname + "_reads", Long.toString(reads));
				lastDiskUsageMap.put(devname + "_writebytes", Long.toString(writeBytes));
				lastDiskUsageMap.put(devname + "_writes", Long.toString(writes));
				
			}
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultMap;
	}
}
