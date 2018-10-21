/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Framework.Camera;
import Framework.Game;
import Framework.Main;

/**
 *
 * @author Joseph
 */
public class OptionsMenu extends javax.swing.JFrame {

    /**
     * Creates new form OptionsMenu
     */
    public OptionsMenu() {
        initComponents();
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        renderDelayLabel = new javax.swing.JLabel();
        tickPerSecondLabel = new javax.swing.JLabel();
        TickRateSpinner = new javax.swing.JSpinner();
        RenderDelaySpinner = new javax.swing.JSpinner();
        OverviewLabel = new javax.swing.JLabel();
        overviewCheckbox = new javax.swing.JCheckBox();
        okButton = new javax.swing.JButton();
        lowSpecButton = new javax.swing.JButton();
        standardButton = new javax.swing.JButton();
        disableCamLabel = new javax.swing.JLabel();
        disableCamCheck = new javax.swing.JCheckBox();
        debugLabel = new javax.swing.JLabel();
        debugCheck = new javax.swing.JCheckBox();
        tripleLabel = new javax.swing.JLabel();
        tripleCheck = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Options");
        setAlwaysOnTop(true);
        setResizable(false);

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        titleLabel.setText("ENGINE OPTIONS");

        renderDelayLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        renderDelayLabel.setText("Render Delay (ms)");
        renderDelayLabel.setToolTipText("Increase to help performance");

        tickPerSecondLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tickPerSecondLabel.setText("Tick Rate");
        tickPerSecondLabel.setToolTipText("Ticks Per Second");

        TickRateSpinner.setToolTipText("Ticks Per Second");
        TickRateSpinner.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        TickRateSpinner.setName(""); // NOI18N
        TickRateSpinner.setValue(Game.ticksPerSecond);

        RenderDelaySpinner.setToolTipText("Increase to help performance");
        RenderDelaySpinner.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        RenderDelaySpinner.setName(""); // NOI18N
        RenderDelaySpinner.setValue(Main.renderDelay);

        OverviewLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        OverviewLabel.setText("Overview Mode");
        OverviewLabel.setToolTipText("Ticks Per Second");

        overviewCheckbox.setText("Enabled");
        overviewCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                overviewCheckboxActionPerformed(evt);
            }
        });

        okButton.setText("APPLY");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        lowSpecButton.setText("Low Spec Preset (slow)");
        lowSpecButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowSpecButtonActionPerformed(evt);
            }
        });

        standardButton.setText("Standard Spec Preset");
        standardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                standardButtonActionPerformed(evt);
            }
        });

        disableCamLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        disableCamLabel.setText("Disable Camera Movement");
        disableCamLabel.setToolTipText("Ticks Per Second");

        disableCamCheck.setText("Disabled");
        disableCamCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disableCamCheckActionPerformed(evt);
            }
        });

        debugLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        debugLabel.setText("Debug Mode");
        debugLabel.setToolTipText("Ticks Per Second");

        debugCheck.setText("Enabled");
        debugCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debugCheckActionPerformed(evt);
            }
        });

        tripleLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tripleLabel.setText("Triple Buffer");
        tripleLabel.setToolTipText("Disable to help performance");

        tripleCheck.setSelected(Main.tripleBuffer);
        tripleCheck.setText("Enabled");
        tripleCheck.setToolTipText("disable to help performance");
        tripleCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tripleCheckActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tickPerSecondLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(TickRateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(renderDelayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(RenderDelaySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tripleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(tripleCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(39, 39, 39)
                                .addComponent(okButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(OverviewLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(disableCamLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(debugLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(debugCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(disableCamCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(overviewCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(lowSpecButton)
                                .addGap(18, 18, 18)
                                .addComponent(standardButton))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(renderDelayLabel)
                            .addComponent(RenderDelaySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tickPerSecondLabel)
                            .addComponent(TickRateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tripleLabel)
                    .addComponent(tripleCheck))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lowSpecButton)
                    .addComponent(standardButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OverviewLabel)
                    .addComponent(overviewCheckbox))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(disableCamLabel)
                    .addComponent(disableCamCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(debugLabel)
                    .addComponent(debugCheck))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        TickRateSpinner.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void lowSpecButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowSpecButtonActionPerformed
       this.RenderDelaySpinner.setValue(11);
       this.TickRateSpinner.setValue(50);
       this.tripleCheck.setSelected(false);
    }//GEN-LAST:event_lowSpecButtonActionPerformed

    private void standardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_standardButtonActionPerformed
        this.RenderDelaySpinner.setValue(0);
       this.TickRateSpinner.setValue(60);
       this.tripleCheck.setSelected(true);
    }//GEN-LAST:event_standardButtonActionPerformed

    private void overviewCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_overviewCheckboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_overviewCheckboxActionPerformed

    private void disableCamCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disableCamCheckActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_disableCamCheckActionPerformed

    private void debugCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debugCheckActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_debugCheckActionPerformed

    private void tripleCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tripleCheckActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tripleCheckActionPerformed

    public static void main(String[] args) {
        OptionsMenu test = new OptionsMenu();
    }
    
    @Override
    public void dispose(){
        super.dispose();
        Game.ticksPerSecond =(int)this.TickRateSpinner.getValue();
        Main.renderDelay = (int)this.RenderDelaySpinner.getValue();
        Main.overviewMode = this.overviewCheckbox.isSelected();
        Camera.disableMovement = this.disableCamCheck.isSelected() || overviewCheckbox.isSelected();
        Main.debugMode = this.debugCheck.isSelected();
        Main.tripleBuffer = this.tripleCheck.isSelected();
        new Game(); // start the game
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel OverviewLabel;
    private javax.swing.JSpinner RenderDelaySpinner;
    private javax.swing.JSpinner TickRateSpinner;
    private javax.swing.JCheckBox debugCheck;
    private javax.swing.JLabel debugLabel;
    private javax.swing.JCheckBox disableCamCheck;
    private javax.swing.JLabel disableCamLabel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton lowSpecButton;
    private javax.swing.JButton okButton;
    private javax.swing.JCheckBox overviewCheckbox;
    private javax.swing.JLabel renderDelayLabel;
    private javax.swing.JButton standardButton;
    private javax.swing.JLabel tickPerSecondLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JCheckBox tripleCheck;
    private javax.swing.JLabel tripleLabel;
    // End of variables declaration//GEN-END:variables
}
