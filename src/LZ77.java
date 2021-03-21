public class LZ77 {

    private static final int DICTIONARY_SIZE = 6;
    private static final int BUFFER_SIZE = 4;
    private char[] buffer;
    private char[] dictionary;
    private int length;
    private int dist;

    public LZ77(){
        this.buffer = new char[BUFFER_SIZE];
        this.dictionary = new char[DICTIONARY_SIZE];
    }

    public String compress(String data){
        String compressedData = "";
        updateBuffer(data);
        while(data.length() > 0){
            char[] prefix = getLargestPrefix();
            this.length = this.getPrefixLength(prefix);
            this.dist = 0;
            if(this.length != 0){
                getPrefixDist(prefix);
            }
            char nextChar = this.getPrefixChar();

            String d = Integer.toString(this.dist);
            String l = Integer.toString(this.length);

            compressedData += d + l + nextChar;

            updateDictionary(prefix);
            if(data.length() > this.length + 1) {
                data = data.substring(this.length + 1);
            } else{
                data = data.substring(1);
            }
            updateBuffer(data);
        }

        return compressedData;
    }

    public char[] getLargestPrefix(){
        char[] prefix = new char[BUFFER_SIZE];
        if(this.dictionary[0] == 0){
            this.dist = 0;
            prefix[0] = this.buffer[0];
            return prefix;
        } else{
            String dict = new String(this.dictionary);
            String buffer = new String(this.buffer);
            if(dict.lastIndexOf(buffer) != -1){
                this.dist = 1 + dict.lastIndexOf(buffer);
                prefix[0] = this.buffer[0];
                prefix[1] = this.buffer[1];
                prefix[2] = this.buffer[2];
                prefix[3] = this.buffer[3];
                return prefix;
            }
            else if(dict.lastIndexOf(buffer.substring(0, buffer.length()-1)) != -1){
                this.dist = 1 + dict.lastIndexOf(buffer.substring(0, buffer.length()-1));
                prefix[0] = this.buffer[0];
                prefix[1] = this.buffer[1];
                prefix[2] = this.buffer[2];
                return prefix;
            }
            else if(dict.lastIndexOf(buffer.substring(0, buffer.length()-2)) != -1){
                this.dist = 1 + dict.lastIndexOf(buffer.substring(0, buffer.length()-2));
                prefix[0] = this.buffer[0];
                prefix[1] = this.buffer[1];
                return prefix;
            } else{
                this.dist = 0;
                prefix[0] = this.buffer[0];
                return prefix;
            }
        }
    }

    public void getPrefixDist(char[] prefix){
        String pre = new String (prefix);
        String dict = new String(this.dictionary);
        int index = 0;
        for(int i = 0; i < this.dictionary.length; i++){
            if(this.dictionary[i] == 0){
                index = i;
                break;
            }
        }
        this.dist = (dict.indexOf(pre) - index) * -1;
    }

    public int getPrefixLength(char[] prefix){
        if(prefix[1] == 0){
            return 0;
        }
        for(int i = 0; i < prefix.length; i++){
            if(prefix[i] == 0){
                return i;
            }
        }
        return 0;
    }

    public char getPrefixChar(){
        return this.buffer[this.length];
    }

    public void updateBuffer(String data){
        for(int i = 0; (i < this.buffer.length && i < data.length()); i++){
            this.buffer[i] = data.charAt(i);
        }
    }

    public void updateDictionary(char[] prefix){
        int dictLength = DICTIONARY_SIZE;
        for(int i = 0; i < DICTIONARY_SIZE; i++){
            if(this.dictionary[i] == 0){
                dictLength = i;
                break;
            }
        }

        if(this.length + dictLength > DICTIONARY_SIZE - 1){
            String p = new String(this.dictionary);
            String dict = new String(prefix);
            dict += p;
            dict.substring(0, dict.length() - this.length);
            for(int i = 0; i < this.dictionary.length; i++){
                this.dictionary[i] = dict.charAt(i);
            }
        } else{
            int j = 0;
            for(int i = dictLength; i < DICTIONARY_SIZE; i++){
                if(prefix[j] != 0){
                    this.dictionary[i] = prefix[j];
                } else{
                    break;
                }
                j++;
            }
        }
    }
}
