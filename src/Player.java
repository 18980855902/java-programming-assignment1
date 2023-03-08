import java.util.concurrent.ThreadLocalRandom;

/**
 * Model the player
 */
public class Player {
    /**
     * The length of the list
     */
    private static final int LIST_LENGTH = 6;
    /**
     * A secret list of a player. This list would not be changed during the game
     */
    private Character[] myList;
    /**
     * The number of veto (reject) voting card. Decrement it when the player vote
     * reject.
     */
    private int vetoCard;
    /**
     * The name of the player.
     */
    private String name;

    /**
     * Compute the score of a player. Each player should have a list of character
     *
     * @return the score of a player.
     */
    public int getScore() {
        Character [] p= new Character[6];
        int score=0;
        for(int i=0;i<6;i++) {
            p[i] = myList[i];
        }
        for(int n=0;n<6;n++) {
            score += p[n].getPosition();
        }
        return score;
    }

    public int getvetoCard() {
        return this.vetoCard;
    }

    /**
     * Return the name of a player.
     * @return the name of the player
     */
    public String getName() {
        return this.name;
     }

    /**
     * Initialize the number of veto card
     * @param card the number of veto card
     */
    public void initVetoCard(int card) {
        this.vetoCard = card;
    }

    /**
     * Initialize all data attributes.
     * @param name the name of the player
     * @param list a whole list of characters and the player should pick
     *             LIST_LENGTH of <b>unique</b> characters
     */
    public Player(String name, Character[] list) {
        this.name = name;
        this.myList = list;
    }

    /**
     * A method to vote according to the preference of the parameter support.
     * A player is forced to vote yes (support) if he/she has no more veto card.
     *
     * @param support The preference of the player
     * @return true if the player support it. False if the player reject (veto).
     */
    public boolean vote(boolean support) {
        if(support == true||this.vetoCard==0) {
            return true;
        }
        else {
            initVetoCard(getvetoCard() - 1);
            return false;
        }
    }

    /**
     * Vote randomly. The player will be forced to vote support(true) if he is
     * running out of veto card.
     * @return true if the player support it. False if the player reject (veto).
     */
    public boolean voteRandomly() {
        int n = (int) (Math.random() * (1 + 1));
        if(this.vetoCard == 0||n==0){
            initVetoCard(getvetoCard()-1);
            return false;
        }
        else return true;
    }

    /**
     * Randomly place a character during the placing stage. This method should pick a
     * random unplaced character and place it to a random valid position.
     * @param list The list of all characters. Some of these characters may have been
     *             placed already
     * @return the character that just be placed.
     */
    public Character placeRandomly(Character[] list) {
        boolean d = false;
        int random = 0;
        while(!d) {
            random = (int) (0 + Math.random() * (13));
            if(list[random].getPosition()==-1) {
                d = true;
            }
        }
        return list[random];
    }

    /**
     * The player shall vote smartly (i.e., its decision will be based on if the player has that
     * character in his/her list.) If the player is running out of veto card, he/she will be forced
     * to vote support (true).
     *
     * @param character The character that is being vote.
     * @return true if the player support, false if the player reject(veto).
     */
    public boolean voteSmartly(Character character) {
        for(int i=0; i<6;i++){
            if(myList[i]==character){
                return true;
            }
        }
        return voteRandomly();
    }

    /**
     * The player shall pick a randomly character that is <i>movable</i> during the playing stage.
     * Movable means the character has not yet be killed and the position right above it is not full.
     *
     * Note: this method should not change the position of the character.
     *
     * @param list The entire list of characters
     * @return the character being picked to move. It never returns null.
     */
    public Character pickCharToMoveRandomly(Character[] list) {

        boolean [] move=new boolean[13];
        for(int n=0;n<13;n++){
            move[n]=true;
            }
        for(int i=0;i<13;i++){
            if(list[i].getPosition()==-1){
                move[i]=false;
            }
        }
        int random=0;
        do{random = (int) (Math.random() * (13));}while(!move[random]);
        return list[random];
    }

    /**
     * This method return the character who's name is the same as the
     * variable name if the character is <i>movable</i>. Movable means
     * the character has not yet be killed and the position right above
     * it is not full.
     *
     * If the name of the character can't be found from the list or the
     * character is not movable, this method returns null.
     *
     * Note: this method should not change the position of the character.
     *
     * @param list The entire list of characters
     * @param name The name of the character being picked.
     * @return the character being picked to move or null if the character
     *          can't be found or the it is not movable.
     */
    public Character pickCharToMove(Character[] list, String name) {
        int [] ls = new int[5];
        for(int n=0; n<5; n++){
            ls[n]=0;
        }
        for(int i=0; i<13;i++){
            if(list[i].getPosition()==1);{ls[0]++;}
            if(list[i].getPosition()==2);{ls[1]++;}
            if(list[i].getPosition()==3);{ls[2]++;}
            if(list[i].getPosition()==4);{ls[3]++;}
            if(list[i].getPosition()==5);{ls[4]++;}
        }
        int l=0;
        for(int i=0; i<13;i++){
            if(name.equals(list[i].getName())){
                l=i;
            }
        }
       return list[l];
    }

    /**
     * Similar to pickCharToMoveRandomly only as the character being picked to move
     * is related to the secret list of the player. The implementation of this part is
     * open and you are advised to optimize it to increase the chance of winning.
     *
     * Note: this method should not change the position of the character.
     *
     * @param list The list of character
     * @return the character to be move. It never returns null.
     */
    public Character pickCharToMoveSmartly(Character[] list) {
        int [] ls = new int[5];
        for(int n=0; n<5; n++){
            ls[n]=0;
        }
        for(int i=0; i<13;i++){
            if(list[i].getPosition()==1);{ls[0]++;}
            if(list[i].getPosition()==2);{ls[1]++;}
            if(list[i].getPosition()==3);{ls[2]++;}
            if(list[i].getPosition()==4);{ls[3]++;}
            if(list[i].getPosition()==5);{ls[4]++;}
        }
        int random=0;
        do{random = (int) (Math.random() * (6));}while(list[random].getPosition()==-1);
        return list[random];
    }

    /**
     * This returns the name of the player and the secret list of the characters.
     * as a string
     * @return The name of the player followed by the secret list of the characters.
     */
    public String toString() {
        String mylist = "";
        for(int i=0; i<6;i++) {
            mylist += myList[i] + " ";
        }
        return this.name + mylist;
    }
}
