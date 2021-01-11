
/*
Implement a console application that connects over JDBC to the database
from the first exercise and calculate based on information from
this database how many passengers the airport can serve.

	For example:
We have 2 Boeing 747 (467 seats) and 5 Airbus A320 (186 seats)
We have 2 pilots that can fly both Boeing 747 and Airbus A320,
1 pilot can fly Boeing 747, and 5 pilots can fly Airbus A320.
So we can use 1 Boeing 747 (2 pilots required) and 3 Airbus A320
(2 pilots for each). Total seats: 467 + (3 * 186) = 1025
 */

public class Main {
    public static void main(String[] args) {
        String airport = "SYD";
        int result = DataSource.getNumberOfPassenger(airport);
        System.out.printf("The airport %s can serve %d passengers", airport,result);
    }

}
