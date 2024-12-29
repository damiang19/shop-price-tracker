package pl.dgorecki.scrapper.service.impl;

import org.springframework.stereotype.Service;
import pl.dgorecki.scrapper.service.CurlService;
import pl.dgorecki.scrapper.service.errors.WebsiteProcessingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
public class CurlServiceImpl implements CurlService {

    @Override
    public String fetchWebsiteContent(String url) {
        Process process;
        try {
            process = prepareProcess(url);
        } catch (IOException e) {
            throw new WebsiteProcessingException("error");
        }
        return readAllLinesWithStream(new BufferedReader(new InputStreamReader(process.getInputStream())));
    }

    private Process prepareProcess(String url) throws IOException {
        String[] command = {"curl", "-X", "GET", url};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        return processBuilder.start();
    }

    private String readAllLinesWithStream(BufferedReader reader) {
        return reader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
