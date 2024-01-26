package com.testing.piggybank;

import com.testing.piggybank.helper.CurrencyConverterService;
import com.testing.piggybank.model.Currency;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class PiggyBankApplicationTests {
	private final CurrencyConverterService currencyConverterService = new CurrencyConverterService();

	@Test
	void contextLoads() {
	}

	@Test
	void currencyConverter() {
		assertEquals(80, currencyConverterService.toEuro(Currency.GBP, BigDecimal.valueOf(100)));
	}
}
