package com.lovesoft.bitpump.simulation;

import com.google.common.base.Preconditions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SimulationDataSupport {
    public enum ChartName{chart01, chart02, chart03, chart04, Bitmarket24_01, Bitmarket24_02}

    public List<Double> readChart(ChartName chartName) {
        InputStream is = getInputStream(chartName);
        try {
            return readNumbersFromStream(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        String fileName = "charts/" + chartName.name() + ".txt";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
        Preconditions.checkNotNull(is, "Can't read file " + fileName);
        return is;
    }
}
