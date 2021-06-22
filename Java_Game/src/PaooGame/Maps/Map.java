package PaooGame.Maps;

import PaooGame.Items.Enemy;
import PaooGame.RefLinks;
import PaooGame.States.State;
import PaooGame.Tiles.Tile;
import PaooGame.Utils.Utils;

import java.awt.*;
import java.util.Random;

/*! \class public class Map
    \brief Implementeaza notiunea de harta a jocului.
 */
public class Map {
    private RefLinks refLink;       /*!< Referinta catre un obiect a carui sarcina este doar de a retine diverse referinte pentru a fi usor accesibile.*/
    private int width, height;     /*!< Referinte catre lungimea si latimea hartii(in numar de dale).*/
    private int[][] tiles;         /*!< Referinta catre matricea ce retine id-urile fiecarei dale necesare pentru desenarea hartii.*/
    private int lvl;                /*!< Referinta catre nivel.*/
    private int incercare = 1;      /*!< Referinta ce salveaza numarul de incercari.*/
    private int req;                /*!< Referinta catre obiectul jocului.*/
    boolean ok = true;         /*!< */
    private final Random random;        /*!< Generator de numere aleatorii.*/

    /*! \fn public Map(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public Map(RefLinks refLink) {
        this.refLink = refLink;
        lvl = 1;
        req = 500;
        LoadWorld("res/map");
        random = new Random();

    }

    /*! \fn public  void Update()
        \brief Actualizarea hartii in functie de evenimente (un copac a fost taiat)
     */
    public void Update() {
        if (refLink.GetGame().GetPlayState().GetManager().GetEntities().get(0).GetX() >= refLink.GetMap().getWidth() * Tile.TILE_WIDTH - 48 && lvl == 1) {
            if (incercare == 1) {
                if (!(refLink.GetGame().GetPlayState().GetScore() == 500)) {
                    refLink.GetGame().GetPlayState().GetManager().GetEntities().get(0).SetX(refLink.GetMap().getWidth() * Tile.TILE_WIDTH - 48);
                } else {
                    req = 1500;
                    LoadWorld("res/map2");
                    refLink.GetGame().GetPlayState().GetManager().GetEntities().get(0).SetX(50);
                    refLink.GetGame().GetPlayState().GetManager().GetEntities().get(0).SetY(50);
                    lvl = 2;
                }
            }

        } else if (incercare == 2) {
            req = 500;
            LoadWorld("res/map");
            lvl = 1;
            refLink.GetGame().GetPlayState().GetManager().GetEntities().get(0).SetX(50);
            refLink.GetGame().GetPlayState().GetManager().GetEntities().get(0).SetY(50);
            incercare = 1;
        }

        if (lvl == 2 && refLink.GetGame().GetPlayState().GetManager().GetEntities().get(0).GetX() >= refLink.GetMap().getWidth() * Tile.TILE_WIDTH - 48) {
            if (!(refLink.GetGame().GetPlayState().GetScore() == 1500)) {
                refLink.GetGame().GetPlayState().GetManager().GetEntities().get(0).SetX(refLink.GetMap().getWidth() * Tile.TILE_WIDTH - 48);
            } else {

                State.SetState(refLink.GetGame().GetGameOverState());
                lvl = 1;
                incercare = 2;
                refLink.GetGame().GetPlayState().GetManager().removeEntityes();
            }
        }
    }

    /*! \fn public void Draw(Graphics g)
        \brief Functia de desenare a hartii.

        \param g Contextul grafic in care se realizeaza desenarea.
     */
    public void Draw(Graphics g) {
        int xStart = (int) Math.max(0, refLink.GetGameCamera().getX() / Tile.TILE_WIDTH),
                xEnd = (int) Math.min(width, (refLink.GetGameCamera().getX() + refLink.GetGame().GetWidth()) / Tile.TILE_WIDTH + 1),
                yStart = (int) Math.max(0, refLink.GetGameCamera().getY() / Tile.TILE_HEIGHT),
                yEnd = (int) Math.min(height, (refLink.GetGameCamera().getY() + refLink.GetGame().GetHeight()) / Tile.TILE_HEIGHT + 1);
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                GetTile(x, y).Draw(g, (int) (x * Tile.TILE_HEIGHT - refLink.GetGameCamera().getX()), (int) (y * Tile.TILE_WIDTH - refLink.GetGameCamera().getY()));
            }
        }
        int xx = random.nextInt(40);
        int yy = random.nextInt(20);
        if (GetTile(xx, yy) == Tile.grassTile)
            if (ok) {
                if (refLink.GetGame().GetPlayState().GetScore() < 500 && lvl == 1) {
                    refLink.GetGame().GetPlayState().GetManager().addEntity(new Enemy(refLink, xx * 48, yy * 48));
                    ok = false;
                }
                if (refLink.GetGame().GetPlayState().GetScore() < 1500 && lvl == 2) {
                    refLink.GetGame().GetPlayState().GetManager().addEntity(new Enemy(refLink, xx * 48, yy * 48));
                    ok = false;
                }
            } else {
                if (refLink.GetGame().GetPlayState().GetManager().GetEntities().size() == 1) {
                    ok = true;
                }
            }
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("   /" + req, 175, 30);

    }

    /*! \fn public Tile GetTile(int x, int y)
        \brief Intoarce o referinta catre dala aferenta codului din matrice de dale.

        In situatia in care dala nu este gasita datorita unei erori ce tine de cod dala, coordonate gresite etc se
        intoarce o dala predefinita (ex. grassTile, mountainTile)
     */
    public Tile GetTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return Tile.grassTile;
        }
        Tile t = Tile.tiles[tiles[x][y]];
        if (t == null) {
            return Tile.grassTile;
        }
        return t;
    }

    /*! \fn private void LoadWorld()
        \brief Functie de incarcare a hartii jocului.
        Aici se poate genera sau incarca din fisier harta. Momentan este incarcata static.
     */
    private void LoadWorld(String path) {
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        tiles = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 2]);
            }
        }
    }

    /*! \fn public int getWidth(){
        \brief Returneaza numarul de dale de pe axa x.
     */
    public int getWidth() {
        return width;
    }

    /*! \fn public int getHeight(){
        \brief Returneaza numarul de dale de pe axa y.
     */
    public int getHeight() {
        return height;
    }
}