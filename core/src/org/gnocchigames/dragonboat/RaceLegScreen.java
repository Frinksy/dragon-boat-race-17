package org.gnocchigames.dragonboat;

import java.util.List;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import org.gnocchigames.dragonboat.Boat.Boat_Type;
import org.gnocchigames.dragonboat.exceptions.IsNotDrawingException;

import org.gnocchigames.dragonboat.RaceStructure;



/**
 * RaceLegScreen
 * Base class for a leg of the dragon boat race
 * Keeps track of entities, updates them
 * Draws scene every frame
 */
public class RaceLegScreen extends ScreenAdapter {
    
    private DragonBoatGame game;
    private List<Entity> entities;
    private GameCamera camera;
    private SpriteBatch batch;

    private Texture background_texture;

    private PlayerBoat player_boat;
    private RaceStructure race_structure;

    ShapeRenderer debug_box_renderer;

    public RaceLegScreen (DragonBoatGame game) {
        super();
        this.game = game;
        //System.out.println("raceover = "+race_structure.raceover(player_boat));
        
    }

    /**
     * Called when DragonBoatGame switches to this screen
     * Instantiates required objects
     */
    @Override
    public void show() {
        batch = new SpriteBatch();
        debug_box_renderer = new ShapeRenderer();

        camera = new GameCamera();
        camera.setToOrtho(false, 1920, 1080);

        entities = new ArrayList<Entity>();
        player_boat = new PlayerBoat(Boat_Type.FAST);
        player_boat.pos_x = 400;
        entities.add(player_boat);

        // DEBUG
        entities.add(new Boat(Boat_Type.ACCEL));

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

        // TODO: Remove debug hitbox rendering
        // DEBUG
        debug_box_renderer.setProjectionMatrix(camera.combined);
        debug_box_renderer.begin(ShapeType.Line);
        debug_box_renderer.setColor(1, 1, 0, 1);
        debug_box_renderer.polygon(player_boat.getBoundingPolygon().getTransformedVertices());
        debug_box_renderer.end();
    }


    /**
     * Update all entities in the scene
     * @param delta_time time since last render() call
     */
    public void update(float delta_time) {

        for (Entity entity : entities) {
            entity.update(delta_time, entities);
        }

        // follow the boat with the camera
        camera.followBoat(player_boat);
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
    

    public List<Entity> getEntities () {
        return entities;
    }

}
