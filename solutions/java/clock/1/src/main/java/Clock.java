public class Clock {
	private int minutes = 0;
	
	public Clock(int hours, int minutes) {
		this.add(hours * 60 + minutes);
	}
	
	public void add(int minutes) {
		this.minutes += minutes;
	}
	
	@Override
	public String toString() {
		return String.format("%02d:%02d", this.getHours(), this.getMinutes());
	}
	
	@Override
	public boolean equals(Object other) {
		if ((other == null) || (this.getClass() != other.getClass()))
			return false;
		return this.hashCode() == other.hashCode();
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	private int getHours() {
		return Math.floorMod(Math.floorDiv(this.minutes, 60), 24);
	}
	
	private int getMinutes() {
		return Math.floorMod(this.minutes, 60);
	}
}