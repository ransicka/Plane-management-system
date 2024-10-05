import java.util.Scanner;

public class PlaneManagement {
    public static void main(String[] args){
        Scanner input=new Scanner(System.in);
        //This array is used to save all information. Created as a ragged 2D array
        Ticket[][] tickets=new Ticket[4][];
        tickets[0]=new Ticket[14];
        tickets[1]=new Ticket[12];
        tickets[2]=new Ticket[12];
        tickets[3]=new Ticket[14];
        //This array is used to identify, book and cancel seats. Created as a ragged 2D array
        int[][] seatingPlan=new int[4][];
        seatingPlan[0]=new int[14];
        seatingPlan[1]=new int[12];
        seatingPlan[2]=new int[12];
        seatingPlan[3]=new int[14];
        //Welcome message
        System.out.println("Welcome to the Plane Management application");
        //While true loop to repeat until valid input is received
        while(true){
            //try and catch to avoid NumberFormatException
            try{
                //Multiline print statement
                System.out.println("""                                                                                  
                ****************************************************
                *                   MENU OPTIONS                   *
                ****************************************************
                       1) Buy a seat
                       2) Cancel a seat
                       3) Find first available seat
                       4) Show seating plan
                       5) Print tickets information and total sales
                       6) Search ticket
                       0) Quit
                ****************************************************""");
                System.out.print("Please select an option: ");
                String optionStr=input.next();
                int option=Integer.parseInt(optionStr);
                switch (option) {
                    case 1:
                        //Calling buy_seat method
                        buy_seat(input, seatingPlan, tickets);
                        continue;
                    case 2:
                        //Calling cancel_seat method
                        cancel_seat(input, seatingPlan, tickets);
                        continue;
                    case 3:
                        //Calling find_first_available method
                        find_first_available(seatingPlan);
                        continue;
                    case 4:
                        /*Calling show_seating_plan method will print the seating plan showing bought seats with X
                        and available ones with 0. Calling this method will print all information of the
                        tickets bought.*/
                        show_seating_plan(seatingPlan);
                        continue;
                    case 5:
                        print_tickets_info(tickets);
                        continue;
                    case 6:
                        search_ticket(input, tickets);
                        continue;
                    case 0:
                        System.out.println("Exiting the application...");
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                        continue;
                }
                break;
            /*If String input could not be passed as an integer, this error would occur.
            This catch block will prevent that.*/
            }catch (NumberFormatException e){
                System.out.println("Please enter a valid number");
            }
        }
    }
    //buy_seat method.
    public static void buy_seat(Scanner input,int[][] array, Ticket[][] tickets) {
        System.out.println("Enter details for the seat");
        //Calling this method will ask for row letter, validate and return it.
        String rowLetter=rowLetterValidator(input);
        //This method will give a corresponding number for each row for ease of use in arrays.
        int rowNum=returnRowNum(rowLetter);
        //This method will ask for seat number, validate with the row and return the number.
        int seatNum=seatValidator(input, rowNum);
        //Declaring price variable
        int price;

        //getting seatIndex from seatNum for use in accessing arrays
        int seatIndex=seatNum-1;
        //Checking if the seat is available.
        if(array[rowNum][seatIndex]==0){
            System.out.println("Seat is available");
            System.out.println("Please enter your details to book the seat.");
            System.out.println();
            input.nextLine();
            String name;
            //Using while true to get a name without a blank input
            while(true){
                System.out.print("Enter first name: ");
                name=input.nextLine();
                if(name.isBlank()){
                    System.out.println("Name cannot be blank");
                }
                else{
                    break;
                }
            }
            String surname;
            //Using while true to get a surname without a blank input
            while(true){
                System.out.print("Enter surname: ");
                surname=input.nextLine();
                if(surname.isBlank()){
                    System.out.println("Surname cannot be blank");
                }
                else {
                    break;
                }
            }
            String email;
            //Using while true to get a valid email with @ symbol.
            while(true){
                System.out.print("Enter your email: ");
                email=input.nextLine();
                //Checks for @ symbol.
                if(email.contains("@")){
                    break;
                }
                else{
                    System.out.println("Enter a valid email address with @ symbols");
                }
            }
            //Calculating the price for the seat.
            if(seatIndex<5){
                price=200;
            }
            else if(seatIndex<9){
                price=150;
            }
            else{
                price=180;
            }

            //Booking the seat in seatingPlan array.
            array[rowNum][seatIndex]=1;
            //Creating a new ticket object in the relevant tickets array slot.
            tickets[rowNum][seatIndex]=new Ticket(rowLetter, seatNum, price, name, surname, email);
            //This method saves a text file containing the ticket information.
            tickets[rowNum][seatIndex].save();
            System.out.println("Seat is booked successfully\n");
            //Print the ticket information of the booked seat.
            tickets[rowNum][seatIndex].printTicket();
        }
        else{
            System.out.println("Seat is already booked");                                                               
        }
    }
    public static void cancel_seat(Scanner input, int[][] array, Ticket[][] tickets) {
        System.out.println("Cancelling a seat");
        //Used in same manner as it was in buy_seat method to get row and seat.
        String rowLetter=rowLetterValidator(input);
        int rowNum=returnRowNum(rowLetter);
        int seatNum=seatValidator(input, rowNum);

        int seatIndex=seatNum-1;
        if(array[rowNum][seatIndex]==0){
            //Display these messages and returns to main menu if seat wasn't booked already.
            System.out.println("Seat is not booked already");
            System.out.println("Returning to the menu");
        }
        else{
            System.out.println("Seat "+tickets[rowNum][seatIndex].getRow()+tickets[rowNum][seatIndex].getSeat()+" was cancelled successfully");
            //Makes the corresponding array slot available.
            array[rowNum][seatIndex]=0;
            //Deletes the text file of ticket information
            tickets[rowNum][seatIndex].delete();
            //Deletes the corresponding ticket information in the array
            tickets[rowNum][seatIndex]=null;
            System.out.println("Returning to the menu");
        }
    }
    public static void find_first_available(int[][] array) {
        //Setting a flag to break the for loop when necessary.
        boolean seatFound=false;
        //Traversing rows.
        for(int i=0; i<array.length; i++){
            //Traversing seats in each row.
            for(int j=0; j<array[i].length; j++){
                if(array[i][j]==0){
                    //Turning up the flag to true.
                    seatFound=true;
                    String row;
                    switch (i){
                        case 0:
                            row="A";
                            break;
                        case 1:
                            row="B";
                            break;
                        case 2:
                            row="C";
                            break;
                        default:
                            row="D";
                            break;
                    }
                    int seat=j+1;
                    //Prints this message if the seat is available.
                    System.out.println("Seat "+row+seat+" is still available");
                    //Breaks the inner for loop.
                    break;
                }
            }
            //Breaks the outer for loop using the flag.
            if(seatFound){
                break;
            }
        }
        //If all the seats are booked, this message will be displayed and returned to the menu.
        if(!seatFound){
        System.out.println("No seat is available");
        }
    }
    public static void show_seating_plan(int[][]array) {
        //Traversing each row.
        for(int i=0;i< array.length;i++){
            if(i==2){
                //Keeps a space between rows B and C.
                System.out.println();
            }
            //Traversing seats in each row.
            for(int j=0;j< array[i].length;j++){
                if(array[i][j]==0){
                    //Prints out a O for each available seat.
                    System.out.print("O");
                }
                else{
                    //Prints out an X for each booked seat.
                    System.out.print("X");
                }
            }
            //Goes for a new line at the end of each row.
            System.out.println();
        }
        System.out.println();
    }
    public static void print_tickets_info(Ticket[][] array){
        //Declaring a variable to store the total price of tickets.
        int totalPrice=0;
        //Traverse the tickets array using an enhanced nested for loop
        for(Ticket[] row:array){
            for(Ticket seat:row){
                //Checks if the seat was booked and prints all information if booked.
                if(seat != null){
                    seat.printTicket();
                    //Adds each ticket price to the total.
                    totalPrice+= seat.getPrice();
                }
            }
        }
        if(totalPrice==0){
            //Displays if no seats were sold.
            System.out.println("Not any seat was sold");
        }
        System.out.println("""
        ---------------------------------------------------
        ---------------------------------------------------""");
        //Displays the total after all separate ticket information is displayed.
        System.out.println("Total price of the all tickets sold: £"+totalPrice);
    }
    /*This method will search for a specific seat and show whether it is available or not.
    If available, will print the ticket information.*/
    public static void search_ticket(Scanner input, Ticket[][] tickets){
        String rowLetter=rowLetterValidator(input);
        int rowNum=returnRowNum(rowLetter);
        int seatNum=seatValidator(input, rowNum);
        //Gets and validates row and seat the same way as in buy and cancel seat methods.

        int seatIndex=seatNum-1;
        Ticket seat=tickets[rowNum][seatIndex];
        //If the seat was booked, it will not be null. Therefore, will print its information using getters.
        if(tickets[rowNum][seatIndex] !=null){
            System.out.println("seat row: "+seat.getRow()+"    seat number: "+seat.getSeat());
            System.out.println("Name: "+seat.getPerson().getName()+"       surname: "+seat.getPerson().getSurname());
            System.out.println("email address: "+seat.getPerson().getEmail());
            System.out.println("Price: £"+seat.getPrice());
        }
        else{
            //Displays the below message if seat is available.
            System.out.println("This seat is available");
        }
    }
    //This method is used to get a row letter from user and checks if the entered row letter is valid.
    public static String rowLetterValidator(Scanner input) {
        String rowLetter;       //Declaring variable for row letter.
        //Loops until user gives a valid row letter(A/B/C/D).
        while (true) {
            System.out.print("Enter the row letter: ");
            rowLetter = input.next();
            //Converts to capital letters if user inputs simple letters.
            rowLetter = rowLetter.toUpperCase();
            //Checks if the letter is one of A,B,C or D.
            if (rowLetter.equals("A") || rowLetter.equals("B") || rowLetter.equals("C") || rowLetter.equals("D")) {
                break;                         //Breaks the loop if letter is valid.
            } else {
                System.out.println("Entered row letter is invalid");
            }
        }
        return rowLetter;          //Returns the valid row letter.
    }
    /*Gets a corresponding row number for the row letter.
    It will be useful in accessing seating plan and ticket arrays.*/
    public static int returnRowNum(String rowLetter){
        int rowNum;
        switch (rowLetter) {
            case "A":
                rowNum = 0;
                break;
            case "B":
                rowNum = 1;
                break;
            case "C":
                rowNum = 2;
                break;
            default:
                rowNum = 3;
        }
        return rowNum;                 //Returns the row number.
    }
    /*Gets seat number from user, checks if it is a valid number and
    checks whether such seat exists in the above entered row.*/
    public static int seatValidator(Scanner input, int rowNum){
        int seatNum;
        while (true) {
            //Try and catch block to prevent errors if user enters strings or other invalid inputs.
            try{
                System.out.print("Enter the seat number: ");
                String seatNumStr=input.next();
                seatNum=Integer.parseInt(seatNumStr);
                if ((rowNum == 1 || rowNum == 2) & seatNum > 12 & seatNum <= 14) {
                    /*This message will display if user asks for a seat number 13 or 14
                    in row B or C, where there are only 12 seats available in those rows.*/
                    System.out.println("Only 12 seats are available in this row");
                } else if (seatNum > 0 && seatNum <= 14) {
                    break;
                } else {
                    System.out.println("Invalid seat number");
                }
            }
            catch (NumberFormatException e){
                System.out.println("Please enter a valid number");
            }
        }
        return seatNum;              //Returns a valid seat number
    }
}