import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SeeMusicPlaylistDialog extends JDialog {
    private MusicPlayerGUI musicPlayerGUI;

    public SeeMusicPlaylistDialog(MusicPlayerGUI musicPlayerGUI){
        this.musicPlayerGUI = musicPlayerGUI;

        // configure dialog
        setTitle("See Playlist");
        setSize(300, 400);
        setResizable(false);
        getContentPane().setBackground(MusicPlayerGUI.FRAME_COLOR);
        setLayout(null);
        setModal(true); // this property makes it so that the dialog has to be closed to give focus
        setLocationRelativeTo(musicPlayerGUI);

        seeDialogComponents();
    }

    public void seeDialogComponents(){
        // container to show de current playlist
        JPanel songContainer = new JPanel();
        songContainer.setLayout(new BoxLayout(songContainer, BoxLayout.Y_AXIS));
        songContainer.setBounds((int)(getWidth() * 0.025), 10, (int)(getWidth() * 0.90), (int) (getHeight() * 0.75));
        add(songContainer);

        ArrayList<Song> miLista = SeeMusicPlaylistDialog.this.musicPlayerGUI.musicPlayer.playlist;
        Song current_song = SeeMusicPlaylistDialog.this.musicPlayerGUI.musicPlayer.getCurrentSong();

        for (Song song : miLista) {
            if(song == current_song) {
                JLabel filePathLabel = new JLabel(song.getSongTitle() + " <-- Current Song");
                filePathLabel.setFont(new Font("Dialog", Font.BOLD, 12));
                filePathLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                // add to container
                songContainer.add(filePathLabel);
            } else {
                JLabel filePathLabel = new JLabel(song.getSongTitle());
                filePathLabel.setFont(new Font("Dialog", Font.BOLD, 12));
                filePathLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                // add to container
                songContainer.add(filePathLabel);
            }
            songContainer.revalidate();
        }
    }
}
