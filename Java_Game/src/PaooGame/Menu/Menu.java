package PaooGame.Menu;

import PaooGame.RefLinks;

import java.awt.*;

public class Menu {
    private Font font;
    int x, y;
    Color color = Color.GREEN;
    private final int SIZE = 50;
    private String fontName;
    String option;
    public Menu(RefLinks refLink, String fontName, String option, int x, int y){

        this.fontName = fontName;
        this.option = option;
        this.x = x;
        this.y = y;
    }

    public void Update(){
        font = new Font(fontName, Font.BOLD, SIZE);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void Draw(Graphics g){
        g.setFont(font);
        g.setColor(color);
        g.drawString(option, x, y);
    }

    public void SetOption(String opt){
        option = opt;
    }
}
