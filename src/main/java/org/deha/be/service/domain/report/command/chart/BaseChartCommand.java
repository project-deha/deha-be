package org.deha.be.service.domain.report.command.chart;

import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public abstract class BaseChartCommand {
    public byte[] chartToByteArray(JFreeChart chart, int width, int height) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(baos, chart, width, height);
        return baos.toByteArray();
    }

    public void addImageToDocument(WordprocessingMLPackage wordPackage, byte[] imageBytes, String altText) throws Exception {

        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();

        // Create image part
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordPackage, imageBytes);

        // Create inline image
        Inline inline = imagePart.createImageInline(
                null,           // filename hint
                altText,        // alt text
                1,              // id1
                2,              // id2
                false           // scale to fit
        );

        P paragraph =
                mainDocumentPart.createParagraphOfText("");

        R run = org.docx4j.jaxb.Context.getWmlObjectFactory().createR();

        Drawing drawing = org.docx4j.jaxb.Context.getWmlObjectFactory().createDrawing();

        drawing.getAnchorOrInline().add(inline);
        run.getContent().add(drawing);
        paragraph.getContent().add(run);

        // Add paragraph to document
        mainDocumentPart.getContent().add(paragraph);
    }

    public void addTextParagraph(WordprocessingMLPackage wordPackage, String text) throws Docx4JException {
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
        mainDocumentPart.addParagraphOfText(text);
    }
}
