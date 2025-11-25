public class CollectionOfFunctions {

    /*
    Grade Assigner
    Takes in an integer as in input and returns a a letter grade as a character.
    */
    public char gradeAssigner(int grade){
        if(grade < 60)
            return 'F';

        if(grade < 70)
            return 'D';

        if(grade < 80)
            return 'C';

        if(grade < 90)
            return 'B';

        return 'A';

    }



    /*
    Sorting Algorithm
    Takes in a refrence to an interger array as input and sorts the array.
     */
    public void sortArray(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            for (int j = i; j > 0 && nums[j - 1] > nums[j]; j--) {
                int temp = nums[j - 1];
                nums[j - 1] = nums[j];
                nums[j] = temp;
            }
        }
    }



    /*
    Row Summation
    Takes in a reference to a multidimensional array and integer x.
    Returns the summation of the row at position x in the 2-d array.
     */
    public int rowSum(int[][] nums, int x){
        int sum = 0;

        for(int j=0; j<nums[x].length; j++){
            sum += nums[x][j];
        }

        return sum;
    }


    /*
    Minimum Value
    Takes in a reference to an array and returns the minimum value value from the array.
     */
    public  int[] minimumValue(int[] nums){
        int min = nums[0];
        int index = 0;

        for(int i=1; i<nums.length; i++){
            if(min > nums[i]){
                min = nums[i];
                index = i;
            }
        }

        return new int[] {min, index};
    }


    /*
    String Cleaner
     Takes in as in input a String str and a char x.
     Returns a new string that removed all of the characters x from str.
     */
    public String stringCleaner(String str, char x){
        String newStr = "";

        for(int i=0; i<str.length(); i++){
            if((Character)str.charAt(i) == (x)){
                continue;
            }else{
                newStr += str.substring(i, i+1);
            }
        }

        return newStr;
    }


}
