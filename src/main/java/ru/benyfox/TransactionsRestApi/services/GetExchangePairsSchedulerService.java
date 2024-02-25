package ru.benyfox.TransactionsRestApi.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.benyfox.TransactionsRestApi.models.ExchangeRate;
import ru.benyfox.TransactionsRestApi.repositories.cassandra.ExchangeRateRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class GetExchangePairsSchedulerService {
    private final ExchangeRateRepository rateRepository;

    @Value("${twelvedata.api.key}")
    private String apiKey;

    @Scheduled(cron = "0 0 0 * * ?")
    public void getExchangePairs() throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        ObjectMapper mapper = new ObjectMapper();

        Map<String, List<String>> map = mapper.readValue(new File("src/main/resources/pairs.json"), Map.class);
        List<String> pairs = map.get("pairs");

        for (String pair: pairs) {
            String url = "https://api.twelvedata.com/time_series?symbol="+ pair + "&interval=1min&apikey=" + apiKey;

            String response = restTemplate.getForObject(url, String.class);
            JsonNode node = mapper.readTree(response);

            if (node.get("status").asText().equals("error")) {
                log.error("Error getting exchange rate for " + pair + ": " + node.get("message").asText());
                throw new RuntimeException("Error getting exchange rate for " + pair + ": " + node.get("message").asText());
            }

            double closeValue = node.get("values").get(0).get("close").asDouble();

            rateRepository.save(new ExchangeRate(UUID.randomUUID(), pair, LocalDate.now(), closeValue));
        }
    }
}
