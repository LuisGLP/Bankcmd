# Bankcmd – Banking Transaction Validation System

Bankcmd is a Java-based project that implements a set of validators to verify the data required for banking operations.  
The system validates essential information such as bank codes, branch codes, account numbers, and personal keys used during electronic transactions.

This project is built with **Java 17** and **Maven**, and includes unit tests to ensure the reliability of each validator.

---

## 📂 Project Structure

```
Bankcmd/
├── LICENSE
├── pom.xml
├── src
│ ├── main
│ │ └── java/com/bank/app
│ │ ├── model
│ │ │ ├── Transaction.java
│ │ │ └── ValidationResult.java
│ │ ├── service
│ │ │ └── TransactionValidationService.java
│ │ ├── validator
│ │ │ ├── AccountNumberValidator.java
│ │ │ ├── BankCodeValidator.java
│ │ │ ├── BranchCodeValidator.java
│ │ │ ├── PersonalKeyValidator.java
│ │ │ └── Validator.java
│ │ └── BankTransactionValidator.java
│ └── test
│ └── java/validators
│ └── ValidatorTest.java


```


---

##  Features

- Individual validation of:
  - Bank Code
  - Branch Code
  - Account Number
  - Personal Key
- Comprehensive validation through `BankTransactionValidator`
- Centralized service layer: `TransactionValidationService`
- Data models for:
  - Transactions (`Transaction`)
  - Validation results (`ValidationResult`)
- Unit tests included

---

##  Technologies Used

- **Java 17**
- **Maven**
- **JUnit 5** for testing
- Modular validator architecture

---

##  Installation

1. Clone the repository:

```bash
git clone https://github.com/LuisGLP/Bankcmd
```

2. Enter the project directory:
```
cd Bankcmd
```

3. Build the project and install dependencies:
```
mvn clean install
```

## License

This project uses the license found in the LICENSE file (likely MIT—check the file for details).


