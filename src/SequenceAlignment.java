public class SequenceAlignment {
    // class attributes
    private String text1;
    private String text2;
    private int [][] a;
    private final char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    // overload constructor
    public SequenceAlignment(String text1, String text2){
        this.text1 =   text1;
        this.text2 =   text2;
        this.a = new int [text1.length()+1][text2.length()+1];
    }
    public void computeAlignment(int num)
    {
        int m = text1.length();
        int n = text2.length();
        a[0][0] = 0;
        // initializing the first row
        for (int i = 1; i <= m; i++) {
            a[i][0] = a[i - 1][0] + num;
        }
        // initializing the first column
        for (int j = 1; j <= n; j++) {
            a[0][j] = a[0][j - 1] + num;

        }
        // setting up the table
        for(int j=1; j<=n; j++){
            for(int i=1; i<=m; i++){
                char ch1=text1.charAt(i-1);
                char ch2=text2.charAt(j-1);
                // characters are the same
                if(ch1 == ch2){
                    a[i][j] = Math.min(Math.min(a[i-1][j-1], num + a[i-1][j]), num + a[i][j-1]);
                }
                // both characters are vowels
                else if( "aeiou".contains(ch1+"") && "aeiou".contains(ch2+"") ){
                    a[i][j] = Math.min(Math.min(1+ a[i-1][j-1], num + a[i-1][j]), num + a[i][j-1]);
                }
                // both characters constant
                else if( !("aeiou".contains(ch1+"")) && !("aeiou".contains(ch2+"")) ){
                    a[i][j] = Math.min(Math.min(1+ a[i-1][j-1], num + a[i-1][j]), num + a[i][j-1]);
                }
                // one character is vowel and other one is constant
                else{
                    a[i][j] = Math.min(Math.min(3+ a[i-1][j-1], num + a[i-1][j]), num + a[i][j-1]);
                }

            }
        }


    }
    public String getAlignment()
    {
        int i = text1.length();
        int j = text2.length();
        int penalty;
        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();

        while (!(i == 0 || j == 0)){
            // calculating the cost for the specific character
            penalty = calculatePenalty(text1.charAt(i-1), text2.charAt(j-1));

            //both chars are aligned
            if (text1.charAt(i-1) == text2.charAt(j-1) || a[i][j] == penalty + a[i-1][j-1]){
                str1.insert(0, text1.charAt(--i));
                str2.insert(0, text2.charAt(--j));
            }

            //put an underline in second string
            else if (a[i][j] == a[i-1][j]+2){
                str1.insert(0, text1.charAt(--i));
                str2.insert(0, "-");
            }

            //put an underline in first string
            else if (a[i][j] == 2 + a[i][j-1]){
                str1.insert(0, "-");
                str2.insert(0, text2.charAt(--j));
            }
        }

        //prepend the remaining chars or gaps if i or j are not 0
        while (i > 0){
            str1.insert(0, text1.charAt(--i));
            str2.insert(0, "-");
        }

        while (j > 0){
            str1.insert(0, "-");
            str2.insert(0, text2.charAt(--j));
        }

        return str1.append(" ").append(str2).toString();

    }
    //returns 1 if char is vowel, 0 if consonant
    public int check(char letter){
        for (char c: vowels){
            if (c==letter)
                return 1;
        }
        return 0;
    }
    //calculate the penalty
    public int calculatePenalty(char char1, char char2){
        int check_char1 = check(char1);
        int check_char2 = check(char2);

        // 2 vowels or 2 consonants
        if (check_char1 == check_char2)
            return 1;

        //vowel and consonant
        return 3;
    }
    // display the table
    public void displayTable(){
        for(int i=0; i<a.length; i++){
            for(int j=0; j<a[i].length; j++){
                System.out.print(a[i][j]);
            }
            System.out.println();
        }
    }

}