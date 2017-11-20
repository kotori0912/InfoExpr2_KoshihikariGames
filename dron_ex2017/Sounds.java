// ==================================================================================
//
// ==================================================================================
package dron_ex2017;

// ==================================================================================
// ライブラリ
// ==================================================================================
import java.applet.AudioClip;
import javax.swing.JApplet;

public class Sounds {
    /* 音楽ファイル 定義 */
    private AudioClip buttonHighlightSE;
    private AudioClip buttonPushed;
    private AudioClip stageBgm;
    private AudioClip menuBgm;
	private AudioClip explosion;

    // ==================================================================================
    // コンストラクタ(変数に音を代入)
    // ==================================================================================
    public Sounds() {
        buttonHighlightSE = JApplet.newAudioClip(getClass().getResource("sounds/chari05_b.wav"));
        buttonPushed = JApplet.newAudioClip(getClass().getResource("sounds/click.wav"));
        stageBgm = JApplet.newAudioClip(getClass().getResource("sounds/bgml044.wav"));
    	explosion =  JApplet.newAudioClip(getClass().getResource("sounds/se_maoudamashii_explosion06.wav"));
    }

    // ==================================================================================
    // ボタン音
    // ==================================================================================
    /* マウスポインタがオブジェクトに入った時の音 */
    public void buttonHighlight() {
        buttonHighlightSE.play();
    }

    /* ボタンが押された時の音 */
    public void buttonPushed() {
        buttonPushed.play();
    }

    // ==================================================================================
    // ステージBGM
    // ==================================================================================
    public void stageBgm() {
        stageBgm.loop();
    }

    public void stageBgmStop() {
        stageBgm.stop();
    }

    // ==================================================================================
    // メニューBGM
    // ==================================================================================
    public void menuBgm() {}
	
    // ==================================================================================
    // 爆発音
    // ==================================================================================
    public void explosion() {
        explosion.play();
    }
}
