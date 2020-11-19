package org.gnocchigames.dragonboat;

import java.util.List;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private List<Entity> entities_to_remove;
    private List<Entity> entities_collided;

    private GameCamera camera;
    private OrthographicCamera ui_camera;
    private SpriteBatch batch;
    private ShapeRenderer shape_renderer;

    private Texture background_texture;
    private Texture buoy_texture;

    public PlayerBoat player_boat;
    public Boat other_boat;
    private RaceStructure race_structure;

    private Boat.Boat_Type type;

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

        race_structure = new RaceStructure(this);

        batch = new SpriteBatch();
        shape_renderer = new ShapeRenderer();
        debug_box_renderer = new ShapeRenderer();

        camera = new GameCamera();
        camera.setToOrtho(false, 1920, 1080);
        ui_camera = new OrthographicCamera();
        ui_camera.setToOrtho(false, 1920, 1080);

        entities = new ArrayList<Entity>();
        entities_to_remove = new ArrayList<Entity>();
        entities_collided = new ArrayList<Entity>();

        // gets chosen boat type from boat choose screen
        type = BoatSelectScreen.getBoat();
        player_boat = new PlayerBoat(this, type);
        entities.add(player_boat);

        // DEBUG
        other_boat = new Boat(this, Boat_Type.ACCEL);
        entities.add(other_boat);

        entities.add(new Duck(this, 400, 400, 10));


        background_texture = new Texture("water_tile.png");
        buoy_texture = new Texture("buoy.png");

    }

    /**
     * Draws screen with all entities and background
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

        draw_info();

        // TODO: Remove debug hitbox rendering
        // DEBUG
        // debug_box_renderer.setProjectionMatrix(camera.combined);
        // debug_box_renderer.begin(ShapeType.Line);
        // debug_box_renderer.setColor(1, 1, 0, 1);

        // for (Entity entity : entities) {
        //     debug_box_renderer.polygon(entity.getBoundingPolygon().getTransformedVertices());
        //     debug_box_renderer.polygon(entity.hitbox.getTransformedVertices());
        // }

        // debug_box_renderer.end();
        // END DEBUG


    }


    /**
     * Update all entities in the scene
     * Apply movement, check for and apply collisions
     * @param delta_time time since last render() call
     */
    public void update(float delta_time) {
        
        for (Entity entity : entities) {
            entity.update(delta_time, entities);
        }

        // Detect collisions
        for (Entity entity : entities) {
            if (entity.isCollided(entities)) {
                entities_collided.add(entity);
            }
        }

        // Apply collisions
        for (Entity entity : entities_collided) {
            entity.applyCollision(null);
        }
        // Clear collision list
        entities_collided = new ArrayList<Entity>();
        
        // follow the boat with the camera
        camera.followBoat(player_boat);
        
        for (Entity entity : entities_to_remove) {
            entities.remove(entity);
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
        shape_renderer.dispose();
    }


    /**
     * Draw the tiled background
     */
    private void draw_background() {
        for (int x = 0; x <= 1920; x+=200) {
            for (int y = -1080*100; y <= 1080*100; y+=200) {
                batch.draw(background_texture, x, y, 200, 200);
            }
        }

        // Draw buoys for the lanes
        for (int y = -1080*100; y <= 1080*100; y+=256) {
            for (int x = 384; x < 1920; x+=384) {
                batch.draw(buoy_texture, x-buoy_texture.getWidth()/2, y-buoy_texture.getHeight()/2);
            }
        }
    }
    
    /**
     * Draw the UI elements of the RaceLegScreen
     * Elements include health bars, timers, etc.
     */
    private void draw_info() {

        shape_renderer.setProjectionMatrix(ui_camera.combined);
        shape_renderer.begin(ShapeType.Filled);
        
        for (Entity entity : entities) {
            if (entity instanceof Boat) {
                Boat boat = (Boat) entity;

                shape_renderer.setColor(0, 0, 0, 1);
                shape_renderer.rect(
                    boat.lane_number * (1920/5) + 140,
                    8,
                    104,
                    14
                );
                shape_renderer.setColor(0, 1, 0, 1);
                shape_renderer.rect(
                    boat.lane_number * (1920/5) + 142,
                    10,
                    boat.current_health,
                    10
                );

            }
        }



        shape_renderer.end();

    }
    
    public List<Entity> getEntities () {
        return entities;
    }

    public void addEntity (Entity entity) {
        entities.add(entity);
    } 

    public void removeEntity (Entity entity) {
        entities_to_remove.add(entity);
    }

}
