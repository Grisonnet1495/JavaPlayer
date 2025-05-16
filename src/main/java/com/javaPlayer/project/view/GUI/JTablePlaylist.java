package com.javaPlayer.project.view.GUI;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class JTablePlaylist extends JTable {
    public JTablePlaylist(TableModel model) {
        super(model);
        initUI();
    }

    private void initUI() {
        setRowHeight(30);
        setShowGrid(true);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setShowHorizontalLines(false);
        setShowVerticalLines(false);
        setIntercellSpacing(new Dimension(0, 0));
        setBorder(BorderFactory.createEmptyBorder());

        Font font = UIManager.getFont("Label.font").deriveFont(14f);
        setFont(font);

        setBackground(Color.WHITE);

        JTableHeader header = getTableHeader();
        getTableHeader().setFont(font.deriveFont(Font.BOLD, 14f));

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        if (getColumnModel().getColumnCount() > 0) {
            setColumnWidths();
        }
    }

    private void setColumnWidths() {
        int[] widths = {5, 300, 200, 100, 50, 100};
        for (int i = 0; i < widths.length && i < getColumnModel().getColumnCount(); i++) {
            TableColumn column = getColumnModel().getColumn(i);
            column.setPreferredWidth(widths[i]);
        }
    }

    @Override
    public void setModel(TableModel dataModel) {
        super.setModel(dataModel);

        SwingUtilities.invokeLater(this::setColumnWidths);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);

        if (isRowSelected(row)) {
            c.setBackground(new Color(160, 43, 147));
            c.setForeground(Color.WHITE);
        } else {
            c.setBackground(getBackground());
            c.setForeground(getForeground());
        }

        return c;
    }
}
