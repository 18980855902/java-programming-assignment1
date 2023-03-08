/**
 * Model a Character in the game. It should keeps its name and its position.
 */
public class Character {
    public static final int OUT_OF_GAME = -1;
    private String name;
    private int position;

    /**
     * Initialize the charcter with its name
     * @param name The name of the character
     */
    public Character(String name) {
        this.name = name;
        this.position =OUT_OF_GAME;
    }

    /**
     * Return the position of where the character is.
     *
     * @return the position of where the character is. Only possible values are OUT_OF_GAME (-1), 0, 1, 2, 3, 4 ,5, 6
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Set the position of the character. It should not be allowed to set to a level lower than its position, or two steps (or more)
     * higher than its current position, except:
     * 1) The character is OUT_OF_GAME, which mean it is in placing stage
     * 2) The character is to be taken out of the game, which mean it is killed during the voting stage.
     *
     * @param pos The position where the character is to be placed.
     * @return true if the setPosition is successful, false if the calling of the method is disallowed (as stated above).
     */
    public boolean setPositionvalid(int pos, Character[] list) {
        int [] ls = new int[6];
        for(int n=0; n<6; n++){
            ls[n]=0;
        }
        for(int i=0; i<13;i++){
            if(list[i].getPosition()==1);{ls[0]++;}
            if(list[i].getPosition()==2);{ls[1]++;}
            if(list[i].getPosition()==3);{ls[2]++;}
            if(list[i].getPosition()==4);{ls[3]++;}
            if(list[i].getPosition()==5);{ls[4]++;}
        }
        if(this.position==-1){
            if(pos<=0||pos>=6||ls[pos-1]==4){
                return false;
            }
            else return true;
        }
        else{if(pos-this.position>=2||ls[pos]==4){
            return false;
        }
        else return true;
        }
     }

     public void setPosition(int pos){
        this.position = pos;
     }

    /**
     * Return the name of the character with its position in the following format
     * NAME[POSITION], e.g. Aligliero[2] which means Aligliero in position 2. or
     * e.g. Dario[-1] which means Dario is out of the game.
     *
     * @return the name of the character with its position
     */
    public String toString() {
        return this.name+"["+this.position+"]";
    }

    /**
     * Return the name of the character
     * @return the name of the character
     */
    public String getName() {
        return this.name;
    }
}
