package PaooGame.Items;

import PaooGame.RefLinks;
import java.awt.*;
import java.util.ArrayList;

public class EntityManager {
    private RefLinks refLinks;
    private Hero hero;
    private ArrayList<Entity> entities;

    public EntityManager(RefLinks refLinks, Hero hero){
        this.refLinks = refLinks;
        this.hero = hero;
        entities = new ArrayList<Entity>();
        addEntity(hero);
    }

    public void Update(){
        for(int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            e.Update();
            if(!e.isActive())
                entities.remove(e);
        }
    }

    public void Draw(Graphics g){
        for(Entity e: entities){
            e.Draw(g);
        }
    }

    public void addEntity(Entity e){
        entities.add(e);
    }

    /*! \fn public void removeEntityes()
        \brief Elimina inamicii.
     */
    public void removeEntityes(){
        for(int i = 1; i < entities.size(); i++){
            Entity e = entities.get(i);
            entities.remove(e);
        }
    }

    /*! \fn public ArrayList<Entity> GetEntities()
        \brief Returneaza toate entitatile.
     */
    public ArrayList<Entity> GetEntities() {
        return entities;
    }
}
