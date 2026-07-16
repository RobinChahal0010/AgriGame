package app;

import grid.FieldPatch;
import machinery.AutonomousTractor;
import models.Crop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final int GRID_SIZE = 3;
    private static final FieldPatch[][] farm = new FieldPatch[GRID_SIZE][GRID_SIZE];
    private static final JButton[][] gridButtons = new JButton[GRID_SIZE][GRID_SIZE];
    
    private static final AtomicInteger economyBalance = new AtomicInteger(100);
    
    // UI Components
    private static JLabel balanceLabel;
    private static JTextArea logArea;

    public static void main(String[] args) {
        // 1. Initialize Grid Data Matrix
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                farm[i][j] = new FieldPatch();
            }
        }

        // 2. Setup Graphical Window Layout (Swing Frame)
        JFrame frame = new JFrame("AgroCore: Autonomous Farming Simulation Engine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout(10, 10));

        // Top Status Panel (Header & Wallet Info)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(46, 125, 50)); // Dark Green Background
        JLabel titleLabel = new JLabel("  AGROCORE FARM SIMULATOR", JLabel.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        balanceLabel = new JLabel("Wallet Balance: $100  ", JLabel.RIGHT);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setForeground(Color.YELLOW);
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(balanceLabel, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);

        // Center Grid Panel (Farming Cells as Clickable Buttons)
        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 5, 5));
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                final int row = i;
                final int col = j;
                
                JButton btn = new JButton("[ . ] Empty");
                btn.setFont(new Font("Arial", Font.BOLD, 14));
                btn.setBackground(new Color(141, 110, 99)); // Soil Brown color
                btn.setForeground(Color.WHITE);
                
                // Click action to plant seed on the clicked box
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handlePlantAction(row, col);
                    }
                });
                
                gridButtons[i][j] = btn;
                gridPanel.add(btn);
            }
        }
        frame.add(gridPanel, BorderLayout.CENTER);

        // Right Event Logger Panel (Real-time tracking of tractor actions)
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setPreferredSize(new Dimension(250, 0));
        JLabel logTitle = new JLabel("System Logs / Alerts:", JLabel.CENTER);
        logTitle.setFont(new Font("Arial", Font.BOLD, 12));
        
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        logArea.setBackground(Color.BLACK);
        logArea.setForeground(Color.GREEN);
        JScrollPane scrollPane = new JScrollPane(logArea);
        
        logPanel.add(logTitle, BorderLayout.NORTH);
        logPanel.add(scrollPane, BorderLayout.CENTER);
        frame.add(logPanel, BorderLayout.EAST);

        // Render Frame
        frame.setLocationRelativeTo(null); // Center window on user screen
        frame.setVisible(true);

        // 3. Fire up Background Worker Threads (Same backend logic as before)
        AutonomousTractor tractorLogic = new AutonomousTractor(farm);
        Thread tractorThread = new Thread(tractorLogic);
        tractorThread.start();

        Thread natureThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    for (int i = 0; i < GRID_SIZE; i++) {
                        for (int j = 0; j < GRID_SIZE; j++) {
                            farm[i][j].updateGrowth();
                        }
                    }
                    // Trigger UI screen refresh synchronously on Swing thread
                    SwingUtilities.invokeLater(() -> refreshUiDisplay());
                    TimeUnit.SECONDS.sleep(2); // Speed up ticks to 2s for better UI engagement
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        natureThread.start();
        
        // Push initial text message to dashboard log
        logMessage("Simulation Engine Initialized.");
        logMessage("Tractor status: ACTIVE [ONLINE]");
    }

    private static void handlePlantAction(int row, int col) {
        if (economyBalance.get() < 20) {
            logMessage("❌ Error: Insufficient funds!");
            return;
        }

        FieldPatch patch = farm[row][col];
        synchronized (patch) {
            if (patch.getState() == FieldPatch.PatchState.PLOWED) {
                patch.plantCrop(new Crop("Wheat", 3, 50));
                economyBalance.addAndGet(-20);
                balanceLabel.setText("Wallet Balance: $" + economyBalance.get() + "  ");
                logMessage("🌱 Planted Wheat at [" + row + "," + col + "]");
                refreshUiDisplay();
            } else {
                logMessage("❌ Blocked: Crop needs PLOWED space.");
            }
        }
    }

    public static void addFunds(int amount) {
        economyBalance.addAndGet(amount);
        // Swing safe run to prevent GUI stuttering
        SwingUtilities.invokeLater(() -> {
            balanceLabel.setText("Wallet Balance: $" + economyBalance.get() + "  ");
        });
    }

    public static void logMessage(String msg) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(msg + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength()); // Auto Scroll to bottom
        });
    }

    private static void refreshUiDisplay() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                FieldPatch.PatchState s = farm[i][j].getState();
                JButton btn = gridButtons[i][j];
                
                if (s == FieldPatch.PatchState.EMPTY) {
                    btn.setText("[ . ] Unplowed Land");
                    btn.setBackground(new Color(141, 110, 99)); // Dark brown
                } else if (s == FieldPatch.PatchState.PLOWED) {
                    btn.setText("[ P ] Ready For Seeds");
                    btn.setBackground(new Color(109, 76, 65));  // Rich soft soil brown
                } else if (s == FieldPatch.PatchState.SEEDED) {
                    Crop c = farm[i][j].getCurrentCrop();
                    int progress = (c != null) ? c.getCurrentTicks() : 0;
                    btn.setText("[ S ] Growing (" + progress + "/3)");
                    btn.setBackground(new Color(251, 192, 45)); // Yellowish crop sprout tint
                } else if (s == FieldPatch.PatchState.READY_FOR_HARVEST) {
                    btn.setText("[ H ] Click to Auto-Harvest");
                    btn.setBackground(new Color(56, 142, 60));  // Harvest Green
                }
            }
        }
    }
}
