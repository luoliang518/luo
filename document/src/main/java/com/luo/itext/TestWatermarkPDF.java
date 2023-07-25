package com.luo.itext;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfAnnotationAppearance;
import com.itextpdf.kernel.pdf.annot.PdfFixedPrint;
import com.itextpdf.kernel.pdf.annot.PdfWatermarkAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;

import java.io.IOException;

public class TestWatermarkPDF {
    public static void main(String[] args) throws IOException {
        WatermarkPDF("D:/领克用户中心接口.pdf","D:/1234.pdf");
    }
    public static void WatermarkPDF(String sourceFile, String destinationPath) throws IOException, IOException {
        float watermarkTrimmingRectangleWidth = 300;
        float watermarkTrimmingRectangleHeight = 300;

        float formWidth = 300;
        float formHeight = 300;
        float formXOffset = 0;
        float formYOffset = 0;

        float xTranslation = 50;
        float yTranslation = 25;

        double rotationInRads = Math.PI/3;

//        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        float fontSize = 50;

        PdfDocument pdfDoc = new PdfDocument(new PdfReader(sourceFile), new PdfWriter(destinationPath));
        int numberOfPages = pdfDoc.getNumberOfPages();
        PdfPage page = null;

        for (int i = 1; i <= numberOfPages; i++)
        {
            page = pdfDoc.getPage(i);
            Rectangle ps = page.getPageSize();

            //Center the annotation
            float bottomLeftX = ps.getWidth()/2 - watermarkTrimmingRectangleWidth/2;
            float bottomLeftY = ps.getHeight()/2 - watermarkTrimmingRectangleHeight/2;
            Rectangle watermarkTrimmingRectangle = new Rectangle(bottomLeftX, bottomLeftY, watermarkTrimmingRectangleWidth, watermarkTrimmingRectangleHeight);

            PdfWatermarkAnnotation watermark = new PdfWatermarkAnnotation(watermarkTrimmingRectangle);

            //Apply linear algebra rotation math
            //Create identity matrix
            AffineTransform transform = new AffineTransform();//No-args constructor creates the identity transform
            //Apply translation
            transform.translate(xTranslation, yTranslation);
            //Apply rotation
            transform.rotate(rotationInRads);

            PdfFixedPrint fixedPrint = new PdfFixedPrint();
            watermark.setFixedPrint(fixedPrint);
            //Create appearance
            Rectangle formRectangle = new Rectangle(formXOffset, formYOffset, formWidth, formHeight);

            //Observation: font XObject will be resized to fit inside the watermark rectangle
            PdfFormXObject form = new PdfFormXObject(formRectangle);
            PdfExtGState gs1 = new PdfExtGState().setFillOpacity(0.1f);
            PdfCanvas canvas = new PdfCanvas(form, pdfDoc);

            float[] transformValues = new float[6];
            transform.getMatrix(transformValues);
            canvas.saveState()
                    .beginText().setColor(ColorConstants.GRAY, true).setExtGState(gs1)
                    .setTextMatrix(transformValues[0], transformValues[1], transformValues[2], transformValues[3], transformValues[4], transformValues[5])
//                    .setFontAndSize(font, fontSize)
                    .showText("watermark text")
                    .endText()
                    .restoreState();
            canvas.release();
            watermark.setAppearance(PdfName.N, new PdfAnnotationAppearance(form.getPdfObject()));
            watermark.setFlags(PdfAnnotation.PRINT);

            page.addAnnotation(watermark);

        }
        page.flush();
    }
}
