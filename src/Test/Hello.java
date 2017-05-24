package Test;
import java.util.ArrayList;
import java.util.List;

//import org.hyperic.sigar.CpuInfo;
//import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.Ps;

public class Hello 
{
	

	private Sigar sigar = new Sigar();
	public List<ProcessInfo> processInfos;
	
	//private StringBuilder builder;
	
	//获取进程的相关信息
	public  void getProcessInfo()
	{
		Ps ps = new Ps();
		processInfos = new ArrayList<ProcessInfo>();
		try {
			long[] pids = sigar.getProcList();
			for(long pid : pids)
			{
				List<String> list = ps.getInfo(sigar, pid);
				ProcessInfo info = new ProcessInfo();
				for(int i = 0; i <= list.size(); i++)
				{
					switch(i)
					{
						case 0 : info.setPid(list.get(0)); break;
						case 1 : info.setUser(list.get(1)); break;
						case 2 : info.setStartTime(list.get(2)); break;
						case 3 : info.setMemSize(list.get(3)); break;
						case 4 : info.setMemUse(list.get(4)); break;
						case 5 : info.setMemhare(list.get(5)); break;
						case 6 : info.setState(list.get(6)); break;
						case 7 : info.setCpuTime(list.get(7)); break;
						case 8 : info.setName(list.get(8)); break;
					}
				}
				processInfos.add(info);
			}
		} catch (SigarException e) {
			e.printStackTrace();
		}
	
	}
	

	
	public static void main(String[] args) {
		Hello info = new Hello(); 
		
		info.getProcessInfo();
		
		for (ProcessInfo processInfo: info.processInfos)
		{
			System.out.print("PID: " + processInfo.getPid());
			System.out.print("  CPUTIME: " + processInfo.getCpuTime());
			System.out.print("  MEMSHAR: " + processInfo.getMemhare());
			System.out.print("  MEMUSE:  " + processInfo.getMemUse());
			System.out.print("  NAME:    " + processInfo.getName());
			System.out.print("  MEMSIZE: " + processInfo.getMemSize() + "\n");
		}
		
	}

}




