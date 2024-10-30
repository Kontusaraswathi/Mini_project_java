import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class DateCalculator {

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Error: Requires 4 command-line arguments.");
            System.exit(1);
        }

        String input = args[0];
        String refDateStr = args[1];
        String dateFormatStr = args[2];
        String delimiter = args[3];


        DateTimeFormatter formatter = createFormatter(dateFormatStr, delimitcer);
        LocalDate refDate = parseDate(refDateStr, formatter);


        if (input.startsWith("DOB=")) {
            LocalDate dob = parseDate(input.substring(4), formatter);
            Period age = Period.between(dob, refDate);
            System.out.println("Age is " + age.getYears() + " years, " + age.getMonths() + " months, " + age.getDays() + " days");
        } else if (input.startsWith("AGE=")) {
            try {
                String[] ageParts = input.substring(4).split(delimiter);
                int years = Integer.parseInt(ageParts[0]);
                int months = Integer.parseInt(ageParts[1]);
                int days = Integer.parseInt(ageParts[2]);
                LocalDate dob = refDate.minusYears(years).minusMonths(months).minusDays(days);
                System.out.println("DOB: " + dob.format(formatter));
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid AGE format");
            } catch (IndexOutOfBoundsException e){
                System.out.println("Error: Invalid AGE format");
            }

        } else {
            System.out.println("Error: Invalid input format. Use 'DOB=' or 'AGE='.");
        }
    }

    private static DateTimeFormatter createFormatter(String dateFormatStr, String delimiter) {
          String format = dateFormatStr.replace("dIc", delimiter);
          return DateTimeFormatter.ofPattern(format);
    }

    private static LocalDate parseDate(String dateStr, DateTimeFormatter formatter) {
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format: " + e.getMessage());
            System.exit(1);
            return null; // unreachable
        }
    }
}