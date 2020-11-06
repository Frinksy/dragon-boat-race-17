package org.gnocchigames.dragonboat;

import java.util.ArrayList;
import java.util.List; 

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.gnocchigames.dragonboat.exceptions.IsNotDrawingException;

public class DragonBoatGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	List<Entity> game_entities;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		game_entities = new ArrayList<Entity>();
		game_entities.add(new Boat());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 15, 0);

		for (Entity entity : game_entities) {
			try {
				entity.draw(batch);
			}catch (IsNotDrawingException e) {
				System.out.println(e.getMessage());
			}
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
