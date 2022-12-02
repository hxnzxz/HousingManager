package ui;

import model.EventLog;
import model.Event;

public class LogPrinter {

    public LogPrinter(){
    }

    public void printLog(EventLog el) {
        String logMessage = "";
        for (Event next : el) {
            logMessage += next.toString() + "\n";
        }
        System.out.println(logMessage);
    }
}
