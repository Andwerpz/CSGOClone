package weapon;

public class AK47 extends Weapon {

	public AK47() {
		super(30, 90, 100, 6f, 1000f, 2f, 30, 7f, 0.04f, 1000);
	}
	
	public String getModelName() {
		return "ak47";
	}

}
