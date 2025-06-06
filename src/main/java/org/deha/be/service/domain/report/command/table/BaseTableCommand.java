package org.deha.be.service.domain.report.command.table;

import jakarta.xml.bind.JAXBElement;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTableCommand {
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    protected void setTableData(Tbl table, String[][] data) {
        List<Object> rows = table.getContent();
        int rowIndex = 1; // Start from the first row after header
        int dataRowIndex = 0;

        while (rowIndex < rows.size() && dataRowIndex < data.length) {
            Tr row = (Tr) rows.get(rowIndex);
            fillRow(row, data[dataRowIndex]);
            rowIndex++;
            dataRowIndex++;
        }
    }

    protected void setCellText(Tc cell, String text) {
        if (cell.getContent().isEmpty()) {
            P p = new P();
            cell.getContent().add(p);
        }

        Object firstParaObj = cell.getContent().getFirst();
        P firstPara;

        if (firstParaObj instanceof JAXBElement) {
            firstPara = (P) ((JAXBElement<?>) firstParaObj).getValue();
        } else {
            firstPara = (P) firstParaObj;
        }

        firstPara.getContent().clear();

        R run = new R();
        Text textElement = new Text();
        textElement.setValue(text);
        run.getContent().add(textElement);
        firstPara.getContent().add(run);
    }

    protected void fillRow(Tr row, String[] cellValues) {
        List<Object> cells = row.getContent();

        for (int i = 0; i < Math.min(cells.size(), cellValues.length); i++) {
            Object cellObj = cells.get(i);
            Tc cell;

            if (cellObj instanceof JAXBElement) {
                cell = (Tc) ((JAXBElement<?>) cellObj).getValue();
            } else {
                cell = (Tc) cellObj;
            }

            // Set the text in the cell
            setCellText(cell, cellValues[i]);
        }
    }

    public List<Tbl> getDocxTables(MainDocumentPart documentPart) {
        List<Tbl> tableList = new ArrayList<>();

        List<Object> bodyElements = documentPart.getContent();

        for (Object obj : bodyElements) {
            if (obj instanceof Tbl) {
                tableList.add((Tbl) obj);
            } else if (obj instanceof JAXBElement &&
                    ((JAXBElement<?>) obj).getValue() instanceof Tbl) {
                tableList.add((Tbl) ((JAXBElement<?>) obj).getValue());
            }
        }

        return tableList;
    }
}
