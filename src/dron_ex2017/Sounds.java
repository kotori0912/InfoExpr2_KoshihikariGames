package dron_ex2017;

// ==================================================================================
// ライブラリ
// ==================================================================================
import java.applet.AudioClip;
import javax.swing.JApplet;

public class Sounds {
    /* 音楽ファイル 定義 */
    private AudioClip ButtonHighlightSE;

    public Sounds() {
        ButtonHighlightSE = JApplet.newAudioClip(getClass().getResource("sounds/chari05_b.wav"));
    }

    public void buttonHighlight() {
        ButtonHighlightSE.play();       //
    }
}
