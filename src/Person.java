public class Person {               //Creating the class "Person"
    private String name;            //Declaring variables
    private String surname;
    private String email;
    Person(String name, String surname, String email){         //Constructor for Person
        this.name=name;
        this.surname=surname;
        this.email=email;
    }
    public String getName(){                                                                                            //Getter for first name
        return this.name;
    }           //Getter for first name
    public void setName(String name){                                                                                   //Setter for first name
        this.name=name;
    }       //Setter for first name
    public String getSurname(){                   //Getter for surname
        return this.surname;
    }
    public void setSurname(String surname){                                                                             //Setter for surname
        this.surname=surname;
    }       //Setter for surname
    public String getEmail(){                                                                                           //Getter for email
        return this.email;
    }                       //Getter for email
    public void setEmail(String email){                                                                                 //Setter for email
        this.email=email;
    }               //Setter for email
    public void printPerson(){                                          //Method to print person information
        System.out.println("Name: "+this.name+" "+this.surname);
        System.out.println("email address: "+this.email);
    }
}
