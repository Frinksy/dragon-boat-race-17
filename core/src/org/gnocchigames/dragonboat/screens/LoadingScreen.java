package org.gnocchigames.dragonboat.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import org.gnocchigames.dragonboat.DragonBoatGame;

public class LoadingScreen implements Screen{
    
    private DragonBoatGame parent;

    public LoadingScreen(DragonBoatGame game){
        parent = game;
    }

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta_time) {
		parent.changeScreen(DragonBoatGame.MENU);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
}