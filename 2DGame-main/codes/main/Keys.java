package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keys implements KeyListener{
	GameScreen gs;
	public boolean upPress , downPress , leftPress, rightPress, enterPress,shotKeyPress;
	public boolean checkDrawTime=false;
	
	public Keys(GameScreen gs) {
		this.gs=gs;
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		//title
		if(gs.gameStates == gs.titleState) {
			tittleState(code);
			
		}
		
		//play
		else if(gs.gameStates == gs.huntState) {
			huntState(code);
			
		}
		
		//pause
		else if(gs.gameStates == gs.pauseState){
			pauseState(code);
		}
		
		//dialogue
		else if(gs.gameStates == gs.dialogState){
			dialogueState(code);
		}
		
		//character state
		else if(gs.gameStates == gs.characterState) {
			characterState(code);
		}
		
		//option state
		else if(gs.gameStates == gs.optionState) {
			optionsState(code);
		}
		
		//gameOver state
		else if(gs.gameStates == gs.gameOverState) {
			gameOverState(code);
		}
		
		//gameOver state
		else if(gs.gameStates == gs.tradingState) {
			tradeState(code);
		}
		
		
	}
	
	
	public void tittleState(int code) {
		
		if(gs.ui.titleScreenState == 0) {
			
			if(code == KeyEvent.VK_W) {
				gs.playSE(9);
				gs.ui.selectedNum--;
				
				if(gs.ui.selectedNum <0) {
					gs.ui.selectedNum =3;
				}
			}
			if(code == KeyEvent.VK_S) {
				gs.playSE(9);
				gs.ui.selectedNum++;
				
				if(gs.ui.selectedNum >3) {
					gs.ui.selectedNum =0;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gs.ui.selectedNum == 0) {
					gs.ui.titleScreenState =1;
				}
				if(gs.ui.selectedNum == 1) {
					//load map
				}
				if(gs.ui.selectedNum == 2) {
					//options
				}
				if(gs.ui.selectedNum == 3) {
					System.exit(0);
				}
			}
		}else if(gs.ui.titleScreenState == 1) {
			if(code == KeyEvent.VK_W) {
				gs.ui.selectedNum--;
				gs.playSE(9);
				if(gs.ui.selectedNum <0) {
					gs.ui.selectedNum=1;
				}
			}
			if(code == KeyEvent.VK_S) {
				gs.ui.selectedNum++;
				gs.playSE(9);
				if(gs.ui.selectedNum >1) {
					gs.ui.selectedNum=0;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gs.ui.selectedNum == 0) {
					gs.gameStates = gs.huntState;
					gs.playMusic(14);
				}
				if(gs.ui.selectedNum == 1) {
					gs.ui.titleScreenState = 0;
				}
			}
		}
	}
	
	public void tradeState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			enterPress = true;
		}
		
		if(gs.ui.subState == 0) {
			if(code == KeyEvent.VK_W) {
				gs.ui.selectedNum--;
				if(gs.ui.selectedNum < 0) {
					gs.ui.selectedNum = 2;
				}
				gs.playSE(9);
			}
			if(code == KeyEvent.VK_S) {
				gs.ui.selectedNum++;
				if(gs.ui.selectedNum > 2) {
					gs.ui.selectedNum = 0;
				}
				gs.playSE(9);
			}
		}
		if(gs.ui.subState == 1) {
			npcInventory(code);
			if(code == KeyEvent.VK_ESCAPE) {
				gs.ui.subState =0;
			}
		}
		
		if(gs.ui.subState == 2) {
			playerInventory(code);
			if(code == KeyEvent.VK_ESCAPE) {
				gs.ui.subState =0;
			}
		}
	}
	
	public void optionsState(int code) {
		
		if(code == KeyEvent.VK_ESCAPE) {
			gs.gameStates = gs.huntState;
		}
		
		if(code == KeyEvent.VK_ENTER) {
			enterPress = true;
		}
		
		int maxSelectNum = 0;
		
		switch(gs.ui.subState) {
		case 0: maxSelectNum=5; break;
		case 3: maxSelectNum=1; break;
		}
		
		if(code == KeyEvent.VK_W) {
			gs.ui.selectedNum--;
			gs.playSE(9);
			if(gs.ui.selectedNum < 0) {
				gs.ui.selectedNum = maxSelectNum;
			}
		}
		if(code == KeyEvent.VK_S) {
			gs.ui.selectedNum++;
			gs.playSE(9);
			if(gs.ui.selectedNum > maxSelectNum) {
				gs.ui.selectedNum = 0;
			}
		}
		
		
		if(code == KeyEvent.VK_A) {
			if(gs.ui.subState == 0) {
				if(gs.ui.selectedNum == 1 && gs.music.volumeScale > 0) {
					gs.music.volumeScale--;
					gs.music.checkVolume();
					gs.playSE(9);
				}
				if(gs.ui.selectedNum == 2 && gs.se.volumeScale > 0) {
					gs.se.volumeScale--;
					gs.playSE(9);
				}
			}
		}
		
		if(code == KeyEvent.VK_D) {
			if(gs.ui.subState == 0) {
				if(gs.ui.selectedNum == 1 && gs.music.volumeScale < 5) {
					gs.music.volumeScale++;
					gs.music.checkVolume();
					gs.playSE(9);
				}
				if(gs.ui.selectedNum == 2 && gs.se.volumeScale < 5) {
					gs.se.volumeScale++;
					gs.playSE(9);
				}
			}
		}
	}

	public void gameOverState(int code) {
		if(code == KeyEvent.VK_W) {
			gs.ui.selectedNum--;
			if(gs.ui.selectedNum < 0) {
				gs.ui.selectedNum=1;
			}
			gs.playSE(9);
		}
		if(code == KeyEvent.VK_S) {
			gs.ui.selectedNum++;
			if(gs.ui.selectedNum > 1) {
				gs.ui.selectedNum=0;
			}
			gs.playSE(9);
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gs.ui.selectedNum == 0) {
				gs.retry();
				gs.gameStates = gs.huntState;
				
			}else if(gs.ui.selectedNum == 1) {
				gs.gameStates = gs.titleState;
				gs.ui.titleScreenState = 0;
				gs.restart();
				gs.ui.selectedNum = 0;
				
			}
		}
	}
	
	public void huntState(int code) {
		if(code == KeyEvent.VK_W) {
			upPress = true;
		}
		if(code == KeyEvent.VK_S) {
			downPress = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPress = true;		
		}
		if(code == KeyEvent.VK_D) {
			rightPress= true;
		}
		if(code == KeyEvent.VK_P) {
			gs.gameStates= gs.pauseState;
		}
		if(code == KeyEvent.VK_C) {
			gs.gameStates= gs.characterState;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPress = true;
		}
		if(code == KeyEvent.VK_F) {
			shotKeyPress = true;
		}
		if(code == KeyEvent.VK_ESCAPE) {
			gs.gameStates = gs.optionState;
		}
		
		//debug
		if(code == KeyEvent.VK_T) {
			if(checkDrawTime == false) {
				checkDrawTime = true;
			}else if(checkDrawTime == true){
				checkDrawTime=false;
			}
		}
		if(code == KeyEvent.VK_R) {
			switch(gs.currentMap) {
			case 0: gs.rectM.loadMap("/maps/worldOK.txt",0);break;
			case 1: gs.rectM.loadMap("/maps/interior01.txt",1);break;
			}
		}
	}
	
	public void pauseState(int code) {
		if(code == KeyEvent.VK_P) {
			gs.gameStates= gs.huntState;
		}
	}
	
	public void dialogueState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			gs.gameStates = gs.huntState;
		}
	}
	
	public void characterState(int code) {
		if(code == KeyEvent.VK_C) {
			gs.gameStates = gs.huntState;
		}
		if(code == KeyEvent.VK_ENTER) {
			gs.player.selectItem();
		}
		playerInventory(code);
	}

	public void playerInventory(int code) {
		if(code == KeyEvent.VK_W) {
			if(gs.ui.playerSlotRow != 0) {
				gs.ui.playerSlotRow--;
				gs.playSE(9);
			}
		}
		if(code == KeyEvent.VK_A) {
			if(gs.ui.playerSlotCol != 0) {
			gs.ui.playerSlotCol--;
			gs.playSE(9);
			}
		}
		if(code == KeyEvent.VK_S) {
			if(gs.ui.playerSlotRow != 3) {
			gs.ui.playerSlotRow++;
			gs.playSE(9);
			}
		}
		if(code == KeyEvent.VK_D) {
			if(gs.ui.playerSlotCol != 4) {
			gs.ui.playerSlotCol++;
			gs.playSE(9);
			}
		}
	}
	
	public void npcInventory(int code) {
		if(code == KeyEvent.VK_W) {
			if(gs.ui.npcSlotRow != 0) {
				gs.ui.npcSlotRow--;
				gs.playSE(9);
			}
		}
		if(code == KeyEvent.VK_A) {
			if(gs.ui.npcSlotCol != 0) {
			gs.ui.npcSlotCol--;
			gs.playSE(9);
			}
		}
		if(code == KeyEvent.VK_S) {
			if(gs.ui.npcSlotRow != 3) {
			gs.ui.npcSlotRow++;
			gs.playSE(9);
			}
		}
		if(code == KeyEvent.VK_D) {
			if(gs.ui.npcSlotCol != 4) {
			gs.ui.npcSlotCol++;
			gs.playSE(9);
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPress = false;
		}
		if(code == KeyEvent.VK_S) {
			downPress = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPress = false;		
		}
		if(code == KeyEvent.VK_D) {
			rightPress= false;
		}
		if(code == KeyEvent.VK_F) {
			shotKeyPress = false;
		}
	}

}
