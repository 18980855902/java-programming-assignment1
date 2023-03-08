import java.util.Objects;
import java.util.Scanner;

/**
 * The class Gameboard is to print control the flow of the game and
 * contains a set of players and characters.
 */
public class Gameboard {

    /**
     * The maximum number of characters can be placed in the same position.
     */
    public static final int FULL = 4;
    /**
     * The total number of characters
     */
    public static final int NO_OF_CHARACTER = 13;
    /**
     * The total number of player
     */
    public static final int NO_OF_PLAYERS = 4;
    /**
     * The position of Throne
     */
    public static final int THRONE = 6;
    /**
     * The scores calculation formula
     */
    public static final int[] SCORES = {0, 1, 2, 3, 4, 5, 10};
    /**
     * The name of the characters
     */
    public static final String[] CHARACTER_NAMES = {
            "Aligliero", "Beatrice", "Clemence", "Dario",
            "Ernesto", "Forello", "Gavino", "Irima",
            "Leonardo", "Merlino", "Natale", "Odessa", "Piero"
    };
    /**
     * The name of the players
     */
    public static final String[] PLAYER_NAMES = {
        "You", "Computer 1", "Computer 2", "Computer 3"
    };
    /**
     * Determine if the players are human player or not.
     */
    public static final boolean[] HUMAN_PLAYERS = {
        true, false, false, false
    };
    /**
     * A list of character
     */
    private Character[] characters;
    /**
     * A list of player
     */
    private Player[] players;


    public static void main(String[] argv) {
        new Gameboard().runOnce();
    }

    /**
     * Initialize all data attributes. You will need to initialize and create
     * the array players and characters. You should initialize them using the
     * String array PLAYER_NAMES and CHARACTER_NAMES respectively.
     */
    public Gameboard() {
        characters = new Character [13];
        for(int i=0; i<13; i++) {
            characters[i] = new Character(CHARACTER_NAMES[i]);
        }
        players= new Player[4];
        Character [] list1= new Character[6];
        Character [] list2= new Character[6];
        Character [] list3= new Character[6];
        Character [] list4= new Character[6];
        {
            list1[0] = characters[0];
            list1[1] = characters[2];
            list1[2] = characters[4];
            list1[3] = characters[6];
            list1[4] = characters[8];
            list1[5] = characters[10];

            list2[0] = characters[1];
            list2[1] = characters[3];
            list2[2] = characters[5];
            list2[3] = characters[7];
            list2[4] = characters[9];
            list2[5] = characters[11];

            list3[0] = characters[0];
            list3[1] = characters[3];
            list3[2] = characters[6];
            list3[3] = characters[9];
            list3[4] = characters[12];
            list3[5] = characters[5];

            list4[0] = characters[1];
            list4[1] = characters[4];
            list4[2] = characters[7];
            list4[3] = characters[10];
            list4[4] = characters[12];
            list4[5] = characters[11];
        }

        players[0]= new Player(PLAYER_NAMES[0],list1);
        players[1]= new Player(PLAYER_NAMES[1],list2);
        players[2]= new Player(PLAYER_NAMES[2],list3);
        players[3]= new Player(PLAYER_NAMES[3],list4);

        for(int i=0; i<4;i++) {
            players[i].initVetoCard(3);
        }
    }

    /**
     * The main logic of the game. This part has been done for you.
     */
    public void runOnce() {
        print();
        System.out.println("======= Placing stage ======= \n"
                + "Each player will take turns to place three characters on the board.\n"
                + "No character can be placed in the position 0 or 5 or 6 (Throne) at this stage.\n"
                + "A position is FULL when there are four characters placed there already.\n"
                + "The remaining character will be placed at the position 0.\n");

        placingStage();

        print();
        System.out.println("======= Playing stage ======= \n"
                + "Each player will take turn to move a character UP the board.\n"
                + "You cannot move a character that is been killed or its immediate upper position is full.\n"
                + "A voting will be trigger immediately when a character is moved to the Throne (position 6).\n"
                + "Your list is"+ characters[0] + characters[2] + characters[4] + characters[6] + characters[8] + characters[10]);

        playingStage();

        print();
        System.out.println("======= Scoring stage ======= \n"
                + "This will trigger if and only if the voting result is ALL positive, i.e., no player play the veto (reject) card. \n"
                + "The score of each player is computed by the secret list of characters owned by each player.");

        scoringStage();
    }

    /**
     * Print the scores of all players correctly. This part has been done
     * for you.
     */
    private void scoringStage() {
        for (Player p : players) {
            System.out.println(p);
            System.out.println("Score: " + p.getScore());
        }
    }

    /**
     * Perform the placing stage. You have to be careful that human player will need to chosen on what to place
     * Non-human player will need to place it using the method placeRandomly (see Player.java)
     */
    private void placingStage() {
        boolean fill = false;

        while(!fill) {

            Scanner in = new Scanner(System.in);
            System.out.println("Input the name of character which you want to place");
            String name = in.nextLine();

            for (Character character : this.characters) {
                if (Objects.equals(character.getName(), name) && character.getPosition() == -1) {
                    boolean doo = false;
                    while(!doo){
                        System.out.println("Input the position you want to place");
                        int position = in.nextInt();
                        if (character.setPositionvalid(position, characters)) {
                            character.setPosition(position);
                            doo = true;
                        }
                    }
                }
            }

            for(int m=0;m<3;m++) {
                boolean dooo = false;
                while (!dooo) {
                    Character c = new Character(this.players[0].placeRandomly(characters).getName());
                    for (int n = 0; n < 13; n++) {
                        if (characters[n].getName().equals(c.getName()) && c.getPosition() == -1) {
                            if (this.characters[n].setPositionvalid(1 + (int) (Math.random() * (5)), characters)) {
                                this.characters[n].setPosition(1 + (int) (Math.random() * (5)));
                                dooo = true;
                            }
                        }
                    }
                }
            }

            print();
            int number=0;
            for (Character character : this.characters) {
                if (character.getPosition() != -1) {
                    number += 1;
                }
            }
            if(number==12){
                fill=true;
            }
        }
        for (Character character : this.characters) {
            if (character.getPosition() == -1) {
                character.setPosition(0);
            }
        }
        print();
    }

    /**
     * Perform playing stage. Be careful that human player will need to pick the character to move.
     * You should detect any invalid input and stop human player from doing something nonsense or illegal.
     * Computer players will need to run the code pickCharToMoveRandomly or pickCharToMoveSmartly to pick which character to move.
     */
    private void playingStage() {
        boolean win;
        do {
            win = false;
            Scanner in=new Scanner(System.in);
            System.out.println("Input the name of character which you want to move");
            String name =in.nextLine();

            for (int i = 0; i < 13; i++) {
                if (this.characters[i].getName().equals(this.players[0].pickCharToMove(characters, name).getName())) {
                    this.characters[i].setPosition(this.characters[i].getPosition() + 1);
                }
                if (this.characters[i].getName().equals(this.players[1].pickCharToMoveRandomly(characters).getName())) {
                    this.characters[i].setPosition(this.characters[i].getPosition() + 1);
                }
                if (this.characters[i].getName().equals(this.players[2].pickCharToMoveRandomly(characters).getName())) {
                    this.characters[i].setPosition(this.characters[i].getPosition() + 1);
                }
                if (this.characters[i].getName().equals(this.players[3].pickCharToMoveRandomly(characters).getName())) {
                    this.characters[i].setPosition(this.characters[i].getPosition() + 1);
                }
            }
            print();
            for (int n = 0; n < 13; n++){
                if(this.characters[n].getPosition()==6){
                    print();
                    boolean [] supports = new boolean[4];
                    for(int m=0; m<4; m++){
                        supports[m]=false;
                    }
                    System.out.println("The " + characters[n].getName() + "on the level 6, if you support type yes, else no");
                    System.out.println("Your list is"+ characters[0] + characters[2] + characters[4] + characters[6] + characters[8] + characters[10]);
                    String vote =in.next();
                    if(vote.equals("yes")){
                        supports[0]  = true;
                    }else{
                        supports[0] = false;
                    }

                    if(this.players[0].vote(supports[0])&&
                    this.players[1].voteSmartly(characters[n])&&
                    this.players[2].voteSmartly(characters[n])&&
                    this.players[3].voteSmartly(characters[n])){
                        win = true;
                    }
                    else characters[n].setPosition(-1);
                    System.out.println("Someone vote no, so game continue.");
                }
            }
        }while(!win);//loop until a character has been voted for the new King.
    }

    /**
     * Print the gameboard. Please see the assignment webpage or the demo program for
     * the format. You should call this method after a character has been moved or placed or killed.
     */
    private void print() {
        String level="";
        String out_unplaced="";
        for(int i=6;i>=0;i--) {
            for(int n=0;n<13;n++){
                if(characters[n].getPosition()==i){
                    level+=characters[n]+" ";
                }

            }
            System.out.println("Level"+ i + ":" + level);
            level="";
        }
        for(int m=0; m<13; m++) {
            if (characters[m].getPosition() == -1) {
                out_unplaced = out_unplaced + characters[m] + " ";
            }
        }
        System.out.println("Unplaced/Killed Characters: " + out_unplaced);
        System.out.println();
        System.out.println("You   Veto Card :" + players[0].getvetoCard());
        System.out.println();
    }

}
