package com.lovesoft.bitpump.exchange;

import com.lovesoft.bitpump.commons.BitPumpRuntimeException;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.lovesoft.bitpump.exchange.Downloader.DATE_FORMAT;

/**
 * Created by Patryk Kaluzny on 15.08.18 09:25 at Milky Way Galaxy.
 */
public class TradeExchangeToSerializer {

    private static final String SEPARATOR = "|";

    public static void save(List<? extends TradeExchangeTO> trades, OutputStream outputStream) {
        OutputStreamWriter writer = new OutputStreamWriter( new BufferedOutputStream(outputStream, 10000));
        trades.forEach( t -> {
            try {
                writer.write("" + t.getPrice());
                writer.write(SEPARATOR);
                writer.write(DATE_FORMAT.format(t.getDate()));
                writer.write("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<HistoricalTransactionTO> load(InputStream inputStream) {

        List<HistoricalTransactionTO> list = new ArrayList<>();
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bReader.readLine()) != null) {
                String[] columns = line.split("\\|");
                if(columns.length != 2) {
                    throw new BitPumpRuntimeException("Can't parse line " + line);
                }
                HistoricalTransactionTO ht = new HistoricalTransactionTO(DATE_FORMAT.parse(columns[1]).getTime(), Double.parseDouble(columns[0]));
                list.add(ht);
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
