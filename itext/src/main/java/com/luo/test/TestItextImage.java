package com.luo.test;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;

import java.io.IOException;

public class TestItextImage {
    public static void main(String[] args) throws IOException {
        PdfReader pdfReader = new PdfReader("D:/领克用户中心接口.pdf");
        PdfWriter pdfWriter = new PdfWriter("D:/1234.pdf");
        PdfDocument pdfDocument = new PdfDocument(pdfReader, pdfWriter);

        ImageData imageData = ImageDataFactory.create("C:/Users/18223/Pictures/Saved Pictures/v2-d29a9ed9425ed9aae1d78cd5e9f3a9f1_r.jpg");
        Image image = new Image(imageData);
        // 位置
        image.setFixedPosition(50, 40);
        // 水印倾斜
        image.setRotationAngle(0);
        // 图片缩放
        image.scale(0.1f,0.1f);
        // 透明度
        image.setOpacity(0.7f);
        Rectangle mediaBox = pdfDocument.getPage(1).getMediaBox();
        float y = mediaBox.getHeight();
        // 创建矩形坐标框
        Rectangle rectangle = new Rectangle(20, y - 80, 200, 30);
        PdfCanvas pdfCanvas = new PdfCanvas(pdfDocument.getPage(1));
        Canvas add = new Canvas(pdfCanvas, rectangle).add(image);
        pdfCanvas.release();

        if (null != add) {
            add.close();
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
