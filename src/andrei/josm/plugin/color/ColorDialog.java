package andrei.josm.plugin.color;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.command.ChangePropertyCommand;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.gui.ExtendedDialog;

@SuppressWarnings("serial")
public class ColorDialog extends ExtendedDialog implements ActionListener{

    String value;
    Collection<OsmPrimitive> globalSelectWall;
    Collection<OsmPrimitive> globalSelectRoof;

    private String global; 

    private String codHexa;

    String numar;

    public String getCodHexa() {
        return codHexa;
    }

    public void setCodHexa(String codHexa) {
        this.codHexa = codHexa;
    }

    private static final String[] BUTTON_TEXTS = new String[] { //tr("OK"),
        tr("Cancel") };


    private static final String[] BUTTON_ICONS = new String[] { //"ok.png",
    "cancel.png" };

    String hexaCode;

    JFormattedTextField myOutput =new JFormattedTextField(createFormatter("*HHHHHH"));;


    public void setInputWidth(){
        myOutput.setColumns(10); 
    }

    //A convenience method for creating a MaskFormatter.
    protected MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

    public void inserare ( JPanel jp , String hexaCode){
        setInputWidth();
        myOutput.setText(hexaCode);
        jp.add(myOutput);
        repaint();
    }

    JButton button2 = new JButton("Add Attribute");
    JButton button1 = new JButton("Choose Color");

    public ColorDialog() {

        super(Main.parent, tr("Color Picker"),BUTTON_TEXTS);
        setCancelButton(2);

        setButtonIcons(BUTTON_ICONS);

        final JPanel jp =  new JPanel(); 

        button1.setSize(50, 50);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(jp, "Choose a color", Color.red);

                if (color != null){
                    hexaCode = toRGBCode(color);
                }
                ColorDialog colorDialog = new ColorDialog();

                colorDialog.setCodHexa(hexaCode);

                inserare(jp, hexaCode); // show value after click OK from colorChooser

                global=colorDialog.getCodHexa();

            }
        });

        jp.add(button1);

        JLabel l = new JLabel("Hex must contain #");// add text to panel
        jp.add(l);

        inserare(jp,global); // show input after Choose color

        button2.setSize(50, 50);

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                value= insertMaxSevenCharaters(e); // get value from input

                Collection<OsmPrimitive> sel;
                sel = Main.main.getInProgressSelection(); // objects selected in 2D

                if (sel!=null){
                    String key = "colour"; // set attribute 
                    Main.main.undoRedo.add(new ChangePropertyCommand(sel, key, value)); //  add values
                    dispose(); //after click on button close panel
                }
            }
        });

        jp.add(button2);
        setContent(jp); 
    }

    /*
     * return what I select from 3D
     * for walls 
     */
    public Collection<OsmPrimitive> performTagAddingForWall(Collection<OsmPrimitive> sel) {

        globalSelectWall = sel;

        return sel;
    }

    /*
     * return what I select from 3D
     * for roof 
     */
    public Collection<OsmPrimitive> performTagAddingForRoof(Collection<OsmPrimitive> sel) {

        globalSelectRoof = sel;

        return sel;
    }

    public static String toRGBCode( Color c )
    {
        String red= Integer.toHexString( c.getRed() & 0xFFFFFF); 
        String green= Integer.toHexString( c.getGreen() & 0xFFFFFF); 
        String blue= Integer.toHexString( c.getBlue() & 0xFFFFFF); 

        if (red.length() < 2) {
            red= "0" + red;
        }

        if (green.length() < 2) {
            green= "0" + green;
        }

        if (blue.length() < 2) {
            blue= "0" +blue;
        }
        String hexa = red+green+blue;

        String hex = "#" + hexa;

        return hex ;
    }

    public boolean checkHex (){
        boolean diez = value.startsWith("#");
        boolean isHex = value.matches("^[#0-9A-Fa-f]+$");

        boolean t=false;

        if (diez&& isHex){
            t=true;
        }
        return t;
    }

    /*
     * method cut the text if is longer than 7 characters, allows first 7 characters
     */
    private String insertMaxSevenCharaters(ActionEvent evt)
    {
        if(myOutput.getText().length()>=7)
        {
            myOutput.setText(myOutput.getText().substring(0, 7));

        }
        return myOutput.getText().substring(0, 7);
    }

    @Override
    public void actionPerformed(ActionEvent evt) { 
    }

}
