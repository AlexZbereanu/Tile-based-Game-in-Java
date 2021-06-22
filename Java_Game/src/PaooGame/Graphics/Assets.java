package PaooGame.Graphics;

import java.awt.image.BufferedImage;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets {
    /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static BufferedImage[] heroLeft, heroRight, heroDown, heroUp;
    public static BufferedImage[] attackLeft, attackRight, attackDown, attackUp;
    public static BufferedImage enemy;
    public static BufferedImage grass;
    public static BufferedImage tree;

    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init() {
        /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/P.png"));
        SpriteSheet hero = new SpriteSheet(ImageLoader.LoadImage("/spreet.png"));
        enemy = ImageLoader.LoadImage("/enemy.png");
        /// Se obtin subimaginile corespunzatoare elementelor necesare.
        heroLeft = new BufferedImage[9];
        heroRight = new BufferedImage[9];
        heroUp = new BufferedImage[9];
        heroDown = new BufferedImage[9];
        attackLeft = new BufferedImage[8];
        attackRight = new BufferedImage[8];
        attackUp = new BufferedImage[8];
        attackDown = new BufferedImage[8];
        for(int i = 0;i<9;i++) {
            heroLeft[i]  = hero.crop(i, 9, 64, 64);
            heroDown[i]  = hero.crop(i, 10, 64, 64);
            heroUp[i]    = hero.crop(i, 8, 64, 64);
            heroRight[i] = hero.crop(i, 11, 64, 64);
            if( i == 8)
                break;
            attackLeft[i]  = hero.crop(i, 5, 64, 64);
            attackDown[i]  = hero.crop(i, 6, 64, 64);
            attackUp[i]    = hero.crop(i, 4, 64, 64);
            attackRight[i] = hero.crop(i, 7, 64, 64);
        }
        int width = 48;
        int height = 48;
        grass = sheet.crop(0, 0, width, height);
        tree = sheet.crop(3, 1, width, height);
    }
}
