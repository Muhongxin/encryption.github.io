package com.mhx.encryption.util.sm;

import java.math.BigInteger;

/**
 * @author MuHongXin.
 * @Description: 数据类型转换工具包
 * @dateTime:  2022/8/17 下午4:54
 */

public class DataConversionUtils {

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:43 2023/6/19
     * @description: 整型转换成网络传输的字节流（字节数组）型数据
     *               The integer is converted to a byte stream (byte array) of data transmitted over the network
     *
     *               该代码用于将一个 int 类型的数据转换为一个长度为 4 的 byte 数组。
     *               这个方法会将这个 int 类型的数据划分为四个字节，然后将每个字节分别赋值给 byte 数组中的对应元素。
     *               在赋值时，使用了位运算符和按位与操作来取出每个字节中的低八位，并将其赋值给 byte 数组对应的元素，以保证转换后的数据正确无误。
     *               最后返回这个 byte 数组。
     *
     * -------------------------------------------------------------------------------------------------------------------------丨
     *               This code converts data of type int to a byte array of length 4.                                           丨
     *               This method divides the int data into four bytes and assigns each byte to the corresponding                丨
     *               element in the byte array.                                                                                 丨
     *               During assignment, the bit operator and the bit-and-and-operation are used to take the lower eight bits    丨
     *               of each byte and assign them to the corresponding element of the byte array                                丨
     *               to ensure that the converted data is correct.                                                              丨
     *               Finally return the byte array.                                                                             丨
     * -------------------------------------------------------------------------------------------------------------------------丨
     * @param num 一个整型数据
     * @return byte[] 4个字节的自己数组
    **/
    public static byte[] intToBytes(int num) {
        byte[] bytes = new byte[4];

        // 将 num 根据位运算符右移 0、8、16、24 位之后的低八位赋值给 bytes 数组
        // Assign the low eight bits of 'num' after right shift by 0, 8, 16, and 24 bits using bitwise operators to the 'bytes' array
        bytes[0] = (byte) (0xff & (num >> 0));
        bytes[1] = (byte) (0xff & (num >> 8));
        bytes[2] = (byte) (0xff & (num >> 16));
        bytes[3] = (byte) (0xff & (num >> 24));
        return bytes;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:44 2023/6/19
     * @description: 四个字节的字节数据转换成一个整形数据
     *               Four bytes of byte data are converted to an shaping data
     *
     *               该代码用于将一个长度为 4 的 byte 数组转换为一个 int 类型的数据。
     *               这个方法会将这个 byte 数组中的每个字节根据位运算符左移 0、8、16、24 位，并将移动后的结果合并为一个 int 类型的数据。
     *               这个方法处理了使用补码表达的负数的情况，即当第一个字节的最高位为 1 时，将剩余的 24 个比特位设置为 1。
     *               最后返回这个 int 类型的数据。
     *
     * -------------------------------------------------------------------------------------------------------------------------丨
     *               This code converts a byte array of length 4 to data of type int.                                           丨
     *               This method shifts each byte in the byte array to the left by 0, 8, 16, and 24 bits according              丨
     *               to the bit operator, and combines the moved result into an int.                                            丨
     *               This method handles the case of negative numbers reached using the complement table,                       丨
     *               that is, when the highest bit of the first byte is 1, the remaining 24 bits are set to 1.                  丨
     *               Finally returns the data of type int.                                                                      丨
     * -------------------------------------------------------------------------------------------------------------------------丨
     * @param bytes 4个字节的字节数组
     * @return int 一个整型数据
    **/
    public static int byteToInt(byte[] bytes) {
        int num = 0;
        int temp;
        // 使用位运算符将 bytes 中前 4 个字节合并为一个 int 类型的数据
        // Use bitwise operators to combine the first four bytes in 'bytes' into an 'int' type data
        temp = (0x000000ff & (bytes[0])) << 0;
        num = num | temp;
        temp = (0x000000ff & (bytes[1])) << 8;
        num = num | temp;
        temp = (0x000000ff & (bytes[2])) << 16;
        num = num | temp;
        temp = (0x000000ff & (bytes[3])) << 24;
        num = num | temp;
        return num;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:46 2023/6/19
     * @description: 将 long 类型的数据转换为 byte 数组
     *               Convert a 'long' type data into a 'byte' array
     *
     *               该代码用于将一个 long 类型的数据转换成一个长度为 8 的 byte 数组。
     *               详细实现是：
     *               首先创建一个长度为 8 的 byte 数组 bytes，
     *               然后使用 for 循环遍历该 byte 数组中的 8 个元素。
     *               在遍历过程中，使用位运算符和按位与操作取出该 long 类型数据中逐字节表示的低八位，
     *               并将其依次赋值给 bytes 数组中对应的元素。最后返回该 byte 数组。
     *
     * -------------------------------------------------------------------------------------------------------------------------丨
     *               This code converts data of type long into a byte array of length 8.                                        丨
     *               The detailed implementation is:                                                                            丨
     *               First create a byte array of length 8 bytes,                                                               丨
     *               The for loop is then used to iterate over the eight elements of the byte array.                            丨
     *               During traversal, use the bit operator and bit-and-sum operation to retrieve the byte                      丨
     *               by byte representation of the lower octet of the long type data,                                           丨
     *               and assigns them in turn to the corresponding elements in the bytes array. Finally,                        丨
     *               the byte array is returned.                                                                                丨
     * -------------------------------------------------------------------------------------------------------------------------丨
     * @param num long 类型的数据 | a 'long' type data
     * @return byte 数组 | a 'byte' array
    **/
    public static byte[] longToBytes(long num) {
        byte[] bytes = new byte[8];

        // 将 num 根据位运算符右移 0~56 位之后的低八位赋值给 bytes 数组
        // Assign the low eight bits of 'num' after right shift by 0~56 bits using bitwise operators to the 'bytes' array
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) (0xff & (num >> (i * 8)));
        }

        return bytes;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:47 2023/6/19
     * @description: 将 BigInteger 类型的数据转换为长度为 32 的 byte 数组
     *               Convert a 'BigInteger' type data into a 32-byte 'byte' array
     *
     *               该代码用于将一个 BigInteger 类型的数据转换成一个长度为 32 的 byte 数组。
     *               具体实现分三种情况：
     *               1. 如果 n 转换成的 byte 数组的长度为 33，则需要去掉数组前面的无用零（即头部的 0），
     *               只保留后面的 32 个字节，用 System.arraycopy 方法进行复制。
     *               2. 如果 n 转换成的 byte 数组的长度为 32，则直接将该 byte 数组赋值给变量 byte tmpd[]。
     *               3. 如果 n 转换成的 byte 数组长度小于 32 bytes，则需要进行填充还原操作，即将前面的字节用 0 进行填充。最后返回该 byte 数组。
     *
     * -------------------------------------------------------------------------------------------------------------------------丨
     *               This code converts data of type big integer to a byte array of length 32.                                  丨
     *               There are three types of implementation:                                                                   丨
     *               1. If the length of the byte array converted from n is 33, you need to remove the useless zeros            丨
     *               in front of the array (that is, the 0 in the head),                                                        丨
     *               Only the last 32 bytes are kept and copied using the system.arraycopy method.                              丨
     *               2. If n is converted to a byte array of length 32, the byte array is directly assigned                     丨
     *               to the variable byte tmpd[].                                                                               丨
     *               3. If the length of the byte array converted from n is less than 32 bytes, a padding restore operation     丨
     *               is required, that is, the previous bytes are filled with zeros. Finally, the byte array is returned.       丨
     * -------------------------------------------------------------------------------------------------------------------------丨
     * @param n BigInteger 类型的数据 | a 'BigInteger' type data
     * @return byte[] 长度为 32 的 byte 数组 | a 32-byte 'byte' array
    **/
    public static byte[] byteConvert32Bytes(BigInteger n) {
        byte tmpd[] = (byte[]) null;
        if (n == null) {
            return null;
        }

        // 判断长度是否等于 33，如果等于，则需要去掉头部的 0，只保留后面的 32 个字节
        // Determine if the length is 33. If it is, remove the leading 0 and keep only the last 32 bytes
        if (n.toByteArray().length == 33) {
            tmpd = new byte[32];
            System.arraycopy(n.toByteArray(), 1, tmpd, 0, 32);

        // 判断长度是否等于 32，如果等于，则直接将 n 转换为 byte 数组
        // Determine if the length is 32. If it is, directly convert 'n' to a byte array
        } else if (n.toByteArray().length == 32) {
            tmpd = n.toByteArray();

        // 如果长度既不等于 33 ，也不等于 32，则需要进行填充操作
        // If the length is neither 33 nor 32, padding is required.
        } else {
            tmpd = new byte[32];
            for (int i = 0; i < 32 - n.toByteArray().length; i++) {
                tmpd[i] = 0;
            }
            System.arraycopy(n.toByteArray(), 0, tmpd, 32 - n.toByteArray().length, n.toByteArray().length);
        }
        return tmpd;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将长度为 b.length 的 byte 数组转换为 BigInteger 类型的数据
     *               Convert a byte array 'b' with a length of 'b.length' to a 'BigInteger' type data
     *
     *               该代码用于将一个长度为 b.length 的 byte 数组转换为一个 BigInteger 类型的数据。
     *               具体实现是：
     *               1. 判断 byte 数组中第一个字节（即最高位）是否为负数。如果是负数，则需要在前面补一个 0 进行符号扩展，
     *               再将其转换为对应的 BigInteger 类型数据。
     *               2. 如果第一个字节不是负数，则将 byte 数组直接转换成 BigInteger 类型数据。
     *               3. 最后返回这个 BigInteger 类型数据。
     *
     * -------------------------------------------------------------------------------------------------------------------------丨
     *               This code converts a byte array of length b.length to data of type big integer.                            丨
     *               The specific implementation is:                                                                            丨
     *               1. Determine whether the first byte (that is, the highest bit) in the byte array is negative.              丨
     *               If it is negative, it needs to be preceded by a 0 for symbolic extension,                                  丨
     *               Then convert it to the corresponding big integer type data.                                                丨
     *               2. If the first byte is not negative, the byte array is directly converted to big integer data.            丨
     *               3. Finally return the big integer data.                                                                    丨
     * -------------------------------------------------------------------------------------------------------------------------丨
     * @param b 长度为 b.length 的 byte 数组 | a byte array 'b' with a length of 'b.length'
     * @return BigInteger 类型的数据 | a 'BigInteger' type data
    **/
    public static BigInteger byteConvertInteger(byte[] b) {
        if (b[0] < 0) {
            // 如果 b 的最高位为 1，则需要进行符号扩展
            // If the most significant bit of 'b' is 1, sign extension is required
            byte[] temp = new byte[b.length + 1];
            temp[0] = 0;
            System.arraycopy(b, 0, temp, 1, b.length);
            return new BigInteger(temp);
        }

        // 如果 b 的最高位为 0，则可以直接将其转换为 BigInteger 类型的数据
        // If the most significant bit of 'b' is 0, 'b' can be converted to 'BigInteger' type data directly
        return new BigInteger(b);
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将 byte 数组转换为十六进制字符串（默认将字符转换为大写）
     *               Convert a byte array to a hexadecimal string (convert characters to uppercase by default)
     *
     *               该方法是 getHexString 方法的一个重载，只需要传入一个 byte 数组即可。
     *               该方法的实现只是调用了原来的 getHexString(byte[] bytes, boolean upperCase) 方法，
     *               将 upperCase 参数设为 true，即默认将结果字符串中的所有字符转换成大写字母，然后将结果字符串返回。
     *  -------------------------------------------------------------------------------------------------------------------------丨
     *               This method is an overloading of the get hex string method and only requires passing in a byte array.       丨
     *               The implementation of this method simply calls the original get                                             丨
     *               hex string(byte[] bytes, boolean upper case) method,                                                        丨
     *               Sets the upper case argument to true, that is, all characters in the result string are converted            丨
     *               to uppercase by default, and the result string is returned.                                                 丨
     *  -------------------------------------------------------------------------------------------------------------------------丨
     * @param bytes byte 数组 | a byte array
     * @return 十六进制字符串 | a hexadecimal string
     **/
    public static String getHexString(byte[] bytes) {
        // 默认将字符转换为大写
        // Convert all characters to uppercase by default
        return getHexString(bytes, true);
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将 byte 数组转换为十六进制字符串
     *               Convert a byte array to a hexadecimal string
     *
     *               该方法用于将给定的 byte 数组转换为一个十六进制字符串。
     *               具体实现：
     *               1. 遍历给定的 byte 数组，对于数组中的每个字节，使用位运算符和与操作将其转换为一个两位十六进制数，并将其添加到结果字符串 ret 中。
     *               2. 最后判断 upperCase 参数是否为 true，如果是，则将 ret 中的字符全部转换成大写字母，并返回这个十六进制字符串。
     *
     * -------------------------------------------------------------------------------------------------------------------------丨
     *               This method is used to convert the given byte array to a hexadecimal string.                               丨
     *               Specific implementation:                                                                                   丨
     *               1. Traverse the given byte array and, for each byte in the array, convert it to a two-digit                丨
     *               hexadecimal number using the bit operator and and operation, and add it to the resulting string ret.       丨
     *               2. Finally determine whether the upper case parameter is true. If yes, convert all characters in ret       丨
     *               to uppercase letters and return the hexadecimal string.                                                    丨
     * -------------------------------------------------------------------------------------------------------------------------丨
     * @param bytes byte 数组 | a byte array
     * @param upperCase 是否将字符转换为大写 | whether to convert characters to uppercase
     * @return 十六进制字符串 | a hexadecimal string
     **/
    public static String getHexString(byte[] bytes, boolean upperCase) {
        String ret = "";
        for (int i = 0; i < bytes.length; i++) {
            ret += Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1);
        }
        return upperCase ? ret.toUpperCase() : ret;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将给定的 byte 数组按照十六进制格式打印出来
     *               Print the given byte array in hexadecimal format
     *
     *               该方法用于将给定的 byte 数组按照十六进制格式打印出来。
     *               具体实现是：
     *               1. 遍历给定的 byte 数组，对于数组中的每一个元素，使用 Integer.toHexString 方法将其转换为十六进制格式的字符串，
     *               并按照 “0xXX,” 的格式打印在控制台上（其中 “XX” 是一个两位的十六进制数），最后打印一个空行。
     *               2. 注意，如果转换得到的字符串长度为 1，则需要在字符串前面补一个 0，以保证结果字符串的长度为 2。
     *
     * -------------------------------------------------------------------------------------------------------------------------丨
     *               This method is used to print out the given byte array in hexadecimal format.                               丨
     *               The specific implementation is:                                                                            丨
     *               1. Go through the given byte array, and for each element in the array, convert it to                       丨
     *               a hexadecimal string using the integ.to hex string method.                                                 丨
     *               and print in the format "0x x x," on the                                                                   丨
     *               console (where "x x" is a two-digit hexadecimal number), and finally print a blank line.                   丨
     *               2. Note that if the length of the converted string is 1, a 0 needs to be added before the string           丨
     *               to ensure that the length of the resulting string is 2.                                                    丨
     * -------------------------------------------------------------------------------------------------------------------------丨
     * @param bytes 给定的 byte 数组 | the given byte array
     **/
    public static void printHexString(byte[] bytes) {
        // 将数组中的每一个元素转换为十六进制格式，并打印出来
        // Convert each element in the array to hexadecimal format and print it out
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print("0x" + hex.toUpperCase() + ",");
        }
        System.out.println("");
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将给定的十六进制字符串转换为 byte 数组
     *               Convert the given hexadecimal string to a byte array
     *
     *               该方法用于将给定的十六进制字符串转换为一个 byte 数组。
     *               具体实现是：
     *               1. 首先判断要转换的字符串是否为空或者其长度是否为 0，如果是，则返回 null。
     *               2. 然后将字符串中的小写字母全部转换为大写字母，然后根据字符串的长度计算出最终 byte 数组的长度。
     *               3. 接着遍历字符串中的每一对（两位）字符，将其转换为对应的一个 byte 数组元素。
     *               在转换过程中，对于每一对字符，先使用 charToByte 方法将其转换为对应的 byte 值，
     *               4. 然后使用位运算符进行移位和或运算，最终得到要转换的 byte 数组的一个元素。最后返回转换后的 byte 数组。
     *
     * -------------------------------------------------------------------------------------------------------------------------丨
     *               This method converts the given hexadecimal string to a byte array.                                         丨
     *               The specific implementation is:                                                                            丨
     *               1. First determine whether the string to be converted is empty or its length is 0, if so, return null.     丨
     *               2. Then convert all lowercase letters in the string to uppercase letters, and calculate the length         丨
     *               of the final byte array based on the length of the string.                                                 丨
     *               3. Then iterate over each pair (two bits) of characters in the string, converting it to                    丨
     *               a corresponding byte array element.                                                                        丨
     *               In the conversion process, for each pair of characters, the char to byte method is first used              丨
     *               to convert it to the corresponding byte value.                                                             丨
     *               4. The bit operator is then used for the shift and or operation, resulting in an element of                丨
     *               the byte array to be converted. Finally, the converted byte array is returned.                             丨
     * -------------------------------------------------------------------------------------------------------------------------丨
     * @param hexString 要转换的十六进制字符串 | the hexadecimal string to be converted
     * @return 转换后的 byte 数组 | the resulting byte array
     **/
    public static byte[] hexStringToBytes(String hexString) {
        // 如果要转换的字符串为空或者长度为 0，则返回 null
        // Return null if the string to be converted is empty or its length is 0
        if (hexString == null || hexString.equals("")) {
            return null;
        }

        // 将要转换的字符串全都转换成大写字母
        // Convert all characters in the string to uppercase
        hexString = hexString.toUpperCase();

        // 用于存储转换后的 byte 数组
        // A byte array used to store the converted result
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];

        // 遍历十六进制字符串中的每一对字符，将其转换为相应的 byte 数组元素
        // Traverse each pair of characters in the hexadecimal string and convert them to the corresponding byte array elements
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将给定的字符转换为一个 byte 值
     *               Convert the given character to a byte value
     *
     *               该方法用于将一个给定的字符转换为一个 byte 值。
     *               具体实现是：
     *               1. 将当前字符在字符集 "0123456789ABCDEF" 中的索引作为 byte 值返回。
     *               由于字符在 Java 中占两个字节，在进行强制类型转换后将高 8 位截去，只保留低 8 位，最终得到要转换的 byte 值。
     *
     * -------------------------------------------------------------------------------------------------------------------------丨
     *               This method converts a given character to a byte value.                                                    丨
     *               The specific implementation is:                                                                            丨
     *               1. Return the index of the current character in the character set "0123456789ABCDEF" as the byte value.    丨
     *               Since characters take up two bytes in java, the high 8 bits are truncated after casting, leaving only      丨
     *               the low 8 bits, and finally the byte value to be converted.                                                丨
     * -------------------------------------------------------------------------------------------------------------------------丨
     * @param c 要转换的字符 | the character to be converted
     * @return 转换后得到的 byte 值 | the resulting byte value
     **/
    public static byte charToByte(char c) {
        // 从 "0123456789ABCDEF" 字符串中找到给定字符的位置（索引），并将其返回
        // Find the index of the given character in the "0123456789ABCDEF" string and return it as a byte value
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 十六进制字符集，包含 '0' 到 '9' 和 'a' 到 'f' 的所有字符
     *
     * -----------------------------------------------------------------------------------------------丨
     * A hexadecimal character set that includes all characters from '0' to '9' and 'a' to 'f'        丨
     * -----------------------------------------------------------------------------------------------丨
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 十六进制字符集，包含 '0' 到 '9' 和 'A' 到 'F' 的所有字符
     *
     * -----------------------------------------------------------------------------------------------丨
     * A hexadecimal character set that includes all characters from '0' to '9' and 'A' to 'F'        丨
     * -----------------------------------------------------------------------------------------------丨
     */
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将给定的 byte 数组转换为十六进制字符串，使用小写字母字符集
     *               Convert the given byte array to a hexadecimal string using lowercase characters
     *
     *               该方法用于将给定的 byte 数组转换为其对应的十六进制字符串，并使用出现频率较高的小写字母字符集。
     *               具体实现是：
     *               调用带有两个参数的 encodeHex 方法，并将参数 toLowerCase 设置为 true，
     *               使用小写字母字符集进行转换。然后将转换后的字符数组作为返回值返回。
     *
     * ----------------------------------------------------------------------------------------------------------丨
     *               This method is used to convert a given byte array to its corresponding hexadecimal string,  丨
     *               using the lower-case character set that occurs more frequently.                             丨
     *               The specific implementation is:                                                             丨
     *               Call the encode hex method with two arguments and set the argument to lower case to true,   丨
     *               Convert using the lower-case character set. The converted character array is then returned  丨
     *               as a return value.                                                                          丨
     * ----------------------------------------------------------------------------------------------------------丨
     * @param data 要转换的 byte 数组 | the byte array to be converted
     * @return 转换后得到的十六进制字符串 | the resulting hexadecimal string
     **/
    public static char[] encodeHex(byte[] data) {
        // 调用带有两个参数的 encodeHex 方法，并将 toLowerCase 参数设置为 true，使用小写字母字符集
        // Call the encodeHex method with two parameters, and set the toLowerCase parameter to true, using the lowercase character set
        return encodeHex(data, true);
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将给定的 byte 数组转换为十六进制字符串，使用给定的字符集（如果为 true，则使用小写字母）
     *               Convert the given byte array to a hexadecimal string using the given characters (if true, use lowercase letters)
     *
     *               该方法用于将给定的 byte 数组转换为其对应的十六进制字符串。
     *               具体实现是：
     *               根据参数 toLowerCase 的值选择要使用的字符集，如果 toLowerCase 为 true，
     *               则使用字符集 DIGITS_LOWER，否则使用字符集 DIGITS_UPPER。
     *               然后调用 encodeHex 方法完成具体的转换工作，并将转换后的字符数组作为返回值返回。
     *
     * -----------------------------------------------------------------------------------------------------------------------------丨
     *               This method is used to convert a given byte array to its corresponding hexadecimal string.                     丨
     *               The specific implementation is:                                                                                丨
     *               Select the character set to use according to the value of the to lower case argument. If to lower case is true,丨
     *               The character set DIGITS_LOWER is used, otherwise the character set DIGITS_UPPER is used.                      丨
     *               The encode hex method is then called to complete the conversion work, and the converted character array        丨
     *               is returned as the return value.                                                                               丨
     * -----------------------------------------------------------------------------------------------------------------------------丨
     * @param data         要转换的 byte 数组 | the byte array to be converted
     * @param toLowerCase 是否使用小写字母作为十六进制数字 | whether to use lowercase letters for hexadecimal digits
     * @return 转换后得到的十六进制字符串 | the resulting hexadecimal string
     **/
    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        // 如果 toLowerCase 为true，将字母字符集设置为小写字母字符集 DIGITS_LOWER；否则设置为大写字母字符集 DIGITS_UPPER
        // If toLowerCase is true, set the character set to the lowercase character set DIGITS_LOWER; otherwise, set it to the uppercase character set DIGITS_UPPER
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将给定的 byte 数组转换为十六进制字符串，使用给定的字符集
     *
     *               该方法用于将给定的 byte 数组转换为其对应的十六进制字符串，并使用给定的字符集作为字符集合。
     *               具体实现是：
     *               先创建一个长度为原始 byte 数组长度的两倍的字符数组 out，用于保存转换后的结果。
     *               然后遍历原始 byte 数组，每次取出数组中的一个元素 data[i]，将其高 4 位和低 4 位分别转换为相应的十六进制字符，
     *               然后依次存储到字符数组 out 中。具体地，对于原始 byte 数组中的第 i 个元素，将其高 4 位与 0xF0 进行与运算，得到一个值，
     *               再将其右移 4 个单位，得到一个数值在 0 至 15 之间的整数，作为字符集 toDigits 中的一个索引，将其对应的字符放在 out 数组的第 j 个位置上。
     *               同理，取 data[i] 的低 4 位，将其与 0x0F 进行与运算后得到一个值，再将该值作为字符集 toDigits 中的另一个索引，
     *               将其对应的字符放在 out 数组的第 j+1 个位置上。最后返回转换后的字符数组 out。
     *
     * -----------------------------------------------------------------------------------------------------------------------------丨
     *               This method is used to convert a given byte array to its corresponding hexadecimal string,                     丨
     *               using the given character set as a set of characters.                                                          丨
     *               The specific implementation is:                                                                                丨
     *               First create a character array out twice the length of the original byte array to hold the converted result.   丨
     *               Then iterate through the original byte array, retrieving one element of the array data[i] ata time,            丨
     *               converting its high 4 bits and low 4 bits to the corresponding hexadecimal characters,                         丨
     *               Then store in sequence into the character array out. Specifically, for the I-th element in the original        丨
     *               byte array, the higher 4 bits and 0x f0 are combined to obtain a value,                                        丨
     *               Shift it 4 units to the right to get an integer between 0 and 15, which is used as an index in the             丨
     *               to digits character set, and its corresponding character is placed in the JTH position of the out array.       丨
     *               Similarly, take the lower 4 bits of data[i], sum it with 0x0f to get a value, and use that value as another    丨
     *               index in the character set to digits.                                                                          丨
     *               Puts its corresponding character in the j+1 position of the out array. Finally, the converted                  丨
     *               character array out is returned.                                                                               丨
     * -----------------------------------------------------------------------------------------------------------------------------丨
     * @param data     要转换的 byte 数组 | the byte array to be converted
     * @param toDigits 保存转换后的十六进制字符的字符数组 | the character array that contains the resulting hexadecimal characters
     * @return 转换后得到的十六进制字符串 | the resulting hexadecimal string
     **/
    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];

        // 除以 16 和模 16 得到十六进制字符的前四位和后四位
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将字节数组转换为十六进制字符串
     *
     *
     * @param data byte[]
     * @return 十六进制String
     **/
    public static String encodeHexString(byte[] data) {
        return encodeHexString(data, true);
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将字节数组转换为十六进制字符串
     *
     *
     * @param data        byte[]
     * @param toLowerCase <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
     * @return 十六进制String
     **/
    public static String encodeHexString(byte[] data, boolean toLowerCase) {
        return encodeHexString(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将字节数组转换为十六进制字符串
     *
     *
     * @param data     byte[]
     * @param toDigits 用于控制输出的char[]
     * @return 十六进制String
     **/
    protected static String encodeHexString(byte[] data, char[] toDigits) {
        return new String(encodeHex(data, toDigits));
    }

    /**
     *
     *

     */
    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将十六进制字符数组转换为字节数组
     *
     *
     * @param data 十六进制char[]
     * @return byte[]
     * @throws RuntimeException 如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
     **/
    public static byte[] decodeHex(char[] data) {
        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将十六进制字符转换成一个整数
     *
     *
     * @param ch    十六进制char
     * @param index 十六进制字符在字符数组中的位置
     * @return 一个整数
     * @throws RuntimeException 当ch不是一个合法的十六进制字符时，抛出运行时异常
     **/
    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch
                    + " at index " + index);
        }
        return digit;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 数字字符串转ASCII码字符串
     *
     *
     * @param content 字符串
     * @return ASCII字符串
     **/
    public static String StringToAsciiString(String content) {
        String result = "";
        int max = content.length();
        for (int i = 0; i < max; i++) {
            char c = content.charAt(i);
            String b = Integer.toHexString(c);
            result = result + b;
        }
        return result;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 十六进制转字符串
     *
     *
     * @param hexString  十六进制字符串
     * @param encodeType 编码类型4：Unicode，2：普通编码
     * @return 字符串
     **/
    public static String hexStringToString(String hexString, int encodeType) {
        String result = "";
        int max = hexString.length() / encodeType;
        for (int i = 0; i < max; i++) {
            char c = (char) hexStringToAlgorism(hexString
                    .substring(i * encodeType, (i + 1) * encodeType));
            result += c;
        }
        return result;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 十六进制字符串装十进制
     *
     *
     * @param hex 十六进制字符串
     * @return 十进制数值
     **/
    public static int hexStringToAlgorism(String hex) {
        hex = hex.toUpperCase();
        int max = hex.length();
        int result = 0;
        for (int i = max; i > 0; i--) {
            char c = hex.charAt(i - 1);
            int algorism = 0;
            if (c >= '0' && c <= '9') {
                algorism = c - '0';
            } else {
                algorism = c - 55;
            }
            result += Math.pow(16, max - i) * algorism;
        }
        return result;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 十六转二进制
     *
     *
     * @param hex 十六进制字符串
     * @return 二进制字符串
     **/
    public static String hexStringToBinary(String hex) {
        hex = hex.toUpperCase();
        String result = "";
        int max = hex.length();
        for (int i = 0; i < max; i++) {
            char c = hex.charAt(i);
            switch (c) {
                case '0':
                    result += "0000";
                    break;
                case '1':
                    result += "0001";
                    break;
                case '2':
                    result += "0010";
                    break;
                case '3':
                    result += "0011";
                    break;
                case '4':
                    result += "0100";
                    break;
                case '5':
                    result += "0101";
                    break;
                case '6':
                    result += "0110";
                    break;
                case '7':
                    result += "0111";
                    break;
                case '8':
                    result += "1000";
                    break;
                case '9':
                    result += "1001";
                    break;
                case 'A':
                    result += "1010";
                    break;
                case 'B':
                    result += "1011";
                    break;
                case 'C':
                    result += "1100";
                    break;
                case 'D':
                    result += "1101";
                    break;
                case 'E':
                    result += "1110";
                    break;
                case 'F':
                    result += "1111";
                    break;
            }
        }
        return result;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: ASCII码字符串转数字字符串
     *
     *
     * @param content ASCII字符串
     * @return 字符串
     **/
    public static String AsciiStringToString(String content) {
        String result = "";
        int length = content.length() / 2;
        for (int i = 0; i < length; i++) {
            String c = content.substring(i * 2, i * 2 + 2);
            int a = hexStringToAlgorism(c);
            char b = (char) a;
            String d = String.valueOf(b);
            result += d;
        }
        return result;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将十进制转换为指定长度的十六进制字符串
     *
     *
     * @param algorism  int 十进制数字
     * @param maxLength int 转换后的十六进制字符串长度
     * @return String 转换后的十六进制字符串
     **/
    public static String algorismToHexString(int algorism, int maxLength) {
        String result = "";
        result = Integer.toHexString(algorism);

        if (result.length() % 2 == 1) {
            result = "0" + result;
        }
        return patchHexString(result.toUpperCase(), maxLength);
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 字节数组转为普通字符串（ASCII对应的字符）
     *
     *
     * @param bytearray byte[]
     * @return String
     **/
    public static String byteToString(byte[] bytearray) {
        String result = "";
        char temp;

        int length = bytearray.length;
        for (int i = 0; i < length; i++) {
            temp = (char) bytearray[i];
            result += temp;
        }
        return result;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 二进制字符串转十进制
     *
     *
     * @param binary 二进制字符串
     * @return 十进制数值
     **/
    public static int binaryToAlgorism(String binary) {
        int max = binary.length();
        int result = 0;
        for (int i = max; i > 0; i--) {
            char c = binary.charAt(i - 1);
            int algorism = c - '0';
            result += Math.pow(2, max - i) * algorism;
        }
        return result;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 十进制转换为十六进制字符串
     *
     *
     * @param algorism int 十进制的数字
     * @return String 对应的十六进制字符串
     **/
    public static String algorismToHEXString(int algorism) {
        String result = "";
        result = Integer.toHexString(algorism);

        if (result.length() % 2 == 1) {
            result = "0" + result;

        }
        result = result.toUpperCase();

        return result;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: HEX字符串前补0，主要用于长度位数不足。
     *
     *
     * @param str       String 需要补充长度的十六进制字符串
     * @param maxLength int 补充后十六进制字符串的长度
     * @return 补充结果
     **/
    static public String patchHexString(String str, int maxLength) {
        String temp = "";
        for (int i = 0; i < maxLength - str.length(); i++) {
            temp = "0" + temp;
        }
        str = (temp + str).substring(0, maxLength);
        return str;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将一个字符串转换为int
     *
     *
     * @param s          String 要转换的字符串
     * @param defaultInt int 如果出现异常,默认返回的数字
     * @param radix      int 要转换的字符串是什么进制的,如16 8 10.
     * @return int 转换后的数字
     **/
    public static int parseToInt(String s, int defaultInt, int radix) {
        int i = 0;
        try {
            i = Integer.parseInt(s, radix);
        } catch (NumberFormatException ex) {
            i = defaultInt;
        }
        return i;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 将一个十进制形式的数字字符串转换为int
     *
     *
     * @param s          String 要转换的字符串
     * @param defaultInt int 如果出现异常,默认返回的数字
     * @return int 转换后的数字
     **/
    public static int parseToInt(String s, int defaultInt) {
        int i = 0;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            i = defaultInt;
        }
        return i;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 十六进制串转化为byte数组
     *
     * @param hex
     * @return the array of byte
     **/
    public static byte[] hexToByte(String hex)
            throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description: 字节数组转换为十六进制字符串
     *
     *
     * @param b byte[] 需要转换的字节数组
     * @return String 十六进制字符串
     **/
    public static String byteToHex(byte b[]) {
        if (b == null) {
            throw new IllegalArgumentException(
                    "Argument b ( byte array ) is null! ");
        }
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * @author: MuHongXin.
     * @dateTime: 下午5:48 2023/6/19
     * @description:
     *
     *
     * @param input
     * @param startIndex
     * @param length
     * @return java.math.BigInteger
     **/
    public static byte[] subByte(byte[] input, int startIndex, int length) {
        byte[] bt = new byte[length];
        for (int i = 0; i < length; i++) {
            bt[i] = input[i + startIndex];
        }
        return bt;
    }
}
