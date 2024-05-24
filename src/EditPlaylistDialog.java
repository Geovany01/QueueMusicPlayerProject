import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class EditPlaylistDialog extends JDialog {
    private MusicPlayerGUI musicPlayerGUI;
    private JPanel songContainer;

    public EditPlaylistDialog(MusicPlayerGUI musicPlayerGUI) {
        this.musicPlayerGUI = musicPlayerGUI;
        setTitle("Edit Playlist");
        setSize(300, 500);
        setResizable(false);
        getContentPane().setBackground(MusicPlayerGUI.FRAME_COLOR);
        setLayout(null);
        setModal(true);
        setLocationRelativeTo(musicPlayerGUI);
        initDialogComponents();
    }

    public void initDialogComponents() {
        songContainer = new JPanel();
        songContainer.setLayout(new BoxLayout(songContainer, BoxLayout.Y_AXIS));
        songContainer.setBounds((int) (getWidth() * 0.025), 10, (int) (getWidth() * 0.90), (int) (getHeight() * 0.65));
        add(songContainer);

        JButton addSongButton = new JButton("Add Song");
        addSongButton.setBounds(20, getHeight() - 120, 120, 30);
        addSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSongToPlaylist();
            }
        });
        add(addSongButton);

        JButton deleteSongButton = new JButton("Delete Song");
        deleteSongButton.setBounds(getWidth() - 140, getHeight() - 120, 120, 30);
        deleteSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSongFromPlaylist();
            }
        });
        add(deleteSongButton);

        JButton saveChangesButton = new JButton("Save Changes");
        saveChangesButton.setBounds(20, getHeight() - 80, getWidth() - 40, 30);
        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });
        add(saveChangesButton);

        displayPlaylist();
    }

    private void displayPlaylist() {
        songContainer.removeAll();
        if (musicPlayerGUI.musicPlayer.playlist != null) {
            for (Song song : musicPlayerGUI.musicPlayer.playlist) {
                JLabel songLabel = new JLabel(song.getSongTitle());
                songLabel.setFont(new Font("Dialog", Font.BOLD, 12));
                songLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                songContainer.add(songLabel);
            }
        }
        songContainer.revalidate();
        songContainer.repaint();
    }

    private void addSongToPlaylist() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("src/assets"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("MP3", "mp3"));

        int result = fileChooser.showOpenDialog(EditPlaylistDialog.this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            Song newSong = new Song(selectedFile.getPath());

            // Add the new song to the playlist at the top
            musicPlayerGUI.musicPlayer.playlist.add(0, newSong);

            // Update the displayed playlist
            displayPlaylist();
        }
    }

    private void deleteSongFromPlaylist() {
        if (!musicPlayerGUI.musicPlayer.playlist.isEmpty()) {
            // Remove the last song from the playlist
            musicPlayerGUI.musicPlayer.playlist.remove(musicPlayerGUI.musicPlayer.playlist.size() - 1);

            // Update the displayed playlist
            displayPlaylist();
        }
    }

    private void saveChanges() {
        // Save changes to the playlist file
        File playlistFile = new File(musicPlayerGUI.getCurrentPlaylistPath()); // Update with your playlist file path
        musicPlayerGUI.musicPlayer.savePlaylistToFile(playlistFile);
    }
}
