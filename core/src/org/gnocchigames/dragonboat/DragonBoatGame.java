package org.gnocchigames.dragonboat;

import java.util.ArrayList;
import java.util.List; 

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.gnocchigames.dragonboat.exceptions.IsNotDrawingException;

public class DragonBoatGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	private OrthographicCamera camera;
	
	List<Entity> game_entities;

	@Override
	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);

		game_entities = new ArrayList<Entity>();
		game_entities.add(new PlayerBoat());
	}


	public void draw () {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		for (Entity entity : game_entities) {
			try {
				entity.draw(batch);
			}catch (IsNotDrawingException e) {
				System.out.println(e.getMessage());
			}
		}

		batch.end();
	}

	public void update() {


		for (Entity entity : game_entities) {
			entity.update();
		}

	}


	@Override
	public void render () {


		draw();
		update();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
