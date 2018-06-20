/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_poo2;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class AlertPanel extends javax.swing.JPanel {

    /**
     * Panel for easy administration of alerts on Alert Panel of MainFrames
     */
    public AlertPanel(String name) {
        initComponents();
        equityName.setText(name);
        
        formatter = new DecimalFormat("#0.00");     
    }
    
    public void setBuy() {
        state.setText("COMPRAR");
        state.setBackground(Color.GREEN);
    }

    public void setSell() {
        state.setText("VENDER");
        state.setBackground(Color.RED);
    }
    
    public void setSMA(double sma) {
        this.smaDisplay.setText(formatter.format(sma));
    }
    
    public void setValue(double value) {
        this.valueDisplay.setText(formatter.format(value));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        state = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        equityName = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        smaDisplay = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        valueDisplay = new javax.swing.JTextPane();

        setMaximumSize(new java.awt.Dimension(312, 63));
        setMinimumSize(new java.awt.Dimension(312, 63));

        state.setEditable(false);
        state.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(state);

        equityName.setEditable(false);
        equityName.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane2.setViewportView(equityName);

        smaDisplay.setEditable(false);
        smaDisplay.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setViewportView(smaDisplay);

        jLabel1.setText("SMA:");

        jLabel2.setText("Valor:");

        valueDisplay.setEditable(false);
        valueDisplay.setBackground(new java.awt.Color(255, 255, 255));
        valueDisplay.setMaximumSize(new java.awt.Dimension(6, 21));
        valueDisplay.setName(""); // NOI18N
        jScrollPane4.setViewportView(valueDisplay);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane4)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private NumberFormat formatter;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane equityName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextPane smaDisplay;
    private javax.swing.JTextPane state;
    private javax.swing.JTextPane valueDisplay;
    // End of variables declaration//GEN-END:variables
}
