# The Ledger Co

Command-line app to track loan balances for The Ledger Co. Given loans, lump-sum payments, and an EMI number, it reports amount paid so far and EMIs remaining.

## Commands
- `LOAN BANK_NAME BORROWER_NAME PRINCIPAL YEARS RATE` (simple interest; EMI is ceiled)
- `PAYMENT BANK_NAME BORROWER_NAME LUMP_SUM_AMOUNT EMI_NO` (lump sum after EMI_NO)
- `BALANCE BANK_NAME BORROWER_NAME EMI_NO` → prints `BANK_NAME BORROWER_NAME AMOUNT_PAID NO_OF_EMIS_LEFT`

## Input/Output
- Input: text file with one command per line (only the above formats).
- Output: balances for each `BALANCE` line.

## Assumptions
- Simple interest `I = P*N*R`; total `A = P + I`.
- EMI and remaining EMI counts are ceiled.
- Last EMI is trimmed to the remaining amount.
- Lump sums reduce outstanding from that EMI onward and count in balances at/after that EMI.

## Sample
```
LOAN IDIDI Dale 5000 1 6
PAYMENT IDIDI Dale 1000 5
BALANCE IDIDI Dale 3      # -> IDIDI Dale 1326 9
BALANCE IDIDI Dale 6      # -> IDIDI Dale 3652 4
```

## Run
```bash
mvn clean install -DskipTests assembly:single -q
java -jar target/geektrust.jar sample_input/input1.txt
```

Use `run.bat` (Windows) or `run.sh` (macOS/Linux) to execute against `sample_input/input1.txt` by default. Replace the file path to run other cases.
