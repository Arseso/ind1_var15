import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Country {
    String name;
    double healthExpenditure;
    double income;
    double inflation;
    double lifeExpectancy;



    public Country(String name, double healthExpenditure, double income, double inflation, double lifeExpectancy) {
        this.name = name;
        this.healthExpenditure = healthExpenditure;
        this.income = income;
        this.inflation = inflation;
        this.lifeExpectancy = lifeExpectancy;
    }

    public double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public String toString() {
        return name + "," + healthExpenditure + "," + income + "," + inflation + "," + lifeExpectancy;
    }
}

class CountryComparator implements Comparator<Country> {
    String parameter;

    public CountryComparator(String parameter) {
        this.parameter = parameter;
    }

    public int compare(Country c1, Country c2) {
        switch (parameter) {
            case "health":
                return Double.compare(c2.healthExpenditure, c1.healthExpenditure);
            case "income":
                return Double.compare(c1.income, c2.income);
            case "inflation":
                return Double.compare(c1.inflation, c2.inflation);
            case "life":
                return Double.compare(c2.lifeExpectancy, c1.lifeExpectancy);
            case "life2":
                return Double.compare(c1.lifeExpectancy, c2.lifeExpectancy);
            default:
                return 0;
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        List<Country> countries = readCountriesFromFile("C:\\Users\\arses\\OneDrive\\Рабочий стол\\data_country2.csv");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter sorting parameter (health, income, inflation, life): ");
        String sortingParameter = scanner.nextLine();

        Collections.sort(countries, new CountryComparator(sortingParameter));

        System.out.println("List of countries sorted by " + sortingParameter + ":");
        for (Country country : countries) {
            System.out.println(country);
        }

        writeSortedCountriesToFiles(countries);
    }

    private static List<Country> readCountriesFromFile(String fileName) throws IOException {
        List<Country> countries = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            String name = fields[0];
            double healthExpenditure = Double.parseDouble(fields[1]);
            double income = Double.parseDouble(fields[2]);
            double inflation = Double.parseDouble(fields[3]);
            double lifeExpectancy = Double.parseDouble(fields[4]);
            Country country = new Country(name, healthExpenditure, income, inflation, lifeExpectancy);
            countries.add(country);
        }
        reader.close();
        return countries;
    }

    private static void writeSortedCountriesToFiles(List<Country> countries) throws IOException {
        FileWriter healthAndLifeWriter = new FileWriter("health_and_life.txt");
        FileWriter incomeInflationLifeWriter = new FileWriter("income_inflation_life.txt");

        ArrayList<Country> c2 = new ArrayList<>(countries);

        c2.sort(new CountryComparator("life"));
        countries.sort(new CountryComparator("health"));

        for (int i = 0; i < countries.size(); i++ ) {
            healthAndLifeWriter.write(countries.get(i).name + "     " + c2.get(i).name + "\n");
        }

        ArrayList<Country> c3 = new ArrayList<>(countries);

        c2.sort(new CountryComparator("inflation"));
        c3.sort(new CountryComparator("life2"));
        countries.sort(new CountryComparator("income"));

        for (int i = 0; i < countries.size(); i++ ) {
            incomeInflationLifeWriter.write(countries.get(i).name + "       " + c2.get(i).name + "      " + c3.get(i).name + "\n");
        }

        healthAndLifeWriter.close();
        incomeInflationLifeWriter.close();
    }
}