package sjsu.edu.cmpe275.model;

import java.time.LocalDateTime;

public class VirtualTime {
	private LocalDateTime systemTime;
	
	private static VirtualTime virtualTime = new VirtualTime();
	private VirtualTime(){
		
	}
	
	public static VirtualTime getInstance() {
		return virtualTime;
	}

	public LocalDateTime getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(LocalDateTime systemTime) {
		this.systemTime = systemTime;
	}
	
}
