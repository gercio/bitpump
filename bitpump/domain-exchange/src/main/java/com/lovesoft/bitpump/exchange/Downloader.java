package com.lovesoft.bitpump.exchange;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.commons.BitPumpRuntimeException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Just to get some market data, to teach/test trading algorithms.
 * Created by Patryk on 06.08.18 18:53 at Milky Way Galaxy.
 */
public class Downloader {

    private static final Logger LOG = LoggerFactory.getLogger(Downloader.class);
    private static final String BITMARKET_PL_TRADES_URL = "https://www.bitmarket.pl/json/BTCPLN/trades.json";
    private static final int MAX_LOG_SIZE = 100;
    private static final String FILE_NAME = "downloaded.txt";
    private static final String START_DATE = "2017-08-01 01:00:00";
    private static final String ENCODING = "UTF-8";

    void download(Date pastDate, OutputStream outputStream) {
        List<? extends TradeExchangeTO> trades = downloadTrades(pastDate);
        LOG.info("For date {} , there is {} numbers of transactions returned.", pastDate,  trades.size());
        TradeExchangeToSerializer.save(trades, outputStream);
        LOG.info("All data saved. Finished with success.");
    }

    private List<? extends TradeExchangeTO> downloadTrades(Date pastDate) {
        Preconditions.checkNotNull(pastDate);
        List<ParsingTradeTO> actualTrades = downloadActualTrades();
        TradeExchangeTO actualTrade = actualTrades.get(0);
        while (actualTrade.getDate().compareTo(pastDate) > 0) {
            actualTrades.addAll(0, downloadTrades(actualTrades.get(0).getTransactionId() - 500));
            TradeExchangeTO prev = actualTrade;
            actualTrade = actualTrades.get(0);
            Preconditions.checkArgument(actualTrade.getTransactionId() < prev.getTransactionId(), " Transaction id in past should be smaller " + actualTrade.getTransactionId() + "  " + prev.getTransactionId());
        }
        return actualTrades;
    }

    private List<ParsingTradeTO> downloadActualTrades() {
        return downloadTrades((Long) null);
    }

    private List<ParsingTradeTO> downloadTrades(Long transactionID) {
        String body = downHistoricalTrades(transactionID);
        int last = MAX_LOG_SIZE > body.length() ? body.length() : MAX_LOG_SIZE;
        if(LOG.isInfoEnabled()) {
            LOG.info("Answer = {}...", body.substring(0, last - 1));
        }
        return parseTrades(body);
    }

    /**
     * It parse [{"amount":0.04226696,"price":1888.9800,"date":1393869593,"tid":500,"type":"bid"},
     *
     * @param body
     * @return
     */
    private List<ParsingTradeTO> parseTrades(String body) {
        LinkedList<ParsingTradeTO> tradeList = new LinkedList<>();
        Preconditions.checkNotNull(body, "Body can't be null.");
        Preconditions.checkArgument(body.length() > 20, "Body length must me grater than 20.");
        String filtered = body.replaceAll("[{},\"\\[\\]]", " ");
        String withMark = filtered.replaceAll("  +", "|");
        String[] pairs = withMark.split("\\|");
        Preconditions.checkArgument(pairs.length > 10, "Can't parse " + withMark);
        ParsingTradeTO parsingTradeTO = new ParsingTradeTO();
        for (String pair : pairs) {
            if (!pair.isEmpty()) {
                String[] p = pair.split(":");
                Preconditions.checkArgument(p.length == 2, "Pair size should be 2 " + p);
                Token token = Token.find(p[0]);
                String value = p[1];
                parsingTradeTO.setValueFromToken(token, value);
                if (parsingTradeTO.allValuesAreSet()) {
                    tradeList.addFirst(parsingTradeTO);
                    parsingTradeTO = new ParsingTradeTO();
                }
            }
        }
        if(!tradeList.isEmpty()) {
            LOG.info("Found trade {} ", tradeList.get(0));
        }
        return tradeList;
    }

    private enum Token {
        AMOUNT, PRICE, DATE, TID, TYPE;

        public static Token find(String tokenName) {
            Preconditions.checkArgument(!tokenName.isEmpty(), "tokenName can't be empty");
            for (Token t : Token.values()) {
                if (tokenName.startsWith(t.toString().toLowerCase())) {
                    return t;
                }
            }
            throw new IllegalArgumentException("Can't find token for " + tokenName + " possible values are " + Token.values());
        }
    }

    private String downHistoricalTrades(Long transactionID) {
        try {
            URL url;
            if(transactionID != null) {
                Preconditions.checkArgument(transactionID >= 0, "TransactionId should not be negative");
                url = new URL(BITMARKET_PL_TRADES_URL + "?since=" + transactionID);
            } else {
                url = new URL(BITMARKET_PL_TRADES_URL);
            }
            LOG.info("URL = {}", url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            String body = IOUtils.toString(con.getInputStream(), ENCODING);
            int status = con.getResponseCode();
            if (status != 200) {
                LOG.error("HTTP error code {}", status);
            }
            return body;
        } catch (IOException e) {
            throw new BitPumpRuntimeException("Can't download historical data", e);
        }
    }

    private class ParsingTradeTO extends TradeExchangeTO {

        public void setAmount(String amount) {
            Preconditions.checkArgument(this.getAmount() == null);
            super.setAmount(Double.parseDouble(amount));
        }


        public void setPrice(String price) {
            Preconditions.checkArgument(this.getPrice() == null);
            super.setPrice(Double.parseDouble(price));
        }

        public void setDate(String seconds) {
            Preconditions.checkArgument(this.getDate() == null);
            int sec = Integer.parseInt(seconds);
            super.setDate(toDateFromSeconds(sec)); // converting from seconds to mseconds
        }

        public void setTransactionId(String transactionId) {
            Preconditions.checkArgument(this.getTransactionId() == null);
            super.setTransactionId(Long.parseLong(transactionId));
        }

        @Override
        public void setType(String type) {
            Preconditions.checkArgument(this.getType() == null);
            super.setType(type);
        }

        public void setValueFromToken(Token token, String value) {
            switch (token) {
                case AMOUNT:
                    setAmount(value);
                    break;
                case DATE:
                    setDate(value);
                    break;
                case PRICE:
                    setPrice(value);
                    break;
                case TID:
                    setTransactionId(value);
                    break;
                case TYPE:
                    setType(value);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized token " + token);
            }
        }

        public boolean allValuesAreSet() {
            return getDate() != null && getAmount() != null && getPrice() != null && getTransactionId() != null && getType() != null;
        }
    }

    public static Date toDateFromSeconds(long sec) {
        return new Date(sec * 1000l);
    }

    public static String toDateString(Date date) {
       return  createDateFormat().format(date);
    }

    public static void main(String[] args) {
        try {
            new Downloader().download(createDateFormat().parse(START_DATE), new FileOutputStream(FILE_NAME));
        } catch (Exception e) {
            LOG.error("Can't run downloading.", e);
        }
    }

    public static DateFormat createDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
    }
}
