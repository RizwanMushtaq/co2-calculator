### CO2 Calculator App (Java)

### Setting Up Environment Variables

Open your command prompt (cmd) and follow these steps:
For Window users:

* navigate to the project directory.
* Set ORS_TOKEN as Environment Variable in Windows Command Prompt:
  `$env:ORS_TOKEN="your_api_key_here"`

For Linux or Mac users:

* navigate to the project directory.
* Set ORS_TOKEN as Environment Variable in your terminal:
  `export ORS_TOKEN="your_api_key_here"`

### Building and Running the Application

* Ensure you have Java 17 or installed on your machine.
* Build the application using Maven:

```
mvn clean package
```

### Running the Application

* Then, execute the following commands in your terminal to play with the
  application:

```
./co2-calculator --version
./co2-calculator --help  
./co2-calculator --start Hamburg --end Berlin --transportation-method diesel-car-medium
./co2-calculator.bat --start "Los Angeles" --end "New York" --transportation-method=electric-car-large
``` 
