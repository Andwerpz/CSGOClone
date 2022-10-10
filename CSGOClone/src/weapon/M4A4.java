package weapon;

public class M4A4 extends Weapon {
	
	public M4A4() {
		super(30, 90, 90, 5f, 1000f, 2f, 30, 7f, 0.06f, 1000);
		
		this.description = "More accurate but less damaging than its AK-47 counterpart, the M4A4 is the full-auto assault rifle of choice for CTs.";
	}
	
	public String getModelName() {
		return "m4a4";
	}

}
