package ru.benyfox.TransactionsRestApi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionsRestApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
	void contextLoads() {
	}

    @Test
    @DisplayName("Test get exceeded transactions")
    public void getExceededTransactionsTest() throws Exception {
        String accountNumber = "";
        String category = "";

        mockMvc.perform(get("/transactions/{accountNumber}/{category}/exceeded", accountNumber, category))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test get transaction")
    public void getTransactionTest() throws Exception {
        String id = "0";

        mockMvc.perform(get("/transactions/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test get transactions")
    public void getTransactionsTest() throws Exception {
        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test save transaction")
    public void saveTransactionTest() throws Exception {
        String transactionDTO = """
                {
                	"accountFrom": "",
                	"accountTo": "",
                	"currencyShortname": "",
                	"sum": "",
                	"expenseCategory": {
                		"product": {},
                		"service": {}
                	}
                }""";

        mockMvc.perform(post("/transactions")
                        .content(transactionDTO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
