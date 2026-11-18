package de.femtopedia.legacyforgej8.transformers.asm4warn;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

// This class needs to be compiled with an older Java release (e.g., Java 7).
// Otherwise, it cannot get loaded in ASM 4.
public class Warnings extends JOptionPane {

    private JDialog container;

    private Box makeMessagePanel() {
        Box box = Box.createVerticalBox();
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel welcomeLabel = new JLabel(
                "<html><b><font size='+1'>" +
                "LegacyForgeJ8 needs additional setup!" +
                "</font></b></html>");
        box.add(welcomeLabel);
        welcomeLabel.setAlignmentY(0.0F);
        welcomeLabel = new JLabel(
                "<html>" +
                "In order for LegacyForgeJ8 to work properly in this version of Minecraft,<br>" +
                "you need to follow the additional installation instructions on Modrinth!" +
                "</html>");
        welcomeLabel.setAlignmentY(0.0F);
        box.add(welcomeLabel);
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        return box;
    }

    public JDialog makeDialog() {
        setMessageType(WARNING_MESSAGE);
        setMessage(makeMessagePanel());
        setOptions(new Object[]{"Open Link", "Continue"});
        addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getSource() == Warnings.this && "value".equals(evt.getPropertyName())) {
                    if (evt.getNewValue().equals("Open Link")) {
                        open(URI.create("https://modrinth.com/mod/legacyforgej8"));
                    } else {
                        Warnings.this.container.dispose();
                    }
                }
            }
        });
        container = new JDialog((Frame) null, "This will not work...", true);
        container.setModal(true);
        container.setResizable(false);
        container.setLocationRelativeTo(null);
        container.add(this);
        updateUI();
        container.pack();
        container.setMinimumSize(container.getPreferredSize());
        container.setVisible(true);
        container.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        return container;
    }

    private static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException ignored) {
            }
        }
    }

    private static boolean isASM4(ClassLoader classLoader) {
        try {
            Class<?> Opcodes = Class.forName("org.objectweb.asm.Opcodes", false, classLoader);
            Field Opcodes_ASM5 = Opcodes.getField("ASM5");
            return Opcodes_ASM5.getInt(null) != 327680;
        } catch (Throwable ignored) {
        }
        return true;
    }

    private static boolean warned = false;

    public static void warnIfNeeded(ClassLoader classLoader) {
        if (warned) return;
        warned = true;

        if (isASM4(classLoader)) {
            System.err.println(
                    "==============================================================================");
            System.err.println(
                    "================================== WARNING ===================================");
            System.err.println(
                    "==============================================================================");
            System.err.println(
                    "LegacyForgeJ8 needs additional setup!");
            System.err.println();
            System.err.println(
                    "In order for LegacyForgeJ8 to work properly in this version of Minecraft,");
            System.err.println(
                    "you need to follow the additional installation instructions on Modrinth!");
            System.err.println(
                    "https://modrinth.com/mod/legacyforgej8");
            System.err.println(
                    "==============================================================================");
            System.err.println(
                    "================================== WARNING ===================================");
            System.err.println(
                    "==============================================================================");

            new Warnings().makeDialog();
        }
    }

}
