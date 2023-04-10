package com.luo.test;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.FontKerning;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestItextText {
    public static void main(String[] args) throws Exception {
        // 创建临时文件
        Path tempPdfPath = Files.createTempFile("paddingPdf-", ".pdf");;
        PdfReader pdfReader = new PdfReader("D:/领克用户中心接口.pdf");
        PdfWriter pdfWriter = new PdfWriter("D:/1234.pdf");
//        PdfWriter pdfWriter = new PdfWriter(tempPdfPath.toString());
        PdfDocument pdfDocument = new PdfDocument(pdfReader, pdfWriter);
        Canvas canvas = null;
        PdfCanvas pdfCanvas = null;
        int numberOfPages = pdfDocument.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            PdfPage page = pdfDocument.getPage(i);
            Rectangle mediaBox = page.getMediaBox();
            float x = mediaBox.getWidth();
            float y = mediaBox.getHeight();
            // 创建矩形坐标框
            Rectangle rectangle = new Rectangle(20, y - 80, 200, 30);

            // 创建pdf画布对象
            pdfCanvas = new PdfCanvas(pdfDocument, i);
            // 设置坐标矩形框
            pdfCanvas.rectangle(rectangle);
            // 生成画布
            canvas = new Canvas(pdfCanvas, rectangle);

            // 设置字体
            File file = new File("E:/IdeaProject/esign_6.0/esign-signs/signs-service/src/main/resources/fonts/simsun.ttc");
            PdfFont pdfFont = PdfFontFactory.createFont(file.getPath() + ",0", PdfEncodings.IDENTITY_H);

            // 段落的内容，字体，字体大小
            Paragraph paragraph =
                    new Paragraph("罗亮1234")
                            .setFont(pdfFont)
                            // 设置字号
                            .setFontSize(16)
                            // 设置字体颜色
                            .setFontColor(DeviceRgb.RED)
                            // 加粗
                            .setBold()
                            // 设置斜体
                            .setItalic()
                            // 设置对齐方式
//                            .setTextAlignment(TextAlignment.LEFT)
                            // 设置字间距
                            .setFontKerning(FontKerning.NO)
                            // 设置行间距
                            .setMultipliedLeading(1.0F)
                            // 设置外边距
                            .setMargin(0F);
            canvas.add(paragraph);
            pdfCanvas.release();
        }

        if (null != canvas) {
            canvas.close();
        }
        if (null != pdfDocument) {
            pdfDocument.close();
        }
        if (null != pdfWriter) {
            pdfWriter.close();
        }
        if (null != pdfReader) {
            pdfReader.close();
        }
    }
}

