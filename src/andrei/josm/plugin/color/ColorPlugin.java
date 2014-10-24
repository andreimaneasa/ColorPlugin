package andrei.josm.plugin.color;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.MainMenu;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;

public class ColorPlugin extends Plugin {

    public ColorPlugin(final PluginInformation info) {
        super(info);
        MainMenu.add(Main.main.menu.moreToolsMenu, new ColorAction());
    }


}
