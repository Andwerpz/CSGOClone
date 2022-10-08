package weapon;

public class AWP extends Weapon {

	public AWP() {
		super(10, 30, 1500, 30f, 1500f, 0.5f, 120, 7f, 0.02f, 2000);
	}
	
	public String getModelName() {
		return "awp";
	}
	
}
