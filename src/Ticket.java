import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {                 //Creating Ticket class
    private String row;               //Declaring variables of the class
    private int seat;
    private int price;
    private Person person;

    //Constructor for Ticket
    Ticket(String row, int seat, int price, String name, String surname, String email){
        this.row=row;
        this.seat=seat;
        this.price=price;
        this.person=new Person(name, surname, email);
    }
    public String getRow(){                                  //Getter to get the row letter
        return this.row;
    }
    public void setRow(String row) {                                                                                    //Setter for row letter
        this.row = row;
    }       //Setter for row letter
    public int getSeat(){                                                                                               //Getter for seat number
        return this.seat;
    }               //Getter for seat number

    public void setSeat(int seat) {                                                                                     //Setter for seat number
        this.seat = seat;
    }       //Setter for seat number
    public int getPrice(){                                                                                              //Getter for price of ticket
        return this.price;
    }               //Getter for price
    public void setPrice(int price){                                                                                    //Setter for price
        this.price=price;
    }       //Setter for price
    public Person getPerson(){                                                                                          //Getter for person of a ticket
        return this.person;
    }           //Getter for person
    public void setPerson(Person person){                                                                               //Setter for person
        this.person=person;
    }           //Setter for person
    public void printTicket(){                                    //Prints ticket information
        System.out.println("------Ticket information------");
        System.out.println("seat row: "+row+"    seat number: "+seat);
        System.out.println("Name: "+person.getName()+" "+person.getSurname());
        System.out.println("email address: "+person.getEmail());
        System.out.println("Price: £"+price);
        System.out.println("------------------------------");
    }
    public void save(){                          //Saves information of a ticket to a text file
        try{
            //File is named as "row letter""seat number".txt
            File file=new File(getRow()+getSeat()+".txt");
            FileWriter writer=new FileWriter(file);
            String text= "Ticket information\n"+"Seat: "+getRow()+getSeat()+"\n"+
                    "Person: "+getPerson().getName()+" "+getPerson().getSurname()+"\n"+
                    "email: "+getPerson().getEmail()+"\n"+
                    "Price: "+getPrice();
            writer.write(text);
            writer.close();
        }
        catch (IOException e){
            System.out.println("ERROR: IOException");
        }

    }

    //Deletes a text file. Used to delete corresponding text file when canceling a seat.
    public void delete(){
        try{
            File file=new File(getRow()+getSeat()+".txt");
            file.delete();
        }
        catch (Exception e){
            System.out.println("ERROR");
        }
    }
}
