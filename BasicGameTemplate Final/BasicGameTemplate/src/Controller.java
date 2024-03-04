//Student: Jintao Yi 
//Student num:23205376
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

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

//Singeton pattern
public class Controller implements KeyListener {
        
	   private static boolean KeyAPressed= false;
	   private static boolean KeySPressed= false;
	   
	   private static boolean KeyDPressed= false;
	   private static boolean KeyWPressed= false;
	   private static boolean KeySpacePressed= false;
	   private static boolean KeyJPressed= false;
	   private static boolean KeyKPressed= false;
	   private static boolean KeyLPressed= false;
	   private static boolean KeyUPressed= false;
	   private static boolean KeyMPressed= false;
	   private static boolean KeyNPressed= false;
	   private static final Controller instance = new Controller(); 
	   private static boolean stop= false;
	   private static boolean KeySPressed2= false;
	   private static boolean KeyAPressed2= false;
	   private static boolean KeyDPressed2= false;
	   private static boolean KeyWPressed2= false;
	   private static boolean KeyJPressed2= false;
	   private static boolean KeyKPressed2= false;
	   private static boolean KeyLPressed2= false;
	   private static boolean KeyUPressed2= false;
	   private static boolean KeyIPressed= false;
	   private static boolean KeyIPressed2= false;
	  
	   
	
	   
	 public Controller() { 
	}
	 
	 public static Controller getInstance(){
	        return instance;
	    }
	   
	@Override
	// Key pressed , will keep triggering 
	public void keyTyped(KeyEvent e) { 
		 
	}


	public void keyPressed(KeyEvent e) { 
	    switch (e.getKeyCode()) {
	        case KeyEvent.VK_A:
	            setKeyAPressed(true);
	            break; 
	        case KeyEvent.VK_S:
	            setKeySPressed(true);
	            break;
	        case KeyEvent.VK_DOWN:
	            setKeySPressed2(true);
	            break;
	        case KeyEvent.VK_UP:
	            setKeyWPressed2(true);
	            break;
	        case KeyEvent.VK_RIGHT:
	            setKeyDPressed2(true);
	            break;
	        case KeyEvent.VK_LEFT:
	            setKeyAPressed2(true);
	            break;
	        case KeyEvent.VK_W:
	            setKeyWPressed(true);
	            break;
	        case KeyEvent.VK_D:
	            setKeyDPressed(true);
	            break; 
	        case KeyEvent.VK_J:
	            setKeyJPressed(true);
	            break;
	        case KeyEvent.VK_K:
	            setKeyKPressed(true);
	            break;
	        case KeyEvent.VK_L:
	            setKeyLPressed(true);
	            break;
	        case KeyEvent.VK_U:
	            setKeyUPressed(true);
	            break;
	        case KeyEvent.VK_M:
	            setKeyMPressed(true);
	            break;
	        case KeyEvent.VK_N:
	            setKeyNPressed(true);
	            break;
	        case KeyEvent.VK_I:
	            setKeyIPressed(true);
	            break;
	        case KeyEvent.VK_NUMPAD5:
	            setKeyNPressed(true);
	            break;
	        case KeyEvent.VK_SPACE:
	            setKeySpacePressed(true);
	            break;   
	        case KeyEvent.VK_NUMPAD1:
	        	setKeyJPressed2(true);
	            break;
	        case KeyEvent.VK_NUMPAD2:
	        	setKeyKPressed2(true);
	            break;
	        case KeyEvent.VK_NUMPAD3:
	        	setKeyLPressed2(true);
	            break;
	        case KeyEvent.VK_NUMPAD4:
	        	setKeyUPressed2(true);
	            break;
	        case KeyEvent.VK_ESCAPE:
	        	if(isStop())
	        	setStop(false);
	        	else
	        		setStop(true);
	            break;
	        default:
	            //System.out.println("Controller test:  Unknown key pressed");
	            break;
	    }  
	    
	}


	public void keyReleased(KeyEvent e) { 
	    switch (e.getKeyCode()) {
	        case KeyEvent.VK_A:
	            setKeyAPressed(false);
	            break;  
	        case KeyEvent.VK_S:
	            setKeySPressed(false);
	            break;
	        case KeyEvent.VK_DOWN:
	            setKeySPressed2(false);
	            break;
	        case KeyEvent.VK_UP:
	            setKeyWPressed2(false);
	            break;
	        case KeyEvent.VK_RIGHT:
	            setKeyDPressed2(false);
	            break;
	        case KeyEvent.VK_LEFT:
	            setKeyAPressed2(false);
	            break;
	        case KeyEvent.VK_W:
	            setKeyWPressed(false);
	            break;
	        case KeyEvent.VK_D:
	            setKeyDPressed(false);
	            break;
	        case KeyEvent.VK_J:
	            setKeyJPressed(false);
	            break;
	        case KeyEvent.VK_K:
	            setKeyKPressed(false);
	            break;
	        case KeyEvent.VK_I:
	            setKeyIPressed(false);
	            break;
	        case KeyEvent.VK_NUMPAD5:
	            setKeyKPressed(false);
	            break;
	        case KeyEvent.VK_L:
	            setKeyLPressed(false);
	            break;
	        case KeyEvent.VK_U:
	            setKeyUPressed(false);
	            break;
	        case KeyEvent.VK_M:
	            setKeyMPressed(false);
	            break;
	        case KeyEvent.VK_N:
	            setKeyNPressed(false);
	            break;
	        case KeyEvent.VK_SPACE:
	            setKeySpacePressed(false);
	            break;
	        case KeyEvent.VK_NUMPAD1:
	        	setKeyJPressed2(false);
	            break;
	        case KeyEvent.VK_NUMPAD2:
	        	setKeyKPressed2(false);
	            break;
	        case KeyEvent.VK_NUMPAD3:
	        	setKeyLPressed2(false);
	            break;
	        case KeyEvent.VK_NUMPAD4:
	        	setKeyUPressed2(false);
	            break;

	        default:
	            //System.out.println("Controller test:  Unknown key pressed");
	            break;
	    }  
	}

	private void setKeyJPressed(boolean keyJPressed) {
		// TODO Auto-generated method stub
		KeyJPressed = keyJPressed;
	}
	public boolean isKeyJPressed() {
		return KeyJPressed;
	}

	public boolean isKeyAPressed() {
		return KeyAPressed;
	}


	public void setKeyAPressed(boolean keyAPressed) {
		KeyAPressed = keyAPressed;
	}


	public boolean isKeySPressed() {
		return KeySPressed;
	}


	public void setKeySPressed(boolean keySPressed) {
		KeySPressed = keySPressed;
	}


	public boolean isKeyDPressed() {
		return KeyDPressed;
	}


	public void setKeyDPressed(boolean keyDPressed) {
		KeyDPressed = keyDPressed;
	}


	public boolean isKeyWPressed() {
		return KeyWPressed;
	}


	public void setKeyWPressed(boolean keyWPressed) {
		KeyWPressed = keyWPressed;
	}


	public boolean isKeySpacePressed() {
		return KeySpacePressed;
	}


	public void setKeySpacePressed(boolean keySpacePressed) {
		KeySpacePressed = keySpacePressed;
	}

	public  boolean isKeyLPressed() {
		return KeyLPressed;
	}

	public  void setKeyLPressed(boolean keyLPressed) {
		KeyLPressed = keyLPressed;
	}

	public  boolean isKeyKPressed() {
		return KeyKPressed;
	}

	public  void setKeyKPressed(boolean keyKPressed) {
		KeyKPressed = keyKPressed;
	}

	public  boolean isKeyUPressed() {
		return KeyUPressed;
	}

	public  void setKeyUPressed(boolean keyUPressed) {
		KeyUPressed = keyUPressed;
	}

	public  boolean isKeySPressed2() {
		return KeySPressed2;
	}

	public  void setKeySPressed2(boolean keySPressed2) {
		KeySPressed2 = keySPressed2;
	}

	public  boolean isKeyAPressed2() {
		return KeyAPressed2;
	}

	public  void setKeyAPressed2(boolean keyAPressed2) {
		KeyAPressed2 = keyAPressed2;
	}

	public  boolean isKeyDPressed2() {
		return KeyDPressed2;
	}

	public  void setKeyDPressed2(boolean keyDPressed2) {
		KeyDPressed2 = keyDPressed2;
	}

	public  boolean isKeyWPressed2() {
		return KeyWPressed2;
	}

	public  void setKeyWPressed2(boolean keyWPressed2) {
		KeyWPressed2 = keyWPressed2;
	}

	public  boolean isKeyJPressed2() {
		return KeyJPressed2;
	}

	public  void setKeyJPressed2(boolean keyJPressed2) {
		KeyJPressed2 = keyJPressed2;
	}

	public  boolean isKeyKPressed2() {
		return KeyKPressed2;
	}

	public  void setKeyKPressed2(boolean keyKPressed2) {
		KeyKPressed2 = keyKPressed2;
	}

	public  boolean isKeyLPressed2() {
		return KeyLPressed2;
	}

	public  void setKeyLPressed2(boolean keyLPressed2) {
		KeyLPressed2 = keyLPressed2;
	}

	public  boolean isKeyUPressed2() {
		return KeyUPressed2;
	}

	public  void setKeyUPressed2(boolean keyUPressed2) {
		KeyUPressed2 = keyUPressed2;
	}

	public static boolean isStop() {
		return stop;
	}

	public static void setStop(boolean stop) {
		Controller.stop = stop;
	}

	public static boolean isKeyMPressed() {
		return KeyMPressed;
	}

	public static void setKeyMPressed(boolean keyMPressed) {
		KeyMPressed = keyMPressed;
	}

	public static boolean isKeyNPressed() {
		return KeyNPressed;
	}

	public static void setKeyNPressed(boolean keyNPressed) {
		KeyNPressed = keyNPressed;
	}

	public static boolean isKeyIPressed() {
		return KeyIPressed;
	}

	public static void setKeyIPressed(boolean keyIPressed) {
		KeyIPressed = keyIPressed;
	}

	public static boolean isKeyIPressed2() {
		return KeyIPressed2;
	}

	public static void setKeyIPressed2(boolean keyIPressed2) {
		KeyIPressed2 = keyIPressed2;
	} 
	
	 
}

/*
 * 
 * KEYBOARD :-) . can you add a mouse or a gamepad 

 *@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ @@@@@@@@@@@@@@@

  @@@     @@@@    @@@@    @@@@    @@@@     @@@     @@@     @@@     @@@     @@@  

  @@@     @@@     @@@     @@@@     @@@     @@@     @@@     @@@     @@@     @@@  

  @@@     @@@     @@@     @@@@    @@@@     @@@     @@@     @@@     @@@     @@@  

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

@     @@@     @@@     @@@      @@      @@@     @@@     @@@     @@@     @@@     @

@     @@@   W   @@@     @@@      @@      @@@     @@@     @@@     @@@     @@@     @

@@    @@@@     @@@@    @@@@    @@@@    @@@@     @@@     @@@     @@@     @@@     @

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@N@@@@@@@@@@@@@@@@@@@@@@@@@@@

@@@     @@@      @@      @@      @@      @@@     @@@     @@@     @@@     @@@    

@@@   A   @@@  S     @@  D     @@      @@@     @@@     @@@     @@@     @@@     @@@    

@@@@ @  @@@@@@@@@@@@ @@@@@@@    @@@@@@@@@@@@    @@@@@@@@@@@@     @@@@   @@@@@   

    @@@     @@@@    @@@@    @@@@    $@@@     @@@     @@@     @@@     @@@     @@@

    @@@ $   @@@      @@      @@ /Q   @@ ]M   @@@     @@@     @@@     @@@     @@@

    @@@     @@@      @@      @@      @@      @@@     @@@     @@@     @@@     @@@

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

@       @@@                                                @@@       @@@       @

@       @@@              SPACE KEY       @@@        @@ PQ     

@       @@@                                                @@@        @@        

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * 
 * 
 * 
 * 
 * 
 */
