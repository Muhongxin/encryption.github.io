package com.mhx.encryption.util.sm;



import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * @author MuHongXin.
 * @Description: SM2 Cipher
 * @data 2022/8/17 下午4:29
 */

public class Cipher {
    /**
     * 定义整型变量 ct
     *
     * ---------------------------------丨
     * Define an integer variable 'ct'  丨
     * ---------------------------------丨
     */
    private int ct;

    /**
     * 定义椭圆曲线上的点 p2
     *
     * -------------------------------------------丨
     * Define a point 'p2' on the elliptic curve  丨
     * -------------------------------------------丨
     */
    private ECPoint p2;

    /**
     * 定义 SM3 摘要算法对象 sm3keybase
     *
     * ----------------------------------------------------丨
     * Define an SM3 digest algorithm object 'sm3keybase'  丨
     * ----------------------------------------------------丨
     */
    private SM3Digest sm3keybase;

    /**
     * 定义 SM3 摘要算法对象 sm3c3
     *
     * -----------------------------------------------丨
     * Define an SM3 digest algorithm object 'sm3c3'  丨
     * -----------------------------------------------丨
     */
    private SM3Digest sm3c3;

    /**
     * 定义字节数组 key
     *
     * ---------------------------丨
     * Define a byte array 'key'  丨
     * ---------------------------丨
     */
    private byte key[];

    /**
     * 定义字节型变量 keyOff
     *
     * ---------------------------------丨
     * Define a byte variable 'keyOff'  丨
     * ---------------------------------丨
     */
    private byte keyOff;

    /**
     * @author: MuHongXin.
     * @dateTime:  下午4:43 2022/8/17
     * @description: 该函数是一个构造函数，用于初始化 Cipher 类
     *               This function is a constructor that initializes the cipher class
     *
     *               具体步骤：
     *
     *               1. 将当前数据块数量 ct 设为 1。
     *               2. 创建一个长度为 32 的新字节数组 key，用于存储密钥数据。
     *               3. 将密钥偏移量 keyOff 设为 0，表示当前还未开始使用密钥数组中的任何数据。
     *
     *               该构造函数是 Cipher 类的一个初始状态，当对象被创建时会调用该函数，然后会通过其他方法逐渐更新对象的状态，
     *               比如设置密钥、加密数据、重置状态等等。
     *
     * -------------------------------------------------------------------------------------------------------------------------丨
     *               Specific steps:                                                                                            丨
     *                                                                                                                          丨
     *               1. Set the current number of data blocks ct to 1.                                                          丨
     *               2. Creates a new byte array key of length 32 to store the key data.                                        丨
     *               3. Setting the key offset key off to 0 indicates that no data in the key array is currently being used.    丨
     *                                                                                                                          丨
     *               This constructor is an initial state of the cipher class that is called when the object is created,        丨
     *               and then gradually updates the state of the object through other methods, such as setting the key,         丨
     *               encrypting data, resetting the state, and so on.                                                           丨
     * -------------------------------------------------------------------------------------------------------------------------丨
    **/
    public Cipher() {
        // 将 ct 设为 1，表示当前数据块数量为 1 | Set 'ct' to 1, indicating the number of current data blocks is 1
        this.ct = 1;

        // 创建长度为 32 的新字节数组 key | Create a new byte array 'key' with length 32
        this.key = new byte[32];

        // 将 keyOff 设为 0，表示当前密钥偏移量为 0 | Set 'keyOff' to 0, indicating the current key offset is 0
        this.keyOff = 0;
    }

    /**
     * @author: MuHongXin.
     * @dateTime:  下午5:13 2022/8/17
     * @description: 该函数用于重置 Cipher 类的状态
     *               This function is used to reset the cipher class status
     *
     *               具体步骤：
     *
     *               1. 初始化 SM3Digest 对象 sm3keybase 和 sm3c3。
     *               2. 获取椭圆曲线上点 p2 的横坐标，并转换为长度为 32 的字节数组，然后更新到 sm3keybase 和 sm3c3 对象中。
     *               3. 获取椭圆曲线上点 p2 的纵坐标，并转换为长度为 32 的字节数组，然后更新到 sm3keybase 对象中。
     *               4. 将当前数据块数量 ct 设为 1。
     *               5. 调用 nextKey 方法，生成加密密钥。
     *
     *               该函数是 Cipher 类的一个重置状态，用于在切换密钥或加密数据块时重置 Cipher 对象的状态，以便下一次加密或解密操作能够顺利进行。
     *               在重置状态的过程中，还会更新 SM3 摘要状态和生成下一个加密密钥。
     *
     * -------------------------------------------------------------------------------------------------------------------------丨
     *               Specific steps:                                                                                            丨
     *                                                                                                                          丨
     *               1. Example Initialize m3 digest objects sm3keybase and sm3c3.                                              丨
     *               2. Gets the horizontal coordinate of point p2 on the elliptic curve, converts it to a byte array           丨
     *               of length 32, and then updates it to sm3keybase and sm3c3 objects.                                         丨
     *               3. Gets the ordinate of point p2 on the elliptic curve, converts it to a byte array of length 32,          丨
     *               and then updates it into the sm3keybase object.                                                            丨
     *               4. Set the current number of data blocks ct to 1.                                                          丨
     *               5. Invoke the next key method to generate the encryption key.                                              丨
     *                                                                                                                          丨
     *               This function is a reset status of the cipher class. It is used to reset the status of the                 丨
     *               cipher object when switching keys or encrypting data blocks, so that the next encryption or                丨
     *               decryption operation can proceed smoothly. During the state reset process, the m3 summary                  丨
     *               state is also updated and the next encryption key is generated.                                            丨
     * -------------------------------------------------------------------------------------------------------------------------丨
    **/
    private void reset() {
        // 初始化 sm3keybase 和 sm3c3 对象 | Initialize the 'sm3keybase' and 'sm3c3' objects
        this.sm3keybase = new SM3Digest();
        this.sm3c3 = new SM3Digest();

        // 获取椭圆曲线上点 p2 的横坐标，并转换为长度为 32 的字节数组，然后更新到 sm3keybase 和 sm3c3 对象中 | Get the x-coordinate of point p2 on the elliptic curve, convert it to a byte array with length 32, and then update it to the 'sm3keybase' and 'sm3c3' objects
        byte p[] = DataConversionUtils.byteConvert32Bytes(p2.getX().toBigInteger());
        this.sm3keybase.update(p, 0, p.length);
        this.sm3c3.update(p, 0, p.length);

        // 获取椭圆曲线上点 p2 的纵坐标，并转换为长度为 32 的字节数组，然后更新到 sm3keybase 对象中 | Get the y-coordinate of point p2 on the elliptic curve, convert it to a byte array with length 32, and then update it to the 'sm3keybase' object
        p = DataConversionUtils.byteConvert32Bytes(p2.getY().toBigInteger());
        this.sm3keybase.update(p, 0, p.length);

        // 将 ct 设为 1，表示当前数据块数量为 1 | Set 'ct' to 1, indicating the number of current data blocks is 1
        this.ct = 1;

        // 调用 nextKey 方法，生成加密密钥 | Call the 'nextKey' method to generate the encryption key
        nextKey();
    }

    /**
     * @author: MuHongXin.
     * @dateTime:  下午6:33 2022/8/17
     * @description: 生成SM3密钥
     *               Generate m3 key
     *
     *               具体步骤：
     *
     *               1. 基于 sm3keybase 创建一个 SM3Digest 对象 sm3Digest。
     *               2. 将当前数据块数量 ct 转换为四个字节，依次将其更新到 SM3Digest 对象 sm3Digest 中。
     *               3. 调用 SM3Digest 对象的 doFinal 方法，计算 SM3 摘要值，并将结果存储在长度为 32 的字节数组 key 中。
     *               4. 将密钥偏移量 keyOff 设为 0，表示从 key 数组的起始位置开始使用密钥。
     *               5. 将当前数据块数量 ct 自增 1，以便下次生成新的密钥。
     *
     * -------------------------------------------------------------------------------------------------------------------------丨
     *               Specific steps:                                                                                            丨
     *                                                                                                                          丨
     *               1. Create an S3 digest object sm3 digest based on sm3keybase.                                              丨
     *               2. Convert the current number of data blocks ct into four bytes and update them to the m3 digest           丨
     *               object sm3 digest in sequence.                                                                             丨
     *               3. Call the do final method of the m3 digest object to calculate the m3 digest value and store the         丨
     *               result in the 32 byte array key.                                                                           丨
     *               4. Set key offset key off to 0, indicating that the key is used from the start position of the key array.  丨
     *               5. Increase the number of current data blocks ct by 1, so that a new key can be generated next time.       丨
     * -------------------------------------------------------------------------------------------------------------------------丨
    **/
    private void nextKey() {
        // 基于 sm3keybase 创建一个 SM3Digest 对象 | Create an SM3Digest object 'sm3Digest' based on 'sm3keybase'
        SM3Digest sm3Digest = new SM3Digest(this.sm3keybase);

        // 将 ct 转换为四个字节，依次更新到 sm3Digest 对象中 | Convert 'ct' to four bytes and update them to 'sm3Digest' object one by one
        sm3Digest.update((byte) (ct >> 24 & 0xff));
        sm3Digest.update((byte) (ct >> 16 & 0xff));
        sm3Digest.update((byte) (ct >> 8 & 0xff));
        sm3Digest.update((byte) (ct & 0xff));

        // 计算 SM3 摘要结果，结果存储在 key 数组中 | Calculate the SM3 digest result and store it in the 'key' array
        sm3Digest.doFinal(key, 0);

        // 将密钥偏移量 keyOff 设为 0 | Set the key offset 'keyOff' to 0
        this.keyOff = 0;

        // 将当前数据块数量 ct 自增 1 | Increase the current data block count 'ct' by 1
        this.ct++;
    }

    /**
     * @author: MuHongXin.
     * @dateTime:  上午8:47 2022/8/18
     * @description: 初始化 SM2 加密过程中的加密参数和状态
     *               Initializes the encryption parameters and status of m2 encryption
     *
     *               具体步骤：
     *               1. 调用传入的 SM2 对象的 eccKeyPairGenerator 属性生成一对随机生成的秘钥对，
     *               并将其转换为 ECPrivateKeyParameters 和 ECPublicKeyParameters 对象。
     *               2. 分别获取私钥和公钥点 c1。
     *               3. 计算 SM2 加密参数 p2。具体计算方式为将用户公钥 userKey 与私钥 d 做乘法运算，即 p2 = userKey * d。
     *               4. 重置系统状态。具体是将缓存区、密钥偏移量和当前数据块数量 ct 等变量均设置为 0。
     *               5. 返回公钥点 c1。
     *
     *               需要注意的是，该函数需要传入一个 SM2 对象和一个 ECPoint 对象 userKey，
     *               为了确保加密安全，userKey 的值需要是用户的公钥。
     *
     * -------------------------------------------------------------------------------------------------------------------------丨
     *               Specific steps:                                                                                            丨
     *               1. Call the ecc key pair generator property of the passed m2 object to generate a randomly                 丨
     *               generated secret key pair. and convert it to c private key parameters and c public key parameters objects. 丨
     *               2. Obtain the private key and public key point c1 respectively.                                            丨
     *               3. Calculate m2 encryption parameter p2. The calculation method is to multiply the user public key and     丨
     *               private key d, that is, p2 = user key * d.                                                                 丨
     *               4. Reset the system status. Specifically, the variables such as cache area, key offset and the number      丨
     *               of current data blocks ct are set to 0.                                                                    丨
     *               5. Return to public key point c1.                                                                          丨
     *                                                                                                                          丨
     *               Note that this function needs to pass in an m2 object and an ec point object user key,                     丨
     *               To ensure encryption security, the value of the user key needs to be the user's public key.                丨
     * -------------------------------------------------------------------------------------------------------------------------丨
     *
     * @param sm2 SM2 对象 | SM2 Object
     * @param userKey 用户公钥点 | User public key point
     * @return org.bouncycastle.math.ec.ECPoint
    **/
    public ECPoint initEnc(SM2 sm2, ECPoint userKey) {
        // 调用 sm2 的 eccKeyPairGenerator 生成一对秘钥 | Use the eccKeyPairGenerator of 'sm2' to generate a key pair
        AsymmetricCipherKeyPair key = sm2.eccKeyPairGenerator.generateKeyPair();

        // 将生成的秘钥转换为 ECPrivateKeyParameters 和 ECPublicKeyParameters 对象 | Convert the generated key pair to 'ECPrivateKeyParameters' and 'ECPublicKeyParameters' objects
        ECPrivateKeyParameters ecPrivateKeyParameters = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters ecPublicKeyParameters = (ECPublicKeyParameters) key.getPublic();

        // 获取私钥 d 和公钥点 c1 | Get the private key 'd' and the public key point 'c1'
        BigInteger k = ecPrivateKeyParameters.getD();
        ECPoint c1 = ecPublicKeyParameters.getQ();

        // 计算 SM2 加密参数 p2 | Calculate the SM2 encryption parameter 'p2'
        this.p2 = userKey.multiply(k);

        // 重置系统状态 | Reset the system status
        reset();

        // 返回公钥点 c1 | Return the public key point 'c1'
        return c1;
    }

    /**
     * @author: MuHongXin.
     * @dateTime:  上午9:32 2022/8/18
     * @description:  对数据进行加密的公开方法
     *                A public method of encrypting data
     *
     *                具体步骤：
     *                1. 将数据更新到 SM3Digest 对象 sm3c3 中，以供后续计算 SM3 摘要值使用。
     *                2. 遍历加密数据的每个字节，对每个字节进行加密操作。
     *                3. 如果密钥偏移量 keyOff 等于密钥长度 key.length，使用 nextKey 方法生成下一个密钥。
     *                4. 将当前字节与密钥中对应字节进行异或运算。
     *                5. 如果密钥偏移量 keyOff 等于密钥长度 key.length，使用 nextKey 方法生成下一个密钥。
     *
     *                需要注意的是，加密操作中使用到的密钥是通过调用 nextKey 函数生成的，并非固定的。此外，该函数也会更新 SM3 摘要值和状态。
     *
     * -----------------------------------------------------------------------------------------------------------------------------丨
     *                Specific steps:                                                                                               丨
     *                1. Update the data to the m3 digest object sm3c3 for subsequent calculation of the m3 digest value.           丨
     *                2. Iterate over each byte of the encrypted data and encrypt each byte.                                        丨
     *                3. If key offset key off is equal to key length key.length, use the next key method to generate the next key. 丨
     *                4. Perform the XOR operation between the current byte and the corresponding byte in the key.                  丨
     *                5. If key offset key off is equal to key length key.length, use the next key method to generate the next key. 丨
     *                                                                                                                              丨
     *                Note that the key used in the encryption operation is generated by calling the next key function and is not   丨
     *                fixed. In addition, the function also updates the m3 summary value and status.                                丨
     * -----------------------------------------------------------------------------------------------------------------------------丨
     * @param data 明文
     * @return void
    **/
    public void encrypt(byte data[]) {
        // 将数据更新到 sm3c3 对象中 | Update the data to 'sm3c3' object
        this.sm3c3.update(data, 0, data.length);

        // 遍历加密数据的每个字节，进行加密操作 | Traverse each byte of the data to perform encryption operation
        for (int i = 0; i < data.length; i++) {

            // 如果密钥偏移量 keyOff 等于密钥长度 key.length，使用 nextKey 方法生成下一个密钥 | If the key offset 'keyOff' is equal to the key length 'key.length', use the 'nextKey' method to generate the next key
            if (keyOff == key.length) {
                nextKey();
            }

            // 将当前字节与密钥中对应字节进行异或运算 | Use XOR operation to encrypt the current byte with the corresponding byte in the key
            data[i] ^= key[keyOff++];

            // 如果密钥偏移量 keyOff 等于密钥长度 key.length，使用 nextKey 方法生成下一个密钥 | If the key offset 'keyOff' is equal to the key length 'key.length', use the 'nextKey' method to generate the next key
            if (keyOff == key.length) {
                nextKey();
            }
        }
    }

    /**
     * @author: MuHongXin.
     * @dateTime:  上午10:02 2022/8/18
     * @description: SM2 解密过程中的初始化函数
     *               SM2 initialization function during decryption
     *
     *               具体步骤：
     *               1. 根据输入参数 userD 和公钥点 c1 计算 SM2 加密参数 p2。具体运算是将公钥点 c1 与私钥 userD 做乘法运算，即 p2 = c1 * userD。
     *               2. 重置系统状态。具体是将缓存区、密钥偏移量和当前数据块数量 ct 等变量均设置为 0。
     *
     *               需要注意的是，该函数需要传入一个 BigInteger 对象 userD 和一个 ECPoint 对象 c1。
     *               其中，userD 表示解密过程中使用的私钥，而 c1 则表示加密过程中使用的公钥点。调用该函数后，可以进行 SM2 的解密操作。
     *
     * -----------------------------------------------------------------------------------------------------------------------------丨
     *               Specific steps:                                                                                                丨
     *               1. Calculate S2 encryption parameter p2 based on the input parameter user d and the public key point c1.       丨
     *               The specific operation is to multiply the public key point c1 with the private key user d, that is,            丨
     *               p2 = c1 * user d.                                                                                              丨
     *               2. Reset the system status. Specifically, the variables such as cache area,                                    丨
     *               key offset and the number of current data blocks ct are set to 0.                                              丨
     *                                                                                                                              丨
     *               Note that this function needs to pass in a big integer object, user d, and an ec point object, c1.             丨
     *               Where user d represents the private key used in the decryption process and c1 represents                       丨
     *               the public key point used in the encryption process. After calling this function, the decryption               丨
     *               operation of m2 can be performed.                                                                              丨
     * -----------------------------------------------------------------------------------------------------------------------------丨
     * @param userD  私钥
     * @param c1 ECPoint
     * @return void
    **/
    public void initDec(BigInteger userD, ECPoint c1) {
        // 计算 SM2 加密参数 p2 | Calculate the SM2 encryption parameter 'p2'
        this.p2 = c1.multiply(userD);

        // 重置系统状态 | Reset the system status
        reset();
    }

    /**
     * @author: MuHongXin.
     * @dateTime:  上午10:18 2022/8/18
     * @description: 对数据进行解密的公开方法
     *               A public method for decrypting data
     *
     *               具体步骤：
     *               1. 遍历加密数据的每个字节，对每个字节进行解密操作。
     *               2. 如果密钥偏移量 keyOff 等于密钥长度 key.length，使用 nextKey 方法生成下一个密钥。
     *               3. 将当前字节与密钥中对应字节进行异或运算。
     *               4. 如果密钥偏移量 keyOff 等于密钥长度 key.length，使用 nextKey 方法生成下一个密钥。
     *               5. 将解密后的数据更新到 SM3Digest 对象 sm3c3 中，以便后续计算 SM3 摘要值使用。
     *
     *               需要注意的是，解密操作中使用到的密钥是通过调用 nextKey 函数生成的，并非固定的。
     *               此外，该函数还会更新 SM3 摘要值和状态。
     *
     * -----------------------------------------------------------------------------------------------------------------------------丨
     *               Specific steps:                                                                                                丨
     *               1. Iterate over each byte of the encrypted data and decrypt each byte.                                         丨
     *               2. If key offset key off is equal to key length key.length, use the next key method to generate the next key.  丨
     *               3. Perform the XOR operation between the current byte and the corresponding byte in the key.                   丨
     *               4. If key offset key off is equal to key length key.length, use the next key method to generate the next key.  丨
     *               5. Update the decrypted data to the m3 digest object sm3c3 for subsequent calculation of the m3 digest value.  丨
     *                                                                                                                              丨
     *               Note that the key used in the decryption operation is generated by calling the next key                        丨
     *               function and is not fixed.In addition, the function updates the m3 summary value and status.                   丨
     * -----------------------------------------------------------------------------------------------------------------------------丨
     * @param data
    **/
    public void decrypt(byte data[]) {
        // 遍历加密数据的每个字节，进行加密操作 | Traverse each byte of the data to perform encryption operation
        for (int i = 0; i < data.length; i++) {

            // 如果密钥偏移量 keyOff 等于密钥长度 key.length，使用 nextKey 方法生成下一个密钥 | If the key offset 'keyOff' is equal to the key length 'key.length', use the 'nextKey' method to generate the next key
            if (keyOff == key.length) {
                nextKey();
            }

            // 将当前字节与密钥中对应字节进行异或运算 | Use XOR operation to encrypt the current byte with the corresponding byte in the key
            data[i] ^= key[keyOff++];

            // 如果密钥偏移量 keyOff 等于密钥长度 key.length，使用 nextKey 方法生成下一个密钥 | If the key offset 'keyOff' is equal to the key length 'key.length', use the 'nextKey' method to generate the next key
            if (keyOff == key.length) {
                nextKey();
            }
        }

        // 将加密后的数据更新到 sm3c3 对象中 | Update the encrypted data to 'sm3c3' object
        this.sm3c3.update(data, 0, data.length);
    }

    /**
     * @author: MuHongXin.
     * @dateTime:  下午2:44 2022/8/18
     * @description: SM2 加密和解密过程中的最后一步，用于计算 SM3 摘要 c3 和重置系统状态
     *               The final step in the sm2 encryption and decryption process is used to calculate sm3 summary c3 and reset the system state
     *
     *               具体步骤：
     *               1. 将加密参数 p2 的 y 坐标转换为长度为 32 字节的 byte 数组。
     *               2. 将转换后的数组 p 更新到 SM3Digest 对象 sm3c3 中，以便后续计算 SM3 摘要值使用。
     *               3. 计算摘要 c3。具体是调用 SM3Digest 对象的 doFinal 方法，将计算后的摘要值存入传入的 byte 数组 c3 中。
     *               4. 重置系统状态。具体是将缓存区、密钥偏移量和当前数据块数量 ct 等变量均设置为 0。
     *
     *               需要注意的是，调用该函数前，应先进行加密或解密操作，并保证 SM2 加密参数和数据已经更新到 SM3Digest 对象 sm3c3 中。
     *
     * -----------------------------------------------------------------------------------------------------------------------------丨
     *               Specific steps:                                                                                                丨
     *               1. Convert the y coordinate of the encryption parameter p2 into a byte array of 32 bytes.                      丨
     *               2. Update the converted array p to the m3 digest object sm3c3 for subsequent calculation                       丨
     *               of the m3 digest value.                                                                                        丨
     *               3. Calculate summary c3. Specifically, the do final method of the m3 digest object is called to                丨
     *               store the calculated digest value into the incoming byte array c3.                                             丨
     *               4. Reset the system status. Specifically, the variables such as cache area, key offset and the number          丨
     *               of current data blocks ct are set to 0.                                                                        丨
     *                                                                                                                              丨
     *               It should be noted that before calling this function, encrypt or decrypt it first, and ensure that             丨
     *               the m2 encryption parameters and data have been updated to the m3 digest object sm3c3.                         丨
     * -----------------------------------------------------------------------------------------------------------------------------丨
     * @param c3
    **/
    public void doFinal(byte c3[]) {
        // 将加密参数 p2 的 y 坐标转换为长度为 32 字节的 byte 数组 | Convert the 'y' coordinate of encryption parameter 'p2' to a 32-byte byte array
        byte p[] = DataConversionUtils.byteConvert32Bytes(p2.getY().toBigInteger());

        // 将 p 更新到 sm3c3 对象中 | Update 'p' to 'sm3c3' object
        this.sm3c3.update(p, 0, p.length);

        // 计算摘要 c3 | Calculate the digest 'c3'
        this.sm3c3.doFinal(c3, 0);

        // 重置系统状态 | Reset the system status
        reset();
    }
}