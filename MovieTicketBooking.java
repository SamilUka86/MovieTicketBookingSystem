import java.util.Scanner;

//Main Class --> Contains PSVM & a static Scanner class object which is used for entire program
class Main {
    // Only one scanner used
    static Scanner scanner = new Scanner(System.in);

    // HomePage method-->Ask Users if they either want to access system as developer
    // or customer
    static void HomePage() {
        System.out.println("Enter 1 for Developer");
        System.out.println("Enter 2 for Customer");
        System.out.println("Enter 3 To Terminate programme");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                scanner.nextLine();
                System.out.println("Enter Password to access developer mode: ");
                String tryPass = scanner.nextLine();
                if (tryPass.equals(Developer.developer_password)) {
                    // Will enter if block only if enter password matches actual developer mode
                    // password in developer class
                    Developer.developerAction();// Calling developerAction static method from Developer class
                } else { // Will enter if password doesn't match actual password
                    System.out.println("Invalid password");
                    System.out.println("Back to HomePage");
                    HomePage();// Uses Recursion and calls it self to ask again how user would like to access
                               // programme
                }
                break;
            case 2:
                Customer.customerAction();// Calling customerAction static method from Customer class
                break;
            case 3:
                System.out.println("Program terminated successfully");// To end the program
                break;
            default:
                System.out.println("Enter valid choice!");
                // Recursion-->calling HomePage Again so that user can enter appropriate input.
                HomePage();
                break;
        }
    }

    public static void main(String[] args) {
        // Intialize all the default Movies from each subclass of Movies class
        ComedyMovies.defaultMovies();
        RomanceMovies.defaultMovies();
        ActionMovies.defaultMovies();
        SliceOfLifeMovies.defaultMovies();
        HorrorMovies.defaultMovies();
        AnimatedMovies.defaultMovies();
        ScienceFictionMovies.defaultMovies();
        // Intialize all the default theatre objects from Theatre class
        Theatre.defaultTheatre();
        // After everything is intialized it calls HomePage method which asks user about
        // how they want to access program
        HomePage();
    }
}

class Developer {
    // PassWord Set to access Developer Mode from HomePage Method
    static String developer_password = "@integration09";

    // developerAction-->For the possible actions developer can do:
    // (1)Add Movies
    // (2)Delete Movies
    static void developerAction() {
        System.out.println("What would you like to do ?");
        System.out.println("Enter 1 to Add a Movie");
        System.out.println("Enter 2 to Delete a Movie");
        System.out.println("Enter 3 to for logging out and going back to Home page");
        int choice = Main.scanner.nextInt();
        switch (choice) {
            case 1:
                // Calls addMovies method from the same class
                addMovies();
                // After movie is added and the method is exited,it comes back to
                // developerAction method
                // and it calls it self to provide the interface it used to have before
                // addMovies was called(one type of exit interface).
                developerAction();
                break;
            case 2:
                /*
                 * In delete movies action you ask for movie name first which is String input
                 * but previously
                 * when you asked for choice of what developer would like to do,it took inout of
                 * integer so
                 * String input just after any numeric input will cause String to set null(cause
                 * when you press
                 * "Enter" after numeric value it will stored in String automatically).To avoid
                 * such situations
                 * we added Main.scanner.nextLine() which we will take that entered
                 * "Enter key press" inside itself
                 */
                Main.scanner.nextLine();
                // Calls deleteMovies method
                deleteMovies();
                // After exiting deleteMovies method user gets the same interface they had
                // before
                developerAction();
                break;
            case 3:
                // If user chose to exit developer mode
                System.out.println("Going Back to Home Page");
                // calls first window
                Main.HomePage();
                break;
            default:
                System.out.println("Enter valid choice next time,Going back to home page by default");
                Main.HomePage();
                break;
        }

    }

    // Allows developer to add the movies to their desired theatre and categories
    // from the list and allows to set Base Price
    static void addMovies() {
        boolean check = false;
        // Even A single movie can have multiple genre
        // If the movie I want to add has multiple genre then I will have to add that
        // movie
        // in to multiple subclasses of Movie class based on genre entered
        System.out.println("How many Genre does your movie have?(1-7)//Press 8 to exit.");
        int NumOfGenre = Main.scanner.nextInt();
        do {
            // Allows user to exit AddMovies method
            if (NumOfGenre == 8) {
                return;
            }
            // Verifies if Enter number of Genres is valid(reason:There are only 7 genres
            // available)
            else if (NumOfGenre >= 1 && NumOfGenre <= 7) {
                check = false;
            } else {
                System.out.println("Enter valid Number of Genre(1-7)/Press '8' to exit ");
                NumOfGenre = Main.scanner.nextInt();
                check = true;
            }
        } while (check);
        // Required to avoid GenreofMovie[0] becoming null
        Main.scanner.nextLine();
        // Creating an array which will store all the genres of single movie
        String GenreofMovie[] = new String[NumOfGenre];
        // Displays available Genres using an array(data type:String) created in Movies
        // class
        System.out.println("Available Genres:");
        for (int i = 0; i < Movies.existingGenre.length; i++) {
            System.out.println("*" + Movies.existingGenre[i]);
        }
        boolean check1 = false;
        System.out.println("Enter Genres of Movie(In the above format)://Press Q to exit.");
        // Takes input of all the Genres the movie you want to add has:
        for (int i = 0; i < NumOfGenre; i++) {
            for (;;) {
                check1 = false;
                GenreofMovie[i] = Main.scanner.nextLine();
                // Allows user to exit AddMovies method at this stage
                if (GenreofMovie[i].equals("Q")) {
                    return;
                }
                // Verifies if entered Genre name matches existing Genre or not
                // if it doesn't--> it asks to re-enter or to exit the method
                for (int j = 0; j < Movies.existingGenre.length; j++) {
                    if (GenreofMovie[i].equals(Movies.existingGenre[j])) {
                        check1 = true;
                        break;
                    }
                }
                if (check1 == false) {
                    System.out.println("Enter valid Genre or Press Q to exit.");
                } else {
                    break;
                }
            }
        }
        System.out.println("Enter the name of movie you want to add/Press Q to exit:");
        String name = Main.scanner.nextLine();
        if (name.equals("Q")) {
            // User still has time to exit AddMovies method
            return;
        }
        // Adds movie to all the subclasses(GenreClasses)' as an object
        for (int i = 0; i < NumOfGenre; i++) {
            switch (GenreofMovie[i]) {
                case "Comedy":

                    ComedyMovies.ComedyMovieslist[ComedyMovies.index] = new ComedyMovies(name);
                    ComedyMovies.index++;
                    break;
                case "Action":

                    ActionMovies.ActionMovieslist[ActionMovies.index] = new ActionMovies(name);
                    ActionMovies.index++;
                    break;
                case "Slice of Life":
                    SliceOfLifeMovies.SliceOfLifeMovieslist[SliceOfLifeMovies.index] = new SliceOfLifeMovies(name);
                    SliceOfLifeMovies.index++;
                    break;
                case "Horror":
                    HorrorMovies.HorrorMovieslist[HorrorMovies.index] = new HorrorMovies(name);
                    HorrorMovies.index++;
                    break;
                case "Science Fiction":
                    ScienceFictionMovies.ScienceFictionMovieslist[ScienceFictionMovies.index] = new ScienceFictionMovies(
                            name);
                    ScienceFictionMovies.index++;
                    break;
                case "Romance":
                    RomanceMovies.RomanceMovieslist[RomanceMovies.index] = new RomanceMovies(name);
                    RomanceMovies.index++;
                    break;
                case "Animated":
                    AnimatedMovies.AnimatedMovieslist[AnimatedMovies.index] = new AnimatedMovies(name);
                    AnimatedMovies.index++;

                    break;

            }
        }
        // Now user cannot exit addMovies method unless they add the movie in atleast
        // one theatre
        // For the above purpose we created following variable
        int theatre_added_to_movie_count = 0;
        // Shows available Theatres
        System.out.println("Available Theatres: ");
        for (int i = 0; i < Theatre.index; i++) {
            System.out.println("* " + Theatre.TheatreList[i].name);
        }
        System.out.println("Enter the name of theatres you would like to add this movie to: ");
        for (;;) {
            check = false;
            String theatre_name = Main.scanner.nextLine();
            // Can never execute unless the movie has been added into atleast one theatre
            if (theatre_name.equals("Q") && theatre_added_to_movie_count != 0) {
                System.out.println("Exited Add movie method succesfully");
                return;
            }
            // Check if theatre with such name exits,if not-->Re enter
            for (int i = 0; i < Theatre.index; i++) {
                if (Theatre.TheatreList[i].name.equals(theatre_name)) {
                    Theatre.TheatreList[i].availableMovies[Theatre.TheatreList[i].movieIndex] = name;
                    System.out.println("Enter the Base price for the Movie for selected theatre");
                    Theatre.TheatreList[i].basePrice[Theatre.TheatreList[i].movieIndex] = Main.scanner.nextInt();
                    Theatre.TheatreList[i].movieIndex++;
                    System.out.println("Movie Successfully added to " + Theatre.TheatreList[i].name);
                    check = true;
                    Main.scanner.nextLine();
                    break;
                }
            }
            if (check == false) {
                System.out.println("Invalid Theatre Name");
                System.out.println("Re-enter Theatre Name.");
            }
            // Asks if you want to add same movie into another theatre also.
            else {
                theatre_added_to_movie_count++;
                System.out.println(
                        "Enter another Theatre in which you would like to add this movie to or Press Q to  exit: ");
            }

        }

    }

    // Allows User to delete desired existing movie from all the subclasses of
    // Movies class(in which that movie exits)
    // and also delete it from the theatres in which it was available in
    static void deleteMovies() {
        System.out.println("Enter the name of Movie you want to delete or press Q to exit");
        String name = Main.scanner.nextLine();
        boolean check = true;
        for (; check;) {
            // Allows user to exit deleteMovies method
            if (name.equals("Q")) {
                return;
            }
            /*
             * Logic for deleting movie through shifting of each element to the left(from
             * the index of that movie
             * to the length of CategoryMovieslist array) and setting last object to null
             */
            for (int i = 0; i < ComedyMovies.index; i++) {
                // outerloop-->Runs from 0th index to the index to which movies has been added
                if (name.equals(ComedyMovies.ComedyMovieslist[i].name)) {
                    // if-->check if any movie from ComedyMovies class matches entered movie name
                    // Enter -->if-->Movies exits in the given array
                    // Hence,innerloop--> will run from Index of the entered movie in that array to
                    // the
                    // index to which movies are stored and it will shift all movies from that
                    // indexes to left by 1 index
                    for (int j = i; j < ComedyMovies.index - 1; j++) {
                        ComedyMovies.ComedyMovieslist[j] = ComedyMovies.ComedyMovieslist[j + 1];
                    }
                    // Will set last object to null
                    // cause movie deleted,other wise second and last movie will be same
                    ComedyMovies.ComedyMovieslist[ComedyMovies.index - 1] = null;
                    ComedyMovies.index--;
                    check = false;
                    break;
                }
            }
            // Same logics for all the following loops
            for (int i = 0; i < ActionMovies.index; i++) {
                if (name.equals(ActionMovies.ActionMovieslist[i].name)) {

                    for (int j = i; j < ActionMovies.index - 1; j++) {
                        ActionMovies.ActionMovieslist[j] = ActionMovies.ActionMovieslist[j + 1];
                    }
                    ActionMovies.ActionMovieslist[ActionMovies.index - 1] = null;
                    ActionMovies.index--;
                    check = false;
                    break;
                }
            }
            for (int i = 0; i < SliceOfLifeMovies.index; i++) {
                if (name.equals(SliceOfLifeMovies.SliceOfLifeMovieslist[i].name)) {

                    for (int j = i; j < SliceOfLifeMovies.index - 1; j++) {
                        SliceOfLifeMovies.SliceOfLifeMovieslist[j] = SliceOfLifeMovies.SliceOfLifeMovieslist[j + 1];
                    }
                    SliceOfLifeMovies.SliceOfLifeMovieslist[SliceOfLifeMovies.index - 1] = null;
                    SliceOfLifeMovies.index--;
                    check = false;
                    break;
                }
            }
            for (int i = 0; i < HorrorMovies.index; i++) {
                if (name.equals(HorrorMovies.HorrorMovieslist[i].name)) {

                    for (int j = i; j < HorrorMovies.index - 1; j++) {
                        HorrorMovies.HorrorMovieslist[j] = HorrorMovies.HorrorMovieslist[j + 1];
                    }
                    HorrorMovies.HorrorMovieslist[HorrorMovies.index - 1] = null;
                    HorrorMovies.index--;
                    check = false;
                    break;
                }
            }
            for (int i = 0; i < ScienceFictionMovies.index; i++) {
                if (name.equals(ScienceFictionMovies.ScienceFictionMovieslist[i].name)) {

                    for (int j = i; j < ScienceFictionMovies.index - 1; j++) {
                        ScienceFictionMovies.ScienceFictionMovieslist[j] = ScienceFictionMovies.ScienceFictionMovieslist[j
                                + 1];
                    }
                    ScienceFictionMovies.ScienceFictionMovieslist[ScienceFictionMovies.index - 1] = null;
                    ScienceFictionMovies.index--;
                    check = false;
                    break;
                }
            }
            for (int i = 0; i < RomanceMovies.index; i++) {
                if (name.equals(RomanceMovies.RomanceMovieslist[i].name)) {

                    for (int j = i; j < RomanceMovies.index - 1; j++) {
                        RomanceMovies.RomanceMovieslist[j] = RomanceMovies.RomanceMovieslist[j + 1];
                    }
                    RomanceMovies.RomanceMovieslist[RomanceMovies.index - 1] = null;
                    RomanceMovies.index--;
                    check = false;
                    break;
                }
            }
            for (int i = 0; i < AnimatedMovies.index; i++) {
                if (name.equals(AnimatedMovies.AnimatedMovieslist[i].name)) {

                    for (int j = i; j < AnimatedMovies.index - 1; j++) {
                        AnimatedMovies.AnimatedMovieslist[j] = AnimatedMovies.AnimatedMovieslist[j + 1];
                    }
                    AnimatedMovies.AnimatedMovieslist[AnimatedMovies.index - 1] = null;
                    AnimatedMovies.index--;
                    check = false;
                    break;
                }
            }

            if (check) {
                System.out.println("Invalid Name: ");
                System.out.println("Re-enter the name of movie or press 'Q' to exit:");
                name = Main.scanner.nextLine();

            } else {
                break;
            }

        }
        // For deleting Movie from Theatre
        for (int i = 0; i < Theatre.index; i++) {

            for (int j = 0; j < Theatre.TheatreList[i].movieIndex; j++) {
                if (Theatre.TheatreList[i].availableMovies[j].equals(name)) {
                    for (int k = j; k < Theatre.TheatreList[i].movieIndex - 1; k++) {
                        Theatre.TheatreList[i].availableMovies[k] = Theatre.TheatreList[i].availableMovies[k + 1];
                    }
                    Theatre.TheatreList[i].availableMovies[Theatre.TheatreList[i].movieIndex - 1] = null;
                    Theatre.TheatreList[i].movieIndex--;
                }
            }
        }
        System.out.println("Movie Deleted Successfully");
    }
}

class Customer {
    static int customerCount = 0; // Number of existing customers
    static Customer customerList[] = new Customer[20];// Limited custommer array
    String customer_name;
    String user_name;
    String pass;
    static int current_customer_index;// Will be set at run time by the code we will do below
    double total_bill;
    // Will store the index of the Theatre that user wants to book ticket from
    int choosenTheatreIndex = 0;
    // Will store the index of the movie that user wants to book in availableMovies
    // array of theatre class
    int choosenMovieIndex = 0;

    // Constructor to set customer details
    Customer(String customer_name, String user_name, String pass) {
        this.customer_name = customer_name;
        this.user_name = user_name;
        this.pass = pass;
    }

    // Will start after you select choice 2 in HomePage of Main class
    // Customer can do multiplle action beforing logging in:
    // (1)Create acc
    // (2)Login acc
    // (3)Return to Homepage
    static void customerAction() {

        System.out.println("Enter 1 to Create account");
        System.out.println("Enter 2 to Login account");
        System.out.println("Enter 3 to return to the Home page");
        int choice = Main.scanner.nextInt();

        switch (choice) {
            case 1:
                // Necessary to avoid entering null value to the first string input taken in
                // createAcc method
                Main.scanner.nextLine();
                createAccount();
                // Will give same interface to the customer after exiting create acc method
                customerAction();
                break;

            case 2:
                Main.scanner.nextLine();
                login();
                customerAction();
                break;

            case 3:
                // Calls HomePage method and user gets HomePage interface again
                System.out.println("Returning to HomePage");
                Main.HomePage();
                break;

            default:
                // Will ask to enter valid input and call itself until the input is valid
                System.out.println("Enter Valid Option:");
                customerAction();
        }
    }

    static void createAccount() {
        // If there are more than limited number of customers
        if (customerCount >= customerList.length) {
            System.out.println("Too Many Customers!");
            System.out.println("Cannot store any more data. Customer can't create account.");
            return;
        }
        System.out.println("Enter your name: ");
        String name = Main.scanner.nextLine();

        System.out.println("Enter a username: ");
        String username = Main.scanner.nextLine();

        // Check if the username is already taken
        for (int i = 0; i < customerCount; i++) {
            if (customerList[i].user_name.equals(username)) {
                System.out.println(
                        "An account with this name already exists. Try logging in or enter different user name.");
                return;
            }
        }

        System.out.println("Enter a password: ");
        String password = Main.scanner.nextLine();

        // Save the new user
        customerList[customerCount] = new Customer(name, username, password);
        customerCount++;
        System.out.println("Account created successfully!");
        System.out.println("Returning to customer page.");
    }

    // Allows user to login their account
    static void login() {

        System.out.println("Enter your username or press Q to exit");
        String username = Main.scanner.nextLine();
        if (username.equals("Q")) {
            System.out.println("Exiting login method");
            return;
        } else {
            System.out.println("Enter password: ");
            String password = Main.scanner.nextLine();
            boolean check = true;
            for (;;) {
                check = true;

                for (int i = 0; i < customerCount; i++) {
                    if (customerList[i].user_name.equals(username) && customerList[i].pass.equals(password)) {
                        System.out.println("Login successful! Welcome, " + username);
                        // After login all the action for example ticket booking,storing total_bill will
                        // be done for logined user
                        // but logined user will be "i"th customer cause they just logged in so
                        // current_customer_index will be set to value i
                        // And this value will be used in customerActionAfterLogin method
                        current_customer_index = i;
                        check = false;
                        break;
                    }
                }
                if (check) {
                    System.out.println("Invalid details");
                    System.out.println("Re-enter correct username or Press Q to exit:");
                    username = Main.scanner.nextLine();
                    if (username.equals("Q")) {
                        System.out.println("Exiting Login method");
                        System.out.println("Returning to customer page");
                        break;
                    } else {
                        System.out.println("Re-Enter correct password");
                        password = Main.scanner.nextLine();
                    }
                } else {
                    break;
                }
            }
            // Will call the customerActionAfterLogin if the login is successful
            // else it will keep asking for correct password and username or ask to exit the
            // login Method
            if (!check) {
                customerActionAfterLogin();
            }
        }
    }

    // Contains the actions customer can do after logging in
    // (1)see all movies
    // (2)see movie sorted by each Genre as per input by user
    // (3)logout
    static void customerActionAfterLogin() {
        System.out.println("What would you like to do?");
        System.out.println("Enter 1 to see list of All Movies (sorted by genre):");
        System.out.println("Enter 2 to see list of Comedy Movies :");
        System.out.println("Enter 3 to see list of Slice Of Life Movies :");
        System.out.println("Enter 4 to see list of Romance Movies :");
        System.out.println("Enter 5 to see list of Science Fiction Movies :");
        System.out.println("Enter 6 to see list of Animated Movies :");
        System.out.println("Enter 7 to see list of Horror Movies :");
        System.out.println("Enter 8 to see list of Action Movies :");
        System.out.println("Enter 9 to logout:");
        int choice = Main.scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println();
                System.out.println("*****Comedy Movies:***** ");
                ComedyMovies.print();
                System.out.println();
                System.out.println("*****Slice of Life Movies:*****");
                SliceOfLifeMovies.print();
                System.out.println();
                System.out.println("*****Romance Movies*****");
                RomanceMovies.print();
                System.out.println();
                System.out.println("*****Science Fiction Movies*****");
                ScienceFictionMovies.print();
                System.out.println();
                System.out.println("*****Animated Movies:*****");
                AnimatedMovies.print();
                System.out.println();
                System.out.println("*****Horror Movies*****");
                HorrorMovies.print();
                System.out.println();
                System.out.println("*****Action Movies*****");
                ActionMovies.print();
                System.out.println("");
                System.out.println("**************************************************");
                System.out.println("");
                Main.scanner.nextLine();
                // Will Call bookYourMovie method for current loggined customer
                // Same logic for all the cases
                customerList[current_customer_index].bookYourMovie();
                customerActionAfterLogin();
                break;

            case 2:
                System.out.println("*****Comedy Movies:***** ");
                ComedyMovies.print();
                Main.scanner.nextLine();
                customerList[current_customer_index].bookYourMovie();
                customerActionAfterLogin();
                break;

            case 3:
                System.out.println("*****Slice of Life Movies:*****");
                SliceOfLifeMovies.print();
                Main.scanner.nextLine();
                customerList[current_customer_index].bookYourMovie();
                customerActionAfterLogin();
                break;

            case 4:
                System.out.println("*****Romance Movies*****");
                RomanceMovies.print();
                Main.scanner.nextLine();
                customerList[current_customer_index].bookYourMovie();
                customerActionAfterLogin();
                break;

            case 5:
                System.out.println("*****Science Fiction Movies*****");
                ScienceFictionMovies.print();
                Main.scanner.nextLine();
                customerList[current_customer_index].bookYourMovie();
                customerActionAfterLogin();
                break;

            case 6:
                System.out.println("*****Animated Movies:*****");
                AnimatedMovies.print();
                Main.scanner.nextLine();
                customerList[current_customer_index].bookYourMovie();
                customerActionAfterLogin();
                break;

            case 7:
                System.out.println("*****Horror Movies*****");
                HorrorMovies.print();
                Main.scanner.nextLine();
                customerList[current_customer_index].bookYourMovie();
                customerActionAfterLogin();
                break;

            case 8:
                System.out.println("*****Action Movies*****");
                ActionMovies.print();
                Main.scanner.nextLine();
                customerList[current_customer_index].bookYourMovie();
                customerActionAfterLogin();
                break;

            case 9:
                System.out.println("log out succesful");
                System.out.println("Back to Customer Page");
                return;

            default:
                System.out.println("Enter Valid Option !");
                customerActionAfterLogin();
        }
    }

    // Will allows user to book their ticket
    // It is a non static method cause each customer will have different total_bill
    // variable
    // value so each customer will have different booking history.
    void bookYourMovie() {
        boolean checkQpress = true;
        System.out.println("Enter the name of the movie you want to buy tickets for/Press Q to exit.");
        String nameofBooking;
        for (;;) {
            checkQpress = false;
            nameofBooking = Main.scanner.nextLine();
            if (Movies.checkMovieExist(nameofBooking)) {
                // Checks if such Movies exists by accessing checkMovieExist static method
                // from Movies class which returns boolean value
                break;
            } else if (nameofBooking.equals("Q")) {
                // If user doesn't want to book movie
                checkQpress = true;
                System.out.println("Exiting Book your Movie method.");
                return;
            } else {
                // If mistyped movie name
                System.out.println("Invalid name,Re Enter Movie Name or Press Q to Exit");
            }
        }
        if (!checkQpress)// not required-->but works okay-->doesn't want to take risks with recursion
        {
            System.out.println("Available Theatres for the movie *" + nameofBooking + "*");
            // Shows available theatre for the movie name user entered
            for (int i = 0; i < Theatre.index; i++) {
                for (int j = 0; j < Theatre.TheatreList[i].movieIndex; j++) {
                    if (Theatre.TheatreList[i].availableMovies[j].equals(nameofBooking)) {

                        System.out.println("* " + Theatre.TheatreList[i].name);
                        System.out.println("Location : " + Theatre.TheatreList[i].location);
                    }
                }
            }
            System.out.println("Enter the name of theatre you want to book ticket in : ");
            String nameOfTheatre = Main.scanner.nextLine();
            boolean check = false;
            // sets the index of the theatre in which you want to book ticket in
            for (;;) {
                for (int i = 0; i < Theatre.index; i++) {
                    if (Theatre.TheatreList[i].name.equals(nameOfTheatre)) {
                        this.choosenTheatreIndex = i;
                        check = true;
                        break;
                    }
                }
                if (check == false) {
                    System.out.println("Invalid Theatre Name. Re enter theatre name.");
                    nameOfTheatre = Main.scanner.nextLine();
                } else {
                    break;
                }
            }
            for (int i = 0; i < Theatre.TheatreList[choosenTheatreIndex].movieIndex; i++) {

                if (Theatre.TheatreList[choosenTheatreIndex].availableMovies[i].equals(nameofBooking)) {
                    // sets the index of that movie from the choosen theatre's availableMovies array
                    choosenMovieIndex = i;
                    break;
                }
            }
            // Shows prices for the different scenerios for the movie
            double basePrice_local = Theatre.TheatreList[this.choosenTheatreIndex].basePrice[this.choosenMovieIndex];
            System.out.println("Pricing For Your Movie In The Selected Theatre :");
            System.out.println("Seat: Silver");
            System.out.println("Timings:");
            System.out.println("Morning : price--> " + (calculateBasePrice(nameOfTheatre, nameofBooking)
                    * Theatre.timeFactor[0] * Theatre.seatFactor[0]));
            System.out.println("Afternoon : price--> " + (calculateBasePrice(nameOfTheatre, nameofBooking)
                    * Theatre.timeFactor[1] * Theatre.seatFactor[0]));
            System.out.println("Night : price--> " + (calculateBasePrice(nameOfTheatre, nameofBooking)
                    * Theatre.timeFactor[2] * Theatre.seatFactor[0]));
            System.out.println("Seat: Gold");
            System.out.println("Timings:");
            System.out.println("Morning : price--> " + (calculateBasePrice(nameOfTheatre, nameofBooking)
                    * Theatre.timeFactor[0] * Theatre.seatFactor[1]));
            System.out.println("Afternoon : price--> " + (calculateBasePrice(nameOfTheatre, nameofBooking)
                    * Theatre.timeFactor[1] * Theatre.seatFactor[1]));
            System.out.println("Night : price--> " + (calculateBasePrice(nameOfTheatre, nameofBooking)
                    * Theatre.timeFactor[2] * Theatre.seatFactor[1]));
            System.out.println("Seat: Platinum");
            System.out.println("Timings:");
            System.out.println("Morning : price--> " + (calculateBasePrice(nameOfTheatre, nameofBooking)
                    * Theatre.timeFactor[0] * Theatre.seatFactor[2]));
            System.out.println("Afternoon : price--> " + (calculateBasePrice(nameOfTheatre, nameofBooking)
                    * Theatre.timeFactor[1] * Theatre.seatFactor[2]));
            System.out.println("Night : price--> " + (calculateBasePrice(nameOfTheatre, nameofBooking)
                    * Theatre.timeFactor[2] * Theatre.seatFactor[2]));
            System.out.println("Select Seat Category:(Enter-->1-->Silver,2-->Gold,3-->Platinum) ");
            int seatCategory = Main.scanner.nextInt();
            seatCategory--;// Required cause array index starts from 0 but input is taken from 1

            for (;;) {
                // Verifies correct input for seat Category
                if (seatCategory >= 0 && seatCategory <= 2) {
                    break;
                } else {
                    System.out.println("Enter valid seat category(1-3)");
                    seatCategory = Main.scanner.nextInt();
                    seatCategory--;
                }
            }
            System.out.println("Enter timings for your show:--> 1-->Morning,2-->Afternoon,3-->Night");
            int timeCategory = Main.scanner.nextInt();
            timeCategory--;// Required cause array index starts from 0 but input is taken from 1
            for (;;) {
                // verifies correct inout for timeCategory
                if (timeCategory >= 0 && timeCategory <= 2) {
                    break;
                } else {
                    System.out.println("Re-enter Enter valid timings.");
                    timeCategory = Main.scanner.nextInt();
                    timeCategory--;
                }
            }
            // Asks for how many people do they want to buy ticket for
            System.out.println("Enter the Quantity for tickets: ");
            int quantity = Main.scanner.nextInt();
            for (;;) {
                // Verifies Quantity
                if (quantity <= 0) {
                    System.out.println("Quanity should atleast be 1.");
                    System.out.println("Re-Enter Quantity");
                    quantity = Main.scanner.nextInt();
                } else {
                    break;
                }
            }
            // Prints all the bill details:(I should have done that in some print bill
            // method for better coding)
            System.out.println("*****************************************************************");
            System.out.println("Customer Name : " + this.customer_name);
            System.out.println("Customer ID : " + this.user_name);
            System.out.println("Theatre name :" + Theatre.TheatreList[choosenTheatreIndex].name);
            System.out.println("Theatre Location :" + Theatre.TheatreList[choosenTheatreIndex].location);
            System.out.println(
                    "Movie Name : " + Theatre.TheatreList[choosenTheatreIndex].availableMovies[choosenMovieIndex]);
            System.out.print("Seat : ");
            if (seatCategory == 0) {
                System.out.println("Silver");
            } else if (seatCategory == 1) {
                System.out.println("Gold");
            } else {
                System.out.println("Platinum");
            }
            System.out.print("Show time: ");
            if (timeCategory == 0) {
                System.out.println("Morning");
            } else if (timeCategory == 1) {
                System.out.println("Afternoon");
            } else {
                System.out.println("Night");
            }
            System.out.println("Quantity : " + quantity);
            // Calculates bill based on the base price of that movie in that theatre and the
            // input taken.
            calculateTotalBill(basePrice_local, seatCategory, timeCategory, quantity);
        }
    }

    // Returns the base price of the choosen movie from the choosen theatre
    double calculateBasePrice(String nameOfTheatre, String nameOfBooking) {
        double price = 0;
        for (int i = 0; i < Theatre.index; i++) {
            if (Theatre.TheatreList[i].name.equals(nameOfTheatre)) {
                for (int j = 0; j < Theatre.TheatreList[i].movieIndex; j++) {
                    if (Theatre.TheatreList[i].availableMovies[j].equals(nameOfBooking)) {
                        price = Theatre.TheatreList[i].basePrice[j];
                    }
                }
                break;
            }
        }
        return price;
    }

    // creates final bill based on the seatfactor,timefactor and quantity applied to
    // base price
    void calculateTotalBill(double basePrice, int seatCategory, int timeCategory, int quantity) {
        total_bill = basePrice * Theatre.timeFactor[timeCategory] * Theatre.seatFactor[seatCategory] * quantity;
        System.out.println("Total Bill : " + total_bill);
        System.out.println("Ticket booked successfully");
        System.out.println("Thank you for visiting.");
        System.out.println("*****************************************************************");
    }

}

// Parent class of all the Genres of Movie
class Movies {
    String name;// will store name of the movies
    static String existingGenre[] = new String[] { "Comedy", "Action", "Slice of Life", "Horror", "Science Fiction",
            "Romance", "Animated" };

    // Checks whether the movie name passed as an argument exits in any of the
    // existing subclasses
    // if exits-->return true;
    // else-->false
    static boolean checkMovieExist(String name) {
        boolean check = false;
        for (int i = 0; i < ComedyMovies.index; i++) {
            if (name.equals(ComedyMovies.ComedyMovieslist[i].name)) {
                check = true;
                break;
            }
        }
        for (int i = 0; i < ActionMovies.index; i++) {
            if (name.equals(ActionMovies.ActionMovieslist[i].name)) {

                check = true;
                break;
            }
        }
        for (int i = 0; i < SliceOfLifeMovies.index; i++) {
            if (name.equals(SliceOfLifeMovies.SliceOfLifeMovieslist[i].name)) {

                check = true;
                break;
            }
        }
        for (int i = 0; i < HorrorMovies.index; i++) {
            if (name.equals(HorrorMovies.HorrorMovieslist[i].name)) {

                check = true;
                break;
            }
        }
        for (int i = 0; i < ScienceFictionMovies.index; i++) {
            if (name.equals(ScienceFictionMovies.ScienceFictionMovieslist[i].name)) {

                check = true;
                break;
            }
        }
        for (int i = 0; i < RomanceMovies.index; i++) {
            if (name.equals(RomanceMovies.RomanceMovieslist[i].name)) {

                check = true;
                break;
            }
        }
        for (int i = 0; i < AnimatedMovies.index; i++) {
            if (name.equals(AnimatedMovies.AnimatedMovieslist[i].name)) {

                check = true;
                break;
            }
        }
        return check;
    }

}

// Subclass of Movies class
class ComedyMovies extends Movies {

    static int index = 0;
    // Array of objects
    static ComedyMovies ComedyMovieslist[] = new ComedyMovies[50];

    ComedyMovies() {

    }

    ComedyMovies(String name) {
        this.name = name;
    }

    // setting defaultMovies for comedy genre
    // (This feature wouldn't be required if I were with files like json-->planning
    // on improvement in future :))
    static void defaultMovies() {
        ComedyMovieslist[index] = new ComedyMovies();
        ComedyMovieslist[index].name = "PK";
        index++;
        ComedyMovieslist[index] = new ComedyMovies();
        ComedyMovieslist[index].name = "Hera Pheri";
        index++;
        ComedyMovieslist[index] = new ComedyMovies();
        ComedyMovieslist[index].name = "Munna Bhai M.B.B.S.";
        index++;
        ComedyMovieslist[index] = new ComedyMovies();
        ComedyMovieslist[index].name = "Bhool Bhoolaiya";
        index++;
        ComedyMovieslist[index] = new ComedyMovies();
        ComedyMovieslist[index].name = "The Fall Guy";
        index++;
        ComedyMovieslist[index] = new ComedyMovies();
        ComedyMovieslist[index].name = "3 Idiots";
        index++;
        ComedyMovieslist[index] = new ComedyMovies();
        ComedyMovieslist[index].name = "Kung Fu Panda";
        index++;
    }

    // Prints the name instance var of each ComedyMovies object from
    // ComedyMovieslist array
    static void print() {
        for (int i = 0; i < ComedyMovies.index; i++) {
            System.out.println("* " + ComedyMovieslist[i].name);

        }
    }

}
// same logic for all the subclasses till Theatre class

class ActionMovies extends Movies {

    static int index = 0;
    static ActionMovies ActionMovieslist[] = new ActionMovies[50];

    ActionMovies() {

    }

    ActionMovies(String name) {
        this.name = name;
    }

    static void defaultMovies() {
        ActionMovieslist[index] = new ActionMovies();
        ActionMovieslist[index].name = "Gladiator";
        index++;
        ActionMovieslist[index] = new ActionMovies();
        ActionMovieslist[index].name = "John Wick";
        index++;
        ActionMovieslist[index] = new ActionMovies();
        ActionMovieslist[index].name = "War";
        index++;
        ActionMovieslist[index] = new ActionMovies();
        ActionMovieslist[index].name = "The Dark Knight";
        index++;
        ActionMovieslist[index] = new ActionMovies();
        ActionMovieslist[index].name = "The Avengers";
        index++;
        ActionMovieslist[index] = new ActionMovies();
        ActionMovieslist[index].name = "Star-Wars";
        index++;

    }

    static void print() {
        for (int i = 0; i < ActionMovies.index; i++) {
            System.out.println("* " + ActionMovieslist[i].name);

        }
    }
}

class SliceOfLifeMovies extends Movies {
    static int index = 0;
    static SliceOfLifeMovies SliceOfLifeMovieslist[] = new SliceOfLifeMovies[50];

    SliceOfLifeMovies() {

    }

    SliceOfLifeMovies(String name) {
        this.name = name;
    }

    static void defaultMovies() {
        SliceOfLifeMovieslist[index] = new SliceOfLifeMovies();
        SliceOfLifeMovieslist[index].name = "Zindagi Na Milegi Dobara";
        index++;
        SliceOfLifeMovieslist[index] = new SliceOfLifeMovies();
        SliceOfLifeMovieslist[index].name = "Wake Up Sid";
        index++;
        SliceOfLifeMovieslist[index] = new SliceOfLifeMovies();
        SliceOfLifeMovieslist[index].name = "The Pursuit Of Happyness";
        index++;
        SliceOfLifeMovieslist[index] = new SliceOfLifeMovies();
        SliceOfLifeMovieslist[index].name = "My Neighbour Totoro";
        index++;
        SliceOfLifeMovieslist[index] = new SliceOfLifeMovies();
        SliceOfLifeMovieslist[index].name = "The Wind Rises";
        index++;
        SliceOfLifeMovieslist[index] = new SliceOfLifeMovies();
        SliceOfLifeMovieslist[index].name = "3 Idiots";
        index++;
        SliceOfLifeMovieslist[index] = new SliceOfLifeMovies();
        SliceOfLifeMovieslist[index].name = "Flavors Of Youth";
        index++;

    }

    static void print() {
        for (int i = 0; i < SliceOfLifeMovies.index; i++) {
            System.out.println("* " + SliceOfLifeMovieslist[i].name);

        }
    }

}

class HorrorMovies extends Movies {
    static int index = 0;
    static HorrorMovies HorrorMovieslist[] = new HorrorMovies[50];

    HorrorMovies() {

    }

    HorrorMovies(String name) {
        this.name = name;
    }

    static void defaultMovies() {

        HorrorMovieslist[index] = new HorrorMovies();
        HorrorMovieslist[index].name = "Bhool Bhoolaiya";
        index++;
        HorrorMovieslist[index] = new HorrorMovies();
        HorrorMovieslist[index].name = "Evil Dead(2013)";
        index++;
        HorrorMovieslist[index] = new HorrorMovies();
        HorrorMovieslist[index].name = "The Grudge";
        index++;
        HorrorMovieslist[index] = new HorrorMovies();
        HorrorMovieslist[index].name = "Tumbbad";
        index++;
        HorrorMovieslist[index] = new HorrorMovies();
        HorrorMovieslist[index].name = "Terrifier(2016)";
        index++;

    }

    static void print() {
        for (int i = 0; i < HorrorMovies.index; i++) {
            System.out.println("* " + HorrorMovieslist[i].name);

        }
    }
}

class ScienceFictionMovies extends Movies {

    static int index = 0;
    static ScienceFictionMovies ScienceFictionMovieslist[] = new ScienceFictionMovies[50];

    ScienceFictionMovies() {

    }

    ScienceFictionMovies(String name) {
        this.name = name;
    }

    static void defaultMovies() {
        ScienceFictionMovieslist[index] = new ScienceFictionMovies();
        ScienceFictionMovieslist[index].name = "Interstellar";
        index++;
        ScienceFictionMovieslist[index] = new ScienceFictionMovies();
        ScienceFictionMovieslist[index].name = "The Martian";
        index++;
        ScienceFictionMovieslist[index] = new ScienceFictionMovies();
        ScienceFictionMovieslist[index].name = "PK";
        index++;
        ScienceFictionMovieslist[index] = new ScienceFictionMovies();
        ScienceFictionMovieslist[index].name = "Big Hero 6";
        index++;
        ScienceFictionMovieslist[index] = new ScienceFictionMovies();
        ScienceFictionMovieslist[index].name = "Wall-E";
        index++;
        ScienceFictionMovieslist[index] = new ScienceFictionMovies();
        ScienceFictionMovieslist[index].name = "Blade Runner 2049";
        index++;
        ScienceFictionMovieslist[index] = new ScienceFictionMovies();
        ScienceFictionMovieslist[index].name = "Arrival";
        index++;
        ScienceFictionMovieslist[index] = new ScienceFictionMovies();
        ScienceFictionMovieslist[index].name = "Gravity";
        index++;
        ScienceFictionMovieslist[index] = new ScienceFictionMovies();
        ScienceFictionMovieslist[index].name = "The Passenger";
        index++;
        ScienceFictionMovieslist[index] = new ScienceFictionMovies();
        ScienceFictionMovieslist[index].name = "Star Wars";
        index++;

    }

    static void print() {
        for (int i = 0; i < ScienceFictionMovies.index; i++) {
            System.out.println("* " + ScienceFictionMovieslist[i].name);

        }
    }
}

class RomanceMovies extends Movies {

    static int index = 0;
    static RomanceMovies RomanceMovieslist[] = new RomanceMovies[50];

    RomanceMovies() {

    }

    RomanceMovies(String name) {
        this.name = name;
    }

    static void defaultMovies() {
        RomanceMovieslist[index] = new RomanceMovies();
        RomanceMovieslist[index].name = "Rockstar";
        index++;
        RomanceMovieslist[index] = new RomanceMovies();
        RomanceMovieslist[index].name = "The Wind Rises";
        index++;
        RomanceMovieslist[index] = new RomanceMovies();
        RomanceMovieslist[index].name = "Garden Of Words";
        index++;
        RomanceMovieslist[index] = new RomanceMovies();
        RomanceMovieslist[index].name = "I Wanna Eat Your Pancreas";
        index++;
        RomanceMovieslist[index] = new RomanceMovies();
        RomanceMovieslist[index].name = "Wake Up Sid";
        index++;
        RomanceMovieslist[index] = new RomanceMovies();
        RomanceMovieslist[index].name = "Masaan";
        index++;
        RomanceMovieslist[index] = new RomanceMovies();
        RomanceMovieslist[index].name = "Eternal Sunshine Of The Spotless Mind";
        index++;
        RomanceMovieslist[index] = new RomanceMovies();
        RomanceMovieslist[index].name = "La La Land";
        index++;
        RomanceMovieslist[index] = new RomanceMovies();
        RomanceMovieslist[index].name = "Jab We Met";
        index++;
        RomanceMovieslist[index] = new RomanceMovies();
        RomanceMovieslist[index].name = "Raanjhanaa";
        index++;
        RomanceMovieslist[index] = new RomanceMovies();
        RomanceMovieslist[index].name = "October";
        index++;

    }

    static void print() {
        for (int i = 0; i < RomanceMovies.index; i++) {
            System.out.println("* " + RomanceMovieslist[i].name);

        }
    }
}

class AnimatedMovies extends Movies {

    static int index = 0;
    static AnimatedMovies AnimatedMovieslist[] = new AnimatedMovies[50];

    AnimatedMovies() {

    }

    AnimatedMovies(String name) {
        this.name = name;
    }

    static void defaultMovies() {
        AnimatedMovieslist[index] = new AnimatedMovies();
        AnimatedMovieslist[index].name = "Big Hero 6";
        index++;
        AnimatedMovieslist[index] = new AnimatedMovies();
        AnimatedMovieslist[index].name = "Wall-E";
        index++;
        AnimatedMovieslist[index] = new AnimatedMovies();
        AnimatedMovieslist[index].name = "The Wind Rises";
        index++;
        AnimatedMovieslist[index] = new AnimatedMovies();
        AnimatedMovieslist[index].name = "I Wanna Eat Your Pancreas";
        index++;
        AnimatedMovieslist[index] = new AnimatedMovies();
        AnimatedMovieslist[index].name = "Garden Of Words";
        index++;
        AnimatedMovieslist[index] = new AnimatedMovies();
        AnimatedMovieslist[index].name = "Flavors Of Youth";
        index++;
        AnimatedMovieslist[index] = new AnimatedMovies();
        AnimatedMovieslist[index].name = "Wild Robot";
        index++;
        AnimatedMovieslist[index] = new AnimatedMovies();
        AnimatedMovieslist[index].name = "My Neighbour Totoro";
        index++;
        AnimatedMovieslist[index] = new AnimatedMovies();
        AnimatedMovieslist[index].name = "Tangled";
        index++;
        AnimatedMovieslist[index] = new AnimatedMovies();
        AnimatedMovieslist[index].name = "Ratatouille";
        index++;
        AnimatedMovieslist[index] = new AnimatedMovies();
        AnimatedMovieslist[index].name = "Cars";
        index++;
        AnimatedMovieslist[index] = new AnimatedMovies();
        AnimatedMovieslist[index].name = "Shrek";
        index++;
        AnimatedMovieslist[index] = new AnimatedMovies();
        AnimatedMovieslist[index].name = "Return Of Hanuman";
        index++;
        AnimatedMovieslist[index] = new AnimatedMovies();
        AnimatedMovieslist[index].name = "Kung Fu Panda";
        index++;

    }

    static void print() {
        for (int i = 0; i < AnimatedMovies.index; i++) {
            System.out.println("* " + AnimatedMovieslist[i].name);

        }
    }
}

class Theatre {
    // Contains array of object
    // Data members such as name,location which will store names and locations of
    // that perticular theatre(object)
    static Theatre TheatreList[] = new Theatre[20];
    String location;
    String name;
    static int index = 0;
    String availableMovies[] = new String[30];// will contain names of movies available in that specific theatre
    double basePrice[] = new double[30];// will contain basePrice of movies available in that specific theatre
    // timeFactor,seatFactor-->contains constant values which will be used
    // as per user input in bookYourTicket method from Customer class
    static double timeFactor[] = new double[] { 0.8, 1, 1.2 };
    static double seatFactor[] = new double[] { 1, 1.5, 2 };

    int movieIndex = 0;

    // Intializes default theatres("Wouldn't be required if I were working with
    // files-->Will surely work on that")
    static void defaultTheatre() {
        TheatreList[index] = new Theatre();
        TheatreList[index].name = "Rajhans Cinema";
        TheatreList[index].location = "Vastral,Ahmedabad";
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "PK";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 130;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Hera Pheri";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 120;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Munna Bhai M.B.B.S.";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 110;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "The Wind Rises";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 100;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Garden Of Words";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 80;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Bhool Bhoolaiya";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 130;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "3 Idiots";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 110;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "My Neighbour Totoro";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 150;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Kung Fu Panda";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 140;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Interstellar";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 115;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "The Fall Guy";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 135;
        TheatreList[index].movieIndex++;
        index++;

        TheatreList[index] = new Theatre();
        TheatreList[index].name = "City Gold";
        TheatreList[index].location = "Ashram Road,Ahmedabad";
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Gladiator";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 90;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "John Wick";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 100;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "War";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 80;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "The Dark Knight";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 130;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "The Avengers";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 100;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Star Wars";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 120;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Zindagi Na Milegi Dobara";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 123;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Wake Up Sid";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 125;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "The Pursuit Of Happyness";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 133;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Flavors Of Youth";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 103;
        TheatreList[index].movieIndex++;
        index++;

        TheatreList[index] = new Theatre();
        TheatreList[index].name = "Devi Multiplex";
        TheatreList[index].location = "Naroda,Ahmedabad";
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Bhool Bhoolaiya";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 70;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Evil Dead(2013)";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 100;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "The Grudge";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 90;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Tumbbad";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 95;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Terrifier(2016)";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 102;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Interstellar";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 75;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "PK";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 83;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Big Hero 6";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 96;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "The Martian";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 100;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Wall-E";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 120;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Blade Runner 2049";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 130;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Arrival";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 120;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Gravity";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 100;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "The Passenger";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 108;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Rockstar";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 98;
        TheatreList[index].movieIndex++;
        index++;

        TheatreList[index] = new Theatre();
        TheatreList[index].name = "SB multiplex";
        TheatreList[index].location = "Agora mall,Ahmedabad";
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Garden Of Words";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 120;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "I Wanna Eat Your Pancreas";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 110;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Wake Up Sid";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 140;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Masaan";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 150;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Eternal Sunshine of the Spotless Mind";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 110;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "La La Land";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 100;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Jab We Met";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 103;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Raanjhanaa";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 142;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "October";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 136;
        TheatreList[index].movieIndex++;
        index++;

        TheatreList[index] = new Theatre();
        TheatreList[index].name = "Apple Multiplex";
        TheatreList[index].location = "Maninagar,Ahmedabad";
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Big Hero 6";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 200;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Wall-E";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 230;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "The Wind Rises";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 170;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "I Wanna Eat Your Pancreas";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 196;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Wild Robot";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 245;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Evil Dead(2013)";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 147;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "The Grudge";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 178;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Tumbbad";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 155;
        TheatreList[index].movieIndex++;
        index++;

        TheatreList[index] = new Theatre();
        TheatreList[index].name = "Mango Plus Cinemas";
        TheatreList[index].location = "Nikol,Ahmedabad";
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Tangled";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 300;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Ratatouille";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 250;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Cars";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 270;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Shrek";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 160;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Return Of Hanuman";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 280;
        TheatreList[index].movieIndex++;
        index++;

        TheatreList[index] = new Theatre();
        TheatreList[index].name = "P Square MoviePlex";
        TheatreList[index].location = "Gota,Ahmedabad";
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Kung Fu Panda";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 130;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Interstellar";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 120;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "The Fall Guy";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 100;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "The Martian";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 180;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Wall-E";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 145;
        TheatreList[index].movieIndex++;
        TheatreList[index].availableMovies[TheatreList[index].movieIndex] = "Blade Runner 2049";
        TheatreList[index].basePrice[TheatreList[index].movieIndex] = 103;
        index++;

    }
}