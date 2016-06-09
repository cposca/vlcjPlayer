package vlcjPlayer;

import java.awt.EventQueue;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui {
////////////////////////////////////////////////////////////////////
//VARIABLES/////////////////////////////////////////////////////////	
////////////////////////////////////////////////////////////////////	
	private JFrame frame;
	private AudioMediaPlayerComponent m;
	private boolean ppToggle = true;
	private JButton ppButton;
////////////////////////////////////////////////////////////////////
//RUNNABLES/////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////
//MAIN//////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Gui() {
		initialize();
	}
////////////////////////////////////////////////////////////////////
//GUI///////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 102);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[146.00,grow][]", "[]"));
////////////////////////////////////////////////////////////////////
//GUI>MEDIA PLAYER//////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////
m = new AudioMediaPlayerComponent();
String media = "/home/chris/daydreaming.mp3";
////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////
		JPanel seekInfoPanel = new JPanel();
		TitledBorder seekInfoPanelBorder = new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null);
		seekInfoPanel.setBorder(seekInfoPanelBorder);
		frame.getContentPane().add(seekInfoPanel, "cell 0 0,grow");
		seekInfoPanel.setLayout(new MigLayout("", "[146.00,grow]", "[]"));
		
		JSlider seekSlider = new JSlider();
		seekSlider.setMaximum((int) m.getMediaPlayer().getLength());
		seekInfoPanel.add(seekSlider, "cell 0 0");
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, "flowx,cell 1 0");
		
		JButton button = new JButton("<");
		panel.add(button);
				
		JButton ppButton = new JButton("|>");
		ppButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!m.getMediaPlayer().isPlaying() && ppToggle)
				{
					m.getMediaPlayer().playMedia(media);
				}
				else if(!m.getMediaPlayer().isPlaying() && !ppToggle)
				{
					m.getMediaPlayer().play();
				}
				else{
					if(m.getMediaPlayer().canPause())
					{
						m.getMediaPlayer().pause();
						ppToggle = false;
					}
				}
			}
		});
		panel.add(ppButton);
		
		JButton btnNewButton = new JButton("[]");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					m.getMediaPlayer().stop();
					ppToggle = true ;
			}
		});
		panel.add(btnNewButton);
		
		JButton button_2 = new JButton(">");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.getMediaPlayer().skip(10000);
			}
		});
		panel.add(button_2);
		
		m.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter(){
			@Override
			public void paused(MediaPlayer mediaPlayer)
			{
				SwingUtilities.invokeLater(	new Runnable(){
					public void run(){
						ppButton.setText("|>");
					}
				});
			};
			public void stopped(MediaPlayer mediaPlayer)
			{
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						ppButton.setText("|>");
					}
				});
			}
			public void finished(MediaPlayer mediaPlayer)
			{
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						ppButton.setText("|>");
					}
				});
			}
			public void playing(MediaPlayer mediaPlayer)
			{
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						ppButton.setText("||");

					}
				});
			}
			public void timeChanged(MediaPlayer mediaPlayer, long time)
			{
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						seekInfoPanelBorder.setTitle(m.getMediaPlayer().getTime() + "");
					}
				});
			}
		});
	}
}
