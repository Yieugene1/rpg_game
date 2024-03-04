//Student: Jintao Yi 
//Student num:23205376
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import util.GameObject;
import util.Point3f;
import util.Vector3f; 

import java.util.Timer;
import java.util.TimerTask;
import java.util.List;

/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
   
   (MIT LICENSE ) e.g do what you want with this :-) 
 */ 
public class Model {
	
	 private charactors Player;
	 private charactors Player2;
	 private Controller controller = Controller.getInstance();
	 private  CopyOnWriteArrayList<Enemies> EnemiesList  = new CopyOnWriteArrayList<Enemies>();
	 private  CopyOnWriteArrayList<GameObject> BulletList  = new CopyOnWriteArrayList<GameObject>();
	 private  CopyOnWriteArrayList<GameObject> AttackList  = new CopyOnWriteArrayList<GameObject>();
	 private  CopyOnWriteArrayList<charactors> PlayerList  = new CopyOnWriteArrayList<charactors>();
	 private String[] maplist=new String[5];
	 private String map;
	 private boolean gameover=false;
	 private int number=7;
	 private int Money;
	 private int Score=0; 
	 private int movement =0;
	 private GameObject fire;
	 private GameObject slash;
	 private GameObject slash2;
	 private GameObject bullet;
	 private GameObject kick;
	 private Timer timer = new Timer();
	 private int attack_movement =0;
	 private int min=1000;
	 private long lastAttackTime = 0;
	 private boolean single =true;
	 private boolean skill=false;

	 
	public Model() {
		 //setup game world 
		//Player 
		Player= new charactors("res/1.png",160,200,new Point3f(0,200,0),"king",20);	
		PlayerList.add(Player);
		
		Player2= new charactors("res/1.png",160,200,new Point3f(300,200,0),"prince",20);
		
		PlayerList.add(Player2);
		for(int i=0;i<4;i++)
		{
			maplist[i]=("res/game_background_"+i+".png");
		}
		map=maplist[0];
	    
	}
	
	// This is the heart of the game , where the model takes in all the inputs ,decides the outcomes and then changes the model accordingly. 
	public void gamelogic() 
	{
		// Player Logic first
		
		if(controller.isStop()==false) {
		playerLogic(); 
		
		// Enemy Logic next
		enemyLogic();
		// Bullets move next 
		bulletLogic();
		// interactions between objects 
		gameLogic(); 
		}
	   
	}
	boolean isstop()
	{
		return Controller.isStop();
			
	}
	private void gameLogic() { 
		
		if(single)
			PlayerList.remove(Player2);
		// this is a way to increment across the array list data structure 

		
		//see if they hit anything 
		// using enhanced for-loop style as it makes it alot easier both code wise and reading wise too 
		

		for (charactors charactor:PlayerList)
		{
			
			for (GameObject temp : AttackList) 

		{
				
			if ( temp==fire&&Math.abs(temp.getCentre().getX()+10- charactor.getCentre().getX())<50 
				&& Math.abs(temp.getCentre().getY()- charactor.getCentre().getY())<50)
			{
				hurt(charactor.getImgs_hurt(),charactor);
//				System.out.println(charactor.getHP());
				charactor.getDamage(1);
			}  
			if ( temp==slash&&Math.abs(temp.getCentre().getX()- charactor.getCentre().getX()-70)<30 
					&& Math.abs(temp.getCentre().getY()- charactor.getCentre().getY()-105)<25)
			{
				hurt(charactor.getImgs_hurt(),charactor);
				charactor.getDamage(temp.getdamge());
				AttackList.remove(temp);
//				System.out.println(charactor.getHP());
			}
			if ( temp==slash2&&Math.abs(temp.getCentre().getX()- charactor.getCentre().getX()-70)<150 
					&& Math.abs(temp.getCentre().getY()- charactor.getCentre().getY()-105)<45)
			{
				hurt(charactor.getImgs_hurt(),charactor);
				charactor.getDamage(temp.getdamge());
				AttackList.remove(temp);
//				System.out.println(charactor.getHP());
			}
			
			if ( temp==slash2&&Math.abs(temp.getCentre().getX()- charactor.getCentre().getX()-70)<100 
					&& Math.abs(temp.getCentre().getY()- charactor.getCentre().getY()-105)<45)
			{
				hurt(charactor.getImgs_hurt(),charactor);
				charactor.getDamage(temp.getdamge());
				AttackList.remove(temp);
//				System.out.println(charactor.getHP());
			}
			
			if(temp==kick&&Math.abs(temp.getCentre().getX()- charactor.getCentre().getX()-70)<30 
					&& Math.abs(temp.getCentre().getY()- charactor.getCentre().getY()-105)<25)
			{
				
				hurt(charactor.getImgs_hurt(),charactor);
				charactor.getCentre().ApplyVector( new Vector3f(70,0,0));
				charactor.getDamage(temp.getdamge());
				AttackList.remove(temp);
//				System.out.println(charactor.getHP());
			}
			
		}
			if(charactor.getHP()<0)
			{
				charactor.setCanMove(false);
				PlayerList.remove(charactor);
				if(PlayerList.isEmpty())
				{
					setGameover(true);
				}
			}
		}
		for (GameObject temp : AttackList)
		{
			for (Enemies enemy:EnemiesList)
			{
				if ( temp==fire&&Math.abs(temp.getCentre().getX()+10- enemy.getCentre().getX())<50 
						&& Math.abs(temp.getCentre().getY()- enemy.getCentre().getY())<50)
					{
						hurt(enemy.getImgs_hurt(),enemy);
//						System.out.println(enemy.getHP())
						enemy.getDamage(1);
					}  
					if ( temp==slash&&Math.abs(temp.getCentre().getX()- enemy.getCentre().getX()-70)<40 
							&& Math.abs(temp.getCentre().getY()- enemy.getCentre().getY()-75)<45)
					{
						hurt(enemy.getImgs_hurt(),enemy);
						enemy.getDamage(temp.getdamge());
						AttackList.remove(temp);
//						System.out.println(enemy.getHP());
					}
					if(temp==kick&&Math.abs(temp.getCentre().getX()- enemy.getCentre().getX()-70)<30 
							&& Math.abs(temp.getCentre().getY()- enemy.getCentre().getY()-75)<25)
					{
						
						hurt(enemy.getImgs_hurt(),enemy);
						enemy.getCentre().ApplyVector( new Vector3f(70,0,0));
						enemy.getDamage(temp.getdamge());
						AttackList.remove(temp);
//						System.out.println(enemy.getHP());
					}

			}
		}

		
	}
	private void enemyLogic() {

		
//		
		for (Enemies enemy : EnemiesList) {
		    if(enemy.isCanMove()) {
		        charactors nearestCharacter = findNearestCharacter(enemy, PlayerList);
		        long currentTime = System.currentTimeMillis();
		        if (nearestCharacter != null&&enemy.getName()=="a0") { 
		        	
		            double distanceX = Math.abs(enemy.getCentre().getX() - nearestCharacter.getCentre().getX());
		            double distanceY = Math.abs(enemy.getCentre().getY() - 50 - nearestCharacter.getCentre().getY());
		            double distanceY2 = enemy.getCentre().getY() - 50 - nearestCharacter.getCentre().getY();
		            double distanceX2 = enemy.getCentre().getX() - (nearestCharacter.getCentre().getX()+10);
		        	

		            
		            if (distanceX > 28) { 
		                if (distanceX2 > 110) {
		                    enemy.setRightDirection(true);
		                    run(enemy.getImgs_run(), enemy,1);
		                } else if (distanceX2 < -60) {
		                    enemy.setRightDirection(false);
		                    run(enemy.getImgs_run(), enemy,1);
		                }
		            }
		            
		            if (distanceY2 > 4) {
		            	enemy.setHigh(false);
		                run2(enemy.getImgs_run(), enemy,1);
		            } else if (distanceY2 < -4) {
		            	enemy.setHigh(true);
		                run2(enemy.getImgs_run(), enemy,1);
		            }
		            
		        	
		        	
		        		
		    	    if (currentTime - enemy.getLastAttackTime() > enemy.getATTACK_COOLDOWN()) {
		    	    enemy.setLastAttackTime(currentTime);
		            double distanceToAttack = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
		            if(enemy.isRightDirection()==true&&distanceToAttack <= 100)
		            {
		           
		    			slash2=new GameObject("res/blankSprite.png",0,10,new Point3f(enemy.getCentre().getX(),enemy.getCentre().getY()+80,0.0f),enemy.getdamge());
		    			AttackList.add(slash2);
		            	attack(enemy.getImgs_attack(), enemy);
		            }
		            if (distanceToAttack <= 70) {
		                
		                slash2=new GameObject("res/blankSprite.png",0,10,new Point3f(enemy.getCentre().getX()+80,enemy.getCentre().getY()+80,0.0f),enemy.getdamge());
		                AttackList.add(slash2);
		                attack(enemy.getImgs_attack(), enemy);
		            }
		        }

		    }   
		        if(nearestCharacter != null&&enemy.getName()!="a0")
	    	    {
		        	
		            double distanceX = Math.abs(enemy.getCentre().getX() - nearestCharacter.getCentre().getX());
		            double distanceY = Math.abs(enemy.getCentre().getY() - 30 - nearestCharacter.getCentre().getY());
		            double distanceY2 = enemy.getCentre().getY() - 30 - nearestCharacter.getCentre().getY();
		            double distanceX2 = enemy.getCentre().getX() - (nearestCharacter.getCentre().getX()+10);
		        	

		       
		            if (distanceX > 28) { 
		                if (distanceX2 > 110) {
		                    enemy.setRightDirection(true);
		                    run(enemy.getImgs_run(), enemy,1);
		                } else if (distanceX2 < -60) {
		                    enemy.setRightDirection(false);
		                    run(enemy.getImgs_run(), enemy,1);
		                }
		            }
		            
		            if (distanceY2 > 4) {
		            	enemy.setHigh(false);
		                run2(enemy.getImgs_run(), enemy,1);
		            } else if (distanceY2 < -4) {
		            	enemy.setHigh(true);
		                run2(enemy.getImgs_run(), enemy,1);
		            }
		            
		        	
		        	
		        		
		    	    if (currentTime - enemy.getLastAttackTime() > enemy.getATTACK_COOLDOWN()) {
		    	    enemy.setLastAttackTime(currentTime);
		            double distanceToAttack = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
		            if(enemy.isRightDirection()==true&&distanceToAttack <= 200)
		            {
		           
		    			slash2=new GameObject("res/blankSprite.png",0,10,new Point3f(enemy.getCentre().getX(),enemy.getCentre().getY()+80,0.0f),enemy.getdamge());
		    			AttackList.add(slash2);
		            	attack(enemy.getImgs_attack(), enemy);
		            }
		            if (distanceToAttack <= 70) {
		                
		                slash2=new GameObject("res/blankSprite.png",0,10,new Point3f(enemy.getCentre().getX()+80,enemy.getCentre().getY()+80,0.0f),enemy.getdamge());
		                AttackList.add(slash2);
		                attack(enemy.getImgs_attack(), enemy);
		            }
		        }
	    	    }
	    	  
		        if(nearestCharacter == null&&currentTime - enemy.getLastAttackTime() > enemy.getATTACK_COOLDOWN())
		        {
		        	 enemy.setLastAttackTime(currentTime);
		        
		        	 int randomDirection = (int) (Math.random() * 4); 
		        	    switch (randomDirection) {
		        	        case 0:
		        	            
		        	        	enemy.setHigh(false);
				                run2(enemy.getImgs_run(), enemy,8);
		        	            break;
		        	        case 1:
		        	            
		        	        	enemy.setHigh(true);
				                run2(enemy.getImgs_run(), enemy,8);
		        	            break;
		        	        case 2:
		        	            
		        	        	enemy.setRightDirection(false);
			                    run(enemy.getImgs_run(), enemy,8);
		        	            break;
		        	        case 3:
		        	          
		        	        	enemy.setRightDirection(true);
			                    run(enemy.getImgs_run(), enemy,8);
		        	            break;
		        }
		}
		        
		    }
		    
		} 

		while (EnemiesList.size()<2&&number>0)
		{
//			{
			if(Score<=15)
				EnemiesList.add(new Enemies("res/33.png",100,10,120,150,new Point3f(((float)Math.random()*400)+800,200,0),"a0",(int)Math.random()*4000+1000)); 
			if(Score>=13)
			EnemiesList.add(new Enemies("res/33.png",300,40,200,150,new Point3f(((float)Math.random()*400)+800,200,0),"a3",(int)Math.random()*4000+1000)); 
			if(Score>=18)
			EnemiesList.add(new Enemies("res/33.png",200,20,200,150,new Point3f(((float)Math.random()*400)+800,200,0),"a2",(int)Math.random()*4000+1000)); 
			if(Score>=20)
			EnemiesList.add(new Enemies("res/33.png",250,50,200,150,new Point3f(((float)Math.random()*400)+800,200,0),"a1",(int)Math.random()*4000+1000)); 
			
//			}
		}
		
	}
	private charactors findNearestCharacter(Enemies enemy, CopyOnWriteArrayList<charactors> playerList) {
		
	    int minDistance = Integer.MAX_VALUE;
	    charactors nearestCharacter = null;
	    
	    for (charactors character : playerList) {
	        int x_distance = (int) Math.abs(enemy.getCentre().getX() - character.getCentre().getX());
	        int y_distance = (int) Math.abs(enemy.getCentre().getX() - character.getCentre().getX());
	        int sum =x_distance*y_distance;
	        if (sum < minDistance && sum < 400*400) {
	            minDistance = sum;
	            nearestCharacter = character;
	        }
	    }
	    
	    return nearestCharacter;
	}

	private void bulletLogic() {
		// TODO Auto-generated method stub
		// move bullets 
		
	  
		for (GameObject temp : BulletList) 
		{
		    //check to move them
			 if(temp.isRight())
			  temp.getCentre().ApplyVector(new Vector3f(2,0,0));
			 else
			  temp.getCentre().ApplyVector(new Vector3f(-2,0,0));
			
			//see if they get to the top of the screen ( remember 0 is the top 
			if (temp.getCentre().getX()<50||temp.getCentre().getX()>800)
			{
			 	BulletList.remove(temp);
			} 
		} 
		for (GameObject temp : BulletList) 
		{
		
			for (Enemies enemy : EnemiesList) 
			{
				if ( temp==bullet&&Math.abs(temp.getCentre().getX()- enemy.getCentre().getX())<30 
						&& Math.abs(temp.getCentre().getY()- enemy.getCentre().getY())<100)
				{
					hurt(enemy.getImgs_hurt(),enemy);
					enemy.getDamage(temp.getdamge());
					BulletList.remove(temp);
				}
			}
		}
		
		
	}

	private void playerLogic() {	
		
		// smoother animation is possible if we make a target position  // done but may try to change things for students  
		  
		if(Player.isCanMove()==true) {
		if(Controller.getInstance().isKeyJPressed())
		{	
			attack(Player.getImgs_attack(),Player);
			if(Player.isRightDirection()==true)
			slash=new GameObject("res/blankSprite.png",0,0,new Point3f(Player.getCentre().getX()+140,Player.getCentre().getY()+110,0.0f),Player.getdamge());
			else
			slash=new GameObject("res/blankSprite.png",0,0,new Point3f(Player.getCentre().getX()+10,Player.getCentre().getY()+110,0.0f),Player.getdamge());
			AttackList.add(slash);
			
		}
		if(Controller.getInstance().isKeyKPressed())
		{	
			if(Player.isRightDirection()==true)
			kick=new GameObject("res/blankSprite.png",0,0,new Point3f(Player.getCentre().getX()+110,Player.getCentre().getY()+110,0.0f),Player.getdamge());
			else
			kick=new GameObject("res/blankSprite.png",0,0,new Point3f(Player.getCentre().getX()+30,Player.getCentre().getY()+110,0.0f),Player.getdamge());
			AttackList.add(kick);
			attack(Player.getImgs_kick(),Player);
		}
		if(Controller.getInstance().isKeyUPressed())
		{	
			attack(Player.getImgs_throw(),Player);	
			if(skill)
			Createfire();
			
		}
		if(Controller.getInstance().isKeyIPressed())
		{	
			attack(Player.getImgs_throw(),Player);	
			if(skill)
			CreateBullet(Player);
			
		}
		if(Controller.getInstance().isKeyIPressed2())
		{	
			
			attack(Player2.getImgs_throw(),Player2);	
			if(skill)
			CreateBullet(Player2);
			
		}
		
		if(Controller.getInstance().isKeyLPressed())
		{	
			slide(Player.getImgs_slide(),Player);	
			
		}
		if(Controller.getInstance().isKeyMPressed()&&Money>=5)
		{	
			Money-=5;
			skill=true;
			Player.add_damage(10);
			
		}
		if(Controller.getInstance().isKeyNPressed()&&Money>=3)
		{	
			Money-=3;
			Player.add_HP(100);
			
		}
		
      if(Controller.getInstance().isKeyAPressed()){
    	  Player.setRightDirection(false);
	Player.getCentre().ApplyVector( new Vector3f(-2,0,0));
}
		
		if(Controller.getInstance().isKeyDPressed())
		{
			Player.setRightDirection(true);
			Player.getCentre().ApplyVector( new Vector3f(2,0,0));
		}
			
		if(Controller.getInstance().isKeyWPressed())
		{
		
			Player.getCentre().ApplyVector( new Vector3f(0,2,0));
			
		}
		
		if(Controller.getInstance().isKeySPressed()){Player.getCentre().ApplyVector( new Vector3f(0,-2,0));}
		
		if(Controller.getInstance().isKeySpacePressed())
		{
			number=7;
			if(Score>=7)
				setmap(Score/7);
		} 
		if(Controller.getInstance().isKeyAPressed()||Controller.getInstance().isKeyWPressed()||Controller.getInstance().isKeyDPressed()||Controller.getInstance().isKeySPressed())
		{
	
			run(Player.getImgs_run(),Player);

		}
		else {
			if(Player.isRightDirection()==true)
			Player.setimage(Player.getImgs_run()[0]);
			if(Player.isRightDirection()==false)
			Player.setimage(Player.getImgs_run()[12]);
		}
		}
		if(single==false) {
		if(Player2.isCanMove()==true)
		{
		if(Controller.getInstance().isKeySPressed2())
		{
			Player2.getCentre().ApplyVector( new Vector3f(0,-2,0));
		}
		if(Controller.getInstance().isKeyWPressed2())
		{
			Player2.getCentre().ApplyVector( new Vector3f(0,2,0));
		}
		if(Controller.getInstance().isKeyDPressed2())
		{
			Player2.setRightDirection(true);
			Player2.getCentre().ApplyVector( new Vector3f(2,0,0));
		}
		if(Controller.getInstance().isKeyAPressed2())
		{
			Player2.setRightDirection(false);
			Player2.getCentre().ApplyVector( new Vector3f(-2,0,0));
		}

		if(Controller.getInstance().isKeyJPressed2())
		{	
			if(Player2.isRightDirection()==true)
			slash=new GameObject("res/blankSprite.png",0,0,new Point3f(Player2.getCentre().getX()+140,Player2.getCentre().getY()+110,0.0f),Player2.getdamge());
			else
			slash=new GameObject("res/blankSprite.png",0,0,new Point3f(Player2.getCentre().getX()+10,Player2.getCentre().getY()+110,0.0f),Player2.getdamge());
			AttackList.add(slash);
			attack(Player2.getImgs_attack(),Player2);
			
		}
		if(Controller.getInstance().isKeyKPressed2())
		{	
			attack(Player2.getImgs_kick(),Player2);
		}
		if(Controller.getInstance().isKeyUPressed2())
		{	
			attack(Player2.getImgs_throw(),Player2);
			if(skill!=false)
			Createfire();
		
		}
		
		if(Controller.getInstance().isKeyLPressed2())
		{	
			slide(Player2.getImgs_slide(),Player2);	
			
		}
		if(Controller.getInstance().isKeyAPressed2()||Controller.getInstance().isKeyWPressed2()||Controller.getInstance().isKeyDPressed2()||Controller.getInstance().isKeySPressed2())
		{
			run(Player2.getImgs_run(),Player2);

		}
		else {
			if(Player2.isRightDirection()==true)
			Player2.setimage(Player2.getImgs_run()[0]);
			if(Player2.isRightDirection()==false)
			Player2.setimage(Player2.getImgs_run()[12]);
	}}
		}
	}

	private void CreateBullet(charactors player) {
		
		bullet=new GameObject("res/Bullet.png",64,32,new Point3f(Player.getCentre().getX()+50,Player.getCentre().getY()+80,0.0f),10);
		if (player.isRightDirection()==false)
			bullet=new GameObject("res/Bullet2.png",64,32,new Point3f(Player.getCentre().getX()+50,Player.getCentre().getY()+80,0.0f),10);
		bullet.setRight(player.isRightDirection());
		BulletList.add(bullet);	
	}
	private void Createfire() {
		if(Player.isRightDirection()==true) {
		fire=new GameObject("",200,150 ,new Point3f(Player.getCentre().getX()+200,Player.getCentre().getY()+30,0.0f),1);
		AttackList.add(fire);
		skill(Player.getImgs_skill(),fire);
	
		}
		if(Player.isRightDirection()==false)
		{fire=new GameObject("",200,150,new Point3f(Player.getCentre().getX()-270,Player.getCentre().getY()+30,0.0f),1);
		AttackList.add(fire);
		skill(Player.getImgs_skill(),fire);
		
		
		}
	}
	public charactors getPlayer() {
		return Player;
	}
	public charactors getPlayer2() {
		return Player2;
	}
	public CopyOnWriteArrayList<Enemies> getEnemies() {
		return EnemiesList;
	}
	
	public CopyOnWriteArrayList<GameObject> getBullets() {
		return BulletList;
	}

	public int getScore() { 
		return Score;
	}
	private void attack(String[] imgs_attack,charactors Player) {
		Player.setCanMove(false);
        Timer timer = new Timer();
        int delay = 0; 
        int interval = 50; 
		if(Player.isRightDirection()==true)
			attack_movement=0;
		if(Player.isRightDirection()==false)
			attack_movement=12;
        TimerTask task = new TimerTask() {
            int currentFrameIndex = attack_movement;

            @Override
            public void run() {
                Player.setimage(imgs_attack[currentFrameIndex]);
                currentFrameIndex++;
                if (currentFrameIndex == 12||currentFrameIndex==23) {
                    timer.cancel(); 
                    Player.setCanMove(true);
                    AttackList.remove(slash);
                    AttackList.remove(kick);
                }
            }
        };

        timer.scheduleAtFixedRate(task, delay, interval); 
    }
	
	private void slide(String[] imgs_attack,charactors Player) {
        Player.setCanMove(false);
        Timer timer = new Timer();
        int delay = 0; 
        int interval = 50; 
        if (Player.isRightDirection()) {
            attack_movement = 0;
        } else {
            attack_movement = 6;
        }
        TimerTask task = new TimerTask() {
            int currentFrameIndex = attack_movement;

            @Override
            public void run() {
                Player.setimage(imgs_attack[currentFrameIndex]);
                currentFrameIndex++;
                if ((Controller.getInstance().isKeyLPressed()||Controller.getInstance().isKeyLPressed2())&&Player.isRightDirection()) {
                    Player.getCentre().ApplyVector(new Vector3f(8, 0, 0));
                }
                if ((Controller.getInstance().isKeyLPressed()||Controller.getInstance().isKeyLPressed2())&&Player.isRightDirection()==false)
                {
                	Player.getCentre().ApplyVector(new Vector3f(-8, 0, 0));
                }
                if (currentFrameIndex == 6 || currentFrameIndex == 11) {
                    timer.cancel(); 
                    Player.setCanMove(true);
                }
            }
        };

        timer.scheduleAtFixedRate(task, delay, interval); 
    }
	private void skill(String[] imgs_skill,GameObject fire) {
		for (GameObject temp : AttackList) 
		if(temp==fire)
		{
        Timer timer = new Timer();
        int delay = 0;
        int interval = 40; 
        TimerTask task = new TimerTask() {
            int currentFrameIndex = attack_movement;
            @Override
            public void run() {
            	temp.setimage(imgs_skill[currentFrameIndex]);
                currentFrameIndex++;
                if (currentFrameIndex==imgs_skill.length-1) {
                    timer.cancel(); 
                    AttackList.remove(fire);
                }
            }
        
        };
		
        timer.scheduleAtFixedRate(task, delay, interval); 
		}
		
    }
	private void die(String[] imgs_die,charactors Player) {
		Player.setCanMove(false);
        Timer timer = new Timer();
        int delay = 0; 
        int interval = 50; 
		if(Player.isRightDirection()==true)
			attack_movement=0;
		if(Player.isRightDirection()==false)
			attack_movement=12;
        TimerTask task = new TimerTask() {
            int currentFrameIndex = attack_movement;

            @Override
            public void run() {
            	
                Player.setimage(imgs_die[currentFrameIndex]);
                currentFrameIndex++;
                if (currentFrameIndex == 12||currentFrameIndex==23) {
                    timer.cancel(); 
//                    Player.setCanMove(true);
                }
            }
        };

        timer.scheduleAtFixedRate(task, delay, interval); 
    }
	private void hurt(String[] imgs_hurt,charactors charactor) {
		charactor.setCanMove(false);
        Timer timer = new Timer();
        int delay = 0; 
        int interval = 50; 
		if(charactor.isRightDirection()==true)
			attack_movement=0;
		if(charactor.isRightDirection()==false)
			attack_movement=12;
        TimerTask task = new TimerTask() {
            int currentFrameIndex = attack_movement;

            @Override
            public void run() {
            	if(charactor.getHP()>0)
            	charactor.setimage(charactor.getImgs_hurt()[currentFrameIndex]);
            	if(charactor.getHP()<=0)
            		charactor.setimage(charactor.getImgs_die()[currentFrameIndex]);
                currentFrameIndex++;
                if (currentFrameIndex == 12||currentFrameIndex==23) {
                    timer.cancel(); 
                    if(charactor.getHP()>0)
                    charactor.setCanMove(true);
                }
            }
        };

        timer.scheduleAtFixedRate(task, delay, interval); 
    }
	
	private void run(String[] imgs_run,charactors Player)
	{
		if(Player.isRightDirection()==true&&movement>12)
			movement=0;
		if(Player.isRightDirection()==false&&movement<12)
			movement=12;
		Player.setimage(imgs_run[movement]);
		movement++;
		if(movement==11)
		{
			movement=0;
		}
		if(movement==23)
		{
			movement=12;
		}

	}

	public GameObject getFire() {
		return fire;
	}

	public void setFire(GameObject fire) {
		this.fire = fire;
	}

	public CopyOnWriteArrayList<GameObject> getAttackList() {
		return AttackList;
	}

	public void setAttackList(CopyOnWriteArrayList<GameObject> attackList) {
		AttackList = attackList;
	}
	private void run(String[] imgs_attack,Enemies Player,int a) {
        Timer timer = new Timer();
        int delay = 0;
        int interval = 50; 
        if (Player.isRightDirection()) {
            attack_movement = 0;
        } else {
            attack_movement = 7;
        }
        TimerTask task = new TimerTask() {
            int currentFrameIndex = attack_movement;

            @Override
            public void run() {
                Player.setimage(imgs_attack[currentFrameIndex]);
                currentFrameIndex++;
                if(a!=1)
                {
                    if(Player.isRightDirection()==true)
                        Player.getCentre().ApplyVector(new Vector3f(-a, 0, 0));
                    if(Player.isRightDirection()==false)
                        Player.getCentre().ApplyVector(new Vector3f(a, 0, 0));
                }
                if (currentFrameIndex == 7 || currentFrameIndex == 13) {
                    timer.cancel(); 
                    if(Player.isRightDirection()==true)
                        Player.getCentre().ApplyVector(new Vector3f(-a, 0, 0));
                    if(Player.isRightDirection()==false)
                        Player.getCentre().ApplyVector(new Vector3f(a, 0, 0));
                }
            }
        };

        timer.scheduleAtFixedRate(task, delay, interval);
    }
	private void run2(String[] imgs_attack,Enemies Player,int a) {
        Timer timer = new Timer();
        int delay = 0; 
        int interval = 50; 
        if (Player.isRightDirection()) {
            attack_movement = 0;
        } else {
            attack_movement = 7;
        }
        TimerTask task = new TimerTask() {
            int currentFrameIndex = attack_movement; 

            @Override
            public void run() {
                Player.setimage(imgs_attack[currentFrameIndex]);
                currentFrameIndex++;
                if(a!=1)
                {
                    if(Player.isHigh()==true)
                        Player.getCentre().ApplyVector(new Vector3f(0, -a, 0));
                    if(Player.isHigh()==false)
                        Player.getCentre().ApplyVector(new Vector3f(0, a, 0));
                }
                if (currentFrameIndex == 7 || currentFrameIndex == 13) {
                    timer.cancel(); 
                    if(Player.isHigh()==true)
                        Player.getCentre().ApplyVector(new Vector3f(0, -a, 0));
                    if(Player.isHigh()==false)
                        Player.getCentre().ApplyVector(new Vector3f(0, a, 0));
                }
            }
        };

        timer.scheduleAtFixedRate(task, delay, interval); 
    }
	private void hurt(String[] imgs_hurt,Enemies charactor) {
		charactor.setCanMove(false);
        Timer timer = new Timer();
        int delay = 0; 
        int interval = 100; 
		if(charactor.isRightDirection()==true)
			attack_movement=0;
		if(charactor.isRightDirection()==false)
			attack_movement=7;
        TimerTask task = new TimerTask() {
            int currentFrameIndex = attack_movement;

            @Override
            public void run() {
            	if(charactor.getHP()>0)
            	charactor.setimage(charactor.getImgs_hurt()[currentFrameIndex]);
            	if(charactor.getHP()<=0)
            		charactor.setimage(charactor.getImgs_die()[currentFrameIndex]);
                currentFrameIndex++;
                if (currentFrameIndex == 7||currentFrameIndex==13) {
                    timer.cancel();
                    if(charactor.getHP()>0)
                    charactor.setCanMove(true);
					if(charactor.getHP()>-399&&charactor.getHP()<=0)
					{
						EnemiesList.remove(charactor);
						Money+=2;
						charactor.setHP(-1000);
						number--;
						System.out.println(++Score);
					}
                }
            }
        };

        timer.scheduleAtFixedRate(task, delay, interval); 
    }
	private void attack(String[] imgs_attack,Enemies Player) {

		Player.setCanMove(false);
        Timer timer = new Timer();
        int delay = 0;
        int interval = 50; 
		if(Player.isRightDirection()==true)
			attack_movement=0;
		if(Player.isRightDirection()==false)
			attack_movement=7;
        TimerTask task = new TimerTask() {
            int currentFrameIndex = attack_movement;

            @Override
            public void run() {
                Player.setimage(imgs_attack[currentFrameIndex]);
                currentFrameIndex++;
                if (currentFrameIndex == 7||currentFrameIndex==13) {
                	
                    timer.cancel(); 
                    Player.setCanMove(true);
                   
                }
                AttackList.remove(slash2);
            }
        };

        timer.scheduleAtFixedRate(task, delay, interval); 
    }

	public boolean isSingle() {
		return single;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}
    public void setmap(int a)
    {
    	map=maplist[a];
    }
    public String getmap()
    {
    	return map;
    }
    public int getMoney()
    {
    	return Money;
    }

	public boolean isSkill() {
		return skill;
	}

	public void setSkill(boolean skill) {
		this.skill = skill;
	}

	public boolean isGameover() {
		return gameover;
	}

	public void setGameover(boolean gameover) {
		this.gameover = gameover;
	}

}


/* MODEL OF your GAME world 
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWNNNXXXKKK000000000000KKKXXXNNNWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWNXXK0OOkkxddddooooooolllllllloooooooddddxkkOO0KXXNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWNXK0OkxddooolllllllllllllllllllllllllllllllllllllllloooddxkO0KXNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXK0OkdooollllllllooddddxxxkkkOOOOOOOOOOOOOOOkkxxdddooolllllllllllooddxO0KXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNK0kxdoollllllloddxkO0KKXNNNNWWWWWWMMMMMMMMMMMMMWWWWNNNXXK00Okkxdoollllllllloodxk0KNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXKOxdooolllllodxkO0KXNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWNXK0OkxdolllllolloodxOKXWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOxdoolllllodxO0KNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXKOkdolllllllloodxOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0kdolllllooxk0KNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNK0kdolllllllllodk0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0xdolllllodk0XNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWMMMMMMMMMMMWN0kdolllllllllodx0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0xoollllodxOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWMMMMMMMMMMWNXKOkkkk0WMMMMMMMMMMMMWNKkdolllloololodx0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWN0kdolllllox0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXK0kxk0KNWWWWNX0OkdoolllooONMMMMMMMMMMMMMMMWXOxolllllllollodk0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOdollllllllokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWN0xooollloodkOOkdoollllllllloxXWMMMMMMMMMMMMMMMWXkolllllllllllllodOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWN0koolllllllllllokNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxolllllllllllllllllllllllllllox0XWWMMMMMMMMMWNKOdoloooollllllllllllok0NWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0xoolllllllllllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxllolllllllllllllllllloollllllolodxO0KXNNNXK0kdoooxO0K0Odolllollllllllox0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOdolllllllllllllllllokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkolllllllllloolllllllllllllllllllolllloddddoolloxOKNWMMMWNKOxdolollllllllodOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMWXOdolllolllllllllllllloxKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxlllolllllloxkxolllllllllllllllllolllllllllllllxKWMWWWNNXXXKKOxoollllllllllodOXWMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMWXOdollllllllllllllllllllokNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOollllllllllxKNKOxooollolllllllllllllllllllolod0XX0OkxdddoooodoollollllllllllodOXWMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMN0xollllllllllllllllllllllld0NMMMMMMMMMMMMMMMMMMMMMMMWWNKKNMMMMMMMMMMMW0dlllllllllokXWMWNKkoloolllllllllllllllllllolokkxoolllllllllllllollllllllllllllox0NMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMWKxolllllllllllllllllllllllllloONMMMMMMMMMMMMMMMMMMMWNKOxdookNMMMMMMMMMWXkollllllodx0NWMMWWXkolooollllllllllllllllllllooollllllllllllllolllllllllllloooolloxKWMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMWXOdllllllllllllllooollllllllollld0WMMMMMMMMMMMMMMMMWXOxollllloOWMMMMMMMWNkollloodxk0KKXXK0OkdoollllllllllllllllllllllllllllllollllllllloollllllollllllllllllldOXWMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMN0xolllllllllllolllllllllllloodddddONMMMMMMMMMMMMMMMNOdolllllllokNMMMMMMWNkolllloddddddoooolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllox0NMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMWXkolllllllllllllllllllodxxkkO0KXNNXXXWMMMMMMMMMMMMMMNkolllllllllod0NMMMMMNOollllloollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllolllllllllllllokXWMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMWKxollllllllllllllllllox0NWWWWWMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllllookKNWWNOolollloolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloxKWMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMN0dlllllllllllllllllllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkoloolllollllolloxO0Odllllllllllllllllllllllllllllllllllllllllllllollllllllllllllllllllllllllllllllllllllllllllllld0NWMMMMMMMMMMMMM
MMMMMMMMMMMMMXkolllllllllllllllllolllxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXOO0KKOdollllllllllooolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloONWMMMMMMMMMMMM
MMMMMMMMMMMWXkollllllllllllllllllllllxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWMMMMWNKOxoollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllokXWMMMMMMMMMMM
MMMMMMMMMMWKxollllllllllllllllllllllokNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWKxollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloxKWMMMMMMMMMM
MMMMMMMMMWKxollllllllllllodxkkkkkkkO0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMNKOkO0KK0OkdolllllloolllllllllllloooollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloxKWMMMMMMMMM
MMMMMMMMWKxllllllllllolodOXWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxolloooollllllllllllllllloollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxKWMMMMMMMM
MMMMMMMWKxlllllllllollokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxololllllllooolloollllloolloooolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxKWMMMMMMM
MMMMMMWXxllllllllooodkKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMKdloollllllllllololodxxddddk0KK0kxxxdollolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxXWMMMMMM
MMMMMMXkolllllodk0KXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMKdllollllllllllllodOXWWNXXNWMMMMWWWNX0xolollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllokNMMMMMM
MMMMMNOollllodONWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dooollllllllllllodOXNWWWWWWMMMMMMMMMWXOddxxddolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloONMMMMM
MMMMW0dllllodKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKKK0kdlllllllllllloodxxxxkkOOKNWMMMMMMWNNNNNXKOkdooooollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllld0WMMMM
MMMWKxllllloOWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkolllllollllllllllllllllodOKXWMMMMMMMMMMMMWNXKK0OOkkkxdooolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxKWMMM
MMMNkollllokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWXOdlllllolllllllllllloloolllooxKWMMMMMMMMMMMMMMMMMMMMWWWNXKOxoollllllllllllllllllllllllllllllllllllllllllllllllllllllllllolokNMMM
MMW0ollllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOOkxdollllllllllllllllllllllllllllox0NWMMMMWWNNXXKKXNWMMMMMMMMMWNKOxolllolllllllllllllllllllllllllllllllllllllllllllllllllllllllo0WMM
MMXxllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXkolllllllllllllllllllllllllllllllllllooxO000OkxdddoooodkKWMMMMMMMMMMWXxllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxXWM
MWOollllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXkollllllllllllllllllllllllllllllllllllllllllllllllllllllld0WMMMMMMMMMWKdlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloOWM
MXxllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXkollllllllllllllllllllllllllllllllllooollllllllllllllllllold0WMMMMMMWN0dolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxXM
W0ollllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNKkdolllllllllllllllllllllllllllllllllllllllllllllllllolllllllllokKXNWWNKkollllllllloxdollllllllolllllllllllllllllllllllllolllllllllllllolo0W
NkllllloxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllodxkkdoolollllllllxKOolllllllllllllllllllllllollooollllllloolllllloolllllkN
KxllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0doolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllkX0dlllllllllllllllllllloollloOKKOkxdddoollllllllllllllxK
Oolllllo0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxXXkollllooolllllllllllllllloONMMMWNNNXX0xolllllllllolloO
kolllllo0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxXWXkollollllllllllllllllllodKMMMMMMMMMMWKxollllolollolok
kllllllo0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloolllllllllxXWWXkolllllllllllllllolllloONMMMMMMMMMMMW0dllllllllllllk
xollolld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxllllolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloollllllolloONMMN0xoolllllllolllllllloxXWMMMMMMMMMMMMXxollllllloollx
dollllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloollld0WMMWWXOdollollollllllloxXWMMMMMMMMMMMMMNOollllllokkold
olllllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNxlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllldONMMMMWXxollllolllllox0NWMMMMMMMMMMMMMMNOollllllxXOolo
llllllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXxllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloONMMMMMXxddxxxxkkO0XWMMMMMMMMMMMMMMMMMNOolllllxKW0olo
llllllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllldONWMMMWNXNNWWWMMMMMMMMMMMMMMMMMMMMMMMW0dllollOWW0oll
llllllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloxO0KXXXXKKKXNWMMMMMMMMMMMMMMMMMMMMMMMNOdolllkNWOolo
ollllllo0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllooooddooloodkKWMMMMMMMMMMMMMMMMMMMMMMWXOolldKNOooo
dollllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloollllo0WMMMMMMMMMMMMMMMMMMMMMMMMXkold0Nkold
xollllloxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllollokNMMMMMMMMMMMMMMMMMMMMMMMMMWOookXXxolx
xolllllloONWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMN00XW0dlox
kollllllloxOKXXNNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOxollllllllllllllllllllllllllllllolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllolllllolo0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWOollk
OolllllllllloodddkKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOkkxddooooollllllllllooodxxdollolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkoloO
KdllllllllllllllllxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWNXXXK0OOkkkkkkkkOKXXXNNX0xolllllllllllllllllllllllllllllllllllllllllllllllllllllllloollllllllloox0NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMKdlldK
NkllllollloolllllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWMMMMMMMMMWNOdlllllllllllllllllllllllllllllllllllllllllllllllllllllllollllllllodOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWOolokN
WOolllllllllllolllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllod0NWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXxolo0W
WXxllllllllllllllllox0NWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxollllllllllllllllllllllllllllllllllllllllllllllllllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOollxXM
MWOollllllllllllllooloxKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdllllllllllllllllllllllllllllllllllllllllllllllllllllloolld0NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdlloOWM
MWXxllolllllllllllllllldOXWWNNK00KXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllllllllllllllllllllllllllllllllllllllllllllllllllllllod0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxollxXWM
MMWOollllllllloollllllolodxkxdollodk0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOollllllllllllllllllllllllllllllllllllllllllllllllllodxOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWN0dlllo0WMM
MMMXxllolllllllllllllllllllllllllllloox0NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMN0dooollllllllllllllllllllllllllllllllllllllllllllodOXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKOkxxolllokNMMM
MMMW0dlllllllllllllllllllolllllllollollokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOdoolllllllllllllllllllllllllllllllllllllllllllxKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNOoollllllldKWMMM
MMMMNOollllllllllllllllllllllllllllllllloOWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXKOdolllllllllllllllllllllllllllllllllllllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOolllllllloOWMMMM
MMMMMXkollllllllllllllllllllllllllllllllokNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllllllllllllllllllllllllllllllllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllolllllokNMMMMM
MMMMMWXxlllllllllllllllllllllllllllllllloxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0ollllllllllllllllllllllllllllllllllllllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWOollllllllxXWMMMMM
MMMMMMWKdlllllllllllllllllllllllllllllllokNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxolllllllllllllllllllllllllllllllllllllloONWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOolllllllxKWMMMMMM
MMMMMMMW0dlllllllllllllllllllllllllllllloOWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllloollllllllllllllllllllllllllllllllloxkOKKXXKKXNMMMMMMMMMMMMMMMMMMMMMMMMNOolllllldKWMMMMMMM
MMMMMMMMW0dllllllllllllllllllllllllllllldKMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdlllllllllllllllllllllllllllllllllllllllllllloooood0WMMMMMMMMMMMMMMMMMMMMMMMNOollolldKWMMMMMMMM
MMMMMMMMMW0dlllllllllllllllllllllllllllokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllllllllllllllllllllllllllllllolllllllllllllllllld0WMMMMMMMMMMMMMMMMMMMMMMWKxllllldKWMMMMMMMMM
MMMMMMMMMMW0dlllllllllllllllllllllllllloxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkolllllllllllllllllllllllllllllllllllllllllllllllllxXMMMMMMMMMMMMMMMMMMMMMWXOdolllldKWMMMMMMMMMM
MMMMMMMMMMMWKxollllllllllllllllllllllllloOWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllllllllllllllllllllllllloolllllllolllollllkNMMMMMMMMMMMMMMMMMMMWXOdolllloxKWMMMMMMMMMMM
MMMMMMMMMMMMWKxollllllllllllllllllllllllod0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkoloollllllllllllllllllllllllllllloddollllllllllllld0WMMMMMMMMMMMMMMMWWNKOdolllllokXWMMMMMMMMMMMM
MMMMMMMMMMMMMWXkollllllllllllllllllllllllldKMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllollllllllllllllllllllllllllllld0XOollllllllllllkNMMMMMMMMMMMMWNK0OkxollllllloONWMMMMMMMMMMMMM
MMMMMMMMMMMMMMMNOdlllllllllllllllllllllllokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMN0dlllllllllllllllllllllllllolllld0NWN0dlllllloodxkKWMMMMMMMMMMMMNOollllllllllld0NMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMWKxolollllllllllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOolllllllllllllllllllllllllllldONMMMWKkdoooxOXNNWMMMMMMMMMMMMMNOollllllllllokXWMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMWXOdlllllllllllllllllloONWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdllllllllllllllllllllllllllld0NMMMMMWWXXXXNWMMMMMMMMMMMMMMMMW0dlllllllllod0NMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMWKxollolllllllllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxllllllllllllllllllllllllloxKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllokXWMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMWNOdollllllloolllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkolllloollllooolllllllllodONWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkllllllolox0NMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMWXkollllllolllllox0NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdlllllollllllllllllllodkKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllllllodOXWMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMWKxoolllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMN0dollllllllllooddxxk0KNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOdollllldOXWMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMN0xolllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxolllllodk0KXNNWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKkdollolodkXWMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMNKxoolllllllllodOKNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOdolldOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0xoollllodkXWMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNKkolllollllllloxOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOx0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOdolllllldOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKOdollllllllllodx0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOdoollllloxOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0xollollollollodxOXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0kdooollllodk0NWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKkdooolllllllllooxOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOxdollllllloxOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWN0kdllllllllollllodkOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWXKOkdoolllllloodOKNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0kdolllllllllllllodxO0XNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNX0OxdollloolllloxOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0kdoolllllllllllllooxkO0XNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWNX0OkxoololllllllooxOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNK0kdoolllllllllllloooodxkO0KXNWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWNXK0Okxdoolllllollllloxk0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOkdoollllllllloolllllloodxkkO00KXXNNWWWWWWMMMMMMMMMWWWWWWWNNXXK00Okxxdoolllllllllllloooxk0KNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNK0kxdoollllllllllllllllllllloodddxxxkkOOOOOOOOOOOkkkxxxdddoollllllllllllllllloodxO0XNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXK0OxdooollllllllllllooolllllllllllllllllllllllllllllllllllllllllllooodkO0KNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXK0OkxdooollllllllllllllllllllllllllllllllllllllllllloooddxkO0KXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNNXK0OOkkxdddoooooollllllllllllllllooooooddxxkOO0KKXNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWNNXXXKK00OOOOOOOOOOOOOOOO00KKXXXNNWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 */

