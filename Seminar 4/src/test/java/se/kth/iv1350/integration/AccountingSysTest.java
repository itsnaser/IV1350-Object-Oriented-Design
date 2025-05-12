package se.kth.iv1350.integration;

import org.junit.jupiter.api.Test;
import se.kth.iv1350.model.dto.SaleDTO;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link AccountingSys} class.
 * <p>
 * This test class verifies the behavior of the {@code sendSaleInfo} method,
 * ensuring that it executes without throwing exceptions when provided with a
 * {@link SaleDTO} object. The method's primary function is to print a message
 * to {@code System.out}, so the test focuses on the absence of errors during
 * execution.
 */
class AccountingSysTest {

  @Test
  void sendSaleInfo_shouldPrintMessage() {
    AccountingSys accountingSys = new AccountingSys();
    SaleDTO mockSaleDTO = mock(SaleDTO.class);

    // Since the method only prints to System.out, we can check that no exceptions
    // are thrown
    accountingSys.sendSaleInfo(mockSaleDTO);
  }
}