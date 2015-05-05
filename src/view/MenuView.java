package view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * Menü panel
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class MenuView extends JPanel {

    public static final Color PLAYER_1_COLOR = new java.awt.Color(255, 153, 153);
    public static final Color PLAYER_2_COLOR = new java.awt.Color(153, 255, 153);
    public static final Color PLAYER_3_COLOR = new java.awt.Color(153, 255, 204);
    public static final Color PLAYER_4_COLOR = new java.awt.Color(255, 255, 153);
    public static final Color PLAYER_5_COLOR = new java.awt.Color(204, 153, 255);
    public static final Color PLAYER_6_COLOR = new java.awt.Color(204, 204, 204);

    public static final Color[] PLAYER_COLORS = {
            PLAYER_1_COLOR,
            PLAYER_2_COLOR,
            PLAYER_3_COLOR,
            PLAYER_4_COLOR,
            PLAYER_5_COLOR,
            PLAYER_6_COLOR
    };

    /**
     * Háttérkép
     */
    private BufferedImage background;

    /**
     * Kiválasztott pálya neve (kiterjesztéssel együtt)
     */
    String selectedMap;

    /**
     * A körök számát itt lehet beállítani
     **/
    int numberOfRounds;

    ArrayList<String> players = new ArrayList<String>();
    ArrayList<String> trackListArray;

    private JTextField player1Field;
    private JLabel player1Label;
    private JTextField player2Field;
    private JLabel player2Label;
    private JTextField player3Field;
    private JLabel player3Label;
    private JTextField player4Field;
    private JLabel player4Label;
    private JTextField player5Field;
    private JLabel player5Label;
    private JTextField player6Field;
    private JLabel player6Label;
    private JLabel roundsLabel;
    private JSpinner roundsSpinner;
    private JButton startButton;
    private JComboBox trackList;
    private JLabel tracksLabel;

    public MenuView() {
        initComponents();

        // háttérkép beolvasása
        /*try {
            background = ImageIO.read(new File("assets/img/menu_background_alternative.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    private void initComponents() {

        trackList = new JComboBox();
        tracksLabel = new JLabel();
        roundsSpinner = new JSpinner();
        roundsLabel = new JLabel();
        startButton = new JButton();
        player1Field = new JTextField();
        player1Label = new JLabel();
        player2Label = new JLabel();
        player2Field = new JTextField();
        player3Label = new JLabel();
        player3Field = new JTextField();
        player4Label = new JLabel();
        player4Field = new JTextField();
        player5Label = new JLabel();
        player5Field = new JTextField();
        player6Label = new JLabel();
        player6Field = new JTextField();

        trackListArray = new ArrayList<String>(Arrays.asList(GameController.getAvailableTrackNames()));

        if(trackListArray.size() == 0) {
            trackListArray.add("No track file");
        }
        trackList= new JComboBox<String>(new Vector<String>(trackListArray));

        tracksLabel.setLabelFor(trackList);
        tracksLabel.setText("Tracks");

        roundsSpinner.setModel(new SpinnerNumberModel(20, 20, 40, 1));
        roundsSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                numberOfRounds = (Integer) roundsSpinner.getValue();
            }
        });

        roundsLabel.setLabelFor(roundsSpinner);
        roundsLabel.setText("Rounds");

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        player1Label.setText("Player 1");
        player2Label.setText("Player 2");
        player3Label.setText("Player 3");
        player4Label.setText("Player 4");
        player5Label.setText("Player 5");
        player6Label.setText("Player 6");

        player1Field.setBackground(PLAYER_1_COLOR);
        player1Field.setHorizontalAlignment(JTextField.CENTER);
        player1Field.setToolTipText("");

        player2Field.setBackground(PLAYER_2_COLOR);
        player2Field.setHorizontalAlignment(JTextField.CENTER);
        player2Field.setToolTipText("");

        player3Field.setBackground(PLAYER_3_COLOR);
        player3Field.setHorizontalAlignment(JTextField.CENTER);
        player3Field.setToolTipText("");

        player4Field.setBackground(PLAYER_4_COLOR);
        player4Field.setHorizontalAlignment(JTextField.CENTER);
        player4Field.setToolTipText("");

        player5Field.setBackground(PLAYER_5_COLOR);
        player5Field.setHorizontalAlignment(JTextField.CENTER);
        player5Field.setToolTipText("");

        player6Field.setBackground(PLAYER_6_COLOR);
        player6Field.setHorizontalAlignment(JTextField.CENTER);
        player6Field.setToolTipText("");

        GroupLayout menuViewLayout = new GroupLayout(this);
        setLayout(menuViewLayout);
        menuViewLayout.setHorizontalGroup(
                menuViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(menuViewLayout.createSequentialGroup()
                                .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addGroup(menuViewLayout.createSequentialGroup()
                                                        .addComponent(roundsLabel)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(roundsSpinner, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE))
                                                .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(menuViewLayout.createSequentialGroup()
                                                                .addGap(30, 30, 30)
                                                                .addComponent(tracksLabel)
                                                                .addGap(3, 3, 3)
                                                                .addComponent(trackList, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGroup(menuViewLayout.createSequentialGroup()
                                                                .addGap(24, 24, 24)
                                                                .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(menuViewLayout.createSequentialGroup()
                                                                                .addComponent(player1Label)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(player1Field, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                                                .addGroup(menuViewLayout.createSequentialGroup()
                                                                                        .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                                                .addComponent(player3Label)
                                                                                                .addComponent(player2Label))
                                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(player2Field, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(player3Field)))
                                                                                .addGroup(GroupLayout.Alignment.LEADING, menuViewLayout.createSequentialGroup()
                                                                                        .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(player4Label)
                                                                                                .addComponent(player5Label)
                                                                                                .addComponent(player6Label))
                                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(player6Field)
                                                                                                .addComponent(player5Field)
                                                                                                .addComponent(player4Field, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE))))))))
                                        .addGroup(menuViewLayout.createSequentialGroup()
                                                .addGap(120, 120, 120)
                                                .addComponent(startButton)))
                                .addContainerGap(27, Short.MAX_VALUE))
        );
        menuViewLayout.setVerticalGroup(
                menuViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(menuViewLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(trackList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tracksLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(roundsSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(roundsLabel))
                                .addGap(18, 18, 18)
                                .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(player1Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(player1Label))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(player2Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(player2Label))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(player3Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(player3Label))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(player4Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(player4Label))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(player5Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(player5Label))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(menuViewLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(player6Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(player6Label))
                                .addGap(18, 18, 18)
                                .addComponent(startButton)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Háttérkép rajzolása
        g.drawImage(background, 0, 0, null);
    }

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        ArrayList<String> maps = new ArrayList<String>(Arrays.asList(GameController.getAvailableTracks()));
        int index = trackListArray.indexOf(trackList.getSelectedItem());
        selectedMap = maps.get(index);
        new GameController(selectedMap, players, numberOfRounds);
    }
}
