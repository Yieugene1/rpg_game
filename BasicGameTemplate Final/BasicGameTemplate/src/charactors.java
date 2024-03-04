//Student: Jintao Yi 
//Student num:23205376
import util.GameObject;
import util.*;

public class charactors extends GameObject {
    private int HP = 300;
    private int MaxHP = 300;
    private String name;
    private int energy = 100;
    private int attack = 0;
    private boolean canMove=true;
    private boolean RightDirection = true;
    private String[] imgs_run = new String[24];
    private String[] imgs_attack = new String[24];
    private String[] imgs_hurt = new String[24];
    private String[] imgs_kick = new String[24];
    private String[] imgs_throw = new String[24];
    private String[] imgs_die = new String[30];
    private String[] imgs_slide = new String[12];
    private String[] imgs_skill = new String[53];

    public charactors(String textureLocation, int width, int height, Point3f centre,int damage) {
        super(textureLocation, width, height, centre,damage);
    }

    public charactors(String textureLocation, int width, int height, Point3f centre, String name,int damage) {
        super(textureLocation, width, height, centre,damage);
        this.setName(name);
        for (int i = 0; i < 24; i++) {
            imgs_run[i] = "res/" + name + "/Running/" + i + ".png";
            imgs_attack[i] = "res/" + name + "/Slashing/" + i + ".png";
            imgs_kick[i] = "res/" + name + "/Kicking/" + i + ".png";
            imgs_throw[i] = "res/" + name + "/Throwing/" + i + ".png";
            imgs_hurt[i] = "res/" + name + "/Hurt/" + i + ".png";
            imgs_die[i] = "res/" + name + "/Dying/" + i + ".png"; 
        }
        for (int i = 0; i < 12; i++) {
            imgs_slide[i] = "res/" + name + "/Sliding/" + i + ".png";
        }

        for (int i = 0; i < 53; i++) {
            imgs_skill[i] = "res/fire/png/" + i + ".png"; 
        }
    }


    public void getDamage(int num) {
        HP = HP - num;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int num) {
        this.HP = num;
    }
    public void add_HP(int num)
    {
    	HP+=num;
    	if(HP>MaxHP)
    		HP=MaxHP;
    }

    public void recover_HP(int num) {
        if (HP < 100)
            HP += num;
        else
            HP = 100;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }



	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}
	public  String[] getImgs_run() {
		return imgs_run;
	}
	public void setImgs_run(String[] imgs_run) {
		this.imgs_run = imgs_run;
	}
	public  String[] getImgs_attack() {
		return imgs_attack;
	}
	public  void setImgs_attack(String[] imgs_attack) {
		this.imgs_attack = imgs_attack;
	}
	public  String[] getImgs_hurt() {
		return imgs_hurt;
	}
	public  void setImgs_hurt(String[] imgs_hurt) {
		this.imgs_hurt = imgs_hurt;
	}
	public  String[] getImgs_throw() {
		return imgs_throw;
	}
	public  void setImgs_throw(String[] imgs_throw) {
		this.imgs_throw = imgs_throw;
	}
	public String[] getImgs_kick() {
		return imgs_kick;
	}
	public void setImgs_kick(String[] imgs_kick) {
		this.imgs_kick = imgs_kick;
	}
	public String[] getImgs_die() {
		return imgs_die;
	}
	public void setImgs_die(String[] imgs_die) {
		this.imgs_die = imgs_die;
	}
	public String[] getImgs_slide() {
		return imgs_slide;
	}
	public void setImgs_slide(String[] imgs_slide) {
		this.imgs_slide = imgs_slide;
	}
	public String[] getImgs_skill() {
		return imgs_skill;
	}
	public  void setImgs_skill(String[] imgs_skill) {
		this.imgs_skill = imgs_skill;
	}

	public boolean isRightDirection() {
		return RightDirection;
	}

	public void setRightDirection(boolean rightDirection) {
		RightDirection = rightDirection;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxHP() {
		return MaxHP;
	}

	public void setMaxHP(int maxHP) {
		MaxHP = maxHP;
	}
}
