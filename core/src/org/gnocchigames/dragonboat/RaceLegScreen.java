package org.gnocchigames.dragonboat;

import java.util.List;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.gnocchigames.dragonboat.Boat.Boat_Type;
import org.gnocchigames.dragonboat.exceptions.IsNotDrawingException;

/**
 * RaceLegScreen
 * Base class for a leg of the dragon boat race
 * Keeps track of entities, updates them
 * Draws scene every frame
 */
public class RaceLegScreen extends ScreenAdapter {
    
    private DragonBoatGame game;
    private List<Entity> entities;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Texture background_texture;

    public RaceLegScreen (DragonBoatGame game) {
        super();
        this.game = game;
    }

    /**
     * Called when DragonBoatGame switches to this screen
     * Instantiates required objects
     */
    @Override
    public void show() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        entities = new ArrayList<Entity>();
        entities.add(new PlayerBoat(Boat_Type.FAST));

        background_texture = new Texture("water_tile.png");

    }

    /**
     * Draws screen
     * Should be called every frame
     */
    public void draw() {
        Gdx.gl.glClearColor(0, 0, 50, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the background on first
        batch.begin();
        draw_background();
        batch.end();

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

    /**
     * Update all entities in the scene
     * @param delta_time time since last render() call
     */
    public void update(float delta_time) {

        for (Entity entity : entities) {
            entity.update(delta_time);
        }

    }

    /**
     * render() is called every frame to render the screen
     */
    @Override
    public void render (float delta_time) {
        draw();
        update(delta_time);
    }

    /**
     * Called when the screen is disposed of
     */
    @Override
    public void dispose () {
        batch.dispose();
    }


    /**
     * Draw the tiled background
     */
    private void draw_background() {
        for (int x = 0; x <= 1920; x+=200) {
            for (int y = 0; y <= 1080; y+=200) {
                batch.draw(background_texture, x, y, 200, 200);
            }
        }
    }

}
