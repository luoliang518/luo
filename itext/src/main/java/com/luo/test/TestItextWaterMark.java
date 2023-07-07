package com.luo.test;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TestItextWaterMark {
    public static void main(String[] args) throws IOException {
        PdfReader pdfReader = new PdfReader("D:/领克用户中心接口.pdf");
        PdfWriter pdfWriter = new PdfWriter("D:/1234.pdf");
        PdfDocument outDocument = new PdfDocument(pdfWriter);
        PdfDocument redDocument = new PdfDocument(pdfReader);
        // 添加事件，该事件拥有添加水印
        WaterMarkHandler waterMarkHandler = new WaterMarkHandler("123");
        outDocument.addEventHandler(PdfDocumentEvent.INSERT_PAGE, waterMarkHandler);
        //获取总页数
        int numberOfPages = redDocument.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            PdfPage page = redDocument.getPage(i);

            //复制每页内容添加到新的文件中
            outDocument.addPage(page.copyTo(outDocument));
        }
        outDocument.close();
        redDocument.close();
        pdfReader.close();
    }

    /**
     * 监听事件 添加水印
     */
    protected static class WaterMarkHandler implements IEventHandler {

        private String waterMarkContent = "";

        public WaterMarkHandler(String str) {
            this.waterMarkContent = str;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent documentEvent = (PdfDocumentEvent) event;
            PdfDocument document = documentEvent.getDocument();
            PdfPage page = documentEvent.getPage();
            Rectangle pageSize = page.getPageSize();
            // 判断输入文字的长度
            JLabel label = new JLabel();
            label.setText(this.waterMarkContent);
            FontMetrics metrics = label.getFontMetrics(label.getFont());
            int textHeight = metrics.getHeight();
            int textWidth = metrics.stringWidth(label.getText());

            PdfFont pdfFont = null;
//            try {
//                // 将字体包拖到路径下
//                pdfFont = PdfFontFactory.createFont("E:/IdeaProject/esign_6.0/esign-signs/signs-service/src/main/resources/fonts/simsun.ttc" + ",0", PdfEncodings.IDENTITY_H,
//                        false);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            // 透明度
//            PdfExtGState gs = new PdfExtGState().setFillOpacity(0.4f);
//            PdfCanvas pdfCanvas = new PdfCanvas(page).setExtGState(gs);
//            //循环添加水印-posX表示横向起始位置，从左向右。posY表示纵向起始位置，从下到上。
//            for (float posX = 75f; posX < pageSize.getWidth(); posX = posX + textWidth * 3) {
//                for (float posY = 50f; posY < pageSize.getHeight(); posY = posY + textHeight * 4) {
//                    new Canvas(pdfCanvas, document, pageSize)
//                            .setFontColor(new DeviceRgb(255,105,180))   //颜色
//                            .setFontSize(15)                //字体大小
//                            .setFont(pdfFont)               //字体的格式   即导入的字体包
//                            //水印的内容（中英文都支持）    坐标（例如：250f, 250f）  当前页数     最后的值为倾斜度（19.5f）
//                            .showTextAligned(new Paragraph("罗亮123"),posX,posY, document.getPageNumber(page),
//                                    TextAlignment.CENTER, VerticalAlignment.MIDDLE, 19.5f);
//                }
//            }


            //Canvas对象过期了，可以使用Document对象
//            Document document1 = new Document(document)
//                    .setBackgroundColor(DeviceRgb.BLACK, 0.1f) //设置不透明度
//                    .setFont(pdfFont)   //设置字体
//                    .setFontColor(DeviceRgb.RED)  //字体颜色
//                    .setFontSize(20);   //字体大小
//            //循环添加水印-posX表示横向起始位置，从左向右。posY表示纵向起始位置，从下到上。
//            for (float posX = 75f; posX < pageSize.getWidth(); posX = posX + textWidth * 3) {
//                for (float posY = 50f; posY < pageSize.getHeight(); posY = posY + textHeight * 4) {
//                    /*document1.showTextAligned(new Paragraph(this.waterMarkContent), posX, posY, document.getPageNumber(page),
//                            TextAlignment.CENTER, VerticalAlignment.MIDDLE, 145);*/
//                    document1.showTextAligned(this.waterMarkContent, posX, posY, TextAlignment.CENTER, 145f);
//                }
//            }

        }
    }
}
