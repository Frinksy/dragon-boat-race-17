package org.gnocchigames.dragonboat;

import java.util.List;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.gnocchigames.dragonboat.exceptions.IsNotDrawingException;

public class RaceLegScreen extends ScreenAdapter {
    
    private DragonBoatGame game;
    private List<Entity> entities;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    public RaceLegScreen (DragonBoatGame game) {
        super();
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        entities = new ArrayList<Entity>();
        entities.add(new PlayerBoat());
    }


    public void draw() {
        Gdx.gl.glClearColor(0, 0, 50, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        for (Entity entity : entities) {
            try {
                entity.draw(batch);
            }catch (IsNotDrawingException e) {
                Gdx.app.log("Exception: ", e.getMessage());
            }
        }

        batch.end();

    }

    public void update() {

        for (Entity entity : entities) {
            entity.update();
        }

    }

    @Override
    public void render (float delta_time) {
        draw();
        update();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }

}
