package projeto_poo2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;
import org.jfree.chart.ChartPanel;
import org.jfree.data.time.TimeSeries;

public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {        
        initComponents();
        for (Equity eq : apiHandler.getEquities()) {
            EquityMenuItem item = new EquityMenuItem(eq);
            item.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent ie) {
                    equityBtnClicked(ie);
                }
            });
            equitiesMenu.add(item);
        }
        
        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(!updater.isAlive()) {        // Checking if updater is not running, if it is, is  not necessary to call it again
                    runUpdateThread();
                }
            }
        };
        
        timer = new Timer(60000, task);
        timer.setRepeats(true);
        timer.start();
        
        alertPane.setLayout(new BoxLayout(alertPane, BoxLayout.Y_AXIS));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        graphsPanel = new javax.swing.JTabbedPane();
        alertPane = new javax.swing.JPanel();
        statusBar = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        equitiesMenu = new javax.swing.JMenu();
        optionsMenu = new javax.swing.JMenu();
        addEquityMenuItem = new javax.swing.JMenuItem();
        removeEquity = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        graphsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        alertPane.setDoubleBuffered(false);

        javax.swing.GroupLayout alertPaneLayout = new javax.swing.GroupLayout(alertPane);
        alertPane.setLayout(alertPaneLayout);
        alertPaneLayout.setHorizontalGroup(
            alertPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1173, Short.MAX_VALUE)
        );
        alertPaneLayout.setVerticalGroup(
            alertPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 666, Short.MAX_VALUE)
        );

        graphsPanel.addTab("Alertas", alertPane);

        statusBar.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        statusLabel.setText("Status");

        javax.swing.GroupLayout statusBarLayout = new javax.swing.GroupLayout(statusBar);
        statusBar.setLayout(statusBarLayout);
        statusBarLayout.setHorizontalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusBarLayout.createSequentialGroup()
                .addComponent(statusLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        statusBarLayout.setVerticalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusLabel)
        );

        equitiesMenu.setText("Ativos");
        menuBar.add(equitiesMenu);

        optionsMenu.setText("Opções");

        addEquityMenuItem.setText("Adicionar ativo");
        addEquityMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEquityMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(addEquityMenuItem);

        removeEquity.setText("Remover ativo");
        removeEquity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeEquityActionPerformed(evt);
            }
        });
        optionsMenu.add(removeEquity);

        menuBar.add(optionsMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(graphsPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(graphsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void addEquityMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEquityMenuItemActionPerformed
        statusLabel.setText("Adicionando ativo.");
        
        JTextField eqName = new JTextField();
        JTextField eqCode = new JTextField();
        
        Object[] message = {
            "Nome do ativo:", eqName,
            "Código do ativo:", eqCode
        };
        
        int option = JOptionPane.showConfirmDialog(this,
                                                   message,
                                                   "Adicionar ativo",
                                                   JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String name = eqName.getText();
            String code = eqCode.getText();
            Equity eq = new Equity(name, code);
            EquityMenuItem item = new EquityMenuItem(eq);
            item.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent ie) {
                    equityBtnClicked(ie);
                    
                }
            });
            equitiesMenu.add(item);
            
            statusLabel.setText("Ativo adicionado: " + name + " - " + code);
            
        } else {
            statusLabel.setText("Adição de ativo cancelada.");
            JOptionPane.showConfirmDialog(this,
                                          "Operação cancelada!",
                                          "Notificação",
                                          JOptionPane.CLOSED_OPTION);
        }
        
    }//GEN-LAST:event_addEquityMenuItemActionPerformed

    private void removeEquityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeEquityActionPerformed
        statusLabel.setText("Removendo ativo.");
        List<JRadioButton> buttons = new ArrayList();
        for (int i = 0; i < equitiesMenu.getItemCount(); i++) {
            EquityMenuItem item = (EquityMenuItem) equitiesMenu.getItem(i);
            buttons.add(new JRadioButton(item.getEquity().getName()));
        }
        
        int option = JOptionPane.showConfirmDialog(this,
                                                   buttons.toArray(),
                                                   "Adicionar ativo",
                                                   JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            for (JRadioButton btn : buttons) {
                if (btn.isSelected()) {
                    for (int i = 0; i < equitiesMenu.getItemCount(); i++) {
                        if (btn.getText().equals(equitiesMenu.getItem(i).getText())) {
                            statusLabel.setText("Ativo removido: " + btn.getText());
                            equitiesMenu.remove(i);
                        }
                    }
                }
            }
        } else {
            statusLabel.setText("Remoção de ativo cancelada.");
            JOptionPane.showConfirmDialog(this,
                                          "Operação cancelada!",
                                          "Notificação",
                                          JOptionPane.CLOSED_OPTION);
        }
    }//GEN-LAST:event_removeEquityActionPerformed

    private void runUpdateThread() { 
        // run one thread for each equity that needs to be updated
        for (int i = 0; i < equitiesMenu.getItemCount(); i++) {
            EquityMenuItem item = (EquityMenuItem) equitiesMenu.getItem(i);
            if (item.isSelected()) {
                updater = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        statusLabel.setText("Atualizando gráficos...");
                        updateChart(item);
                        statusLabel.setText("Grafico " + item.getText() + 
                                " atualizado! " + ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.SECONDS));  
                    }
                });
                updater.start();
            }
        }
    }
    
    
    private void updateChart(EquityMenuItem item) {
        System.out.println("Dealing with " + item.getEquity().getName());///////////////////////////////////////////////////////////////////////////
        TimeSeries sma;
        TimeSeries data;
        if (item.isSelected()) {    
            try {
                data = apiHandler.getIntradayData(item.getEquity(), 1);
            } catch (IOException ex) {
                System.err.println("Deu pau pra pegar os valores");
                return;
            }

            try {
                sma = apiHandler.getSMAIndicatorData(item.getEquity(), 1);
            } catch (IOException ex) {
                System.err.println("Deu pau pra pegar o sma");
                return;
            }

            item.getEquity().initChart(data, sma);


            boolean tabInGraphs = false;
            for (int j = 0; j < graphsPanel.getTabCount(); j++) {
                if (graphsPanel.getTitleAt(j).equals(item.getText())) {
                    tabInGraphs = true;

                    ChartPanel panel = (ChartPanel) graphsPanel.getComponentAt(j);
                    panel.setChart(item.getEquity().getChart()); 
                    panel.setDomainZoomable(false);
                    panel.setRangeZoomable(false);
                }
            }

            if (!tabInGraphs) {
                statusLabel.setText("Criando aba " + item.getText());
                ChartPanel chartPanel = new ChartPanel(item.getEquity().getChart());
                chartPanel.setDomainZoomable(false);
                chartPanel.setRangeZoomable(false);
                graphsPanel.addTab(item.getText(), chartPanel);
                graphsPanel.setSelectedIndex(graphsPanel.getTabCount()-1);
                statusLabel.setText("Aba " + item.getText() + " criada!");
            }
            //

            boolean contains = new ArrayList(Arrays.asList(alertPane.getComponents()))
                                             .contains(item.getEquity().getAlertPanel());

            if (!contains) {
                alertPane.add(item.getEquity().getAlertPanel());
            }
        }
    }
    
    private void equityBtnClicked(ItemEvent ie) {
        EquityMenuItem item = (EquityMenuItem) ie.getSource();
        
        if (item.isSelected()) {            
            new Thread(new Runnable() {
                @Override
                public void run() {
                    statusLabel.setText("Atualizando gráficos...");
                    updateChart(item);
                    statusLabel.setText("Grafico " + item.getText() + 
                            " atualizado! " + ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.SECONDS));  
                }
            }).start();
        } else {
            for (int i = 0; i < graphsPanel.getComponentCount(); i++) {
                if (graphsPanel.getTitleAt(i).equals(item.getText())) {
                    graphsPanel.remove(i);
                    break;
                }
            }
            
            alertPane.remove(item.getEquity().getAlertPanel());
        }        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    
    private Thread updater; 
    private final APIHandler apiHandler = new APIHandler("1624PUYB2HGRNQ9A");
    private Timer timer;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addEquityMenuItem;
    private javax.swing.JPanel alertPane;
    private javax.swing.JMenu equitiesMenu;
    private javax.swing.JTabbedPane graphsPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JMenuItem removeEquity;
    private javax.swing.JPanel statusBar;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
}
