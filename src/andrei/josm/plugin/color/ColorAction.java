// License: GPL. Copyright 2014 by Andrei Maneasa 
package andrei.josm.plugin.color;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collection;

import javax.swing.JOptionPane;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.tools.I18n;
import org.openstreetmap.josm.tools.Shortcut;


@SuppressWarnings("serial")

public class ColorAction extends JosmAction {

    public ColorAction() {
        super(tr("Color "), "dialogs/Color.png", tr("Choose or set a color for objects."),
                Shortcut.registerShortcut("tools:Color", tr("Tool: {0}", tr("Color Plugin")), KeyEvent.VK_Y, Shortcut.CTRL_SHIFT),
                true, "Color", true);

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        try
        {
            Collection<OsmPrimitive> selectedPrimitive = Main.main.getCurrentDataSet().getSelectedNodesAndWays();
            if (selectedPrimitive.size() != 1)
            {
                JOptionPane.showMessageDialog(null, I18n.tr("Choose one node or way", new Object[0]), "Error", 0);

                return;
            }
            else {
                new ColorDialog().showDialog();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    protected void updateEnabledState()
     {
         setEnabled(getCurrentDataSet() != null);
      }

}
