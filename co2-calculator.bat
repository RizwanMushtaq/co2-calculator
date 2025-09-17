@echo off
:: Cross-platform wrapper for CO2 Calculator
set SCRIPT_DIR=%~dp0
java -jar "%SCRIPT_DIR%target\co2-calculator.jar" %*
