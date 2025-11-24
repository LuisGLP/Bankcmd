package validators;

import com.bank.app.model.Transaction;
import com.bank.app.model.ValidationResult;
import com.bank.app.service.TransactionValidationService;
import com.bank.app.validator.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test case for Validators
 */
class ValidatorTests {

    private BankCodeValidator bankCodeValidator;
    private BranchCodeValidator branchCodeValidator;
    private AccountNumberValidator accountNumberValidator;
    private PersonalKeyValidator personalKeyValidator;
    private OrderValueValidator orderValueValidator;

    @BeforeEach
    void setUp() {
        bankCodeValidator = new BankCodeValidator();
        branchCodeValidator = new BranchCodeValidator();
        accountNumberValidator = new AccountNumberValidator();
        personalKeyValidator = new PersonalKeyValidator();
        orderValueValidator = new OrderValueValidator();
    }

    @Test
    @DisplayName("TC01 - Bank Code válido")
    void testValidBankCode() {
        ValidationResult result = bankCodeValidator.validate("123");
        assertTrue(result.isValid(), "Bank code válido debería pasar");
    }

    @Test
    @DisplayName("TC02 - Bank Code corto")
    void testShortBankCode() {
        ValidationResult result = bankCodeValidator.validate("12");
        assertFalse(result.isValid(), "Bank code corto debería fallar");
        assertTrue(result.getErrorMessage().contains("Bank code error"));
    }

    @Test
    @DisplayName("TC03 - Bank Code con letras")
    void testBankCodeWithLetters() {
        ValidationResult result = bankCodeValidator.validate("A23");
        assertFalse(result.isValid(), "Bank code con letras debería fallar");
        assertTrue(result.getErrorMessage().contains("Bank code error"));
    }

    @Test
    @DisplayName("TC04 - Branch Code con letras")
    void testBranchCodeWithLetters() {
        ValidationResult result = branchCodeValidator.validate("10A3");
        assertFalse(result.isValid(), "Branch code con letras debería fallar");
        assertTrue(result.getErrorMessage().contains("Branch code error"));
    }

    @Test
    @DisplayName("TC05 - Branch Code corto")
    void testShortBranchCode() {
        ValidationResult result = branchCodeValidator.validate("123");
        assertFalse(result.isValid(), "Branch code corto debería fallar");
        assertTrue(result.getErrorMessage().contains("Branch code error"));
    }

    @Test
    @DisplayName("TC06 - Account Number corto")
    void testShortAccountNumber() {
        ValidationResult result = accountNumberValidator.validate("12345678");
        assertFalse(result.isValid(), "Account number corto debería fallar");
        assertTrue(result.getErrorMessage().contains("Account number error"));
    }

    @Test
    @DisplayName("TC07 - Account Number con letras")
    void testAccountNumberWithLetters() {
        ValidationResult result = accountNumberValidator.validate("12345A7890");
        assertFalse(result.isValid(), "Account number con letras debería fallar");
        assertTrue(result.getErrorMessage().contains("Account number error"));
    }

    @Test
    @DisplayName("TC08 - Personal Key corta")
    void testShortPersonalKey() {
        ValidationResult result = personalKeyValidator.validate("A12BC");
        assertFalse(result.isValid(), "Personal key corta debería fallar");
        assertTrue(result.getErrorMessage().contains("Personal key error"));
    }

    @Test
    @DisplayName("TC09 - Personal Key con símbolos")
    void testPersonalKeyWithSymbols() {
        ValidationResult result = personalKeyValidator.validate("AB12#C");
        assertFalse(result.isValid(), "Personal key con símbolos debería fallar");
        assertTrue(result.getErrorMessage().contains("Personal key error"));
    }

    @Test
    @DisplayName("TC10 - Order Value inválido")
    void testInvalidOrderValue() {
        ValidationResult result = orderValueValidator.validate("3");
        assertFalse(result.isValid(), "Order value inválido debería fallar");
        assertTrue(result.getErrorMessage().contains("Order error"));
    }

    @Test
    @DisplayName("TC11 - Order Value no numérico")
    void testNonNumericOrderValue() {
        ValidationResult result = orderValueValidator.validate("A");
        assertFalse(result.isValid(), "Order value no numérico debería fallar");
        assertTrue(result.getErrorMessage().contains("Order error"));
    }
}

/**
 * Suite de pruebas integradas para transacciones completas
 */
class TransactionValidationTests {

    private TransactionValidationService validationService;
    private ValidationResultCollector resultCollector;

    @BeforeEach
    void setUp() {
        validationService = new TransactionValidationService(
                new BankCodeValidator(),
                new BranchCodeValidator(),
                new AccountNumberValidator(),
                new PersonalKeyValidator(),
                new OrderValueValidator()
        );
        resultCollector = new ValidationResultCollector();
    }

    @Test
    @DisplayName("TC01 - Todos los datos válidos")
    void testAllValidData() {
        Transaction transaction = new Transaction("123", "1023", "1234567890", "Ab12CD", "1");

        boolean isValid = validateAndCollect(transaction);

        assertTrue(isValid, "Transacción con datos válidos debería pasar");
        assertEquals(0, resultCollector.getErrorCount(), "No debería haber errores");
    }

    @Test
    @DisplayName("TC02 - Bank Code corto")
    void testShortBankCode() {
        Transaction transaction = new Transaction("12", "1023", "1234567890", "Ab12CD", "1");

        boolean isValid = validateAndCollect(transaction);

        assertFalse(isValid, "Transacción con bank code corto debería fallar");
        assertTrue(resultCollector.hasError("Bank code"), "Debería contener error de bank code");
    }

    @Test
    @DisplayName("TC03 - Bank Code con letras")
    void testBankCodeWithLetters() {
        Transaction transaction = new Transaction("A23", "1023", "1234567890", "Ab12CD", "1");

        boolean isValid = validateAndCollect(transaction);

        assertFalse(isValid, "Transacción con bank code con letras debería fallar");
        assertTrue(resultCollector.hasError("Bank code"), "Debería contener error de bank code");
    }

    @Test
    @DisplayName("TC04 - Branch Code con letras")
    void testBranchCodeWithLetters() {
        Transaction transaction = new Transaction("123", "10A3", "1234567890", "Ab12CD", "1");

        boolean isValid = validateAndCollect(transaction);

        assertFalse(isValid, "Transacción con branch code con letras debería fallar");
        assertTrue(resultCollector.hasError("Branch code"), "Debería contener error de branch code");
    }

    @Test
    @DisplayName("TC05 - Branch Code corto")
    void testShortBranchCode() {
        Transaction transaction = new Transaction("123", "123", "1234567890", "Ab12CD", "1");

        boolean isValid = validateAndCollect(transaction);

        assertFalse(isValid, "Transacción con branch code corto debería fallar");
        assertTrue(resultCollector.hasError("Branch code"), "Debería contener error de branch code");
    }

    @Test
    @DisplayName("TC06 - Account Number corto")
    void testShortAccountNumber() {
        Transaction transaction = new Transaction("123", "1023", "12345678", "Ab12CD", "1");

        boolean isValid = validateAndCollect(transaction);

        assertFalse(isValid, "Transacción con account number corto debería fallar");
        assertTrue(resultCollector.hasError("Account number"), "Debería contener error de account number");
    }

    @Test
    @DisplayName("TC07 - Account Number con letras")
    void testAccountNumberWithLetters() {
        Transaction transaction = new Transaction("123", "1023", "12345A7890", "Ab12CD", "1");

        boolean isValid = validateAndCollect(transaction);

        assertFalse(isValid, "Transacción con account number con letras debería fallar");
        assertTrue(resultCollector.hasError("Account number"), "Debería contener error de account number");
    }

    @Test
    @DisplayName("TC08 - Personal Key corta")
    void testShortPersonalKey() {
        Transaction transaction = new Transaction("123", "1023", "1234567890", "A12BC", "1");

        boolean isValid = validateAndCollect(transaction);

        assertFalse(isValid, "Transacción con personal key corta debería fallar");
        assertTrue(resultCollector.hasError("Personal key"), "Debería contener error de personal key");
    }

    @Test
    @DisplayName("TC09 - Personal Key con símbolos")
    void testPersonalKeyWithSymbols() {
        Transaction transaction = new Transaction("123", "1023", "1234567890", "AB12#C", "1");

        boolean isValid = validateAndCollect(transaction);

        assertFalse(isValid, "Transacción con personal key con símbolos debería fallar");
        assertTrue(resultCollector.hasError("Personal key"), "Debería contener error de personal key");
    }

    @Test
    @DisplayName("TC10 - Order Value inválido")
    void testInvalidOrderValue() {
        Transaction transaction = new Transaction("123", "1023", "1234567890", "Ab12CD", "3");

        boolean isValid = validateAndCollect(transaction);

        assertFalse(isValid, "Transacción con order value inválido debería fallar");
        assertTrue(resultCollector.hasError("Order"), "Debería contener error de order value");
    }

    @Test
    @DisplayName("TC11 - Order Value no numérico")
    void testNonNumericOrderValue() {
        Transaction transaction = new Transaction("123", "1023", "1234567890", "Ab12CD", "A");

        boolean isValid = validateAndCollect(transaction);

        assertFalse(isValid, "Transacción con order value no numérico debería fallar");
        assertTrue(resultCollector.hasError("Order"), "Debería contener error de order value");
    }

    @Test
    @DisplayName("TC12 - Múltiples campos inválidos")
    void testMultipleInvalidFields() {
        Transaction transaction = new Transaction("A23", "12B3", "12345A", "#12BC$", "5");

        boolean isValid = validateAndCollect(transaction);

        assertFalse(isValid, "Transacción con múltiples errores debería fallar");
        assertTrue(resultCollector.getErrorCount() >= 4,
                "Debería haber al menos 4 errores. Encontrados: " + resultCollector.getErrorCount());
    }

    @ParameterizedTest(name = "Test {index}: {0}")
    @CsvSource({
            "Válido, 123, 1023, 1234567890, Ab12CD, 1, true",
            "Bank corto, 12, 1023, 1234567890, Ab12CD, 1, false",
            "Bank letras, A23, 1023, 1234567890, Ab12CD, 1, false",
            "Branch letras, 123, 10A3, 1234567890, Ab12CD, 1, false",
            "Order inválido, 123, 1023, 1234567890, Ab12CD, 3, false"
    })
    @DisplayName("Pruebas parametrizadas")
    void testParameterizedValidation(String testName, String bankCode, String branchCode,
                                     String accountNumber, String personalKey,
                                     String orderValue, boolean expectedValid) {
        Transaction transaction = new Transaction(bankCode, branchCode, accountNumber,
                personalKey, orderValue);

        boolean isValid = validateAndCollect(transaction);

        assertEquals(expectedValid, isValid,
                "Test '" + testName + "' falló. Esperado: " + expectedValid + ", Obtenido: " + isValid);
    }

    // Método auxiliar para validar y recolectar resultados
    private boolean validateAndCollect(Transaction transaction) {
        resultCollector.clear();

        ValidationResult bankResult = new BankCodeValidator().validate(transaction.getBankCode());
        ValidationResult branchResult = new BranchCodeValidator().validate(transaction.getBranchCode());
        ValidationResult accountResult = new AccountNumberValidator().validate(transaction.getAccountNumber());
        ValidationResult keyResult = new PersonalKeyValidator().validate(transaction.getPersonalKey());
        ValidationResult orderResult = new OrderValueValidator().validate(transaction.getOrderValue());

        resultCollector.add(bankResult);
        resultCollector.add(branchResult);
        resultCollector.add(accountResult);
        resultCollector.add(keyResult);
        resultCollector.add(orderResult);

        return resultCollector.isAllValid();
    }
}

/**
 * Clase auxiliar para recolectar y analizar resultados de validación
 */
class ValidationResultCollector {
    private final java.util.List<ValidationResult> results = new java.util.ArrayList<>();

    public void add(ValidationResult result) {
        results.add(result);
    }

    public void clear() {
        results.clear();
    }

    public boolean isAllValid() {
        return results.stream().allMatch(ValidationResult::isValid);
    }

    public int getErrorCount() {
        return (int) results.stream().filter(r -> !r.isValid()).count();
    }

    public boolean hasError(String errorKeyword) {
        return results.stream()
                .filter(r -> !r.isValid())
                .anyMatch(r -> r.getErrorMessage().contains(errorKeyword));
    }

    public java.util.List<String> getAllErrors() {
        return results.stream()
                .filter(r -> !r.isValid())
                .map(ValidationResult::getErrorMessage)
                .collect(java.util.stream.Collectors.toList());
    }
}