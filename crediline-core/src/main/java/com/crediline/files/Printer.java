package com.crediline.files;

import com.crediline.files.fiscalprinter.PrinterFileCreator;
import com.crediline.files.print.PDFFileCreator;
import com.crediline.model.Document;
import com.crediline.model.Payment;
import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component("printer")
@Scope("prototype")
public class Printer implements Serializable {
    private static final long serialVersionUID = -6842673224263402844L;
    private static final String PRINTER_CONTAINER = "applet_container";
    private static final String PRINTER_DIRECTORY = "crediline_printer";

    private PrinterFileCreator printerFileCreator;

    public Printer() {
        printerFileCreator = new PrinterFileCreator();
    }

    public void printFee(BigDecimal sum) {
        printerFileCreator.createFeeFile(sum);
        downloadFileInPrinterDir();
    }

    public void printReceiptReport(boolean reset) {
        printerFileCreator.createReceiptReport(reset);
        downloadFileInPrinterDir();
    }

    public void printInterests(List<Payment> payments) {
        printerFileCreator.createInterestsFile(payments);
        downloadFileInPrinterDir();
    }

    public void printInterests(Payment payment) {
        List<Payment> payments = new ArrayList<Payment>();
        payments.add(payment);
        printerFileCreator.createInterestsFile(payments);
        downloadFileInPrinterDir();
    }

    public void printInterest(BigDecimal sum) {
        printerFileCreator.createInterestsFile(sum);
        downloadFileInPrinterDir();
    }

    private void downloadFileInPrinterDir() {
        FileDownloader.downloadFile(printerFileCreator.generateSourceFilename(),
                PrinterFileCreator.FISCAL_PRINTER_FILENAME,
                PRINTER_CONTAINER, PRINTER_DIRECTORY);
    }

    public void printDocumentHTML(Document document) {
        // Invoke printer in client-side
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(String.format("printHTML('%s')", document.getSource()));
    }

    public void printDocumentPdf(Document document) {
        PDFFileCreator pdfFileCreator = new PDFFileCreator(document.getSource());

        File file = pdfFileCreator.createPDFFile();

        // Invoke printer in client-side
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(String.format("printPDF('%s')", file.getPath()));
    }

    public static synchronized Printer createPrinter() {
        return new Printer();
    }
}
