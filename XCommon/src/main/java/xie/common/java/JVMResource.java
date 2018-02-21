package xie.common.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class JVMResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		new JVMResource().printAllSummary();
		new JVMResource().printBaseSummary();
	}

	private NumberFormat fmtI = new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.ENGLISH));
	private NumberFormat fmtD = new DecimalFormat("###,##0.000", new DecimalFormatSymbols(Locale.ENGLISH));

	private final int Mb = 1024 * 1024;

	public void printAllSummary() {
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
		ThreadMXBean threads = ManagementFactory.getThreadMXBean();
		MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
		MemoryUsage heapUsage = mem.getHeapMemoryUsage();
		ClassLoadingMXBean cl = ManagementFactory.getClassLoadingMXBean();
//		System.out.printf("jvmName:%s %s %s%n", runtime.getVmName(), "version", runtime.getVmVersion());
//		System.out.printf("jvmJavaVer:%s%n", System.getProperty("java.version"));
//		System.out.printf("jvmVendor:%s%n", runtime.getVmVendor());
//		System.out.printf("jvmUptime:%s%n", toDuration(runtime.getUptime()));
//		System.out.printf("threadsLive:%d%n", threads.getThreadCount());
//		System.out.printf("threadsDaemon:%d%n", threads.getDaemonThreadCount());
//		System.out.printf("threadsPeak:%d%n", threads.getPeakThreadCount());
//		System.out.printf("threadsTotal:%d%n", threads.getTotalStartedThreadCount());
//		System.out.printf("heapCurr:%d%s%n", mem.getHeapMemoryUsage().getUsed() / Mb, "MB");
//		System.out.printf("heapMax:%d%s%n", mem.getHeapMemoryUsage().getMax() / Mb, "MB");
//		System.out.printf("heapCommitted:%d%s%n", mem.getHeapMemoryUsage().getCommitted() / Mb, "MB");
//		System.out.printf("osName:%s %s %s%n", os.getName(), "version", os.getVersion());
//		System.out.printf("osArch:%s%n", os.getArch());
//		System.out.printf("osCores:%s%n", os.getAvailableProcessors());
//		System.out.printf("clsCurrLoaded:%s%n", cl.getLoadedClassCount());
//		System.out.printf("clsLoaded:%s%n", cl.getTotalLoadedClassCount());
//		System.out.printf("clsUnloaded:%s%n", cl.getUnloadedClassCount());
		logger.info(String.format("uptime:     %s", toDuration(runtime.getUptime())));
		logger.info(String.format("os:         %s %s, %s %s, %s %s ", os.getName(), os.getVersion(), "cpuCore", os.getAvailableProcessors(), "arch", os.getArch()));
		logger.info(String.format("jvm:        %s %s, %s %s", System.getProperty("java.version"), runtime.getVmVendor(), runtime.getVmName(), runtime.getVmVersion()));
		logger.info(String.format("heapUsage:  %s %d%s, %s %d%s, %s %d%s", "used", heapUsage.getUsed() / Mb, "MB", "committed", heapUsage.getCommitted() / Mb, "MB", "max", heapUsage.getMax() / Mb, "MB"));
		logger.info(String.format("threads:    Live %d, Daemon %d, Peak %d, Total %d", threads.getThreadCount(), threads.getDaemonThreadCount(), threads.getPeakThreadCount(), threads.getTotalStartedThreadCount()));
		logger.info(String.format("class:      Loaded %s, Unloaded %s, total %s", cl.getLoadedClassCount(), cl.getUnloadedClassCount(), cl.getTotalLoadedClassCount()));
	}

	public void printBaseSummary() {
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
		MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
		MemoryUsage heapUsage = mem.getHeapMemoryUsage();
		logger.info(String.format("uptime:     %s", toDuration(runtime.getUptime())));
		logger.info(String.format("os:         %s %s, %s %s, %s %s ", os.getName(), os.getVersion(), "cpuCore", os.getAvailableProcessors(), "arch", os.getArch()));
		logger.info(String.format("jvm:        %s %s, %s %s", System.getProperty("java.version"), runtime.getVmVendor(), runtime.getVmName(), runtime.getVmVersion()));
		logger.info(String.format("heapUsage:  %s %d%s, %s %d%s, %s %d%s", "used", heapUsage.getUsed() / Mb, "MB", "committed", heapUsage.getCommitted() / Mb, "MB", "max", heapUsage.getMax() / Mb, "MB"));
	}

	protected String printSizeInKb(double size) {
		return fmtI.format((long) (size / 1024)) + " kbytes";
	}

	protected String toDuration(double uptime) {
		uptime /= 1000;
		if (uptime < 60) {
			return fmtD.format(uptime) + " seconds";
		}
		uptime /= 60;
		if (uptime < 60) {
			long minutes = (long) uptime;
			String s = fmtI.format(minutes) + (minutes > 1 ? " minutes" : " minute");
			return s;
		}
		uptime /= 60;
		if (uptime < 24) {
			long hours = (long) uptime;
			long minutes = (long) ((uptime - hours) * 60);
			String s = fmtI.format(hours) + (hours > 1 ? " hours" : " hour");
			if (minutes != 0) {
				s += " " + fmtI.format(minutes) + (minutes > 1 ? " minutes" : " minute");
			}
			return s;
		}
		uptime /= 24;
		long days = (long) uptime;
		long hours = (long) ((uptime - days) * 24);
		String s = fmtI.format(days) + (days > 1 ? " days" : " day");
		if (hours != 0) {
			s += " " + fmtI.format(hours) + (hours > 1 ? " hours" : " hour");
		}
		return s;
	}

}
