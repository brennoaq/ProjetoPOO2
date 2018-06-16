package projeto_poo2;

import javax.swing.JCheckBoxMenuItem;
/*
 * Just a class to use MenuItens from MenuBar as control array.
*/
public class EquityMenuItem extends JCheckBoxMenuItem {
    public EquityMenuItem(Equity eq) {
        super(eq.getName());
        this.eq = eq;
    }

    public Equity getEquity() {
        return eq;
    }

    public void setEq(Equity eq) {
        this.eq = eq;
    }
       
    // Properties
    private Equity eq;
}
