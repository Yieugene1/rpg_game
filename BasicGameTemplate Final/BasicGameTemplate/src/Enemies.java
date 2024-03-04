//Student: Jintao Yi 
//Student num:23205376
import util.*;

public class Enemies extends GameObject
{
    private int HP = 100;
    private String name;
    private int energy = 100;
    private long lastAttackTime = 0;
    private long ATTACK_COOLDOWN=0;
    private boolean RightDirection = true;
    private String[] imgs_run = new String[24];
    private String[] imgs_attack = new String[24];
    private String[] imgs_hurt = new String[24];
    private String[] imgs_die = new String[24];
    private boolean canMove=true;
	private boolean High = false;
	
    public Enemies()
	{
		super();
	}
	public Enemies(String textureLocation,int hp,int damage, int width, int height, Point3f centre, String name,int time) {
        super(textureLocation, width, height, centre,damage);
        this.name = name;
        this.HP=hp;
        this.ATTACK_COOLDOWN=time;
        for (int i = 0; i < 14; i++) {
            imgs_run[i] = "res/Monster/" +name+ "/Running/" + i + ".png";
            imgs_attack[i] = "res/Monster/"+name + "/Slashing/" + i + ".png";
            imgs_hurt[i] = "res/Monster/" +name+ "/Hurt/" + i + ".png";
            imgs_die[i] = "res/Monster/"+name + "/Dying/" + i + ".png"; 
        }
    }

	public boolean isRightDirection() {
		return RightDirection;
	}

	public void setRightDirection(boolean rightDirection) {
		RightDirection = rightDirection;
	}

	public String[] getImgs_run() {
		return imgs_run;
	}

	public void setImgs_run(String[] imgs_run) {
		this.imgs_run = imgs_run;
	}

	public String[] getImgs_attack() {
		return imgs_attack;
	}

	public void setImgs_attack(String[] imgs_attack) {
		this.imgs_attack = imgs_attack;
	}

	public String[] getImgs_hurt() {
		return imgs_hurt;
	}

	public void setImgs_hurt(String[] imgs_hurt) {
		this.imgs_hurt = imgs_hurt;
	}

	public String[] getImgs_die() {
		return imgs_die;
	}

	public void setImgs_die(String[] imgs_die) {
		this.imgs_die = imgs_die;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}
	public boolean isHigh() {
		return High;
	}
	public void setHigh(boolean high) {
		High = high;
	}
	public void getDamage(int i) {
		HP=HP-i;
		
	}
	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}
	public long getLastAttackTime() {
		return lastAttackTime;
	}
	public void setLastAttackTime(long lastAttackTime) {
		this.lastAttackTime = lastAttackTime;
	}
	public long getATTACK_COOLDOWN() {
		return ATTACK_COOLDOWN;
	}
	public void setATTACK_COOLDOWN(long aTTACK_COOLDOWN) {
		ATTACK_COOLDOWN = aTTACK_COOLDOWN;
	}

	}

