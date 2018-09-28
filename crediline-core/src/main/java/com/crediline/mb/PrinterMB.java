package com.crediline.mb;

import com.crediline.files.Printer;
import org.primefaces.component.commandlink.CommandLink;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.event.ActionEvent;


@Component("printerBean")
@Scope("prototype")
public class PrinterMB extends CommonManagedBean implements ITabBean {
    private static final long serialVersionUID = -8125597877996545968L;
    private static final String DAILY_REPORT = "dailyReport";
    private static final String INTERIM_REPORT = "interimReport";

    private Printer printer;

    public PrinterMB() {
        printer = new Printer();
    }

    @Override
    public Boolean getClosable() {
        return true;
    }

    public void onPrint(ActionEvent event) {
        String id = ((CommandLink) ((ActionEvent) event).getSource()).getId();

        if (id.equals(DAILY_REPORT)) {
            printer.printReceiptReport(true);
        } else if (id.equals(INTERIM_REPORT)) {
            printer.printReceiptReport(false);
        }
    }
}
