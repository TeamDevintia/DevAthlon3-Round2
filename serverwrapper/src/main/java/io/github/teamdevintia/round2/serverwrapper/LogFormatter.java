package io.github.teamdevintia.round2.serverwrapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * our log formatter
 *
 * @author stackoverflow and MiniDigger
 */
public class LogFormatter extends Formatter {

    public static boolean debug = false;

    private Date dat = new Date();
    private final static String format = "{0,date} {0,time}";
    private MessageFormat formatter;
    private Object args[] = new Object[1];

    /**
     * Format the given LogRecord.
     *
     * @param record the log record to be formatted.
     * @return a formatted log record
     */
    public synchronized String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();

        // Minimize memory allocations here.
        dat.setTime(record.getMillis());
        args[0] = dat;

        // Date and time
        StringBuffer text = new StringBuffer();
        if (formatter == null) {
            formatter = new MessageFormat(format);
        }
        formatter.format(args, text, null);
        sb.append(text);
        sb.append(" ");

        if (debug) {
            // Class name
            if (record.getSourceClassName() != null) {
                sb.append(record.getSourceClassName());
            } else {
                sb.append(record.getLoggerName());
            }

            // Method name
            if (record.getSourceMethodName() != null) {
                sb.append(" ");
                sb.append(record.getSourceMethodName());
            }
        }

        sb.append(" - "); // lineSeparator

        String message = formatMessage(record);

        // Level
        sb.append(record.getLevel().getLocalizedName());
        sb.append(": ");

        // Indent - the more serious, the more indented.
        //sb.append( String.format("% ""s") );
        int iOffset = (1000 - record.getLevel().intValue()) / 100;
        for (int i = 0; i < iOffset; i++) {
            sb.append(" ");
        }

        sb.append(message);
        sb.append("\n");
        if (record.getThrown() != null) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
                sb.append(sw.toString());
            } catch (Exception ex) {
            }
        }
        return sb.toString();
    }
}