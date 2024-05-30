package com.example.demofx1.Main;

import com.example.demofx1.Controller.HomeAdminController;
import com.example.demofx1.Data_Connection.ProductData;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GeneratePDF extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Random random = new Random();
            int number;
            number = random.nextInt(900) + 100;
            createPDF("invoice.pdf",number);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createPDF(String path,int random) throws IOException {
        try {
            PdfWriter pdfWriter = new PdfWriter(path);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            Document document = new Document(pdfDocument);

            // Lấy thời gian hiện tại ở múi giờ UTC
            LocalDateTime now = LocalDateTime.now();

            // Chuyển đổi sang múi giờ GMT+7
            ZoneId gmtPlus7 = ZoneId.of("GMT+7");
            ZonedDateTime gmtPlus7Time = ZonedDateTime.of(now, gmtPlus7);

            // Định dạng theo yêu cầu
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");

            // Áp dụng định dạng vào thời gian ở múi giờ GMT+7
            String formattedDateTime = gmtPlus7Time.format(formatter);

            // Áp dụng định dạng vào thời gian hiện tại
            float threecol = 190f;
            float twocol = 275f;
            float twocol150 = twocol + 150f;
            float[] twocolWidth = {twocol150, twocol};
            float[] fullWidth = {twocol * 3};
            float[] threeColWidth = {threecol, threecol, threecol};

            Paragraph onesp = new Paragraph("\n");

            // Tạo bảng chính
            Table table = new Table(twocolWidth);
            table.addCell(new Cell().add(new Paragraph("Invoice")).setBold());

            // Tạo bảng lồng nhau cho chi tiết hóa đơn
            Table nestedTable = new Table(2);
            nestedTable.addCell(new Cell().add(new Paragraph("Invoice No.")).setBold().setBorder(Border.NO_BORDER));
            nestedTable.addCell(new Cell().add(new Paragraph(String.valueOf(random))).setBold().setBorder(Border.NO_BORDER));
            nestedTable.addCell(new Cell().add(new Paragraph("Invoice Date")).setBold().setBorder(Border.NO_BORDER));
            nestedTable.addCell(new Cell().add(new Paragraph(String.valueOf(formattedDateTime))).setBold().setBorder(Border.NO_BORDER));

            // Thêm bảng lồng nhau vào bảng chính
            table.addCell(new Cell().add(nestedTable));

            // Thêm bảng chính vào tài liệu
            document.add(table);

            // Thêm dòng phân cách
            document.add(new Paragraph("\n"));
            Table divider = new Table(fullWidth);
            divider.setBorder(new SolidBorder(ColorConstants.GRAY, 0.5f));
            document.add(divider);

            Table divider2 = new Table(fullWidth);
            Border dashedGrayBorder = new DashedBorder(ColorConstants.GRAY, 0.5f);
            divider2.setBorder(dashedGrayBorder);
            document.add(divider2);

            // Thêm phần sản phẩm
            Paragraph productPara = new Paragraph("Products");
            document.add(productPara.setBold());

            Table threeColTable1 = new Table(threeColWidth);
            threeColTable1.setBackgroundColor(ColorConstants.GRAY, 0.5f);
            threeColTable1.addCell(new Cell().add(new Paragraph("Description")).setBold().setFontColor(ColorConstants.WHITE).setBorder(Border.NO_BORDER));
            threeColTable1.addCell(new Cell().add(new Paragraph("Quantity")).setBold().setFontColor(ColorConstants.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            threeColTable1.addCell(new Cell().add(new Paragraph("Price")).setBold().setFontColor(ColorConstants.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

            Table threeColTable2 = new Table(threeColWidth);

            // Thêm bảng sản phẩm vào tài liệu
            document.add(threeColTable1);

            String controllerName = "HomeAdminController";
            String fxmlPath = "/com/example/demofx1/FXML/" + controllerName.replace("Controller", "") + ".fxml";

            // Lấy URL của tệp FXML
            URL url = getClass().getResource(fxmlPath);
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            HomeAdminController controller = loader.getController();
            float total = controller.getTotalPrice();
            ObservableList<ProductData> productList = controller.productViewListData();

            for (ProductData productData : productList) {
                threeColTable2.addCell(new Cell().add(new Paragraph(productData.getName())).setBorder(Border.NO_BORDER).setMarginLeft(10f));
                threeColTable2.addCell(new Cell().add(new Paragraph(String.valueOf(productData.getQuantity()))).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                threeColTable2.addCell(new Cell().add(new Paragraph(String.valueOf(productData.getPrice()))).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
            }

            // Thêm bảng chi tiết sản phẩm vào tài liệu
            document.add(threeColTable2.setMarginBottom(20f));

            // Thêm tổng giá
            float[] totalWidth = {threecol * 2, threecol}; // Tạo bảng với hai cột
            Table totalTable = new Table(totalWidth);
            totalTable.addCell(new Cell().add(new Paragraph("Total:")).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));
            totalTable.addCell(new Cell().add(new Paragraph(String.valueOf(total)+"VND")).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
            document.add(totalTable);

            // Thêm dòng phân cách cuối
            document.add(divider2);

            // Đóng tài liệu
            document.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
