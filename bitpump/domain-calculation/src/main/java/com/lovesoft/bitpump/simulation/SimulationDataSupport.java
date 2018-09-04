package com.lovesoft.bitpump.simulation;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.commons.BitPumpRuntimeException;
import com.lovesoft.bitpump.exchange.HistoricalTransactionTO;
import com.lovesoft.bitpump.exchange.TradeExchangeToSerializer;
import com.lovesoft.bitpump.simulation.SimulationDataSupport.ChartName.ChartType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

public class SimulationDataSupport {
    public enum ChartName {
        CHART_01(ChartType.DOUBLE_STREAM, "chart01.txt"),
        CHART_02(ChartType.DOUBLE_STREAM, "chart02.txt"),
        CHART_03(ChartType.DOUBLE_STREAM, "chart03.txt"),
        CHART_04(ChartType.DOUBLE_STREAM, "chart04.txt"),
        BITMARKET24_01(ChartType.DOUBLE_STREAM, "Bitmarket24_01.txt"),
        BITMARKET24_02(ChartType.DOUBLE_STREAM, "Bitmarket24_02.txt"),
        BITMARKET24_03(ChartType.DOUBLE_STREAM, "Bitmarket24_03.txt"),
        BITMARKET24_04(ChartType.DOUBLE_STREAM, "Bitmarket24_04.txt"),
        BITMARKET24_05(ChartType.DOUBLE_STREAM, "Bitmarket24_05.txt"),
        BITMARKET_2017_09_2018_08(ChartType.TWO_COLUMNS_ZIPPED, "Not defined yet.");

        public enum ChartType {DOUBLE_STREAM, TWO_COLUMNS_ZIPPED}
        private ChartType chartType;
        private String fileName;

        ChartName(ChartType chartType, String fileName) {
            this.chartType = chartType;
            this.fileName = fileName;
        }

        public ChartType getChartType() {
            return chartType;
        }

        public String getFileName() {
            return "charts/" + fileName;
        }
    }

    public List<HistoricalTransactionTO> readChart(ChartName chartName) {

        try(InputStream is = getInputStream(chartName)) {
            if(chartName.getChartType().equals(ChartType.DOUBLE_STREAM)) {
                return toTradeTO(readNumbersFromStream(is));
            } else if(chartName.getChartType().equals(ChartType.TWO_COLUMNS_ZIPPED)) {
                return readHistoricalTransactions(is);
            } else {
                throw new BitPumpRuntimeException("Unknown ChartType " + chartName.getChartType());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<HistoricalTransactionTO> readHistoricalTransactions(InputStream is) {

        ZipInputStream zipIs = new ZipInputStream(is);
        List<HistoricalTransactionTO> list = TradeExchangeToSerializer.load(zipIs);
        return list;
    }

    private List<HistoricalTransactionTO> toTradeTO(List<Double> doubles) {
        return doubles.stream().map(d -> new HistoricalTransactionTO(0l, d)).collect(Collectors.toList());
    }

    private List<Double> readNumbersFromStream(InputStream is) throws IOException {
        List<Double> list = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader( new InputStreamReader(is))) {
            String line = reader.readLine();
            while (line != null) {
                line = line.replace(",", ".");
                if(!line.trim().isEmpty()) {
                    list.add(Double.parseDouble(line));
                }
                line = reader.readLine();
            }
        }
        return list;
    }

    private InputStream getInputStream(ChartName chartName) {
        String fileName = chartName.getFileName();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
        Preconditions.checkNotNull(is, "Can't read file " + fileName);
        return is;
    }
}
