package dron_ex2017;

// ==================================================================================
// ライブラリ
// ==================================================================================
import java.applet.AudioClip;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;


public class GameButton extends JButton implements MouseListener, ActionListener {
    private AudioClip enterButton;
    Sounds sounds = new Sounds();

    public GameButton(String s, ImageIcon i) {
        super(s, i);
        System.out.println("GameStartButtonメソッド");
        addMouseListener(this);
    }

    // ==================================================================================
    // マウスイベント
    // ==================================================================================
    @Override
    public void mouseClicked(MouseEvent ev) {
        System.out.println("aaaa");
    }

    @Override
    public void mouseEntered(MouseEvent ev) {
        sounds.buttonHighlight();                   // 音再生
    }

    @Override
    public void mouseExited(MouseEvent ev) {
    }

    @Override
    public void mousePressed(MouseEvent ev) {

    }

    @Override
    public void mouseReleased(MouseEvent ev) {

    }

    @Override
    public void actionPerformed(ActionEvent ae) {}
}
