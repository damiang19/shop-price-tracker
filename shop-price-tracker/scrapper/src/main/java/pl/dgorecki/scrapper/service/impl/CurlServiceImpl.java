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
        String[] command = {"curl", "-L", "--compressed",
                "-H", "\"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\"",
                "-H", "\"Accept-Language: en-US,en;q=0.5\"",
                "-H", "\"Connection: keep-alive\"",
                "-H", "\"Upgrade-Insecure-Requests: 1\"",
                "-H", "\"User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36\"", url};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        return processBuilder.start();
    }

    private String readAllLinesWithStream(BufferedReader reader) {
        return reader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
